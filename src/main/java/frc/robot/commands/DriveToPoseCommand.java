// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.subsystems.SwerveDriveSubsystem;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.CommandBase;

/** An example command that uses an example subsystem. */
public class DriveToPoseCommand extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final SwerveDriveSubsystem m_swerve;
  private final Pose2d m_desiredPose;

  private final ProfiledPIDController xController =
      new ProfiledPIDController(0.6, 0.0, 0.0, new TrapezoidProfile.Constraints(1.0, 3.0));
  private final ProfiledPIDController yController =
      new ProfiledPIDController(0.6, 0.0, 0.0, new TrapezoidProfile.Constraints(1.0, 3.0));
  private final ProfiledPIDController thetaController =
      new ProfiledPIDController(2.2, 0.0, 0.0, new TrapezoidProfile.Constraints(1.0, 3.0));

  /**
   * Creates a new DriveToPoseCommand.
   *
   * @param swerve The swerve drive subsystem.
   * @param pose The desired pose of the robot.
   */
  public DriveToPoseCommand(
    SwerveDriveSubsystem swerve,
    Pose2d pose
  ) {
    m_swerve = swerve;
    m_desiredPose = pose;

    xController.setTolerance(0.5);
    yController.setTolerance(0.5);
    thetaController.setTolerance(Units.degreesToRadians(3.0));
    thetaController.enableContinuousInput(-Math.PI, Math.PI);

    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(swerve);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    Pose2d currPose = m_swerve.getPose();
    xController.reset(currPose.getX());
    yController.reset(currPose.getY());
    thetaController.reset(currPose.getRotation().getRadians());
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    Pose2d currPose = m_swerve.getPose();
    Pose2d targetPose = m_desiredPose;

    double xvelocity = xController.calculate(currPose.getX(), targetPose.getX());
    double yvelocity = yController.calculate(currPose.getY(), targetPose.getY());
    double thetaVelocity =
        thetaController.calculate(
            currPose.getRotation().getRadians(), targetPose.getRotation().getRadians());

    if (xController.atGoal() && yController.atGoal() && thetaController.atGoal()) {
      xvelocity = 0.0;
      yvelocity = 0.0;
      thetaVelocity = 0.0;
    }

    m_swerve.setChassisSpeeds(
        ChassisSpeeds.fromFieldRelativeSpeeds(
            xvelocity, yvelocity, thetaVelocity, currPose.getRotation()));
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
