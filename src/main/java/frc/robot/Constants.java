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

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.util.Units;


public final class Constants {
  public static class OperatorConstants {
    public static final int kDriverControllerPort = 0;
  }


  public static class DeviceIDs {
    //chassis
    public static final int RightFrontDriveMotorID = 1;
    public static final int RightFrontTurningMotorID = 2;

    public static final int LeftFrontDriveMotorID = 3;
    public static final int LeftFrontTurningMotorID = 4;
    
    public static final int LeftRearDriveMotorID = 5;
    public static final int LeftRearTurningMotorID = 6;

    public static final int RightRearDriveMotorID = 7;
    public static final int RightRearTurningMotorID = 8;

    
    
    //Encoders
    public static final int RightFrontEncoderID = 9;
    public static final int LeftFrontEncoderID = 10;
    public static final int LeftRearEncoderID = 11;
     public static final int RightRearEncoderID = 12;
    
    //gyro
    public static final int GyroID = 13;
    
    //intake
    public static final int RollerMotorID = 14;
    public static final int StretchMotorID = 15;

    //transfer
    public static final int TransferMotorID = 16;

    //shooter
    public static final int ShooterFeederMotorID = 17;
    public static final int leader_ShooterLeftMotorID = 18;
    public static final int follwer_ShooterRightMotorID = 19;
  }


  public static class SwerveConstants {
    public static final double kDriveGearRatio = 8.14; // L1:8.14, L2:6.75, L3:6.12 (慢到快)
    public static final double kWheelRadius = 2.0; //inch  
    public static final double kWheelRadiusMeters = Units.inchesToMeters(2.0);
    public static final double WheelPerimeter = kWheelRadius * 2 * Math.PI; //inch
    public static final double kWheelCircumferenceMeters = 2.0 * Math.PI * kWheelRadiusMeters;
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
    
    public static final double RIGHT_FRONT_OFFSET = -0.048095;
    public static final double LEFT_FRONT_OFFSET  =  0.271484;
    public static final double LEFT_REAR_OFFSET   = -0.057128;
    public static final double RIGHT_REAR_OFFSET  = -0.218750;
    
    
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
    public static final int SUPPLY_CURRENT_LIMIT_AMPS = 50; //not sure
    public static final double LIMIT_ROTATION = 60.0;
    public static final double ROLLING_VOLTAGE = 3.5;

    public static TalonFXConfiguration RollerConfig() {
      TalonFXConfiguration config = new TalonFXConfiguration();

      config.MotorOutput.Inverted = InvertedValue.Clockwise_Positive;
      config.MotorOutput.NeutralMode = NeutralModeValue.Coast;
      config.CurrentLimits.SupplyCurrentLimitEnable = true;
      config.CurrentLimits.SupplyCurrentLimit = IntakeConstants.SUPPLY_CURRENT_LIMIT_AMPS;
      config.CurrentLimits.StatorCurrentLimitEnable = true;
      config.CurrentLimits.StatorCurrentLimit = IntakeConstants.STATOR_CURRENT_LIMIT_AMPS;

      config.Slot0.kP = 0.1;
      config.Slot0.kI = 0.0;
      config.Slot0.kD = 0.0;
      config.Slot0.kS = 0.19791;
      config.Slot0.kV = 0.11546;
      config.Slot0.kA = 0.001;
      
      return config;
    }

