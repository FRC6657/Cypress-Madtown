package frc.robot;

import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.FieldObject2d;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.PrintCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.StartEndCommand;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.POVButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.autonomous.common.FireOne;
// import frc.robot.autonomous.routines.BlueAllience.BlueFenderFive;
// import frc.robot.autonomous.routines.BlueAllience.BlueFenderThree;
// import frc.robot.autonomous.routines.BlueAllience.BlueFenderTwoHanger;
// import frc.robot.autonomous.routines.BlueAllience.BlueFenderTwoMid;
// import frc.robot.autonomous.routines.BlueAllience.BlueFenderTwoWall;
import frc.robot.autonomous.routines.BlueAllience.BlueTaxiShoot;
// import frc.robot.autonomous.routines.BlueAllience.Taxi;
// import frc.robot.autonomous.routines.RedAlliance.RedFenderFive;
// import frc.robot.autonomous.routines.RedAlliance.RedFenderThree;
// import frc.robot.autonomous.routines.RedAlliance.RedFenderTwoHanger;
// import frc.robot.autonomous.routines.RedAlliance.RedFenderTwoMid;
// import frc.robot.autonomous.routines.RedAlliance.RedFenderTwoWall;
// import frc.robot.autonomous.routines.RedAlliance.RedTwoHanger;
import frc.robot.custom.ArborMath;

import frc.robot.custom.controls.Deadbander;
import frc.robot.subsystems.AcceleratorSubsystem;
import frc.robot.subsystems.DrivetrainSubsystem;
import frc.robot.subsystems.LiftSubsystem;
import frc.robot.subsystems.VisionSubsystem;
import frc.robot.subsystems.intake.IntakePistonsSubsystem;
import frc.robot.subsystems.intake.IntakeSubsystem;
import frc.robot.subsystems.shooter.FlywheelSubsystem;
import frc.robot.subsystems.shooter.HoodSubsystem;
// import frc.robot.subsystems.shooter.Interpolation.InterpolatingTable;

public class RobotContainer {

  private VisionSubsystem vision = new VisionSubsystem();
  private AcceleratorSubsystem accelerator = new AcceleratorSubsystem();
  private LiftSubsystem lift = new LiftSubsystem();
  private IntakeSubsystem intake = new IntakeSubsystem();
  private IntakePistonsSubsystem pistons = new IntakePistonsSubsystem();
  private FlywheelSubsystem flywheel = new FlywheelSubsystem(vision.visionSupplier);
  private HoodSubsystem hood = new HoodSubsystem(vision.visionSupplier);
  private DrivetrainSubsystem drivetrain = new DrivetrainSubsystem(vision.visionSupplier);

  private Trigger intakeExtended = new Trigger(pistons::extended);

  //private SendableChooser<SequentialCommandGroup[]> mAutoChooser = new SendableChooser<>();

  private static Field2d mField = new Field2d();
  private FieldObject2d mIntakeVisualizer = mField.getObject("Intake");
  private SlewRateLimiter mIntakeAnimator = new SlewRateLimiter(1);

  private Joystick mDriverController = new Joystick(0);

  JoystickButton mTrigger = new JoystickButton(mDriverController, 1);
  JoystickButton mSide = new JoystickButton(mDriverController, 2);  
  JoystickButton mBottomLeft = new JoystickButton(mDriverController, 3);
  JoystickButton mBottomRight = new JoystickButton(mDriverController, 4);
  JoystickButton mTopLeft = new JoystickButton(mDriverController, 5);
  JoystickButton mTopRight = new JoystickButton(mDriverController, 6);
  JoystickButton m7 = new JoystickButton(mDriverController, 7);
  JoystickButton m8 = new JoystickButton(mDriverController, 8);
  JoystickButton m9 = new JoystickButton(mDriverController, 9);
  JoystickButton m10 = new JoystickButton(mDriverController, 10);
  JoystickButton m11 = new JoystickButton(mDriverController, 11);
  JoystickButton m12 = new JoystickButton(mDriverController, 12);
  
  POVButton mJoystickHatRight = new POVButton(mDriverController, 90);
  POVButton mJoystickHatUp = new POVButton(mDriverController, 0);
  POVButton mJoystickHatLeft = new POVButton(mDriverController, 270);
  POVButton mJoystickHatDown = new POVButton(mDriverController, 180);


