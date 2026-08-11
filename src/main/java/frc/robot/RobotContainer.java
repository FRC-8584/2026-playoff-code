// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.commands.Autos;
import frc.robot.commands.ExampleCommand;
import frc.robot.commands.Inhale;
import frc.robot.commands.Stretch;
import frc.robot.subsystems.Intake.IntakeSubsystem;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.button.Trigger;

import frc.robot.commands.Move;
import frc.robot.commands.Shoot;
import frc.robot.commands.Transport;
import frc.robot.subsystems.ExampleSubsystem;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.SwerveDrive;
import frc.robot.subsystems.Transfer;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;


public class RobotContainer {
  private final Joystick joystick_1 = new Joystick(0);
  private final Joystick joystick_2 = new Joystick(1);
  private final ExampleSubsystem m_exampleSubsystem = new ExampleSubsystem();
  private final IntakeSubsystem m_intakeSubsystem = new IntakeSubsystem();
  private final SwerveDrive m_swervedrive = new SwerveDrive();
  private final Transfer m_transfer = new Transfer();
  private final Shooter m_shooter = new Shooter();

  private final Joystick joystick_1 = new Joystick(0);
  private final Joystick joystick_2 = new Joystick(1);

  public RobotContainer() {
    configureDefaults();
    configureBindings();
  }

  private void configureDefaults() {
    // Move
    m_swervedrive.setDefaultCommand(new Move(
      m_swervedrive, 
      () -> joystick_1.getY(), 
      () ->joystick_1.getX(), 
      () -> joystick_1.getRawAxis(4)
    ));
  }

  private void configureBindings() {
    // Field Relative
    new JoystickButton(joystick_1, 5).onTrue(Commands.runOnce(() -> m_swervedrive.setFieldRelative(false), m_swervedrive));
    new JoystickButton(joystick_1, 6).onTrue(Commands.runOnce(() -> m_swervedrive.setFieldRelative(true), m_swervedrive));
  
    // Shooter
    new JoystickButton(joystick_2, 6).onTrue(new Shoot(m_shooter));
    
    // intake
    new JoystickButton(joystick_2, 1).whileTrue(new Inhale(m_intakeSubsystem));
    new JoystickButton(joystick_2, 2).onTrue(new Stretch(m_intakeSubsystem));
  }

  public Command getAutonomousCommand() {
    // An example command will be run in autonomous
    return Autos.exampleAuto(m_exampleSubsystem);
  }
}
