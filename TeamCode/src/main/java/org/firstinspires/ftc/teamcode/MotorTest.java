package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "Motor Test", group = "Examples")
public class MotorTest extends LinearOpMode {
    // Declare motor array
    private DcMotor frontLeft, frontRight, backLeft, backRight;

    @Override
    public void runOpMode() {
        // Initialize motors (names must match Driver Station config)
        frontRight = hardwareMap.get(DcMotor.class, "top_right_motor");
        frontLeft = hardwareMap.get(DcMotor.class, "top_left_motor");
        backRight = hardwareMap.get(DcMotor.class, "bottom_right_motor");
        backLeft = hardwareMap.get(DcMotor.class, "bottom_left_motor");

        // Set motor directions
        frontRight.setDirection(DcMotor.Direction.REVERSE);
        backRight.setDirection(DcMotor.Direction.REVERSE);


        waitForStart();

        while (opModeIsActive()) {
           if (gamepad1.dpad_up) {
               frontRight.setPower(1.0);
               telemetry.addData("Front Right ", gamepad1.dpad_up);
           } else if (gamepad1.dpad_right) {
               backRight.setPower(1.0);
               telemetry.addData("Back Right ", gamepad1.dpad_right);
           } else if (gamepad1.dpad_down) {
               backLeft.setPower(1.0);
               telemetry.addData("Back Left ", gamepad1.dpad_down);
           } else if (gamepad1.dpad_left) {
               frontLeft.setPower(1.0);
               telemetry.addData("Front Left ", gamepad1.dpad_left);
           }


            telemetry.update();
        }
    }
}
