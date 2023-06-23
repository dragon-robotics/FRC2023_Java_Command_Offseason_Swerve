// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.TalonFXFeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.BucketMotorConstants;

public class BucketSubsystem extends SubsystemBase {

  private final WPI_TalonFX m_bucketMotor = new WPI_TalonFX(5);

  /** Creates a new BucketSubsystem. */
  public BucketSubsystem() {

    // Set all TalonFX defaults //
    // Factory default configurations for all motors //
    m_bucketMotor.configFactoryDefault();

    // Disable all motors //
    m_bucketMotor.set(TalonFXControlMode.PercentOutput, 0);

    // Set neutral mode to coast on all motors //
    m_bucketMotor.setNeutralMode(NeutralMode.Coast);

    // Set Falcon 500 Voltage Compensation to 10V //
    m_bucketMotor.configVoltageCompSaturation(10);

    // Configure the sensor //
    m_bucketMotor.configSelectedFeedbackSensor(
        TalonFXFeedbackDevice.IntegratedSensor,
        BucketMotorConstants.kPIDLoopIdx,
        BucketMotorConstants.kTimeoutMs);

    /**
     * set deadband to super small 0.001 (0.1 %). 
     * The default deadband is 0.04 (4 %)
     */
    m_bucketMotor.configNeutralDeadband(
        BucketMotorConstants.kDeadband,
        BucketMotorConstants.kTimeoutMs);
    
    /**
		 * Configure Talon FX Output and Sensor direction accordingly Invert Motor to
		 * have green LEDs when driving Talon Forward / Requesting Postiive Output Phase
		 * sensor to have positive increment when driving Talon Forward (Green LED)
		 */
    m_bucketMotor.setSensorPhase(false);
    m_bucketMotor.setInverted(false);

    /* Set acceleration and vcruise velocity - see documentation */
    m_bucketMotor.configMotionCruiseVelocity(
        15000,
        BucketMotorConstants.kTimeoutMs);

    m_bucketMotor.configMotionAcceleration(
        6000,
        BucketMotorConstants.kTimeoutMs);

    // Set position to 0 //
    /* Zero the sensor once on robot boot up */
		m_bucketMotor.setSelectedSensorPosition(
      0,
      BucketMotorConstants.kPIDLoopIdx,
      BucketMotorConstants.kTimeoutMs);
    
  }

  public void setToPosition(int position) {
    /* 2048 ticks/rev * 10 Rotations in either direction */
			double targetPos = position * 2048 * 10.0;
			m_bucketMotor.set(TalonFXControlMode.MotionMagic, targetPos);
  }

  public void setMotionSCurveStrength(int smoothing) {
    if (smoothing < 0) {
      smoothing = 0;
    }
    else if (smoothing > 8) {
      smoothing = 8;
    }

    m_bucketMotor.configMotionSCurveStrength(smoothing);
  }

  public void zeroMotorPosition() {
    m_bucketMotor.setSelectedSensorPosition(0);
  }

  public void stop() {
    m_bucketMotor.set(TalonFXControlMode.PercentOutput, 0);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
