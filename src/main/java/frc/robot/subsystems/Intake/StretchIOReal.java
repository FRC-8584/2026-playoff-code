package frc.robot.subsystems.Intake;

import com.ctre.phoenix6.BaseStatusSignal;
import com.ctre.phoenix6.StatusSignal;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Current;
import edu.wpi.first.units.measure.Voltage;
import frc.robot.Constants.DeviceIDs;
import frc.robot.Constants.IntakeConstants;
import frc.robot.subsystems.Intake.RollerIO.RollerInputs;

public class StretchIOReal implements StretchIO {
  private final TalonFX stretchMotor;

  private final StatusSignal<AngularVelocity> stretchingRotPerSec;
  private final StatusSignal<Voltage> stretchMotorVolts;
  private final StatusSignal<Current> statorCurrentAmps;
  private final StatusSignal<Current> supplyCurrentAmps;

  public StretchIOReal(boolean isInverted, boolean isCoast) {
    this.stretchMotor = new TalonFX(DeviceIDs.StretchMotorID);

    TalonFXConfiguration config = new TalonFXConfiguration();
    config.MotorOutput.Inverted = isInverted ? InvertedValue.Clockwise_Positive : InvertedValue.CounterClockwise_Positive;
    config.MotorOutput.NeutralMode = isCoast ? NeutralModeValue.Coast : NeutralModeValue.Brake;
    config.CurrentLimits.SupplyCurrentLimitEnable = true;
    config.CurrentLimits.SupplyCurrentLimit = IntakeConstants.SUPPLY_CURRENT_LIMIT_AMPS;
    config.CurrentLimits.StatorCurrentLimitEnable = true;
    config.CurrentLimits.StatorCurrentLimit = IntakeConstants.STATOR_CURRENT_LIMIT_AMPS;
    stretchMotor.getConfigurator().apply(config);

    stretchingRotPerSec = stretchMotor.getVelocity();
    stretchMotorVolts = stretchMotor.getMotorVoltage();
    statorCurrentAmps = stretchMotor.getStatorCurrent();
    supplyCurrentAmps = stretchMotor.getSupplyCurrent();

    BaseStatusSignal.setUpdateFrequencyForAll(50.0, stretchingRotPerSec, stretchMotorVolts, statorCurrentAmps, supplyCurrentAmps);
    stretchMotor.optimizeBusUtilization();
  }

  @Override
  public void updateInputs(StretchInputs inputs) {
    BaseStatusSignal.refreshAll(stretchingRotPerSec, stretchMotorVolts, statorCurrentAmps, supplyCurrentAmps);
    inputs.stretchingRotPerSec = stretchingRotPerSec.getValueAsDouble();
    inputs.stretchMotorVolts = stretchMotorVolts.getValueAsDouble();
    inputs.statorCurrentAmps = statorCurrentAmps.getValueAsDouble();
    inputs.supplyCurrentAmps = supplyCurrentAmps.getValueAsDouble();
  }

  
}
