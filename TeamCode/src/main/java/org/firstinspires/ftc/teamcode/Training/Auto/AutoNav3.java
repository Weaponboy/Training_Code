package org.firstinspires.ftc.teamcode.Training.Auto;

import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

public class AutoNav3 extends LinearOpMode {

    DcMotor left_Drive;
    DcMotor right_Drive;

    IMU imu;

    PIDController headingControl = new PIDController(0.08, 0, 0.001);
    PIDController driveControl = new PIDController(0.1, 0, 0.005);

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

    public void driveDistance(double targetPosition){
        double currentPosition;

        left_Drive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        right_Drive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        left_Drive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        right_Drive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        currentPosition = (left_Drive.getCurrentPosition()/ticksPerCM);

        while (currentPosition > 4){
            currentPosition = (left_Drive.getCurrentPosition()/ticksPerCM);

            double motorPower = driveControl.calculate(targetPosition-currentPosition);

            left_Drive.setPower(motorPower);
            right_Drive.setPower(motorPower);
        }

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

            double motorPower = headingControl.calculate(error);

            left_Drive.setPower(motorPower);
            right_Drive.setPower(-motorPower);

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
