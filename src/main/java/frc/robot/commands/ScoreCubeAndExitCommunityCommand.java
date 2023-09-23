// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.BucketSubsystem;
import frc.robot.subsystems.SwerveDriveSubsystem;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class ScoreCubeAndExitCommunityCommand extends SequentialCommandGroup {
  /** Creates a new ScoreCubeAndExitCommunity. */
  public ScoreCubeAndExitCommunityCommand(
    SwerveDriveSubsystem swerveDrive,
    BucketSubsystem bucket) {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
      // Score game piece //
      new InstantCommand(() -> bucket.setToPositionMM(-16384)),
      new WaitCommand(0.5),
      new InstantCommand(() -> bucket.setToPositionMM(0)),
      // Exit Community //
      new CommunityExitCommand(swerveDrive, -0.5, 4)
    );
  }
}
