// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import java.util.function.Supplier;

import com.ctre.phoenix6.hardware.Pigeon2;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.estimator.PoseEstimator;
import edu.wpi.first.math.estimator.SwerveDrivePoseEstimator;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.SwerveModule;
import frc.robot.tools.LimelightHelpers;
import frc.robot.Constants.DeviceIDs;
import frc.robot.Constants.LimelightConstants;
import frc.robot.Constants.SwerveConstants;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.StructArrayPublisher;

public class SwerveDrive extends SubsystemBase {
  private SwerveModule RightFront_module;
  private SwerveModule LeftFront_module;
  private SwerveModule LeftRear_module;
  private SwerveModule RightRear_module;

  private Pigeon2 gyro;
  private SwerveDriveKinematics m_Kinematics;
  private SwerveDrivePoseEstimator poseEstimator;
  private double pose[];
  
  private NetworkTable table = NetworkTableInstance.getDefault().getTable("Swerve");
  private NetworkTable poseTable = table.getSubTable("Pose");
  private NetworkTableEntry poseXEntry = poseTable.getEntry("X");
  private NetworkTableEntry poseYEntry = poseTable.getEntry("Y");
  private NetworkTableEntry poseRotationEntry = poseTable.getEntry("Rotation");

  private boolean fieldRelative = true;
  private final StructArrayPublisher<SwerveModuleState> publisher;
  private SwerveModuleState[] moduleStates = new SwerveModuleState[] {
    new SwerveModuleState(),
    new SwerveModuleState(),
    new SwerveModuleState(),
    new SwerveModuleState()
  };

  public SwerveDrive() {
    m_Kinematics = new SwerveDriveKinematics(
      SwerveConstants.RightFront_location, 
      SwerveConstants.LeftFront_location, 
      SwerveConstants.LeftRear_location, 
      SwerveConstants.RightRear_location
    );

    this.RightFront_module = new SwerveModule(DeviceIDs.RightFrontDriveMotorID, DeviceIDs.RightFrontTurningMotorID, 
    DeviceIDs.RightFrontEncoderID, SwerveConstants.RIGHT_FRONT_OFFSET, true,true, "RightFront");

    this.LeftFront_module = new SwerveModule(DeviceIDs.LeftFrontDriveMotorID, DeviceIDs.LeftFrontTurningMotorID, 
    DeviceIDs.LeftFrontEncoderID, SwerveConstants.LEFT_FRONT_OFFSET, true, true, "LeftFront");
    
    this.LeftRear_module = new SwerveModule(DeviceIDs.LeftRearDriveMotorID, DeviceIDs.LeftRearTurningMotorID, 
    DeviceIDs.LeftRearEncoderID, SwerveConstants.LEFT_REAR_OFFSET, false, true, "LeftRear");

    this.RightRear_module = new SwerveModule(DeviceIDs.RightRearDriveMotorID, DeviceIDs.RightRearTurningMotorID, 
    DeviceIDs.RightRearEncoderID, SwerveConstants.RIGHT_REAR_OFFSET, false, true, "RightRear");
    
    gyro = new Pigeon2(DeviceIDs.GyroID);
    gyro.getConfigurator().apply(SwerveConstants.SwerveConfigs.GyroConfig());
    gyro.reset();
    gyro.setYaw(0.0);

    poseEstimator = new SwerveDrivePoseEstimator(m_Kinematics, getRotation(), getModulePositions(), new Pose2d(0, 0, getRotation()));

    publisher = NetworkTableInstance.getDefault().getStructArrayTopic("MyStates", SwerveModuleState.struct).publish();
    Shuffleboard.getTab("Robot Heading").add("Gyro", gyro);
    Shuffleboard.getTab("Drive Method").addBoolean("Field Relative", this::isFieldRelative);
  }

  public Rotation2d getRotation() {
    return Rotation2d.fromDegrees(MathUtil.inputModulus(gyro.getYaw().getValueAsDouble(), -180.0, 180.0));
  }

