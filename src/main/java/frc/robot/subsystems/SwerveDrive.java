// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.Pigeon2;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.SwerveModule;
import frc.robot.Constants.DeviceIDs;
import frc.robot.Constants.SwerveConstants;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.StructArrayPublisher;

public class SwerveDrive extends SubsystemBase {
  private SwerveModule RightFront_module;
  private SwerveModule LeftFront_module;
  private SwerveModule LeftRear_module;
  private SwerveModule RightRear_module;

  private Pigeon2 gyro;
  private SwerveDriveKinematics m_Kinematics;
  
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

    this.RightRear_module = new SwerveModule(DeviceIDs.RightRearDriveMotorID, DeviceIDs.RightRearTurningMotorID, 
    DeviceIDs.RightRearEncoderID, SwerveConstants.RIGHT_REAR_OFFSET, true, true, "RightRear");
    
    this.LeftRear_module = new SwerveModule(DeviceIDs.LeftRearDriveMotorID, DeviceIDs.LeftRearTurningMotorID, 
    DeviceIDs.LeftRearEncoderID, SwerveConstants.LEFT_REAR_OFFSET, false, true, "LeftRear");

    this.LeftFront_module = new SwerveModule(DeviceIDs.LeftFrontDriveMotorID, DeviceIDs.LeftFrontTurningMotorID, 
    DeviceIDs.LeftFrontEncoderID, SwerveConstants.LEFT_FRONT_OFFSET, true, true, "LeftFront");
    
    
    gyro = new Pigeon2(DeviceIDs.GyroID);
    gyro.getConfigurator().apply(SwerveConstants.SwerveConfigs.GyroConfig());
    gyro.reset();
    gyro.setYaw(0.0);

    publisher = NetworkTableInstance.getDefault()
        .getStructArrayTopic("MyStates", SwerveModuleState.struct).publish();

    Shuffleboard.getTab("Robot Heading").add("Gyro", gyro);
  }

  public Rotation2d getRotation() {
    return Rotation2d.fromDegrees(MathUtil.inputModulus(-gyro.getYaw().getValueAsDouble(), -180.0, 180.0));
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

  @Override
  public void periodic() {
    // This method will be called once per scheduler run.
    RightFront_module.printStatus();
    LeftFront_module.printStatus();
    LeftRear_module.printStatus();
    RightRear_module.printStatus();
    publisher.set(moduleStates);
  }
}
