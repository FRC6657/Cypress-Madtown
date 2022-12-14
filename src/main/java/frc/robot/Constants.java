package frc.robot;

import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Transform2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.simulation.DifferentialDrivetrainSim.KitbotGearing;

public final class Constants {

    
    /**
     * Numbers start from the first picked up ball in a 5 ball auto, and then move clockwise. 4th ball is the human player station
     */
    public static class Field {

        public static final Translation2d RED_1 = new Translation2d(8.9, 8);
        public static final Translation2d RED_2 = new Translation2d(11.438, 6.383);
        public static final Translation2d RED_3 = new Translation2d(11.553, 2);
        public static final Translation2d RED_4 = new Translation2d(15.448, 7.123);

        public static final Translation2d BLUE_1 = new Translation2d(7.609, 0.303);
        public static final Translation2d BLUE_2 = new Translation2d(5.075, 1.881);
        public static final Translation2d BLUE_3 = new Translation2d(4.963, 6.2);
        public static final Translation2d BLUE_4 = new Translation2d(1.07, 1.141);

        public static final Pose2d RED_FENDER_1 = new Pose2d(8.792, 5.363, Rotation2d.fromDegrees(69));
        public static final Pose2d RED_FENDER_2 = new Pose2d(9.56, 3.656, Rotation2d.fromDegrees(-20.645));

        public static final Pose2d BLUE_FENDER_1 = new Pose2d(7.725, 2.768, Rotation2d.fromDegrees(-111.682));
        public static final Pose2d BLUE_FENDER_2 = new Pose2d(6.919, 4.7, Rotation2d.fromDegrees(-202.67));
        
    }


    public static class DriveProfile{
        public static final double kMaxDriveSpeed = 0.25 * 10; //Meters/s
        public static final double kMaxTurnSpeed = 0.25 * Units.degreesToRadians(360*3); //Rad/s

        public static final double kModDriveSpeed = 5; //Meters/s

        public static final double kDriveForwardAccel = 0.25 * 5; //Meters/s^2
        public static final double kDriveBackwardAccel = 0.25 * 5; //Meters/s^2
        public static final double kDriveForwardDecel = 0.25 * 5; //Meters/s^2
        public static final double kDriveBackwardDecel = 0.25 * 5; //Meters/s^2
        public static final double kTurnAccel = 0.25 * Units.degreesToRadians(360*3); //Rad/s^2
        public static final double kInvertAxis = -1;
    }

    public static class CAN{
        public static final int kDrive_FrontLeft = 1;
        public static final int kDrive_FrontRight = 2;
        public static final int kDrive_BackLeft = 3;
        public static final int kDrive_BackRight = 4;
        public static final int kPigeon = 5;
        public static final int kIntake = 6;
        public static final int kPCM = 7;
        public static final int kFlywheelMaster = 8;
        public static final int kFlywheelSlave = 9;
        public static final int kAccelerator = 10;
        public static final int kHood = 11;
        public static final int kRightLift = 12; 
        public static final int kLeftLift = 13;
    }

    public static class Accelerator {
        public static final double kSpeed = 0.75;
    }

    public static class Intake {
        public static final double kSpeed = .75;
    }

    public static class Lift {
        public static final double kSpeed = .5;
    }

    public static class Flywheel {
        public static final SimpleMotorFeedforward kFeedForward = new SimpleMotorFeedforward(0.8, 12d/(6380d*1.5d));
        public static final double kRPMConversionFactor = (600d/2048d) * 1.5;
        public static final double kRPMTolerance = 50;
        public static final double kFlywheelRatio = 1.5;

    }   

    public static class Hood {
        public static final double kHoodRatio = (10*5*3) * 3d/2d * 2d/5d; //90
        public static final double kCountToDegree = (kHoodRatio/360d);

    }

    public static class Drivetrain {
        
        public static final double kTrackwidth = 0.61568;
        public static final double kDistancePerPulse = (2 * Math.PI * Units.inchesToMeters(3)) / (2048 * KitbotGearing.k10p71.value); // Conversion between Counts and Meters

    }
    
    public static class Vision{
        public static final String kCameraName = "limelight";
        public static final double kCameraHeightMeters = 0.638374;// CAD Estimate
        public static final double kTargetHeightMeters = Units.feetToMeters(8 + 8 / 12); // Field Vision Target 
        //public static final double kTargetHeightMeters = Units.inchesToMeters(81.75); // Y4 Vision Target
        public static final double kCameraPitchRadians = Units.degreesToRadians(42);
        public static final double kMaxLEDRange = 200;
        public static final double kCamDiagFOV = 67.8; // degrees
        public static final int kCamResolutionWidth = 320; // pixels
        public static final int kCamResolutionHeight = 240; // pixels
        public static final double kTargetWidth = Units.feetToMeters(4);  
        public static final double kMinTargetArea = 0;
        public static final Pose2d kTargetPos = new Pose2d(8.259, 4.138, Rotation2d.fromDegrees(0));
        public static final Transform2d kCameraToRobot = new Transform2d(
            new Translation2d(-0.008486, -0.403435),
            new Rotation2d(Units.degreesToRadians(180))
        );
    }
}
