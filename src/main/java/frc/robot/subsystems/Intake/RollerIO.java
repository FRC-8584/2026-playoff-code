package frc.robot.subsystems.Intake;

public interface RollerIO  {
  
  default public void updateInputs(RollerInputs inputs) {}
  default public void IntakeBall_Vel(){}
  default public void OuttakeBall_Vel(){}
  default public void IntakeBall_Vol(){}
  default public void OuttakeBall_Vol(){}

  class RollerInputs {
    public double rollingRotPerSec  = 0.0;
    public double rollerMotorVolts  = 0.0;
    public double statorCurrentAmps = 0.0;
    public double supplyCurrentAmps = 0.0;
  };
}
