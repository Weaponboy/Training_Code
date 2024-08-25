package org.firstinspires.ftc.teamcode.Training.Teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

public class Teleop extends OpMode {

    DcMotor left_Drive;
    DcMotor right_Drive;

    DcMotor arm_Motor;

    Servo gripper;

    double vertical;
    double turning;

    int delivery = 200;
    int collect = 0;

    @Override
    public void init() {
        left_Drive = hardwareMap.get(DcMotor.class, "left_Drive");
        right_Drive = hardwareMap.get(DcMotor.class, "right_Drive");

        arm_Motor = hardwareMap.get(DcMotor.class, "hex_arm");

        gripper = hardwareMap.get(Servo.class, "gripper_servo");

        arm_Motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        arm_Motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        right_Drive.setDirection(DcMotorSimple.Direction.REVERSE);

        left_Drive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        right_Drive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    @Override
    public void loop() {

        vertical = -gamepad1.right_stick_y;
        turning = gamepad1.left_stick_x;

        left_Drive.setPower(vertical + turning);
        right_Drive.setPower(vertical - turning);

        //move to collect
        if (gamepad1.a){
            arm_Motor.setTargetPosition(collect);

            arm_Motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }

        //move to delivery
        if (gamepad1.b){
            arm_Motor.setTargetPosition(delivery);

            arm_Motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }

        //open gripper
        if (gamepad1.y){
            gripper.setPosition(0.3);
        }

        //close gripper
        if (gamepad1.x){
            gripper.setPosition(0);
        }

    }

}
