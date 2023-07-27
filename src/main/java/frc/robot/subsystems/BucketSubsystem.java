// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.TalonFXFeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.BucketMotorConstants;

public class BucketSubsystem extends SubsystemBase {

  private final WPI_TalonFX m_bucketMotor = new WPI_TalonFX(BucketMotorConstants.kBucketMotor);

  // Position of the motor //
  private int m_position;

  // Control mode of the motor //
  private TalonFXControlMode m_controlMode;

  /** Creates a new BucketSubsystem. */
  public BucketSubsystem() {

    // Set all TalonFX defaults //
    // Factory default configurations for all motors //
    m_bucketMotor.configFactoryDefault();

    // Disable all motors //
    m_bucketMotor.set(TalonFXControlMode.PercentOutput, 0);

    // Set neutral mode to coast on all motors //
    m_bucketMotor.setNeutralMode(NeutralMode.Brake);

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

    /* Set relevant frame periods to be at least as fast as periodic rate */
		m_bucketMotor.setStatusFramePeriod(
        StatusFrameEnhanced.Status_13_Base_PIDF0, 10, BucketMotorConstants.kTimeoutMs);
		m_bucketMotor.setStatusFramePeriod(
        StatusFrameEnhanced.Status_10_MotionMagic, 10, BucketMotorConstants.kTimeoutMs);

    /* Set the peak and nominal outputs */
    m_bucketMotor.configNominalOutputForward(0, BucketMotorConstants.kTimeoutMs);
    m_bucketMotor.configNominalOutputReverse(0, BucketMotorConstants.kTimeoutMs);
    m_bucketMotor.configPeakOutputForward(1, BucketMotorConstants.kTimeoutMs);
    m_bucketMotor.configPeakOutputReverse(-1, BucketMotorConstants.kTimeoutMs);

    /* Configure PID Values for Motion Magic */
    m_bucketMotor.config_kP(
      BucketMotorConstants.kMMSlotIdx,
      BucketMotorConstants.kMMP,
      BucketMotorConstants.kTimeoutMs);
    m_bucketMotor.config_kI(
      BucketMotorConstants.kMMSlotIdx,
      BucketMotorConstants.kMMI,
      BucketMotorConstants.kTimeoutMs);
    m_bucketMotor.config_kD(
      BucketMotorConstants.kMMSlotIdx,
      BucketMotorConstants.kMMD,
      BucketMotorConstants.kTimeoutMs);
    m_bucketMotor.config_kF(
      BucketMotorConstants.kMMSlotIdx,
      BucketMotorConstants.kMMF,
      BucketMotorConstants.kTimeoutMs);
    m_bucketMotor.config_IntegralZone(
      BucketMotorConstants.kMMSlotIdx,
      BucketMotorConstants.kMMIzone,
      BucketMotorConstants.kTimeoutMs);
    m_bucketMotor.configClosedLoopPeakOutput(
      BucketMotorConstants.kMMSlotIdx,
      BucketMotorConstants.kMMPeakOutput,
      BucketMotorConstants.kTimeoutMs);

    /* Configure PID Values for Position */
    m_bucketMotor.config_kP(
      BucketMotorConstants.kPosSlotIdx,
      BucketMotorConstants.kPosP,
      BucketMotorConstants.kTimeoutMs);
    m_bucketMotor.config_kI(
      BucketMotorConstants.kPosSlotIdx,
      BucketMotorConstants.kPosI,
      BucketMotorConstants.kTimeoutMs);
    m_bucketMotor.config_kD(
      BucketMotorConstants.kPosSlotIdx,
      BucketMotorConstants.kPosD,
      BucketMotorConstants.kTimeoutMs);
    m_bucketMotor.config_kF(
      BucketMotorConstants.kPosSlotIdx,
      BucketMotorConstants.kPosF,
      BucketMotorConstants.kTimeoutMs);
    m_bucketMotor.config_IntegralZone(
      BucketMotorConstants.kPosSlotIdx,
      BucketMotorConstants.kPosIzone,
      BucketMotorConstants.kTimeoutMs);
    m_bucketMotor.configClosedLoopPeakOutput(
      BucketMotorConstants.kPosSlotIdx,
      BucketMotorConstants.kPosPeakOutput,
      BucketMotorConstants.kTimeoutMs);    
    
    /* Set acceleration and vcruise velocity - see documentation */
    m_bucketMotor.configMotionCruiseVelocity(
        15000,
        BucketMotorConstants.kTimeoutMs);

    m_bucketMotor.configMotionAcceleration(
        6000,
        BucketMotorConstants.kTimeoutMs);
    
    /* Zero the sensor once on robot boot up */
		m_bucketMotor.setSelectedSensorPosition(
      0,
      BucketMotorConstants.kPIDLoopIdx,
      BucketMotorConstants.kTimeoutMs);
    
    // Set motor position to 0 //
    m_position = 0;

    // Set motor control to Motion Magic //
    m_controlMode = TalonFXControlMode.MotionMagic;
    m_bucketMotor.selectProfileSlot(
      BucketMotorConstants.kMMSlotIdx, BucketMotorConstants.kPIDLoopIdx);

    // m_bucketMotor.set(TalonFXControlMode.MotionMagic, 0);

    // Configure smoothness of motion magic //
    m_bucketMotor.configMotionSCurveStrength(1);

    // Configure Closed-Loop Ramp Rate //
    // m_bucketMotor.configClosedloopRamp(0.1, 0);
  }

  public void setToPosition(int position) {
    /* 2048 ticks/rev * 10 Rotations in either direction */
    // double targetPos = position * 2048 * 10.0;
    m_bucketMotor.selectProfileSlot(
      BucketMotorConstants.kPosSlotIdx, BucketMotorConstants.kPIDLoopIdx);
    m_controlMode = TalonFXControlMode.Position;
    m_position = position;
    // m_bucketMotor.set(TalonFXControlMode.MotionMagic, m_position);
  }

  public void setToPositionMM(int position) {
    m_bucketMotor.selectProfileSlot(
      BucketMotorConstants.kMMSlotIdx, BucketMotorConstants.kPIDLoopIdx);
    m_controlMode = TalonFXControlMode.MotionMagic;
    m_position = position;
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

  public void move(double speed) {
    m_bucketMotor.set(TalonFXControlMode.PercentOutput, speed);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    m_bucketMotor.set(m_controlMode, m_position);
  }
}
