package frc.robot.subsystems.Transfer;

import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Voltage;

import static edu.wpi.first.units.Units.RotationsPerSecond;
import static edu.wpi.first.units.Units.Volt;

import org.littletonrobotics.junction.AutoLog;

public interface TransferIO {
    
    public default void setVelocity(AngularVelocity velocity){};
    public default void setVoltage(Voltage volt) {};
    public default void setOutput(double output) {};
    public default void stop(){};
    public default AngularVelocity getVelocity(){return RotationsPerSecond.of(0);};
    public default Voltage getVoltage() {return Volt.of(0);};
    public default void updateInputs(TransferIOInputs inputs) {}

    @AutoLog
    public class TransferIOInputs {
        public double transferRPS = 0.0;
        public double transferAppliedVoltage = 0.0;
        public double transferSuppliedAmp = 0.0;
        public double transferSatorAmp = 0.0;
    }

}
