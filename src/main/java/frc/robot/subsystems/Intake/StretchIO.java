package frc.robot.subsystems.Intake;

import org.littletonrobotics.junction.AutoLog;

public interface StretchIO {
  default public void updateInputs() {}

  default public void stretchOut(){}

  default public void foldUp(){}

  @AutoLog
  class StretchInputs {
    public double stretchingRotPerSec  = 0.0;
    public double stretchMotorVolts  = 0.0;
    public double statorCurrentAmps = 0.0;
    public double supplyCurrentAmps = 0.0;
  };
}
