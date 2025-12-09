// 32154 Ducky Dynamix November 2025 Comp OpMode
// This OpMode uses the Comp initialization setup
// This setup needs
// 1. 4 motors for driving named "top_left_motor", "top_right_motor", "bottom_left_motor", and "bottom_right_motor"
// 2. 2 motors named "intake_motor", and "flywheel_motor" for the intake and flywheel respectively
// 3. an inertial measurement unit named "imu" for field oriented driving

// To use this opmode, you need two gamepads
// Gamepad 1 controls movement. Left stick y is forward and backward, left stick x is strafing side to side, and right stick x is rotation.(Note field oriented drive)
// Gamepad 2 controls the intake and the flywheel.
// By default, the intake spins at 0.3 speed and the flywheel spins at 0.5 speed
// left bumper increases intake speed to 0.6, and right bumper increases flywheel speed to 1.0
// Pressing B stops the intake entirely, unless shooting, and pressing A starts the intake again

package org.firstinspires.ftc.teamcode;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;
import static org.firstinspires.ftc.robotcore.external.navigation.AngleUnit.DEGREES;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name = "Test Limelight Comp Code")
public class CompTeleop extends OpMode {
    //imports our Mecanum Field Drive Class
    MecanumDrive drive = new MecanumDrive();
    //imports our turret class
    Turret flywheel = new Turret();


    private Limelight3A limelight;
    private IMU imu;

    // declare the variables that we will need.
    double forward, strafe, rotate, hoodAngle;
    boolean spin, shoot, stop, lastA, currentA, currentB, lastY, currentY, lastB, reverse;

    boolean isRedAlliance = true;

    @Override
    public void init() {
        //Run the init in the Mecanum drive class which finds the Motors and IMU
        drive.init(hardwareMap);
        // Run the init in the Turret class which finds the motors for the flywheel and the intake
        flywheel.init(hardwareMap);

        limelight = hardwareMap.get(Limelight3A.class, "limelight");

    }

    @Override
    public void start() {
        limelight.start();
        if (isRedAlliance) {
            limelight.pipelineSwitch(0);
        } else {
            limelight.pipelineSwitch(1);
        }
    }

    @Override
    public void loop() {
        // get some of the variables from gamepad 1 for our movement
        forward = gamepad1.left_stick_y;
        strafe = -gamepad1.left_stick_x;
        rotate = -gamepad1.right_stick_x;

        LLResult result = limelight.getLatestResult();

        if (gamepad1.left_bumper) {
            rotate = LimelightAim.getRotate(result);
        }

        // gets the variables for Spinning the flywheel and shooting from gamepad 2
        spin = gamepad2.left_bumper;
        shoot = gamepad2.right_bumper;

        // Start and Stop and reverse for the intake
        currentA = gamepad2.a;
        currentB = gamepad2.b;
        currentY = gamepad2.y;

        if (gamepad2.dpad_up) {
            hoodAngle += 0.05;
        }
        if (gamepad2.dpad_down) {
            hoodAngle -= 0.05;
        }
        hoodAngle = Range.clip(hoodAngle, 0.2, 0.8);

        // A pressed → start motor
        if (currentA && !lastA) {
            stop = false;
        }

        // B pressed → stop motor
        if (currentB && !lastB) {
            stop = true;
        }

        if (currentY && !lastY) {
            reverse = !reverse;
        }
        // if the Start button is pressed, resests the IMU Yaw
        if (gamepad1.options) {
            drive.reset();
        }

        lastA = currentA;
        lastB = currentB;
        lastY = currentY;

        telemetry.addData("Strafe ", strafe);
        telemetry.addData("Forward ", forward);
        telemetry.addData("Rotation ", rotate);
        telemetry.addData("Shooting? ", shoot);


        // passes the movement values from gamepad 1 into the mecanum class
        drive.driveFieldRelative(forward, strafe, rotate);

        // passes the button values for the shooter from gamepad 2 into the turret class
        flywheel.shooter(spin, shoot, stop, reverse, hoodAngle);
    }

}
