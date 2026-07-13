package frc.robot;

import static edu.wpi.first.units.Units.Rotation;

import com.ctre.phoenix6.hardware.CANcoder;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModuleState;

public class SwerveModule {
  private TalonFX driveMotor;
  private TalonFX turningMotor;
  private CANcoder turningEncoder;

  private PIDController pid;
  private SwerveModuleState currentState;

  private String name;

  private double driveOutput;
  private double turningOutput;

  public SwerveModule(int DriveMotorID, int TurningMotorID, int EncoderID, double offset, 
    boolean DriveMotorInverted, boolean TurningMotorInverted, String name) {
    driveMotor = new TalonFX(DriveMotorID);
    turningMotor = new TalonFX(TurningMotorID);
    turningEncoder = new CANcoder(EncoderID);
    this.name = name;
    pid = new PIDController(0.1, 0.0, 0.0); // Replace with actual PID values

    driveMotor.set(0);
    turningMotor.set(0);
  }

  public void setState(SwerveModuleState state) {}

  private Rotation2d getEncoderAngle() {
    return Rotation2d.fromDegrees(turningEncoder.getPosition().getValueAsDouble() * 360.0);
  }
}
