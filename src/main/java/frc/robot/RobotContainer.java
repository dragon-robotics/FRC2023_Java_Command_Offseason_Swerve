// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.JoystickConstants;
import frc.robot.Constants.OperatorConstants;
import frc.robot.subsystems.SwerveDriveSubsystem;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.Command;
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

  // Replace with CommandPS4Controller or CommandJoystick if needed
  private final CommandJoystick m_driverController =
      new CommandJoystick(OperatorConstants.kDriverControllerPort);

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
        () -> -m_driverController.getRawAxis(JoystickConstants.STICK_RIGHT_Y),
        () -> -m_driverController.getRawAxis(JoystickConstants.STICK_RIGHT_X),
        () -> -m_driverController.getRawAxis(JoystickConstants.STICK_LEFT_X)
      )
    );

    // Schedule `ExampleCommand` when `exampleCondition` changes to `true`
    // new Trigger(m_swerveDriveSubsystem::exampleCondition)
    //     .onTrue(new ExampleCommand(m_swerveDriveSubsystem));

    // When A is pressed by the driver, zero the gyro //
    m_driverController.button(JoystickConstants.BTN_A)
        .onTrue(new InstantCommand(() -> m_swerveDriveSubsystem.zeroGyro()));
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
