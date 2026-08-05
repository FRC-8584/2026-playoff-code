package frc.robot.subsystems;

import com.ctre.phoenix6.controls.VoltageOut;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.DeviceIDs;
import frc.robot.Constants.ShooterConstants;

public class Shooter extends SubsystemBase {
  private TalonFX leftMotor;
  private TalonFX rightMotor;
  
  private final VoltageOut m_voltage = new VoltageOut(0);
  public Shooter() {
    this.leftMotor = new TalonFX(DeviceIDs.leader_ShooterLeftMotorID);
    this.rightMotor = new TalonFX(DeviceIDs.follwer_ShooterRightMotorID);
    leftMotor.getConfigurator().apply(ShooterConstants.ShooterConfig(ShooterConstants.ShooterLeftMotorInverted));
    rightMotor.getConfigurator().apply(ShooterConstants.ShooterConfig(ShooterConstants.ShooterRightMotorInverted));
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
