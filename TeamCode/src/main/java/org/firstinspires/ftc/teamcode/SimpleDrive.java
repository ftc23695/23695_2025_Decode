package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.Range;

@TeleOp
    public class SimpleDrive extends OpMode
    {
        private DcMotor leftFrontDrive = null;
        private DcMotor leftBackDrive = null;
        private DcMotor rightFrontDrive = null;
        private DcMotor rightBackDrive = null;
        private DcMotorEx shooter = null;
        private DcMotor intakeForward = null;
        private DcMotor intakeBack = null;

        boolean shooterPowerControl = true;
        double shooterVelocity = 0;


        @Override
        public void init() {
            //Declare variables for phone to recognise//

            //names on the config

            leftFrontDrive = hardwareMap.get(DcMotor.class, "left_front");
            rightFrontDrive = hardwareMap.get(DcMotor.class, "right_front");
            leftBackDrive = hardwareMap.get(DcMotor.class, "left_back");
            rightBackDrive = hardwareMap.get(DcMotor.class, "right_back");
            shooter = hardwareMap.get(DcMotorEx.class, "shooter");
            intakeForward = hardwareMap.get(DcMotor.class, "intakeForward");
            intakeBack = hardwareMap.get(DcMotor.class, "intakeBack");


            //other motor initializing

            leftFrontDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            leftBackDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            rightFrontDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            rightBackDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            shooter.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            intakeForward.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            intakeBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);


            leftFrontDrive.setDirection(DcMotor.Direction.REVERSE);
            rightFrontDrive.setDirection(DcMotor.Direction.FORWARD);
            leftBackDrive.setDirection(DcMotor.Direction.REVERSE);
            rightBackDrive.setDirection(DcMotor.Direction.FORWARD);
            shooter.setDirection(DcMotor.Direction.REVERSE);
            intakeForward.setDirection(DcMotor.Direction.REVERSE);
            intakeBack.setDirection(DcMotor.Direction.REVERSE);


            leftFrontDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            rightFrontDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            leftBackDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            rightBackDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            shooter.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            intakeForward.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            intakeBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


            telemetry.addData("status", "Initialized");
        }


        //Set variables//
        @Override
        public void loop() {
            // drive variables
            double leftFrontPower;
            double rightFrontPower;
            double leftBackPower;
            double rightBackPower;

//      y
//   x     b    << controller button layout
//      a
            // joystick controls
            double drive = -gamepad1.left_stick_y;
            double strafe = gamepad1.left_stick_x;
            double turn = gamepad1.right_stick_x;
            // making sure power doesnt exceed 100% and adding and subtracting variables to
            // get individual motor power levels
            leftFrontPower = Range.clip(drive + turn + strafe, -1, 1);
            rightFrontPower = Range.clip(drive - turn - strafe, -1, 1);
            leftBackPower = Range.clip(drive + turn - strafe, -1, 1);
            rightBackPower = Range.clip(drive - turn + strafe, -1, 1);
            // dividing power if right bumper is pressed
            if(gamepad1.right_trigger > 0.5){
                leftFrontPower /= 2;
                leftBackPower /= 2;
                rightFrontPower /= 2;
                rightBackPower /= 2;
            } else if(gamepad1.left_trigger > 0.5){
                leftFrontPower /= 4;
                leftBackPower /= 4;
                rightFrontPower /= 4;
                rightBackPower /= 4;
            }

            // shooter controls
            if (gamepad2.right_trigger > 0.5) {
                shooterVelocity = 1800;
            } else if (gamepad2.right_bumper) {
                shooterVelocity = 1400;
            }
            else {
                shooterVelocity = 0;
            }
//            if (gamepad1.dpad_down) {
//                shooterVelocity = 0;
//            }

            // intake and transfer controls
            if (gamepad2.left_trigger > 0.5) {
                intakeForward.setPower(1);
            }
            else if (gamepad2.left_bumper) {
                intakeBack.setPower(1);
            }
            else if (gamepad2.left_bumper && gamepad2.left_trigger > 0.5) {
                intakeForward.setPower(1);
                intakeBack.setPower(1);
            }
            else if (gamepad2.dpad_down) {
                intakeForward.setPower(-1);
                intakeBack.setPower(-1);
            } else {
                intakeForward.setPower(0);
                intakeBack.setPower(0);
            } // old shooter controls, keep commented out for now
//            if (shooterPowerControl && gamepad1.y && shooterVelocity != 0) {
//                shooterVelocity += 280;
//                shooterPowerControl = false;
//            } else if (shooterPowerControl && gamepad1.a && shooterVelocity != 2800) {
//                shooterVelocity -= 280;
//                shooterPowerControl = false;
//            } else if (!gamepad1.a && !gamepad1.y) {
//                shooterPowerControl = true;
//            }
            // self destruct button
            if ((gamepad1.a && gamepad1.b && gamepad1.x && gamepad1.y) || (gamepad2.a && gamepad2.b && gamepad2.x && gamepad2.y)) {
                leftFrontDrive.setPower(0);
                rightFrontDrive.setPower(0);
                leftBackDrive.setPower(0);
                rightBackDrive.setPower(0);
                shooter.setVelocity(0);
                intakeForward.setPower(0);
                intakeBack.setPower(0);
                terminateOpModeNow();

            }


            //setting final power levels
            leftFrontDrive.setPower(leftFrontPower);
            rightFrontDrive.setPower(rightFrontPower);
            leftBackDrive.setPower(leftBackPower);
            rightBackDrive.setPower(rightBackPower);
            shooter.setVelocity(shooterVelocity);

            //Claw Code: Opens with GP2 X and opens less when past vertical position
            // BIGGER CLOSES MORE*********************

            // telemetry
            telemetry.addData("left front motor expected", "power = %.2f", leftFrontPower);
            telemetry.addData("right front motor expected", "Power = %.2f", rightFrontPower);
            telemetry.addData("left rear motor expected", "Power = %.2f", leftBackPower);
            telemetry.addData("right rear motor expected", "Power = %.2f", rightBackPower);
        }


        @Override
        public void stop() {
            leftFrontDrive.setPower(0);
            rightFrontDrive.setPower(0);
            leftBackDrive.setPower(0);
            rightBackDrive.setPower(0);
            shooter.setPower(0);
            intakeForward.setPower(0);


        }

    }