  public RobotContainer() {

    LiveWindow.disableAllTelemetry();

    configureButtonBindings();
    //configureAutoChooser();

    SmartDashboard.putData(mField);

    //  double throttle = mDriverController.getThrottle(); 
    //  throttle = 0;
    //  double invertAxis;
    //  invertAxis = 1;

    //  if (throttle == 100) {
    //     invertAxis = -1;
    //  } else if (throttle == 0) {
    //     invertAxis = 1;
    //  }

      drivetrain.setDefaultCommand(
        drivetrain.new TeleOpCommand(
          () -> ArborMath.signumPow(Deadbander.applyLinearScaledDeadband(mDriverController.getRawAxis(1), 0.075), 1.2),
          () -> ArborMath.signumPow(-Deadbander.applyLinearScaledDeadband(mDriverController.getRawAxis(2), 0.075), 1.2),
          () -> mDriverController.getTrigger()
        )
      );

    NetworkTableInstance.getDefault().getTable("photonvision").getEntry("version").setValue("v2022.1.6");

  }

  private void configureButtonBindings() {

    //Automated Pistons and Intake 
    m8.whenHeld(
        new StartEndCommand(
          pistons::extend, 
          pistons::retract,
          pistons
        )
    );

    //Lift up
    m9.whenHeld(
      new ParallelCommandGroup(
        new InstantCommand(hood::stop, hood),
        new StartEndCommand(
          () -> lift.set(1), 
          lift::stop, lift
        )
      )
    );

    //Lift down
    m11.whenHeld(
      new ParallelCommandGroup(
        new InstantCommand(hood::stop, hood),
        new StartEndCommand(
          () -> lift.set(-1), 
          lift::stop, 
          lift
        )
      )
    );

    mJoystickHatDown.whenHeld(
      new ParallelCommandGroup(
        new StartEndCommand(
          intake::start,
          intake::stop,
          intake
        ),
        new StartEndCommand(
          accelerator::start,
          accelerator::stop,
          accelerator
        )
      ) 
    );

    mJoystickHatUp.whenHeld(
      new ParallelCommandGroup(
        new StartEndCommand(
          intake::reverse,
          intake::stop,
          intake
        ),
        new StartEndCommand(
          accelerator::reverse,
          accelerator::stop,
          accelerator
        )
      ) 
    );

    mJoystickHatRight.whenHeld(
      new StartEndCommand(
        accelerator::start,
        accelerator::stop,
        accelerator
      )
    );

    mJoystickHatLeft.whenHeld(
      new StartEndCommand(
        accelerator::reverse,
        accelerator::stop,
        accelerator
      )
    );
    
    //Fender Shot bR
    mBottomRight.whenHeld(
      new SequentialCommandGroup(
            new ParallelCommandGroup(
                new InstantCommand(() -> flywheel.easyShoot(8.6)),
                new InstantCommand(() -> hood.setTargetAngle(2), hood)),
            new WaitCommand(2.5),
            new InstantCommand(accelerator::start, accelerator),
            new WaitCommand(1),
            new InstantCommand(() -> accelerator.set(-1)),
            new WaitCommand(0.25),
            new InstantCommand(accelerator::stop),
            new InstantCommand(accelerator::start),
            new InstantCommand(accelerator::stop),
            new WaitCommand(1),
            new InstantCommand(accelerator::start),
            new InstantCommand(pistons::extend))
        ).whenReleased(
            new ParallelCommandGroup(
                new InstantCommand(pistons::retract),
                new InstantCommand(flywheel::stop, flywheel),
                new InstantCommand(hood::stop, hood),
                new InstantCommand(accelerator::stop, accelerator)

            )
    );


    mTopLeft.whenHeld(
      new FireOne(flywheel, hood, accelerator, pistons, 1000, 1)
    ).whenReleased(
      new ParallelCommandGroup(
          new InstantCommand(flywheel::stop, flywheel),
          new InstantCommand(hood::stop, hood),
          new InstantCommand(accelerator::stop, accelerator),
          new InstantCommand(pistons::retract, pistons)
      )
  );

  // mOperatorController.b().whenHeld(
  //   new StartEndCommand(accelerator::start, accelerator::stop, accelerator)
  // );


    // Low Hub & Pop
    m10.whenHeld(
        new SequentialCommandGroup(
            new ParallelCommandGroup(
                new InstantCommand(() -> flywheel.setTargetRPM(1250), flywheel),
                new InstantCommand(() -> hood.setTargetAngle(12), hood)),
            new WaitUntilCommand(() -> flywheel.ready()),
            new InstantCommand(pistons::extend, pistons),
            new RunCommand(accelerator::start, accelerator).withInterrupt(() -> flywheel.shotDetector()),
            new InstantCommand(() -> accelerator.set(-1)),
            new WaitCommand(0.25),
            new InstantCommand(accelerator::stop),
            new WaitUntilCommand(() -> flywheel.ready()),
            new RunCommand(accelerator::start)))
        .whenReleased(
            new ParallelCommandGroup(
                new InstantCommand(flywheel::stop, flywheel),
                new InstantCommand(hood::stop, hood),
                new InstantCommand(accelerator::stop, accelerator),
                new InstantCommand(pistons::retract, pistons)
            )
        );

    // m10.whenHeld(
    //       new StartEndCommand(
    //     () -> hood.setTargetAngle(InterpolatingTable.get(vision.visionSupplier.getPitch()).hoodAngle), hood::stop, hood
    //   ).beforeStarting(
    //     new PrintCommand(Double.toString(vision.visionSupplier.getPitch()))
    //   )
    // );

    //All driver controls

    //Aimbot
    m7.whenHeld(
      drivetrain.new VisionAimAssist()
        .beforeStarting(
          new SequentialCommandGroup(
            new InstantCommand(() -> vision.visionSupplier.enableLEDs()),
            new WaitCommand(0.25)
          )
          ).andThen(new InstantCommand(() -> vision.visionSupplier.disableLEDs()))
    );

    //Used after aimbot (5)
    mTopLeft.whenHeld(
      new FireOne(flywheel, hood, accelerator, pistons, 1000, 1)
    ).whenReleased(
      new ParallelCommandGroup(
          new InstantCommand(flywheel::stop, flywheel),
          new InstantCommand(hood::stop, hood),
          new InstantCommand(accelerator::stop, accelerator),
          new InstantCommand(pistons::retract, pistons)
      )
  );

    // Tarmac Shot (6)
    // mTopRight.whenHeld(
    //     new SequentialCommandGroup(
    //         new ParallelCommandGroup(
    //             new InstantCommand(() -> flywheel.setTargetRPM(3150), flywheel),
    //             //4 volts
    //             new InstantCommand(() -> hood.setTargetAngle(4), hood)),
    //         new WaitUntilCommand(() -> flywheel.ready()),
    //         new RunCommand(accelerator::start, accelerator).withInterrupt(() -> flywheel.shotDetector()),
    //         new InstantCommand(() -> accelerator.set(-1)),
    //         new WaitCommand(0.25),
    //         new InstantCommand(accelerator::stop),
    //         new WaitUntilCommand(() -> flywheel.ready()),
    //         new RunCommand(accelerator::start)))
    //     .whenReleased(
    //         new ParallelCommandGroup(
    //             new InstantCommand(flywheel::stop, flywheel),
    //             new InstantCommand(hood::stop, hood),
    //             new InstantCommand(accelerator::stop, accelerator)

    //         )
    //     );

    mTopRight.whenHeld(
      new SequentialCommandGroup(
            new ParallelCommandGroup(
                new InstantCommand(() -> flywheel.easyShoot(8.2)),
                //4 volts
                new InstantCommand(() -> hood.setTargetAngle(2), hood)),
            new WaitCommand(2.5),
            new InstantCommand(accelerator::start, accelerator),
            new WaitCommand(1),
            new InstantCommand(() -> accelerator.set(-1)),
            new WaitCommand(0.25),
            new InstantCommand(accelerator::stop),
            //new WaitUntilCommand(() -> flywheel.ready()),
            new InstantCommand(accelerator::start),
            new InstantCommand(accelerator::stop),
            new WaitCommand(1),
            new InstantCommand(accelerator::start),
            new InstantCommand(pistons::extend))
        ).whenReleased(
            new ParallelCommandGroup(
                new InstantCommand(pistons::retract),
                new InstantCommand(flywheel::stop, flywheel),
                new InstantCommand(hood::stop, hood),
                new InstantCommand(accelerator::stop, accelerator)

            )
  
    );

    

  }
  
