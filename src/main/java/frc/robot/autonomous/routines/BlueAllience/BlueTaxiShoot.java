package frc.robot.autonomous.routines.BlueAllience;

import java.util.List;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants;
import frc.robot.autonomous.Trajectories;
import frc.robot.autonomous.common.FireOne;
import frc.robot.autonomous.common.FireTwo;
import frc.robot.subsystems.AcceleratorSubsystem;
import frc.robot.subsystems.DrivetrainSubsystem;
import frc.robot.subsystems.intake.IntakePistonsSubsystem;
import frc.robot.subsystems.intake.IntakeSubsystem;
import frc.robot.subsystems.shooter.FlywheelSubsystem;
import frc.robot.subsystems.shooter.HoodSubsystem;

public class BlueTaxiShoot extends SequentialCommandGroup {
  public BlueTaxiShoot(DrivetrainSubsystem drivetrain, IntakeSubsystem intake, IntakePistonsSubsystem pistons,  FlywheelSubsystem flywheel, HoodSubsystem hood, AcceleratorSubsystem accelerator) {
    addCommands(
      new InstantCommand(() -> drivetrain.resetOdometry(Constants.Field.BLUE_FENDER_1)),
      //drivetrain.new TrajectoryFollowerCommand(PATH_TO_FENDER),
      new SequentialCommandGroup(
        new InstantCommand(pistons::extend),
        new InstantCommand(() -> hood.setTargetAngle(2)),
        new InstantCommand(() -> flywheel.easyShoot(8)),
        new WaitCommand(2),
        new InstantCommand(accelerator::start),
        new WaitCommand(2),
        new InstantCommand(pistons::retract),
        new InstantCommand(flywheel::stop),
        new InstantCommand(accelerator::stop)
      ),
      drivetrain.new TrajectoryFollowerCommand(TAXI_PATH)
    );
  }


  private Trajectory PATH_TO_FENDER = Trajectories.generateTrajectory(3, 2, List.of(
    new Pose2d(Constants.Field.BLUE_1, Rotation2d.fromDegrees(-90)),
    Constants.Field.BLUE_FENDER_1
  ), 
  true, 
  "BLUE FIVE PATH_TO_FENDER"
  );

  private Trajectory TAXI_PATH = Trajectories.generateTrajectory(3, 2, List.of(
    Constants.Field.BLUE_FENDER_1,
    new Pose2d(Constants.Field.BLUE_2.minus(new Translation2d(0,1.25)), Rotation2d.fromDegrees(180))
  ), 
  false, 
  "BLUE FIVE PATH_TO_FENDER"
  );

}

