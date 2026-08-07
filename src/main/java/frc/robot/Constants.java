// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
  public static class OperatorConstants {
    public static final int kDriverControllerPort = 0;
  }

  public static class DeviceIDs {
    public static final int RollerMotorID = 14;
    public static final int StretchMotorID = 15; 
  }

  public static class IntakeConstants {
    // Roller Constants
    public static final int STATOR_CURRENT_LIMIT_AMPS = 50; //not sure
    public static final int SUPPLY_CURRENT_LIMIT_AMPS = 50; //not sure
    public static final double LIMIT_ROTATION = 3.0; //not sure

    public static TalonFXConfiguration RollerConfig() {
      TalonFXConfiguration config = new TalonFXConfiguration();

      config.MotorOutput.Inverted = InvertedValue.CounterClockwise_Positive;
      config.MotorOutput.NeutralMode = NeutralModeValue.Coast;
      config.CurrentLimits.SupplyCurrentLimitEnable = true;
      config.CurrentLimits.SupplyCurrentLimit = IntakeConstants.SUPPLY_CURRENT_LIMIT_AMPS;
      config.CurrentLimits.StatorCurrentLimitEnable = true;
      config.CurrentLimits.StatorCurrentLimit = IntakeConstants.STATOR_CURRENT_LIMIT_AMPS;
      
      return config;
    }

    public static TalonFXConfiguration StretchConfig() {
      TalonFXConfiguration config = new TalonFXConfiguration();

      config.MotorOutput.Inverted = InvertedValue.CounterClockwise_Positive; //正反轉
      config.MotorOutput.NeutralMode = NeutralModeValue.Coast; // idle mode時的模式

      //軟體限位
      config.SoftwareLimitSwitch.ForwardSoftLimitEnable = true; 
      config.SoftwareLimitSwitch.ForwardSoftLimitThreshold = 1.0; 
      config.SoftwareLimitSwitch.ReverseSoftLimitEnable = true;
      config.SoftwareLimitSwitch.ReverseSoftLimitThreshold = 0.0;
      
      //電流限制
      config.CurrentLimits.SupplyCurrentLimitEnable = true;
      config.CurrentLimits.SupplyCurrentLimit = IntakeConstants.SUPPLY_CURRENT_LIMIT_AMPS;
      config.CurrentLimits.StatorCurrentLimitEnable = true;
      config.CurrentLimits.StatorCurrentLimit = IntakeConstants.STATOR_CURRENT_LIMIT_AMPS;

      // stretch out pid
      config.Slot0.kP = 0.01;
      config.Slot0.kI = 0.0;
      config.Slot0.kD = 0.0;

      // fold up pid
      config.Slot1.kP = 0.1;
      config.Slot1.kI = 0.0;  
      config.Slot1.kD = 0.0;

      return config;
    }
  }

}
