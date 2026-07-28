// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj.PS4Controller.Axis;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants.DeadBands;
import frc.robot.subsystems.SwerveDrive;

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class Move extends Command {
  private final SwerveDrive m_swervedrive;
  private Supplier<Double> x;
  private Supplier<Double> y;
  private Supplier<Double> turn;


  public Move(SwerveDrive swervedrive, Supplier<Double> _x, Supplier<Double> _y, Supplier<Double> _turn) {
    m_swervedrive = swervedrive;
    x = _x;
    y = _y;
    turn = _turn;
    addRequirements(swervedrive);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_swervedrive.drive(
    DeadBands.deadband(x.get(), 0.07), 
    DeadBands.deadband(y.get(), 0.07), 
    DeadBands.deadband(turn.get(), 0.07)
    );
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
