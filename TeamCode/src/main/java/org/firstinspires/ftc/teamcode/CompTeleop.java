package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

public class CompTeleop extends OpMode {
    //imports our Mecanum Field Drive Class
    MecanumDrive drive = new MecanumDrive();
    Turret flywheel = new Turret();

    double forward, strafe, rotate;
    boolean spin, shoot;

    @Override
    public void init() {
        //Run the init in the drive class which finds the Motors and IMU
        drive.init(hardwareMap);
    }

    @Override
    public void loop() {
        // get some of the variables from gamepad 1 for our movement
        forward = gamepad1.left_stick_y;
        strafe = gamepad1.left_stick_x;
        rotate = gamepad1.right_stick_x;

        // gets the variables for Spinning the flywheel and shooting from gamepad 2
        spin = gamepad2.left_bumper;
        shoot = gamepad2.right_bumper;

        //passes the movement values from gamepad 1 into the mecanum class
        drive.driveFieldRelative(forward, strafe, rotate);

        flywheel.shooter(spin, shoot);
    }

}
