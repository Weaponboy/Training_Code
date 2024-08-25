package org.firstinspires.ftc.teamcode.Training.Auto;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.TouchSensor;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class Failsafes extends OpMode {

    TouchSensor touchSensor;
    DistanceSensor distanceSensor;

    DcMotor testMotor;

    @Override
    public void init() {
        touchSensor = hardwareMap.get(TouchSensor.class, "touchSensor");
        distanceSensor = hardwareMap.get(DistanceSensor.class, "touchSensor");

        testMotor = hardwareMap.get(DcMotor.class, "testMotor");
    }

    @Override
    public void loop() {

        if (touchSensor.isPressed()){

        }

        if (distanceSensor.getDistance(DistanceUnit.CM) > 80) {

        }



    }

}