    public static TalonFXConfiguration StretchConfig() {
      TalonFXConfiguration config = new TalonFXConfiguration();

      config.MotorOutput.Inverted = InvertedValue.CounterClockwise_Positive; //正反轉
      config.MotorOutput.NeutralMode = NeutralModeValue.Coast; // idle mode時的模式

      //軟體限位
      config.SoftwareLimitSwitch.ForwardSoftLimitEnable = true; 
      config.SoftwareLimitSwitch.ForwardSoftLimitThreshold = 60.0; 
      config.SoftwareLimitSwitch.ReverseSoftLimitEnable = true;
      config.SoftwareLimitSwitch.ReverseSoftLimitThreshold = 0.0;
      
      //電流限制
      config.CurrentLimits.SupplyCurrentLimitEnable = true;
      config.CurrentLimits.SupplyCurrentLimit = IntakeConstants.SUPPLY_CURRENT_LIMIT_AMPS;
      config.CurrentLimits.StatorCurrentLimitEnable = true;
      config.CurrentLimits.StatorCurrentLimit = IntakeConstants.STATOR_CURRENT_LIMIT_AMPS;

      // stretch out pid
      config.Slot1.kP = 0.25;
      config.Slot1.kI = 0.0;
      config.Slot1.kD = 0.0;
      config.Slot1.kS = 0.0;
      config.Slot1.kV = 0.12;
      config.Slot1.kA = 0.0;

      return config;
    }
  }


  public static class TransferConstants {
    public static final double kRedLineMaxvelocity = 21020.0 / 60.0; //用AndyMark給的 紅線馬達 最高free RPM作為上限值
    public static final boolean TransferMotorInverted = false;
  }


  public static class ShooterConstants {
    public static final boolean ShooterMotorInverted = true;
    public static final boolean ShooterFeederMotorInverted = false;

    public static final double SHOOT_VOLTAGE = 6.5;
    public static final double FEED_VOLTAGE = 6.0; 
    public static final double ERROR_TOLERANCE_RPM = 100.0;
    
    public static TalonFXConfiguration ShooterConfig(boolean inverted) {
      TalonFXConfiguration config = new TalonFXConfiguration();
      
      config.MotorOutput.Inverted = inverted ? InvertedValue.Clockwise_Positive : InvertedValue.CounterClockwise_Positive;
      config.MotorOutput.NeutralMode = NeutralModeValue.Coast;

      // PID values is still undetermined, need to be tuned
      config.Slot0.kP = 0.1;
      config.Slot0.kI = 0.0;
      config.Slot0.kD = 0.0;
      config.Slot0.kS = 0.34204;
      config.Slot0.kV = 0.11977;
      config.Slot0.kA = 0.006;

      return config;
    }

    public static TalonFXConfiguration FeederConfig(boolean inverted) {
      TalonFXConfiguration config = new TalonFXConfiguration();

      config.MotorOutput.Inverted = inverted ? InvertedValue.Clockwise_Positive : InvertedValue.CounterClockwise_Positive;
      config.MotorOutput.NeutralMode = NeutralModeValue.Coast;

      config.Slot0.kP = 0.1;
      config.Slot0.kI = 0.0;
      config.Slot0.kD = 0.0;
      config.Slot0.kS = 0.0;
      config.Slot0.kV = 0.0;
      config.Slot0.kA = 0.0;

      return config;
    }
  }


  public static class LimelightConstants {
    public static final String LIMELIGHT_NAME = "limelight";
    
    public static class PIDs {
      public static final PIDController x_pid = new PIDController(1.0, 0, 0.1);
      public static final PIDController y_pid = new PIDController(2.5, 2.0*1e-3, 0.01);
      public static final PIDController turn_pid = new PIDController(0.3, 0, 0);
    }
  }

 
  public static final class FieldConstants {
    public static final class HubConstants {
      // Always blue origin
      public static final Translation2d BLUE_HUB_CENTER = new Translation2d(4.625, 4.034);

      public static final Translation2d RED_HUB_CENTER = new Translation2d(11.915, 4.034);

      // 機器人「中心」到 Hub 中心的射擊距離 r
      public static final double SHOOTING_RADIUS_METERS = 2.50; //needed to be tuned later

      public static final double TURN_KP = 0.01; /* tune */
      public static final double DRIVE_KP = 0.09; /* tune */

      public static final double ANGLE_TOLERANCE_RAD = Units.degreesToRadians(2.5);
      public static final double DISTANCE_TOLERANCE_METERS = 0.08;
    }
  }
  

  public static class DeadBands {
    public static double deadband(double parameter, double deadband) {
      if(Math.abs(parameter) < deadband) parameter = 0.0;
      return parameter;
    }
  }
}