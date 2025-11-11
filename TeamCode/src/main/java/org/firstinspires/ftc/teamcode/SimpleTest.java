package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

@TeleOp
public class SimpleTest extends OpMode
{
    private DcMotor leftFrontDrive = null;
    private DcMotor leftBackDrive = null;
    private DcMotor rightFrontDrive = null;
    private DcMotor rightBackDrive = null;

    @Override
    public void init() {
        //Declare variables for phone to recognise//

        //names on the config

        leftFrontDrive = hardwareMap.get(DcMotor.class, "left_front");
        rightFrontDrive = hardwareMap.get(DcMotor.class, "right_front");
        leftBackDrive = hardwareMap.get(DcMotor.class, "left_back");
        rightBackDrive = hardwareMap.get(DcMotor.class, "right_back");


        leftFrontDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftBackDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightFrontDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightBackDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        leftFrontDrive.setDirection(DcMotor.Direction.REVERSE);
        rightFrontDrive.setDirection(DcMotor.Direction.FORWARD);
        leftBackDrive.setDirection(DcMotor.Direction.REVERSE);
        rightBackDrive.setDirection(DcMotor.Direction.FORWARD);

        leftFrontDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFrontDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftBackDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBackDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        telemetry.addData("status", "Initialized");
    }


    //Set variables//
    @Override
    public void loop() {
        double leftFrontPower;
        double rightFrontPower;
        double leftBackPower;
        double rightBackPower;


        boolean forward = gamepad1.dpad_up;
        boolean right = gamepad1.dpad_right;
        boolean left = gamepad1.dpad_left;
        boolean back = gamepad1.dpad_down;
        boolean divider = gamepad1.left_bumper;

        //drive code

        if (forward){
            leftFrontDrive.setPower(1);
            rightFrontDrive.setPower(1);
            leftBackDrive.setPower(1);
            rightBackDrive.setPower(1);
        } else if (forward && divider) {
            leftFrontDrive.setPower(0.5);
            rightFrontDrive.setPower(0.5);
            leftBackDrive.setPower(0.5);
            rightBackDrive.setPower(0.5);
        }
        if (back){
            leftFrontDrive.setPower(-1);
            rightFrontDrive.setPower(-1);
            leftBackDrive.setPower(-1);
            rightBackDrive.setPower(-1);
        } else if (back && divider){
            leftFrontDrive.setPower(-0.5);
            rightFrontDrive.setPower(-0.5);
            leftBackDrive.setPower(-0.5);
            rightBackDrive.setPower(-0.5);
        }
        if (left && !forward && !back){
            leftFrontDrive.setPower(1);
            rightFrontDrive.setPower(-1);
            leftBackDrive.setPower(1);
            rightBackDrive.setPower(-1);
        } else if (left && divider && !forward && !back){
            leftFrontDrive.setPower(0.5);
            rightFrontDrive.setPower(-0.5);
            leftBackDrive.setPower(0.5);
            rightBackDrive.setPower(-0.5);
        }
        if (right && !forward && !back){
            leftFrontDrive.setPower(-1);
            rightFrontDrive.setPower(1);
            leftBackDrive.setPower(-1);
            rightBackDrive.setPower(1);
        } else if (right && divider && !forward && !back){
            leftFrontDrive.setPower(-0.5);
            rightFrontDrive.setPower(0.5);
            leftBackDrive.setPower(-0.5);
            rightBackDrive.setPower(0.5);
        }


        //Claw Code: Opens with GP2 X and opens less when past vertical position
        // BIGGER CLOSES MORE*********************

    }

    @Override
    public void stop() {
        leftFrontDrive.setPower(0);
        rightFrontDrive.setPower(0);
        leftBackDrive.setPower(0);
        rightBackDrive.setPower(0);


    }

}

