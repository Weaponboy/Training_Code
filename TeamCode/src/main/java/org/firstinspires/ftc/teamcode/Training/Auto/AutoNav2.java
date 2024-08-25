package org.firstinspires.ftc.teamcode.Training.Auto;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

public class AutoNav2 extends LinearOpMode {

    DcMotor left_Drive;
    DcMotor right_Drive;

    IMU imu;

    double wheelCircumference = 20;
    double motorTicksPerRev = 400;

    double ticksPerCM = motorTicksPerRev/wheelCircumference;

    double imuHeading;
    double robotHeading;

    @Override
    public void runOpMode() throws InterruptedException {

        left_Drive = hardwareMap.get(DcMotor.class, "left_Drive");
        right_Drive = hardwareMap.get(DcMotor.class, "right_Drive");

        imu = hardwareMap.get(IMU.class, "imu");

        RevHubOrientationOnRobot.LogoFacingDirection logoFacingDirection = RevHubOrientationOnRobot.LogoFacingDirection.UP;
        RevHubOrientationOnRobot.UsbFacingDirection usbFacingDirection = RevHubOrientationOnRobot.UsbFacingDirection.FORWARD;
        RevHubOrientationOnRobot orientationOnRobot = new RevHubOrientationOnRobot(logoFacingDirection, usbFacingDirection);

        imu.initialize(new IMU.Parameters(orientationOnRobot));

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

        imuHeading = -imu.getRobotYawPitchRollAngles().getPitch(AngleUnit.DEGREES);

        if (imuHeading <= 0) {
            robotHeading = (360 + imuHeading);
        } else {
            robotHeading = (0 + imuHeading);
        }

        double error = degrees-robotHeading;

        if (error < -180) {
            error = (360 + error);
        } else if (error > 180) {
            error = (error - 360);
        }

        left_Drive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        right_Drive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        left_Drive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        right_Drive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        while (Math.abs(error) > 3){

            if (error > 0){
                left_Drive.setPower(0.5);
                right_Drive.setPower(-0.5);
            }else if (error < 0){
                left_Drive.setPower(-0.5);
                right_Drive.setPower(0.5);
            }

            imuHeading = -imu.getRobotYawPitchRollAngles().getPitch(AngleUnit.DEGREES);

            if (imuHeading <= 0) {
                robotHeading = (360 + imuHeading);
            } else {
                robotHeading = (0 + imuHeading);
            }

            error = degrees-robotHeading;

            if (error < -180) {
                error = (360 + error);
            } else if (error > 180) {
                error = (error - 360);
            }

        }


    }

}
