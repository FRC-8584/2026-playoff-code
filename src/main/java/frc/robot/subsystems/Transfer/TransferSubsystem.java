package frc.robot.subsystems.Transfer;


import static edu.wpi.first.units.Units.Volts;

import org.littletonrobotics.junction.AutoLogOutput;
import org.littletonrobotics.junction.Logger;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.units.measure.Voltage;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class TransferSubsystem extends SubsystemBase{

    private TransferIO io;
    private final TransferIOInputsAutoLogged inputs = new TransferIOInputsAutoLogged();

    private NetworkTable table = NetworkTableInstance.getDefault().getTable("Transfer");
    private NetworkTableEntry velocity_entry = table.getEntry("Velocity");
    private NetworkTableEntry voltage_entry = table.getEntry("Voltage");
    private NetworkTableEntry current_entry = table.getEntry("Current");
    private NetworkTableEntry temp_entry = table.getEntry("Temp");


    private static TransferSubsystem m_instance = new TransferSubsystem();

    public TransferSubsystem() {
        io = new TransferIOTalonFX();
    }

    @Override
    public void periodic() {
        io.updateInputs(inputs);


        velocity_entry.setDouble(inputs.transferRPS);
        voltage_entry.setDouble(inputs.transferAppliedVoltage);
        current_entry.setDouble(inputs.transferSatorAmp);
        temp_entry.setDouble(io.getTemp());


        Logger.processInputs("Transfer", inputs);
    }

    public static TransferSubsystem getInstance() {
        return m_instance;
    }

    public Command run(boolean reverse) {
        return startEnd(
            ()->{io.setVelocity(
                (reverse)
                ?TransferConstant.RUNNING_RPS
                :TransferConstant.REVERSE_RPS);},
            ()->{io.stop();}
        );
    }
    
}
