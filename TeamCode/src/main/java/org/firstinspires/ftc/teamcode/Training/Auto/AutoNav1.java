package org.firstinspires.ftc.teamcode.Training.Auto;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

public class AutoNav1 extends LinearOpMode {

    DcMotor left_Drive;
    DcMotor right_Drive;

    double wheelCircumference = 20;
    double motorTicksPerRev = 400;

    double ticksPerCM = motorTicksPerRev/wheelCircumference;

    double tuningCircle = 90;
    double degreesToCm = tuningCircle/360;

    @Override
    public void runOpMode() throws InterruptedException {

        left_Drive = hardwareMap.get(DcMotor.class, "left_Drive");
        right_Drive = hardwareMap.get(DcMotor.class, "right_Drive");

        waitForStart();

        driveDistance(10);

        turnDegrees(90);

    }

    public void driveDistance(double distance){
        int motorTarget = (int) (distance*ticksPerCM);

        left_Drive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        right_Drive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        left_Drive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        right_Drive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        left_Drive.setTargetPosition(motorTarget);
        right_Drive.setTargetPosition(motorTarget);
    }

    public void turnDegrees(double degrees){
        double distance = degrees*degreesToCm;
        int motorTarget = (int) (distance*ticksPerCM);

        left_Drive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        left_Drive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        left_Drive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        left_Drive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        left_Drive.setTargetPosition(motorTarget);
        right_Drive.setTargetPosition(-motorTarget);

        while (left_Drive.isBusy()){

        }
    }

}
