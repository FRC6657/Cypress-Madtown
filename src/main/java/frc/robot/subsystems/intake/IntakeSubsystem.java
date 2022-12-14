package frc.robot.subsystems.intake;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatusFrame;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import io.github.oblarg.oblog.annotations.Log;

public class IntakeSubsystem extends SubsystemBase {
  
  private static WPI_TalonSRX mMotor = new WPI_TalonSRX(Constants.CAN.kIntake);

  private Timer mTimer = new Timer();

  public IntakeSubsystem() {
    configureMotor();
  }

  public void configureMotor(){
    mMotor.configFactoryDefault();
    mMotor.setInverted(true);
    mMotor.setNeutralMode(NeutralMode.Coast);
    mMotor.configSupplyCurrentLimit(new SupplyCurrentLimitConfiguration(true, 20, 20, 0));
    mMotor.setStatusFramePeriod(StatusFrame.Status_2_Feedback0, 250);
    mMotor.setStatusFramePeriod(StatusFrameEnhanced.Status_3_Quadrature, 250);
    mMotor.setStatusFramePeriod(StatusFrame.Status_6_Misc, 250);
    mMotor.setStatusFramePeriod(StatusFrameEnhanced.Status_8_PulseWidth, 250);
    mMotor.setStatusFramePeriod(StatusFrame.Status_9_MotProfBuffer, 250);
    mMotor.setStatusFramePeriod(StatusFrame.Status_10_MotionMagic, 250);
    mMotor.setStatusFramePeriod(StatusFrameEnhanced.Status_11_UartGadgeteer, 250);
    mMotor.setStatusFramePeriod(StatusFrame.Status_12_Feedback1, 250);
    mMotor.setStatusFramePeriod(StatusFrame.Status_13_Base_PIDF0, 250);
    mMotor.setStatusFramePeriod(StatusFrame.Status_14_Turn_PIDF1, 250);
    mMotor.setStatusFramePeriod(StatusFrame.Status_17_Targets1, 250);
    mMotor.stopMotor();
  }

  public void set(double percent){
    mMotor.set(percent);
    mTimer.reset();
    mTimer.start();
  }

  public void start(){
    set(Constants.Intake.kSpeed);
  }

  public void reverse() {
    set(-Constants.Intake.kSpeed);
  }

  public void stop(){
    mMotor.stopMotor();
    mTimer.reset();
  }

  @Log(tabName = "Intake")
  private double getAmps(){
    return mMotor.getSupplyCurrent();
  }

  @Log(tabName = "Intake")
  public boolean ballDetected(){
    return (getAmps() > 5) && mTimer.get() > 0.125;
  }

}
