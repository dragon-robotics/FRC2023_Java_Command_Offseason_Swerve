// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.BucketMotorConstants;
import frc.robot.subsystems.BucketSubsystem;

public class ScoreCubeCommand extends CommandBase {
  /** Creates a new ScoreCubeCommand. */
  private final BucketSubsystem m_bucket;


  public ScoreCubeCommand(
    BucketSubsystem bucket
  ) {
    m_bucket = bucket;

    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(m_bucket);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_bucket.setToPositionMM(-16384);
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
