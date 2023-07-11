// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.Extreme3DProConstants;
import frc.robot.Constants.JoystickConstants;
import frc.robot.Constants.OperatorConstants;
import frc.robot.subsystems.BucketSubsystem;
import frc.robot.subsystems.SwerveDriveSubsystem;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.CommandJoystick;
import edu.wpi.first.wpilibj2.command.button.Trigger;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final SwerveDriveSubsystem m_swerveDriveSubsystem = new SwerveDriveSubsystem();
  private final BucketSubsystem m_bucketSubsystem = new BucketSubsystem();

  // Replace with CommandPS4Controller or CommandJoystick if needed
  private final CommandJoystick m_driverController =
      new CommandJoystick(OperatorConstants.kDriverControllerPort);

  private final CommandJoystick m_operatorController =
      new CommandJoystick(OperatorConstants.kOperatorControllerPort);


  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the trigger bindings
    configureBindings();
  }

  /**
   * Use this method to define your trigger->command mappings. Triggers can be created via the
   * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with an arbitrary
   * predicate, or via the named factories in {@link
   * edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for {@link
   * Joystick Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller
   * PS4} controllers or {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight
   * joysticks}.
   */
  private void configureBindings() {

    m_swerveDriveSubsystem.setDefaultCommand(
      m_swerveDriveSubsystem.drive(
        () -> -m_driverController.getRawAxis(Extreme3DProConstants.Y_AXIS) * 0.3, // Translation
        () -> -m_driverController.getRawAxis(Extreme3DProConstants.X_AXIS) * 0.3, // Strafe
        () -> -m_driverController.getRawAxis(Extreme3DProConstants.ROTATE) * 0.3  // Rotation
      )
    );

    m_bucketSubsystem.setDefaultCommand(
      Commands.run(() -> m_bucketSubsystem.stop())
    );

    // m_bucketSubsystem.setDefaultCommand(
    //   new MoveBucket(
    //     m_bucketSubsystem,
    //     () -> -m_operatorController.getRawAxis(JoystickConstants.STICK_RIGHT_Y) * 0.3
    //   )
    // );

    // Schedule `ExampleCommand` when `exampleCondition` changes to `true`
    // new Trigger(m_swerveDriveSubsystem::exampleCondition)
    //     .onTrue(new ExampleCommand(m_swerveDriveSubsystem));

    // When thumb button is pressed by the driver, zero the gyro //
    m_driverController.button(Extreme3DProConstants.BTN_THUMB)
        .onTrue(new InstantCommand(() -> m_swerveDriveSubsystem.zeroGyro()));

    // When trigger button is pressed by the driver, make the speed 40%, otherwise, full speed //
    // Used to slow down robot when climbing onto charge station //
    m_driverController.button(Extreme3DProConstants.BTN_TRIGGER)
        .onTrue(new InstantCommand(() -> m_swerveDriveSubsystem.setThrottle(true)))
        .onFalse(new InstantCommand(() -> m_swerveDriveSubsystem.setThrottle(false)));
    
    m_operatorController.button(JoystickConstants.BTN_A)
        .onTrue(new InstantCommand(() -> m_bucketSubsystem.setToPosition(0)));

    m_operatorController.button(JoystickConstants.BTN_B)
        .onTrue(new InstantCommand(() -> m_bucketSubsystem.setToPosition(-4096)));

    m_operatorController.button(JoystickConstants.BTN_X)
        .onTrue(new InstantCommand(() -> m_bucketSubsystem.setToPosition(-8192)));

    m_operatorController.button(JoystickConstants.BTN_Y)
        .onTrue(new InstantCommand(() -> m_bucketSubsystem.setToPosition(-2048)));
  }

  /**
   * Brake all the motors after a match
   * @param brake True to set the motors to brake. False otherwise.
   */
  public void setMotorBrake(boolean brake) {
    m_swerveDriveSubsystem.setMotorBrake(brake);
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An example command will be run in autonomous
    // return Autos.exampleAuto(m_swerveDriveSubsystem);
    return null;
  }
}
