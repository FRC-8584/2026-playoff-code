// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.Intake;

import com.ctre.phoenix6.BaseStatusSignal;
import com.ctre.phoenix6.StatusSignal;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Current;
import edu.wpi.first.units.measure.Voltage;
import frc.robot.Constants.DeviceIDs;

/** Add your docs here. */
public class RollerIOReal implements RollerIO {
    private final TalonFX rollerMotor;

    private final StatusSignal<AngularVelocity> rollingRotPerSec;
    private final StatusSignal<Voltage> rollerMotorVolts;
    private final StatusSignal<Current> statorCurrentAmps;
    private final StatusSignal<Current> supplyCurrentAmps;
    

    public RollerIOReal() {
        this.rollerMotor = new TalonFX(DeviceIDs.kRollerMotorID);

        TalonFXConfiguration config = new TalonFXConfiguration();

        rollingRotPerSec = rollerMotor.getVelocity();
        rollerMotorVolts = rollerMotor.getMotorVoltage();
        statorCurrentAmps = rollerMotor.getStatorCurrent();
        supplyCurrentAmps = rollerMotor.getSupplyCurrent();
    }

    @Override
    public void updateInputs(RollerInputs inputs) {
        BaseStatusSignal.refreshAll(rollingRotPerSec, rollerMotorVolts, statorCurrentAmps, supplyCurrentAmps);
        inputs.rollingRotPerSec = rollingRotPerSec.getValueAsDouble();
        inputs.rollerMotorVolts = rollerMotorVolts.getValueAsDouble();
        inputs.statorCurrentAmps = statorCurrentAmps.getValueAsDouble();
        inputs.supplyCurrentAmps = supplyCurrentAmps.getValueAsDouble();
    }

    @Override
    public void setVoltage(double voltage) {}

    @Override
    public void setVelocity(double velocityRPS) {}
}
