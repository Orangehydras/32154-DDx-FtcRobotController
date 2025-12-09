package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Range;

public class LimelightAim {
    // Tune this to change how fast the robot turns
    // if it oscillates past it, lower the value
    // if it is too low and doesnt swiftly lock onto the april tag, increase the value

    private static final double kP = 0.035;
    private static final double kD = 0.003;
    private static double lastError = 0;

    public static double getRotate(LLResult result) {
        // returns 0 to not brick the program if an error happens
        if (result == null || !result.isValid()) {
            return 0.0;
        }

        double tx = result.getTx();
        double error = tx;

        double derivative = error - lastError;
        lastError = error;

        double rotate = (kP * error) + (kD * derivative);

        rotate = Range.clip(rotate, -0.5, 0.5);

        return -rotate;
    }
}
