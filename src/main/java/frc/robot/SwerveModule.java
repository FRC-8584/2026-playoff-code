package frc.robot;

import com.ctre.phoenix6.hardware.CANcoder;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants.SwerveConstants;
import frc.robot.Constants.SwerveConstants.SwerveConfigs;

public class SwerveModule {
  private TalonFX driveMotor;
  private TalonFX turningMotor;
  private CANcoder turningEncoder;

  private PIDController pid;

  private String name;

  private double driveOutput;
  private double turningOutput;

  public SwerveModule(int DriveMotorID, int TurningMotorID, int EncoderID, double offset, 
    boolean DriveMotorInverted, boolean TurningMotorInverted, String name) {
    driveMotor = new TalonFX(DriveMotorID);
    turningMotor = new TalonFX(TurningMotorID);
    turningEncoder = new CANcoder(EncoderID);
    this.name = name;
    pid = new PIDController(0.01, 0.0, 0.0); // Replace with actual PID values
    pid.enableContinuousInput(-180.0, 180.0);

    driveMotor.set(0);
    turningMotor.set(0);

    driveMotor.getConfigurator().apply(SwerveConfigs.DriveMotorConfig(DriveMotorInverted)); 
    turningMotor.getConfigurator().apply(SwerveConfigs.TurningMotorConfig(TurningMotorInverted));
    turningEncoder.getConfigurator().apply(SwerveConfigs.CANCoderConfig(offset));
  }

  public void setState(SwerveModuleState state) {
    state.optimize(getEncoderAngle()); //角度最佳化
    state.cosineScale(getEncoderAngle()); //餘弦補償

    driveOutput = state.speedMetersPerSecond / SwerveConstants.MAX_DRIVE_SPEED; // UNDONE
    turningOutput = pid.calculate(getEncoderAngle().getDegrees(), state.angle.getDegrees());
    driveMotor.set(driveOutput*0.2);
    turningMotor.set(turningOutput);
  }

  private Rotation2d getEncoderAngle() {
    return Rotation2d.fromDegrees(turningEncoder.getAbsolutePosition().getValueAsDouble() * 360.0);
  }

  private double getDriveDistanceMeters() {
    double motorRotations = driveMotor.getPosition().getValueAsDouble();

    double wheelRotations = motorRotations / SwerveConstants.kDriveGearRatio;

    return wheelRotations * SwerveConstants.kWheelCircumferenceMeters;
}

  public SwerveModulePosition getPosition() {
    return new SwerveModulePosition(getDriveDistanceMeters(), getEncoderAngle());
  }

  public void printStatus() {
    SmartDashboard.putNumber(name + " Drive Velocity: ", driveOutput);
    SmartDashboard.putNumber(name + " Turning Output: ", turningOutput);
    SmartDashboard.putNumber(name + "Current Angle", getEncoderAngle().getDegrees());
  }
}
