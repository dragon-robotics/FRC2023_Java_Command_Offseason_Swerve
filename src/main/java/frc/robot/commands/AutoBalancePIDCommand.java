// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import java.lang.invoke.ConstantBootstraps;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import frc.robot.Constants.*;
import frc.robot.subsystems.SwerveDriveSubsystem;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class AutoBalancePIDCommand extends PIDCommand {
  /** Creates a new AutoBalancePIDCommand. */
  public AutoBalancePIDCommand(SwerveDriveSubsystem drivetrain, double targetPitchDegrees) {
    super(
        // The controller that the command will use
        new PIDController(
          AutoBalancePIDConstants.P,
          AutoBalancePIDConstants.I,
          AutoBalancePIDConstants.D
        ),
        // This should return the measurement
        drivetrain::getPitch,
        // This should return the setpoint (can also be a constant)
        targetPitchDegrees,
        // This uses the output
        output -> drivetrain.autoDrive(output * 0.2, 0, 0, true),
        drivetrain);
    // Use addRequirements() here to declare subsystem dependencies.
    // Configure additional PID options by calling `getController` here.

    drivetrain.lock();

    // Set the controller to be continuous (because it is an angle controller)
    getController().enableContinuousInput(-30, 30);
    // Set the controller tolerance - the delta tolerance ensures the robot is
    // stationary at the
    // setpoint before it is considered as having reached the reference
    getController()
        .setTolerance(AutoBalancePIDConstants.DEG_TOL, AutoBalancePIDConstants.DEG_PER_S_TOL);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
