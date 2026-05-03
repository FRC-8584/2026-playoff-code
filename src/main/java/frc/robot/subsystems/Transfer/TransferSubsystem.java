package frc.robot.subsystems.Transfer;

import org.littletonrobotics.junction.AutoLogOutput;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class TransferSubsystem extends SubsystemBase{

    private TransferIO io;
    private final TransferIOInputsAutoLogged inputs = new TransferIOInputsAutoLogged();

    

    @Override
    public void periodic() {
        io.updateInputs(inputs);
    }
}
