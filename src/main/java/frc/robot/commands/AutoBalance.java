// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.BucketSubsystem;
import frc.robot.subsystems.SwerveDriveSubsystem;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class AutoBalance extends SequentialCommandGroup {
  /** Creates a new AutoBalance. */
  public AutoBalance(SwerveDriveSubsystem swerveDrive) {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    
    addCommands(
      // Exit Community //
      // Which direction the robot is driving //
      new CommunityExitCommand(swerveDrive, 0.5, 1),
      // Which axis the rotation is on? //
      new AutoBalancePIDCommand(swerveDrive, 0)
    );
  }
}
