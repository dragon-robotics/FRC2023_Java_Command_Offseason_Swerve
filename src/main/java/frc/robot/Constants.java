// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.math.util.Units;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
  
  /** General robot constants */
  public static final class GeneralConstants {
    // Enable or disable competition mode
    public static final boolean tuningMode = true;

    // Joystick axis deadband for the swerve drive
    public static final double swerveDeadband = 0.1;

    public static final double voltageComp = 10.0;

    // Hold time on motor brakes when disabled
    public static final double wheelLockTime = 10;

    public static final double robotMass = (148 - 20.3) * 0.453592;
    public static final double chassisMass = robotMass;
    public static final Translation3d chassisCG = new Translation3d(0, 0, Units.inchesToMeters(8));
    public static final double loopTime = 0.13;
  }

  public static class OperatorConstants {
    public static final int kDriverControllerPort = 0;
    public static final int kOperatorControllerPort = 1;
  }

  public static class JoystickConstants {
    // Joystick Analog Axis/Stick //
    public static final int STICK_LEFT_X = 0;
    public static final int STICK_LEFT_Y = 1;
    public static final int TRIGGER_LEFT = 2;
    public static final int TRIGGER_RIGHT = 3;
    public static final int STICK_RIGHT_X = 4;
    public static final int STICK_RIGHT_Y = 5;

    // Joystick Buttons //
    public static final int BTN_A = 1;
    public static final int BTN_B = 2;
    public static final int BTN_X = 3;
    public static final int BTN_Y = 4;
    public static final int BUMPER_LEFT = 5;
    public static final int BUMPER_RIGHT = 6;
    public static final int BTN_BACK = 7;
    public static final int BTN_START = 8;
    public static final int BTN_STICK_LEFT = 9;
    public static final int BTN_STICK_RIGHT = 10;
  }

  public static class Extreme3DProConstants {
    // Extreme 3D Pro Analog Axis/Stick //
    public static final int X_AXIS = 0;
    public static final int Y_AXIS = 1;
    public static final int ROTATE = 2;
    public static final int SLIDER = 3;

    // Extreme 3D Pro Buttons //
    public static final int BTN_TRIGGER = 1;
    public static final int BTN_THUMB = 2;
    public static final int BTN_BOT_LEFT = 3;
    public static final int BTN_BOT_RIGHT = 4;
    public static final int BTN_TOP_LEFT = 5;
    public static final int BTN_TOP_RIGHT = 6;
    public static final int BTN_7 = 7;
    public static final int BTN_8 = 8;
    public static final int BTN_9 = 9;
    public static final int BTN_10 = 10;
    public static final int BTN_11 = 11;
    public static final int BTN_12 = 12;
  }

  public static class BucketMotorConstants {
    /**
     * Which PID slot to pull gains from. Starting 2018, you can choose from
     * 0,1,2 or 3. Only the first two (0,1) are visible in web-based
     * configuration.
     */
    public static final int kSlotIdx = 0;

    /**
     * Talon FX supports multiple (cascaded) PID loops. For
     * now we just want the primary one.
     */
    public static final int kPIDLoopIdx = 0;

    /**
     * set to zero to skip waiting for confirmation, set to nonzero to wait and
     * report to DS if action fails.
     */
    public static final int kTimeoutMs = 30;

    /**
     * set deadband to super small 0.001 (0.1 %). 
     * The default deadband is 0.04 (4 %)
     */
    public static final double kDeadband = 0.001;

    /**
	   * Gains used in Motion Magic, to be adjusted accordingly
     * Gains(kp, ki, kd, kf, izone, peak output);
     */
    public static final double kP = 0.2;
    public static final double kI = 0.0;
    public static final double kD = 0.0;
    public static final double kF = 0.2;
    public static final int kIzone = 0;
    public static final double kPeakOutput = 1.0;
  }
}
