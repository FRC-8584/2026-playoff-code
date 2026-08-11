package frc.robot.subsystems.Transfer;

import static edu.wpi.first.units.Units.RotationsPerSecond;
import static edu.wpi.first.units.Units.Volt;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.VictorSPXControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.can.VictorSPXConfiguration;

import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Voltage;
import frc.robot.Constants.DeviceIDs;
import frc.robot.Constants.TransferConstants;

public class TransferIOVictor implements TransferIO {

    private VictorSPX transfer_motor;


    public TransferIOVictor() {

        transfer_motor = new VictorSPX(DeviceIDs.TransferMotorID);

    }

    @Override
    public void updateInputs(TransferIOInputs inputs) {
        /*
         * 有刷馬達使用太無腦了，我也不知道不裝sensor有什麼數據好紀錄
         * 
         */
    }

    @Override
    public void stop() {
        transfer_motor.set(ControlMode.Disabled, 0);
    }

    @Override
    public void setOutput(double output) {
        this.transfer_motor.set(ControlMode.PercentOutput, output);
    }

    @Override
    public void setVelocity(AngularVelocity velocity) {
        stop();
        System.err.println("ERROR !!! BRO, you don't have sensor to use velocity control mode.QQ");
    }

    @Override
    public void setVoltage(Voltage volt) {
        stop();
        System.err.println("ERROR !!! BRO, vicotr controller cannot be controlled by the Voltage");
    }

    @Override
    public AngularVelocity getVelocity() {
        double omega_value = this.transfer_motor.getMotorOutputPercent() * TransferConstants.kRedLineMaxvelocity;
        return RotationsPerSecond.of(omega_value);
    }

    @Override
    public Voltage getVoltage() {
        double volt_value =  this.transfer_motor.getBusVoltage() * this.transfer_motor.getMotorOutputPercent();
        return Volt.of(volt_value);
    }



}
