package frc.robot.subsystems;

import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.controls.VelocityVoltage;
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
  private TalonFX shooterLeftMotor;
  private TalonFX shooterRightMotor;

  private double leftTargetVelocityRPM = ShooterConstants.LEFT_TARGET_VELOCITY_RPM;
  private double rightTargetVelocityRPM = ShooterConstants.RIGHT_TARGET_VELOCITY_RPM;
  private double feederTargetVelocityRPM = ShooterConstants.FEEDER_TARGET_VELOCITY_RPM;

  private NetworkTable lefttable = NetworkTableInstance.getDefault().getTable("LeftShooter");
  private NetworkTableEntry leftVelociyEntry = lefttable.getEntry("Velocity");
  private NetworkTableEntry leftVoltageEntry = lefttable.getEntry("Voltage");
  private NetworkTableEntry leftCurrenEntry = lefttable.getEntry("Current");
  private NetworkTableEntry leftReadyEntry = lefttable.getEntry("Ready");

  private NetworkTable righttable = NetworkTableInstance.getDefault().getTable("RightShooter");
  private NetworkTableEntry rightVelociyEntry = righttable.getEntry("Velocity");
  private NetworkTableEntry rightVoltageEntry = righttable.getEntry("Voltage");
  private NetworkTableEntry rightCurrenEntry = righttable.getEntry("Current");
  private NetworkTableEntry rightReadyEntry = righttable.getEntry("Ready");
  
  private final VelocityVoltage m_velocity = new VelocityVoltage(0.0);

  public Shooter() {
    this.feederMotor = new TalonFX(DeviceIDs.ShooterFeederMotorID);
    this.shooterLeftMotor = new TalonFX(DeviceIDs.ShooterLeftMotorID);
    this.shooterRightMotor = new TalonFX(DeviceIDs.ShooterRightMotorID);
    
    
    shooterLeftMotor.getConfigurator().apply(ShooterConstants.ShooterConfig(ShooterConstants.ShooterLeftMotorInverted));
    shooterRightMotor.getConfigurator().apply(ShooterConstants.ShooterConfig(ShooterConstants.ShooterRightMotorInverted));
    feederMotor.getConfigurator().apply(ShooterConstants.FeederConfig(ShooterConstants.ShooterFeederMotorInverted));
  }

  public void runShooterVelocity() {
    shooterLeftMotor.setControl(m_velocity.withVelocity(leftTargetVelocityRPM / 60));
    shooterRightMotor.setControl(m_velocity.withVelocity(rightTargetVelocityRPM / 60));
  }

  public void runFeederVelocity() {
    feederMotor.setControl(m_velocity.withVelocity(feederTargetVelocityRPM / 60));
  }

  public void stop() {
    shooterLeftMotor.set(0);
    shooterRightMotor.set(0);
    feederMotor.set(0.0);
  }

  /*public void setTargetRPM (double RPM) {
    targetVelocityRPM = RPM;
    double targetRPS = RPM / 60;
  }*/

  public double getLeftMotorRPM() {
    double currentRPS = shooterLeftMotor.getVelocity().getValueAsDouble();
    return currentRPS * 60.0;
  }

  public double getRightMotorRPM() {
    double currentRPS = shooterRightMotor.getVelocity().getValueAsDouble();
    return currentRPS * 60.0;
  }

  public boolean isAtTarget() {
    if ((leftTargetVelocityRPM == 0) && (rightTargetVelocityRPM == 0)) {
      return false;
    }

    return ((Math.abs(getLeftMotorRPM() - leftTargetVelocityRPM) <= ShooterConstants.ERROR_TOLERANCE_RPM) 
    && (Math.abs(getRightMotorRPM() - rightTargetVelocityRPM) <= ShooterConstants.ERROR_TOLERANCE_RPM));
  }

  private void updateNetworkTable() {
    // left
    leftVelociyEntry.setDouble(getLeftMotorRPM());
    leftVoltageEntry.setDouble(shooterLeftMotor.getMotorVoltage().getValueAsDouble());
    leftCurrenEntry.setDouble(shooterLeftMotor.getSupplyCurrent().getValueAsDouble());
    leftReadyEntry.setBoolean(isAtTarget());

    // right
    rightVelociyEntry.setDouble(getRightMotorRPM());
    rightVoltageEntry.setDouble(shooterRightMotor.getMotorVoltage().getValueAsDouble());
    rightCurrenEntry.setDouble(shooterRightMotor.getSupplyCurrent().getValueAsDouble());
    rightReadyEntry.setBoolean(isAtTarget());
  }
 
  @Override
  public void periodic() {
    updateNetworkTable();
    // This method will be called once per scheduler run
  }
}
