// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.DeviceIDs;
import frc.robot.Constants.TransferConstants;

public class Transfer extends SubsystemBase {
  private TalonFX transferMotor;
  public Transfer(boolean inverted) {
    this.transferMotor = new TalonFX(DeviceIDs.TransferMotorID);
    transferMotor.getConfigurator().apply(TransferConstants.TransferConfig(inverted));
  }

  public void transporting() {
    transferMotor.set(0.5);
  }

  public void stop() {
    transferMotor.set(0.0);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
