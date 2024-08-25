package org.firstinspires.ftc.teamcode.Training.JavaLesson;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp
public class variables extends OpMode {

    double motorPower = 0.1;

    int slidePosition = 200;

    boolean isGripperClosed = false;

    @Override
    public void init() {

    }

    @Override
    public void loop() {

        motorPower = 1;

        slidePosition = 400;

        isGripperClosed = true;

    }

}
