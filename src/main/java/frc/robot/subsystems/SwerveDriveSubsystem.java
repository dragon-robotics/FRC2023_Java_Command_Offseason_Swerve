// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import java.io.File;
import java.util.function.DoubleSupplier;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.lib.logging.SpartanEntryManager;
import frc.robot.Constants;
import swervelib.SwerveDrive;
import swervelib.parser.SwerveParser;
import swervelib.telemetry.SwerveDriveTelemetry;
import swervelib.telemetry.SwerveDriveTelemetry.TelemetryVerbosity;

public class SwerveDriveSubsystem extends SubsystemBase {

  private boolean m_throttle;

  private final SwerveDrive swerve;

  private SlewRateLimiter translationLimiter = new SlewRateLimiter(Units.feetToMeters(14.5));
  private SlewRateLimiter strafeLimiter = new SlewRateLimiter(Units.feetToMeters(14.5));
  private SlewRateLimiter rotationLimiter = new SlewRateLimiter(Units.feetToMeters(14.5));

  /** Creates a new SwerveDriveSubsystem. */
  public SwerveDriveSubsystem() {

    // Set throttle to false //
    m_throttle = false;

    // Add Swerve Drive Telemetry //
    if (SpartanEntryManager.isTuningMode()) {
      SwerveDriveTelemetry.verbosity = TelemetryVerbosity.HIGH;
    } else {
      SwerveDriveTelemetry.verbosity = TelemetryVerbosity.LOW;
    }

    /* 
     * Initialize the swerve drive based on the
     * configuration files deployed to the RoboRIO
     */
    try {
      swerve =
          new SwerveParser(new File(Filesystem.getDeployDirectory(), "swerve")).createSwerveDrive();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public void autoDrive(
    double translation,
    double strafe,
    double rotation
  ) {
    double translationVal =
        translationLimiter.calculate(
            MathUtil.applyDeadband(
                translation, Constants.GeneralConstants.swerveDeadband));
    double strafeVal =
        strafeLimiter.calculate(
            MathUtil.applyDeadband(
                strafe, Constants.GeneralConstants.swerveDeadband));
    double rotationVal =
        rotationLimiter.calculate(
            MathUtil.applyDeadband(
                rotation, Constants.GeneralConstants.swerveDeadband));

    drive(
        new Translation2d(translationVal, strafeVal)
            .times(swerve.swerveController.config.maxSpeed),
        rotationVal * swerve.swerveController.config.maxAngularVelocity,
        true,
        false);
  }
  
  public Command drive(
    DoubleSupplier translationSup,
    DoubleSupplier strafeSup,
    DoubleSupplier rotationSup
  ) {
    return run(() -> {
          double translation = m_throttle ? translationSup.getAsDouble() * 0.3 : translationSup.getAsDouble();
          double strafe = m_throttle ? strafeSup.getAsDouble() * 0.3 : strafeSup.getAsDouble();
          double rotation = m_throttle ? rotationSup.getAsDouble() * 0.3 : rotationSup.getAsDouble();
          double translationVal =
              translationLimiter.calculate(
                  MathUtil.applyDeadband(
                      translation, Constants.GeneralConstants.swerveDeadband));
          double strafeVal =
              strafeLimiter.calculate(
                  MathUtil.applyDeadband(
                      strafe, Constants.GeneralConstants.swerveDeadband));
          double rotationVal =
              rotationLimiter.calculate(
                  MathUtil.applyDeadband(
                      rotation, Constants.GeneralConstants.swerveDeadband));

          drive(
              new Translation2d(translationVal, strafeVal)
                  .times(swerve.swerveController.config.maxSpeed),
              rotationVal * swerve.swerveController.config.maxAngularVelocity,
              true,
              false);
        })
        .withName("TeleopSwerve");
  }

  public void drive(
    Translation2d translationVal, double rotationVal, boolean fieldRelative, boolean openLoop) {
    swerve.drive(translationVal, rotationVal, fieldRelative, openLoop);
  }

  public void setChassisSpeeds(ChassisSpeeds speeds) {
    swerve.setChassisSpeeds(speeds);
  }

  public void setMotorBrake(boolean brake) {
    swerve.setMotorIdleMode(brake);
  }

  public void zeroGyro() {
    swerve.zeroGyro();
  }

  public void lock() {
    swerve.lockPose();
  }

  public double getYaw() {
    return swerve.getYaw().getDegrees();
  }

  public double getPitch() {
    return swerve.getPitch().getDegrees();
  }

  public void resetOdometry(Pose2d pose) {
    swerve.resetOdometry(pose);
  }

  public Pose2d getPose() {
    return swerve.getPose();
  }

  public void setThrottle(boolean throttle) {
    m_throttle = throttle;
  }

  @Override
  public void periodic() {
    // Update the robot odometry per scheduler run //
    swerve.updateOdometry();
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}