  // public void configureAutoChooser(){
    
  //   mAutoChooser.setDefaultOption("Nothing", new SequentialCommandGroup[]{null,null});

  //   mAutoChooser.addOption("Taxi Shoot", new SequentialCommandGroup[]{
  //     new BlueTaxiShoot(drivetrain, intake, pistons, flywheel, hood, accelerator)
  //   });

  //   mAutoChooser.addOption("FenderFive", new SequentialCommandGroup[]{
  //     new RedFenderFive(drivetrain, intake, pistons, accelerator, flywheel, hood),
  //     new BlueFenderFive(drivetrain, intake, pistons, accelerator, flywheel, hood)
  //   });

  //   mAutoChooser.addOption("FenderThree", new SequentialCommandGroup[]{
  //     new RedFenderThree(drivetrain, intake, pistons, flywheel, hood, accelerator),
  //     new BlueFenderThree(drivetrain, intake, pistons, accelerator, flywheel, hood)
  //   });

  //   mAutoChooser.addOption("FenderTwoHanger", new SequentialCommandGroup[]{
  //     new RedFenderTwoHanger(drivetrain, intake, pistons, flywheel, hood, accelerator),
  //     new BlueFenderTwoHanger(drivetrain, intake, pistons, flywheel, hood, accelerator)
  //   });

