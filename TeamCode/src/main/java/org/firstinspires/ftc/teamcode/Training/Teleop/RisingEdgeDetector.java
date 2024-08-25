package org.firstinspires.ftc.teamcode.Training.Teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Gamepad;

public class RisingEdgeDetector extends OpMode {

    Gamepad CurrentLoop = new Gamepad();
    Gamepad lastLoop = new Gamepad();

    @Override
    public void init() {

    }

    @Override
    public void loop() {

        lastLoop.copy(CurrentLoop);
        CurrentLoop.copy(gamepad1);

        if (CurrentLoop.a && !lastLoop.a){

        }

    }

}
