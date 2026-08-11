package frc.robot.subsystems.Transfer;

import static edu.wpi.first.units.Units.RotationsPerSecondPerSecond;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.units.Unit;
import edu.wpi.first.units.Units;
import edu.wpi.first.units.measure.AngularAcceleration;
import edu.wpi.first.units.measure.AngularVelocity;

public class TransferConstant {

    public static final int motorID = 16;

    public static final AngularVelocity RUNNING_RPS
        = AngularVelocity.ofBaseUnits(60 ,Units.RotationsPerSecond);

    public static final AngularVelocity REVERSE_RPS
        = AngularVelocity.ofBaseUnits(-60,Units.RotationsPerSecond);
    
    public static final AngularAcceleration MAX_ACCELERATION
        = AngularAcceleration.ofBaseUnits(90, RotationsPerSecondPerSecond);
    
    public static final int MAX_SUPPLY_CURRENT = 15;
    public static final int MAX_STATOR_CURRENT = 65;

    public static final boolean IsINVERTED = false;
    
    public static final NeutralModeValue NEUTRAL_MODE = NeutralModeValue.Coast;

    public static final double DEADBAND = 0.1;

    public static final double GEAR_RATIO = 1.0;

    public static final double RedLineMaxvelocity = 21020.0 / 60.0;    

}
