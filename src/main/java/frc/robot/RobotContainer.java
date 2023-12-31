// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.AutoLoader.AutoCommand;
import frc.robot.Constants.CustomButtonBoxConstants;
import frc.robot.Constants.Extreme3DProConstants;
import frc.robot.Constants.JoystickConstants;
import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.AutoBalance;
import frc.robot.commands.CommunityExitCommand;
import frc.robot.commands.ScoreCubeAndBalanceCommand;
import frc.robot.commands.ScoreCubeAndExitCommunityCommand;
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
  public final SwerveDriveSubsystem m_swerveDriveSubsystem = new SwerveDriveSubsystem();
  public final BucketSubsystem m_bucketSubsystem = new BucketSubsystem();

  // Replace with CommandPS4Controller or CommandJoystick if needed
  private final CommandJoystick m_driverController =
      new CommandJoystick(OperatorConstants.kDriverControllerPort);

  private final CommandJoystick m_operatorController =
      new CommandJoystick(OperatorConstants.kOperatorControllerPort);

  private final CommandJoystick m_operatorButtonController =
      new CommandJoystick(OperatorConstants.kOperatorButtonControllerPort);

  // Create the auto loader class to load everything for us //
  private final AutoLoader m_autoLoader = new AutoLoader();

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
        () -> -m_driverController.getRawAxis(Extreme3DProConstants.Y_AXIS), // Translation
        () -> -m_driverController.getRawAxis(Extreme3DProConstants.X_AXIS), // Strafe
        () -> -m_driverController.getRawAxis(Extreme3DProConstants.ROTATE)  // Rotation
      )
    );

    // m_bucketSubsystem.setDefaultCommand(
    //   Commands.startEnd(
    //     () -> m_bucketSubsystem.setToPosition(0),
    //     m_bucketSubsystem)
    // );

    // m_bucketSubsystem.setDefaultCommand(
    //   Commands.run(
    //     () -> m_bucketSubsystem.move(
    //       -m_operatorController.getRawAxis(JoystickConstants.STICK_RIGHT_Y) * 0.3
    //     ),
    //     m_bucketSubsystem
    //   )
    // );

    // When thumb button is pressed by the driver, zero the gyro //
    m_driverController.button(Extreme3DProConstants.BTN_TRIGGER)
        .onTrue(new InstantCommand(() -> m_swerveDriveSubsystem.zeroGyro()));

    // When trigger button is pressed by the driver, make the speed 40%, otherwise, full speed //
    // Used to slow down robot when climbing onto charge station //
    m_driverController.button(Extreme3DProConstants.BTN_THUMB)
        .onTrue(new InstantCommand(() -> m_swerveDriveSubsystem.setThrottle(true)))
        .onFalse(new InstantCommand(() -> m_swerveDriveSubsystem.setThrottle(false)));
    
    m_operatorButtonController.button(CustomButtonBoxConstants.BTN_3)
        .onTrue(Commands.run(
          () -> m_bucketSubsystem.move(-0.3),
          m_bucketSubsystem
        ))
        .onFalse(
          Commands.run(
          () -> m_bucketSubsystem.stop(),
          m_bucketSubsystem
        ));

    m_operatorButtonController.button(CustomButtonBoxConstants.BTN_2)
        .onTrue(Commands.run(
          () -> m_bucketSubsystem.move(0.3),
          m_bucketSubsystem
        ))
        .onFalse(
          Commands.run(
          () -> m_bucketSubsystem.stop(),
          m_bucketSubsystem
        ));
    
    // Motion Magic Controller Setpoints //
    // m_operatorController.button(JoystickConstants.BTN_B)
    //     .onTrue(new InstantCommand(() -> m_bucketSubsystem.setToPositionMM(-4096)))
    //     .onFalse(new InstantCommand(() -> m_bucketSubsystem.setToPositionMM(0)));

    m_operatorButtonController.button(CustomButtonBoxConstants.BTN_11)
        .onTrue(new InstantCommand(() -> m_bucketSubsystem.setToPositionMM(-16384)));

    m_operatorButtonController.button(CustomButtonBoxConstants.BTN_7)
        .onTrue(new InstantCommand(() -> m_bucketSubsystem.setToPositionMM(0)));

    // m_operatorButtonController.button(CustomButtonBoxConstants.BTN_7)
    //     .onTrue(new InstantCommand(() -> m_bucketSubsystem.setToPositionMM(-16384)))
    //     .onFalse(new InstantCommand(() -> m_bucketSubsystem.setToPositionMM(0)));

    // m_operatorController.button(JoystickConstants.BTN_Y)
    //     .onTrue(new InstantCommand(() -> m_bucketSubsystem.setToPositionMM(-2048)))
    //     .onFalse(new InstantCommand(() -> m_bucketSubsystem.setToPositionMM(0)));

    // Position Controller Setpoints //
    // m_operatorButtonController.button(CustomButtonBoxConstants.BTN_12)
    //     .onTrue(new InstantCommand(() -> m_bucketSubsystem.setToPosition(0)))
    //     .onFalse(new InstantCommand(() -> m_bucketSubsystem.setToPositionMM(0)));
    
    // m_operatorButtonController.button(CustomButtonBoxConstants.BTN_8)
    //     .onTrue(new InstantCommand(() -> m_bucketSubsystem.setToPosition(-2048)))
    //     .onFalse(new InstantCommand(() -> m_bucketSubsystem.setToPositionMM(0)));

    // m_operatorButtonController.button(CustomButtonBoxConstants.BTN_5)
    //     .onTrue(new InstantCommand(() -> m_bucketSubsystem.setToPosition(-4096)))
    //     .onFalse(new InstantCommand(() -> m_bucketSubsystem.setToPositionMM(0)));
        
    // m_operatorButtonController.button(CustomButtonBoxConstants.BTN_4)
    //     .onTrue(new InstantCommand(() -> m_bucketSubsystem.setToPosition(-6144)))
    //     .onFalse(new InstantCommand(() -> m_bucketSubsystem.setToPositionMM(0)));
    
    // m_operatorButtonController.button(CustomButtonBoxConstants.BTN_1)
    //     .onTrue(new InstantCommand(() -> m_bucketSubsystem.setToPosition(-4608)))
    //     .onFalse(new InstantCommand(() -> m_bucketSubsystem.setToPositionMM(0)));


    // 3.   Home - starting configuration
    // 2.   Single player station - Cone & Cube
    // 8.   Hybrid Grid - Score Cube
    // 9.   Hybrid Grid - Score Cone
    // 4.   Long distance cube (far side of charging station)
    // 5.   Long distance cone
    // 10.  Level 3 Grid - Score Cone
    // 1.   Level 2 Grid - Score Cube
    // 6.   Level 2 Grid - Score Cone
    // 7.   Level 3 Grid - Score cube
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
    // Get command selected from AutoCommand //
    AutoCommand command = m_autoLoader.getSelected();

    switch (command) {
      case NONE:
        return null;
      case COMMUNITY_EXIT:
        return new CommunityExitCommand(m_swerveDriveSubsystem, 0.5, 3);
      case BALANCE:
        return new AutoBalance(m_swerveDriveSubsystem);
      case SCORE_CUBE_AND_BALANCE:
        return new ScoreCubeAndBalanceCommand(m_swerveDriveSubsystem, m_bucketSubsystem);
      case SCORE_CUBE_AND_COMMUNITY_EXIT:
        return new ScoreCubeAndExitCommunityCommand(m_swerveDriveSubsystem, m_bucketSubsystem);
      default:
        return null;
    }
    // Score Cube and Exit Community //
    // return Commands.sequence(
    //   // // Score Cube //
    //   Commands.startEnd(
    //       () -> m_bucketSubsystem.setToPositionMM(-16384),
    //       () -> m_bucketSubsystem.setToPositionMM(0),
    //       m_bucketSubsystem).withTimeout(2),
    //   // Wait 0.5 seconds //
    //   Commands.waitSeconds(0.5),
    //   // Exit Community //
    //   Commands.startEnd(
    //       () -> m_swerveDriveSubsystem.autoDrive(0.5, 0, 0),
    //       () -> m_swerveDriveSubsystem.autoDrive(0, 0, 0),
    //       m_swerveDriveSubsystem
    //     ).withTimeout(3)
    // );

    // // Score Cube and Balance //
    // return Commands.sequence(
    //   // Score Cube //
    //   Commands.startEnd(
    //       () -> m_bucketSubsystem.setToPositionMM(-16384),
    //       () -> m_bucketSubsystem.setToPosition(0),
    //       m_bucketSubsystem).withTimeout(1),
    //   // Wait 0.5 seconds //
    //   Commands.waitSeconds(0.5),
    //   // Balance //
    //   Commands.startEnd(
    //       () -> m_swerveDriveSubsystem.autoDrive(0, 0.5, 0),
    //       () -> m_swerveDriveSubsystem.autoDrive(0, 0, 0),
    //       m_swerveDriveSubsystem
    //     ).withTimeout(4)
    // );

    // Score Cube and Greedy Balance //
    // return Commands.sequence(
    //   // Score Cube //
    //   Commands.startEnd(
    //       () -> m_bucketSubsystem.setToPositionMM(-16384),
    //       () -> m_bucketSubsystem.setToPosition(0),
    //       m_bucketSubsystem).withTimeout(1),
    //   // Wait 0.5 seconds //
    //   Commands.waitSeconds(0.5),
    //   Commands.startEnd(
    //       () -> m_swerveDriveSubsystem.autoDrive(0, 0.5, 0),
    //       () -> m_swerveDriveSubsystem.autoDrive(0, 0, 0),
    //       m_swerveDriveSubsystem
    //     ).withTimeout(4)
    // );
    
  }
}
