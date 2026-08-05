package frc.robot.subsystems;

import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.controls.VoltageOut;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.MotorAlignmentValue;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.DeviceIDs;
import frc.robot.Constants.ShooterConstants;

public class Shooter extends SubsystemBase {
  private TalonFX leader_leftMotor;
  private TalonFX follower_rightMotor;
  
  private final VoltageOut m_voltage = new VoltageOut(0);
  private double m_targetRPS = 0.0; // 目標轉速 (RPS)
  public Shooter() {
    this.leader_leftMotor = new TalonFX(DeviceIDs.leader_ShooterLeftMotorID);
    this.follower_rightMotor = new TalonFX(DeviceIDs.follwer_ShooterRightMotorID);
    
    leader_leftMotor.getConfigurator().apply(ShooterConstants.ShooterConfig());
    follower_rightMotor.getConfigurator().apply(ShooterConstants.ShooterConfig());

    follower_rightMotor.setControl(new Follower(DeviceIDs.leader_ShooterLeftMotorID, MotorAlignmentValue.Opposed));
  }

  public void runShooterVoltage(double volts) {
    leader_leftMotor.setControl(m_voltage.withOutput(volts));
  }

  public void stop() {
    leader_leftMotor.set(0);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
