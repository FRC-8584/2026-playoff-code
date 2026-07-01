package frc.robot.subsystems.Intake;

import org.littletonrobotics.junction.AutoLog;

public interface RollerIO  {
  default public void updateInputs(RollerInputs inputs) {}

  default public void applyConfiguration() {}
  
  default public void setVoltage(double voltage){}
  
  default public void setVelocity(double velocityRPS){}
  
  default public void stop(){
    setVoltage(0.0);
  }

  @AutoLog
  class RollerInputs {
    public double rollingRotPerSec  = 0.0;
    public double rollerMotorVolts  = 0.0;
    public double statorCurrentAmps = 0.0;
    public double supplyCurrentAmps = 0.0;
  };
}
