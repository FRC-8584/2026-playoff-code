package frc.robot.subsystems.Intake;

public interface StretchIO {
  
  default public void StretchOut(){}
  default public void FoldUp(){}

  class StretchInputs {
    public double stretchingRotPerSec  = 0.0;
    public double stretchMotorVolts  = 0.0;
    public double statorCurrentAmps = 0.0;
    public double supplyCurrentAmps = 0.0;
  };
}
