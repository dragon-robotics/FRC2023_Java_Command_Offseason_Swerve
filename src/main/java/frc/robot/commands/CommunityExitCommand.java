// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.SwerveDriveSubsystem;

public class CommunityExitCommand extends CommandBase {
  @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })
  private final SwerveDriveSubsystem m_drivetrain;
  private final double m_seconds;
  // private final double m_volts;
  private final double m_speed;

  private long m_startTime;

  /**
   * Creates a new ArcadeDriveCommand.
   *
   * @param drivetrain The drivetrain used by this command.
   */
  public CommunityExitCommand(
      SwerveDriveSubsystem drivetrain, // The drivetrain subsystem
      double speed, // motor speed %
      double seconds // Number of seconds to drive
  ) {
    m_drivetrain = drivetrain;
    m_seconds = seconds * 1000;
    m_speed = speed;

    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(drivetrain);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    // Capture current time
    m_startTime = System.currentTimeMillis();
    // Make sure the drivetrain isn't moving
    m_drivetrain.autoDrive(0, 0, 0, false);
    // m_drivetrain.tankDriveVolts(0, 0);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    // Move the drivetrain
    m_drivetrain.autoDrive(m_speed, 0, 0, false);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    // Stop the drivetrain
    m_drivetrain.autoDrive(0, 0, 0, false);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    // Drive backwards for a number of seconds defined by the user
    return (System.currentTimeMillis() - m_startTime) >= m_seconds;
  }
}
