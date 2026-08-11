// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.Intake;

import com.ctre.phoenix6.controls.PositionVoltage;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.DeviceIDs;
import frc.robot.Constants.IntakeConstants;

public class IntakeSubsystem extends SubsystemBase {
  private final TalonFX rollerMotor;
  private final TalonFX stretchMotor;

  private NetworkTable table = NetworkTableInstance.getDefault().getTable("Intaker");
  private NetworkTableEntry positionEntry = table.getEntry("Position");
  private NetworkTableEntry voltageEntry = table.getEntry("Voltage");
  private NetworkTableEntry currentEntry = table.getEntry("Current");
  private NetworkTableEntry velocityEntry = table.getEntry("Velocity");

  private double position;
  private final PositionVoltage m_position;

  public IntakeSubsystem() {
    rollerMotor = new TalonFX(DeviceIDs.RollerMotorID);
    stretchMotor = new TalonFX(DeviceIDs.StretchMotorID);
    rollerMotor.getConfigurator().apply(IntakeConstants.RollerConfig());
    stretchMotor.getConfigurator().apply(IntakeConstants.StretchConfig());
    rollerMotor.clearStickyFaults();
    stretchMotor.clearStickyFaults();
    m_position = new PositionVoltage(0.0);
  }

  public void runRoller() {
    rollerMotor.setVoltage(IntakeConstants.ROLLING_VOLTAGE);
  }
 
  public void stopRoller() {
    rollerMotor.set(0.0);
  }
 
  public void stretchOut() {
    m_position.Slot = 1;
    stretchMotor.setControl(m_position.withPosition(IntakeConstants.LIMIT_ROTATION));
  }

  private void updateNetworkTable() {
    positionEntry.setDouble(position);
    voltageEntry.setDouble(stretchMotor.getMotorVoltage().getValueAsDouble());
    currentEntry.setDouble(stretchMotor.getSupplyCurrent().getValueAsDouble());
    velocityEntry.setDouble(stretchMotor.getVelocity().getValueAsDouble());
  }
 
  @Override
  public void periodic() {
    position = stretchMotor.getPosition().getValueAsDouble();
    SmartDashboard.putNumber("Position", position);
    updateNetworkTable();
  }
}
