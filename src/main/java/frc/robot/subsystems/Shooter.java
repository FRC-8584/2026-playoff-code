package frc.robot.subsystems;

import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.controls.VoltageOut;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.MotorAlignmentValue;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.DeviceIDs;
import frc.robot.Constants.ShooterConstants;

public class Shooter extends SubsystemBase {
  private TalonFX feederMotor;
  private TalonFX leader_leftMotor;
  private TalonFX follower_rightMotor;

  private double targetVelocityRPM = 5000.0;

  private NetworkTable table = NetworkTableInstance.getDefault().getTable("Shooter");
  private NetworkTableEntry velociyEntry = table.getEntry("Velocity");
  private NetworkTableEntry voltageEntry = table.getEntry("Voltage");
  private NetworkTableEntry currenEntry = table.getEntry("Current");
  private NetworkTableEntry readyEntry = table.getEntry("Ready");
  
  private final VoltageOut m_voltage = new VoltageOut(0);

  public Shooter() {
    this.feederMotor = new TalonFX(DeviceIDs.ShooterFeederMotorID);
    this.leader_leftMotor = new TalonFX(DeviceIDs.leader_ShooterLeftMotorID);
    this.follower_rightMotor = new TalonFX(DeviceIDs.follwer_ShooterRightMotorID);
    
    
    leader_leftMotor.getConfigurator().apply(ShooterConstants.ShooterConfig(ShooterConstants.ShooterMotorInverted));
    follower_rightMotor.getConfigurator().apply(ShooterConstants.ShooterConfig(ShooterConstants.ShooterMotorInverted));
    feederMotor.getConfigurator().apply(ShooterConstants.FeederConfig(ShooterConstants.ShooterFeederMotorInverted));

    follower_rightMotor.setControl(new Follower(DeviceIDs.leader_ShooterLeftMotorID, MotorAlignmentValue.Opposed));
  }

  public void runShooterVoltage() {
    leader_leftMotor.setControl(m_voltage.withOutput(ShooterConstants.SHOOT_VOLTAGE));
  }

  public void runFeederVoltage() {
    feederMotor.setControl(m_voltage.withOutput(ShooterConstants.FEED_VOLTAGE));
  }

  public void stop() {
    leader_leftMotor.set(0);
    feederMotor.set(0.0);
  }

  /*public void setTargetRPM (double RPM) {
    targetVelocityRPM = RPM;
    double targetRPS = RPM / 60;
  }*/

  public double getShooterRPM() {
    double currentRPS = leader_leftMotor.getVelocity().getValueAsDouble();
    return currentRPS * 60.0;
  }

  public boolean isAtTarget() {
    if (targetVelocityRPM == 0) {
      return false;
    }

    return (Math.abs(getShooterRPM() - targetVelocityRPM) <= ShooterConstants.ERROR_TOLERANCE_RPM);
  }

  private void updateNetworkTable() {
    velociyEntry.setDouble(getShooterRPM());
    voltageEntry.setDouble(leader_leftMotor.getMotorVoltage().getValueAsDouble());
    currenEntry.setDouble(leader_leftMotor.getSupplyCurrent().getValueAsDouble());
    readyEntry.setBoolean(isAtTarget());
  }
 
  @Override
  public void periodic() {
    updateNetworkTable();
    // This method will be called once per scheduler run
  }
}