  //   mAutoChooser.addOption("FenderTwoMid", new SequentialCommandGroup[]{
  //     new RedFenderTwoMid(drivetrain, intake, pistons, flywheel, hood, accelerator),
  //     new BlueFenderTwoMid(drivetrain, intake, pistons, flywheel, hood, accelerator)
  //   });
    
  //   mAutoChooser.addOption("FenderTwoWall", new SequentialCommandGroup[]{
  //     new RedFenderTwoWall(drivetrain, intake, pistons, flywheel, hood, accelerator),
  //     new BlueFenderTwoWall(drivetrain, intake, pistons, flywheel, hood, accelerator)
  //   });

  //   mAutoChooser.addOption("Taxi", new SequentialCommandGroup[]{
  //     new Taxi(drivetrain, intake, pistons, accelerator, flywheel, hood, vision.visionSupplier),
  //     new Taxi(drivetrain, intake, pistons, accelerator, flywheel, hood, vision.visionSupplier)
  //   });

  //   mAutoChooser.addOption("2 Ball Hangar", new SequentialCommandGroup[]{
  //     new RedTwoHanger(drivetrain, intake, pistons, flywheel, hood, accelerator, vision.visionSupplier),
  //     new RedTwoHanger(drivetrain, intake, pistons, flywheel, hood, accelerator, vision.visionSupplier)
  //   });

  //   SmartDashboard.putData("Auto Chooser", mAutoChooser);

  // }

  public void updateField(){
    if(intakeExtended.getAsBoolean()){
      mIntakeVisualizer.setPose(
        new Pose2d(
          (drivetrain.getRobotPosition().getX() + mIntakeAnimator.calculate(0.6056505) * Math.cos(Units.degreesToRadians(drivetrain.getRobotPosition().getRotation().getDegrees()))),
          (drivetrain.getRobotPosition().getY() + mIntakeAnimator.calculate(0.6056505) * Math.sin(Units.degreesToRadians(drivetrain.getRobotPosition().getRotation().getDegrees()))),
          drivetrain.getRobotPosition().getRotation()
        )
      );
    }else{
      mIntakeVisualizer.setPose(
        new Pose2d(
          (drivetrain.getRobotPosition().getX() + mIntakeAnimator.calculate(0.3796505) * Math.cos(Units.degreesToRadians(drivetrain.getRobotPosition().getRotation().getDegrees()))),
          (drivetrain.getRobotPosition().getY() + mIntakeAnimator.calculate(0.3796505) * Math.sin(Units.degreesToRadians(drivetrain.getRobotPosition().getRotation().getDegrees()))),
          drivetrain.getRobotPosition().getRotation()
        )
      );
    }

    mField.setRobotPose(drivetrain.getRobotPosition());

    vision.visionSupplier.processSim(drivetrain.getRobotPosition());

  }


  public SequentialCommandGroup getAutonomousCommand() {
    return new SequentialCommandGroup(
      new BlueTaxiShoot(drivetrain, intake, pistons, flywheel, hood, accelerator));

  }
  //   int alliance = 0;
  //   if(DriverStation.getAlliance() == Alliance.Red){
  //     alliance = 0;
  //   }else{
  //     alliance = 1;
  //   }
  //   //return new RoutineTesting(drivetrain, intake, pistons, flywheel, hood, accelerator, vision.visionSupplier);
  //   return mAutoChooser.getSelected()[alliance];
  //   //return new BlueFenderTwoHanger(drivetrain, intake, pistons, flywheel, hood, accelerator);
  // }

  public static Field2d getField(){ 
    return mField;
  }

  public Command stopAll(){
      return new ParallelCommandGroup(
        new InstantCommand(intake::stop, intake),
        new InstantCommand(pistons::retract, pistons),
        new InstantCommand(accelerator::stop, accelerator),
        new InstantCommand(lift::stop, lift),
        new InstantCommand(flywheel::stop, flywheel),
        new InstantCommand(vision.visionSupplier::disableLEDs, vision),
        new InstantCommand(drivetrain::stop, drivetrain),
        new InstantCommand(hood::stop, hood),
        new PrintCommand("Robot Disabled")
      );
  }
}
