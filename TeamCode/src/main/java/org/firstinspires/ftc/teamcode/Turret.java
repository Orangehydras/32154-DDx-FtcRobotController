package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Turret {

    // Declare motors
    private DcMotor fly_wheel, intake, increase;
    private Servo servo;

    public void init(HardwareMap hwMap) {
        // Initialize the motors (the name must match the config in the Driver Station)
        // In this case we need "fly_motor", and "intake_motor"
        fly_wheel = hwMap.get(DcMotor.class, "fly_motor");
        intake = hwMap.get(DcMotor.class, "intake_motor");
        increase = hwMap.get(DcMotor.class, "intake2_motor");
        servo = hwMap.get(Servo.class, "hood_servo");

        // Set motor direction (reverse if needed)
        intake.setDirection(DcMotorSimple.Direction.REVERSE);
        fly_wheel.setDirection(DcMotor.Direction.FORWARD);

        servo = hwMap.get(Servo.class, "hood_servo");

// Sets a servo range to avoid breaking stuff
        servo.scaleRange(0.2, 0.8);

    }

    public void shooter(boolean spin, boolean shoot, boolean stop, boolean reverse, double hoodAngle) {
        // if the button to spin the flywheel is pressed(left bumper), sets the flywheel power from 0.5 to 1.0
        // if the button to shoot is pressed(right bumper), sets the intake power from 0.3 to 0.6

        if (spin) {
            fly_wheel.setPower(1.0);
        } else {
            fly_wheel.setPower(0);
        }
        if (shoot) {
            increase.setPower(1); // Shoot
        } else {
            increase.setPower(0);
        }
        if (stop) {
            intake.setPower(0); // Stop the intake
        }

        else if (reverse) {
            intake.setPower(0.4); // If reversed, set intake power to -0.4
        } else {
            intake.setPower(1);
        }
        servo.setPosition(hoodAngle);


    }
}

