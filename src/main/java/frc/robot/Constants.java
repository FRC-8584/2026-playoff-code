// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix6.configs.CANcoderConfiguration;
import com.ctre.phoenix6.configs.Pigeon2Configuration;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.signals.FeedbackSensorSourceValue;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;
import com.ctre.phoenix6.signals.SensorDirectionValue;

import edu.wpi.first.math.geometry.Translation2d;

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
    //chassis
    public static final int RightFrontDriveMotorID = 1;
    public static final int RightFrontTurningMotorID = 2;

    public static final int RightRearDriveMotorID = 3;
    public static final int RightRearTurningMotorID = 4;
    
    public static final int LeftRearDriveMotorID = 5;
    public static final int LeftRearTurningMotorID = 6;

    public static final int LeftFrontDriveMotorID = 7;
    public static final int LeftFrontTurningMotorID = 8;
    
    //Encoders
    public static final int RightFrontEncoderID = 9;
    public static final int RightRearEncoderID = 10;
    public static final int LeftRearEncoderID = 11;
    public static final int LeftFrontEncoderID = 12;

    //gyro
    public static final int GyroID = 13;
    
    //intake
    public static final int RollerMotorID = 14;
    public static final int StretchMotorID = 15;

    //transfer
    public static final int TransferMotorID = 16;
    
    //shooters
    public static final int ShooterFeederMotorID = 17;
  }

  public static class SwerveConstants {
    public static final double kDriveGearRatio = 8.14; // L1:8.14, L2:6.75, L3:6.12 (慢到快)
    public static final double kWheelRadius = 2; //inch               
    public static final double WheelPerimeter = kWheelRadius * 2 * Math.PI; //inch
    public static final double kSwerveWheelDistance_x = 0.68; //meter
    public static final double kSwerveWheelDistance_y = 0.68; //meter
    public static final double kRadian = 
    Math.sqrt(Math.pow(kSwerveWheelDistance_x/2.0, 2) + Math.pow(kSwerveWheelDistance_y/2.0, 2)); //meter
    
    public static final double MAX_DRIVE_SPEED = 1.0; //m/s                UNDONE
    public static final double MAX_TURN_SPEED = MAX_DRIVE_SPEED / kRadian; //rad/s
    
    public static final Translation2d RightFront_location = new Translation2d(kSwerveWheelDistance_x/2.0, -kSwerveWheelDistance_y/2.0); //meter
    public static final Translation2d LeftFront_location  = new Translation2d(kSwerveWheelDistance_x/2.0, kSwerveWheelDistance_y/2.0);
    public static final Translation2d LeftRear_location   = new Translation2d(-kSwerveWheelDistance_x/2.0, kSwerveWheelDistance_y/2.0);
    public static final Translation2d RightRear_location  = new Translation2d(-kSwerveWheelDistance_x/2.0, -kSwerveWheelDistance_y/2.0); 
    
    public static final double RIGHT_FRONT_OFFSET = -0.058837;
    public static final double LEFT_FRONT_OFFSET  =  0.277343;
    public static final double LEFT_REAR_OFFSET   = -0.047119;
    public static final double RIGHT_REAR_OFFSET  = -0.224121;

    public static final class SwerveConfigs {
      public static TalonFXConfiguration DriveMotorConfig(boolean inverted) {
        TalonFXConfiguration config = new TalonFXConfiguration();
        
        config.MotorOutput.Inverted = inverted ? InvertedValue.Clockwise_Positive : InvertedValue.CounterClockwise_Positive;
        config.MotorOutput.NeutralMode = NeutralModeValue.Brake;

        config.Slot0.kP = 0.1;
        config.Slot0.kI = 0.0;
        config.Slot0.kD = 0.0;
        config.Slot0.kV = 0.0;

        return config;
      }

      public static TalonFXConfiguration TurningMotorConfig(boolean inverted) {
        TalonFXConfiguration config = new TalonFXConfiguration();
        
        config.MotorOutput.Inverted = inverted ? InvertedValue.Clockwise_Positive : InvertedValue.CounterClockwise_Positive;
        config.MotorOutput.NeutralMode = NeutralModeValue.Brake;
        config.Feedback.FeedbackSensorSource = FeedbackSensorSourceValue.RotorSensor;
        
        config.Slot0.kP = 0.1;
        config.Slot0.kI = 0.0;
        config.Slot0.kD = 0.0;
        config.Slot0.kV = 0.0;
        return config;
      }

      public static CANcoderConfiguration CANCoderConfig(double offset) {
        CANcoderConfiguration config = new CANcoderConfiguration();
        
        config.MagnetSensor.MagnetOffset = offset;
        config.MagnetSensor.SensorDirection = SensorDirectionValue.CounterClockwise_Positive;
        
        return config;
      }

      public static Pigeon2Configuration GyroConfig() {
        Pigeon2Configuration config = new Pigeon2Configuration();

        return config;
      }
    }
  }

  public static class IntakeConstants {
    // Roller Constants
    public static final int STATOR_CURRENT_LIMIT_AMPS = 50; //not sure
    public static final int SUPPLY_CURRENT_LIMIT_AMPS = 50; //not sures
  }

  public static class TransferConstants {
    public static final boolean TransferMotorInverted = true;
    
    public static TalonFXConfiguration TransferConfig(boolean inverted) {
      TalonFXConfiguration config = new TalonFXConfiguration();
      config.MotorOutput.Inverted = inverted ? InvertedValue.Clockwise_Positive : InvertedValue.CounterClockwise_Positive;
      config.MotorOutput.NeutralMode = NeutralModeValue.Coast;

      return config;
    }
  }

  public static class DeadBands {
    public static double deadband(double parameter, double deadband) {
      if(Math.abs(parameter) < deadband) parameter = 0.0;
      return parameter;
    }
  }

}
