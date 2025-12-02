package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;
import static org.firstinspires.ftc.robotcore.external.navigation.AngleUnit.DEGREES;

import android.net.wifi.rtt.RangingRequest;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;

public class MecanumDrive {

    private DcMotor frontLeft, frontRight, backLeft, backRight;
    private IMU imu;
    public void init(HardwareMap hwMap) {
        // Finds and initializes all of the Motors
        // Requires motors named "top_left_motor", "top_right_motor", "top_left_motor", and "top_right_motor"
        frontLeft = hwMap.get(DcMotor.class, "top_left_motor");
        frontRight = hwMap.get(DcMotor.class, "top_right_motor");
        backLeft = hwMap.get(DcMotor.class, "bottom_left_motor");
        backRight = hwMap.get(DcMotor.class, "bottom_right_motor");

        // Sets the right side to reverse for mecanum
        frontRight.setDirection(DcMotor.Direction.REVERSE);
        backRight.setDirection(DcMotor.Direction.REVERSE);

        // Sets the motors to run using the encoders, meaning it will do more fancy stuff in the background to make our motors perform more optimally.
        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // initialize the limelight camera
        // initialize the intertial measurement unit
        imu = hwMap.get(IMU.class, "imu");

        // Sets the Orientation of the robot so it knows whats the front of the robot and stuff
        // This assumes the hub is mounted on the left side with the logo facing the right, and the USB ports face up.
        RevHubOrientationOnRobot RevOrientation = new RevHubOrientationOnRobot(RevHubOrientationOnRobot.LogoFacingDirection.RIGHT, RevHubOrientationOnRobot.UsbFacingDirection.UP);

        imu.initialize(new IMU.Parameters(RevOrientation));
        imu.resetYaw();

    }



    public void drive(double forward, double strafe, double rotate) {
        // does the math to set the correct power to each wheel
        double frontLeftPower = forward + strafe + rotate;
        double backLeftPower = forward - strafe + rotate;
        double frontRightPower = forward - strafe - rotate;
        double backRightPower = forward + strafe - rotate;

        double maxPower = 1;
        double maxSpeed = 1;

        // Checks to see which value is the maximum value so we can adjust for it
        // If we didn't do this, we could theoretically get powers of up to 3, but it is limited to one, so it would not drive as expected.
        maxPower = Math.max(maxPower, Math.abs(frontLeftPower));
        maxPower = Math.max(maxPower, Math.abs(backLeftPower));
        maxPower = Math.max(maxPower, Math.abs(frontRightPower));
        maxPower = Math.max(maxPower, Math.abs(backRightPower));

        frontLeft.setPower(maxSpeed * (frontLeftPower/maxPower));
        backLeft.setPower(maxSpeed * (backLeftPower/maxPower));
        frontRight.setPower(maxSpeed * (frontRightPower/maxPower));
        backRight.setPower(maxSpeed * (backRightPower/maxPower));
    }



    public void driveFieldRelative(double forward, double strafe, double rotate) {
        double theta = Math.atan2(forward, strafe);
        double r = Math.hypot(strafe, forward);

        // Gets Polar Coordinates of our robot
        theta = AngleUnit.normalizeRadians(theta - (imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS)));

        // Converts our polar coordinates back into cartesian so we can use our drive class
        double finalForward = r * Math.sin(theta);
        double finalStrafe =r * Math.cos(theta);

        this.drive(finalForward, finalStrafe, rotate);

    }

    // resets the yaw when the start button is pressed
    public void reset() {
        imu.resetYaw();
    }

    public void findRobot() {
        YawPitchRollAngles orientation = imu.getRobotYawPitchRollAngles();
        limelight.updateRobotOrientation(orientation.getYaw());
        LLResult result = limelight.getLatestResult();
        if (result != null && result.isValid()) {
            Pose3D botPose = result.getBotpose_MT2();
            telemetry.addData("Tx ", result.getTx());
            telemetry.addData("Ty ", result.getTy());
            telemetry.addData("Ta ", result.getTa());
            telemetry.addData("Bot pose ", botPose.toString());



        }
    }
}
