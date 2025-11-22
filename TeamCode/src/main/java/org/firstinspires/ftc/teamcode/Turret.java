package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Turret {

    // Declare motors
    private DcMotor fly_wheel, intake;

    public void init(HardwareMap hwMap) {
        // Initialize the motors (the name must match the config in the Driver Station)
        // In this case we need "fly_motor", and "intake_motor"
        fly_wheel = hwMap.get(DcMotor.class, "fly_motor");
        intake = hwMap.get(DcMotor.class, "intake_motor");

        // Set motor direction (reverse if needed)
        fly_wheel.setDirection(DcMotor.Direction.FORWARD);
    }

    public void shooter(boolean spin, boolean shoot, boolean stop) {
        // if the button to spin the flywheel is pressed(left bumper), sets the flywheel power from 0.5 to 1.0
        // if the button to shoot is pressed(right bumper), sets the intake power from 0.3 to 0.6

        if (spin == true) {
            fly_wheel.setPower(1.0);
        } else {
            fly_wheel.setPower(0.5);
        }
        if (shoot = true) {
            intake.setPower(0.6);
        } else if (stop == true) {
            intake.setPower(0);
        } else {
            intake.setPower(0.3);
        }
    }
}