  public SwerveModulePosition[] getModulePositions() {
    return new SwerveModulePosition[] {
      RightFront_module.getPosition(), 
      LeftFront_module.getPosition(),
      LeftRear_module.getPosition(),
      RightRear_module.getPosition()
    };
  }

  public void resetPose(Pose2d pose) {
    poseEstimator.resetPose(pose);
  }

  public void updateOdometry() {
    poseEstimator.update(getRotation(), getModulePositions());

    if(LimelightHelpers.getTV(LimelightConstants.LIMELIGHT_NAME)) {
      poseEstimator.addVisionMeasurement(LimelightHelpers.getBotPose2d(LimelightConstants.LIMELIGHT_NAME), Timer.getFPGATimestamp());
    }
  }

  public Pose2d getPose() {
    return poseEstimator.getEstimatedPosition();
  }

  public void driveRobotRelative(ChassisSpeeds speeds) {
    SwerveModuleState[] states = m_Kinematics.toSwerveModuleStates(speeds);

    RightFront_module.setState(states[0]);
    LeftFront_module.setState(states[1]);
    LeftRear_module.setState(states[2]);
    RightRear_module.setState(states[3]);
  }

  public void toTarget() {
    poseEstimator.addVisionMeasurement(getPose(), 0, null);
    if(LimelightHelpers.getTV(LimelightConstants.LIMELIGHT_NAME)) {
      var targetAngle = LimelightHelpers.getTX(LimelightConstants.LIMELIGHT_NAME);
      if(targetAngle < 3.0 && targetAngle > -3.0) {
        pose = LimelightHelpers.getTargetPose_CameraSpace(LimelightConstants.LIMELIGHT_NAME);
        
      }
      else {
        drive(0, 0, targetAngle/30.0);
      }


    }
  }


  public void drive(double xInput, double yInput, double turn) {
    double Vx = xInput * SwerveConstants.MAX_DRIVE_SPEED;
    double Vy = yInput * SwerveConstants.MAX_DRIVE_SPEED;
    double omega = turn * SwerveConstants.MAX_TURN_SPEED;
    Rotation2d robotHeading = getRotation();
    ChassisSpeeds Speeds;
    if(fieldRelative) {
      Speeds = ChassisSpeeds.fromFieldRelativeSpeeds(Vx, Vy, omega, robotHeading);
    }
    else {
      Speeds = new ChassisSpeeds(Vx, Vy, omega);
    }

    moduleStates = m_Kinematics.toSwerveModuleStates(Speeds);
    RightFront_module.setState(moduleStates[0]);
    LeftFront_module.setState(moduleStates[1]);
    LeftRear_module.setState(moduleStates[2]);
    RightRear_module.setState(moduleStates[3]);
  }

  public void setFieldRelative(boolean enabled) {
    fieldRelative = enabled;
  }

  public boolean isFieldRelative() {
    return fieldRelative;
  }

  public void updatePoseTable() {
    Pose2d pose = getPose();
    poseXEntry.setDouble(pose.getX());
    poseYEntry.setDouble(pose.getY());
    poseRotationEntry.setDouble(pose.getRotation().getDegrees());
  }

  @Override
  public void periodic() {
    updatePoseTable();
    // This method will be called once per scheduler run.
    RightFront_module.printStatus();
    LeftFront_module.printStatus();
    LeftRear_module.printStatus();
    RightRear_module.printStatus();
    publisher.set(moduleStates);

    poseEstimator.update(getRotation(), getModulePositions());
    var vision = LimelightHelpers.getBotPoseEstimate_wpiBlue(LimelightConstants.LIMELIGHT_NAME);
    if(vision.tagCount > 0
       && vision.timestampSeconds > 0.0
       && Math.abs(gyro.getAngularVelocityZWorld().getValueAsDouble()) < 360.0 
       && LimelightHelpers.getTA(LimelightConstants.LIMELIGHT_NAME) < 20.0) {
      poseEstimator.addVisionMeasurement(vision.pose,vision.timestampSeconds);
    }
  }
}
