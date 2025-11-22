package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "Motor Joystick Control", group = "Examples")
public class RoboDrive extends LinearOpMode {

    // Declare motor array
    private DcMotor[] motorList = new DcMotor[4];
    private double maxSpeed = 1;

    @Override
    public void runOpMode() {
        // Initialize motors (names must match Driver Station config)
        motorList[0] = hardwareMap.get(DcMotor.class, "top_right_motor");
        motorList[1] = hardwareMap.get(DcMotor.class, "top_left_motor");
        motorList[2] = hardwareMap.get(DcMotor.class, "bottom_right_motor");
        motorList[3] = hardwareMap.get(DcMotor.class, "bottom_left_motor");

        // Set motor directions
        motorList[0].setDirection(DcMotor.Direction.REVERSE);
        motorList[2].setDirection(DcMotor.Direction.REVERSE);
        motorList[1].setDirection(DcMotor.Direction.FORWARD);
        motorList[3].setDirection(DcMotor.Direction.FORWARD);

        waitForStart();

        Drive mainDriver = new Drive();

        while (opModeIsActive()) {
                double leftY = gamepad1.left_stick_y;
                double leftX = -gamepad1.left_stick_x;
                double rightX = -gamepad1.right_stick_x;

                double[] powers = mainDriver.move2d(leftY, leftX, rightX);

               /* motorList[0].setPower(Math.cos(tempRotation*Math.PI/180)*powers[0]+Math.sin(tempRotation*Math.PI/180)*-powers[2]);
                motorList[1].setPower(Math.cos(tempRotation*Math.PI/180)*powers[1]+Math.sin(tempRotation*Math.PI/180)*-powers[0]);
                motorList[2].setPower(Math.cos(tempRotation*Math.PI/180)*powers[2]+Math.sin(tempRotation*Math.PI/180)*powers[1]);
                motorList[3].setPower(Math.cos(tempRotation*Math.PI/180)*powers[3]+Math.sin(tempRotation*Math.PI/180)*powers[3]);*/

                motorList[0].setPower(powers[0]);
                motorList[1].setPower(powers[1]);
                motorList[2].setPower(powers[2]);
                motorList[3].setPower(powers[3]);

                telemetry.addData("Left Stick Y", leftY);
                telemetry.addData("Left Stick X", leftX);
                telemetry.addData("Right Stick X", rightX);
                telemetry.addData("top_right_motor:", motorList[0].getPower());
                telemetry.addData("top_left_motor:", motorList[1].getPower());
                telemetry.addData("bottom_right_motor:", motorList[2].getPower());
                telemetry.addData("bottom_left_motor:", motorList[3].getPower());
                telemetry.update();
            }
        }

        class Drive {
            double[] move2d(double forward, double strafe, double turn) {
                return new double[]{
                    (forward - strafe - turn) * maxSpeed, // top right
                    (forward - strafe + turn) * maxSpeed, // top left
                    (forward + strafe - turn) * maxSpeed, // bottom right
                    (forward + strafe + turn) * maxSpeed  // bottom left
            };
        }
    }
}
