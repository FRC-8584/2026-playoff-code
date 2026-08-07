package frc.robot.subsystems.Transfer;

import java.time.Year;
import java.util.spi.CurrencyNameProvider;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix6.StatusSignal;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.VelocityVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.FeedbackSensorSourceValue;
import com.ctre.phoenix6.signals.InvertedValue;

import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Current;
import edu.wpi.first.units.measure.Temperature;
import edu.wpi.first.units.measure.Voltage;


public class TransferIOTalonFX implements TransferIO{

    private TalonFX trans_motor;

    private final StatusSignal<AngularVelocity> velocity;
    private final StatusSignal<Voltage> volt;
    private final StatusSignal<Temperature> temp;
    private final StatusSignal<Current> sup_current;
    private final StatusSignal<Current> sat_current;

    private VelocityVoltage req = new VelocityVoltage(0);



    private void Config() {
        TalonFXConfiguration configs = new TalonFXConfiguration();

        configs.MotorOutput.Inverted
            = (TransferConstant.IsINVERTED) ? InvertedValue.CounterClockwise_Positive : InvertedValue.Clockwise_Positive; 

        configs.CurrentLimits.StatorCurrentLimit = TransferConstant.MAX_STATOR_CURRENT;
        configs.CurrentLimits.StatorCurrentLimitEnable = true;

        configs.CurrentLimits.SupplyCurrentLimit = TransferConstant.MAX_SUPPLY_CURRENT;
        configs.CurrentLimits.SupplyCurrentLimitEnable = true;

        configs.MotorOutput.NeutralMode = TransferConstant.NEUTRAL_MODE;

        configs.MotorOutput.DutyCycleNeutralDeadband = TransferConstant.DEADBAND;

        configs.Slot0.kP = 0.1;
        configs.Slot0.kI = 0.0;
        configs.Slot0.kD = 0.0;

        configs.Feedback.FeedbackSensorSource = FeedbackSensorSourceValue.RotorSensor;
        configs.Feedback.SensorToMechanismRatio = TransferConstant.GEAR_RATIO;
        
        this.trans_motor.getConfigurator().apply(configs);
    }

    public TransferIOTalonFX() {

        velocity = trans_motor.getVelocity();
        volt = trans_motor.getSupplyVoltage();
        temp = trans_motor.getDeviceTemp();
        sup_current = trans_motor.getSupplyCurrent();
        sat_current = trans_motor.getStatorCurrent();

        Config();
    }

    @Override
    public void setOutput(double output) {
        trans_motor.set(output);
    }

    @Override
    public void setVoltage(Voltage volt) {
        trans_motor.setVoltage(volt.baseUnitMagnitude());
    }

    @Override
    public void setVelocity(AngularVelocity velocity) {
        trans_motor.setControl(req.withVelocity(velocity));
    }

    @Override
    public AngularVelocity getVelocity() {
        return velocity.getValue();
    }

    @Override
    public Voltage getVoltage() {
        return volt.getValue();
    }


    @Override
    public void updateInputs(TransferIOInputs inputs) {
        StatusSignal.refreshAll(
            velocity,
            volt,
            sat_current,
            sup_current
        );

        inputs.transferRPS = velocity.getValueAsDouble();
        inputs.transferSuppliedAmp = sup_current.getValueAsDouble();
        inputs.transferSatorAmp = sat_current.getValueAsDouble();
        inputs.transferAppliedVoltage = volt.getValueAsDouble();

    }


    
}