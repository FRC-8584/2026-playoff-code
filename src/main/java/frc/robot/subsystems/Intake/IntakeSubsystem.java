// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.Intake;

import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.DeviceIDs;
import frc.robot.Constants.IntakeConstants;

public class IntakeSubsystem extends SubsystemBase {
  private final TalonFX rollerMotor;
  private final TalonFX stretchMotor;
  
  public IntakeSubsystem() {
    rollerMotor = new TalonFX(DeviceIDs.RollerMotorID);
    stretchMotor = new TalonFX(DeviceIDs.StretchMotorID);
    rollerMotor.getConfigurator().apply(IntakeConstants.RollerConfig());
    stretchMotor.getConfigurator().apply(IntakeConstants.StretchConfig());
  }

  

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
