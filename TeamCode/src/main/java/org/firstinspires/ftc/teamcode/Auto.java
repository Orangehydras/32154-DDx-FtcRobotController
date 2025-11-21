package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous(name="Mecanum: Move Off Line", group="Auto")
public class Auto extends LinearOpMode {

    private DcMotor frontLeft, frontRight, backLeft, backRight;
    @Override
    public void runOpMode() throws InterruptedException {

        // Map motors
        frontLeft  = hardwareMap.get(DcMotor.class, "top_left_motor");
        frontRight = hardwareMap.get(DcMotor.class, "top_right_motor");
        backLeft   = hardwareMap.get(DcMotor.class, "bottom_left_motor");
        backRight  = hardwareMap.get(DcMotor.class, "bottom_right_motor");

        // Reverse the right side so forward is forward
        frontRight.setDirection(DcMotor.Direction.REVERSE);
        backRight.setDirection(DcMotor.Direction.REVERSE);

        waitForStart();

        // Drive forward for 0.8 seconds
        driveForward(0.3);
        sleep(800);

        // Stop
        driveForward(0);
    }

    private void driveForward(double power) {
        // All motors same power = forward on mecanum
        frontLeft.setPower(power);
        frontRight.setPower(power);
        backLeft.setPower(power);
        backRight.setPower(power);
    }
}
