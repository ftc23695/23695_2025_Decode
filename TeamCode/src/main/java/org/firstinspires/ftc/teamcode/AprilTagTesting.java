package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.LLStatus;
import com.qualcomm.hardware.limelightvision.Limelight3A;

import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;

import java.util.List;

@TeleOp
public class AprilTagTesting extends OpMode
{
    private DcMotor leftFrontDrive = null;
    private DcMotor leftBackDrive = null;
    private DcMotor rightFrontDrive = null;
    private DcMotor rightBackDrive = null;
    private DcMotorEx shooter = null;
    private DcMotor intakeForward = null;
    private DcMotor intakeBack = null;
    private Limelight3A limelight = null;
    private IMU imu; //emu

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
        limelight = hardwareMap.get(Limelight3A.class, "limelight");
        limelight.pipelineSwitch(8); //tag#11 pipeline
        imu = hardwareMap.get(IMU.class, "imu");
        RevHubOrientationOnRobot revHubOrientationOnRobot = new RevHubOrientationOnRobot(RevHubOrientationOnRobot.LogoFacingDirection.UP,
                RevHubOrientationOnRobot.UsbFacingDirection.FORWARD);
        imu.initialize(new IMU.Parameters(revHubOrientationOnRobot));

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
        YawPitchRollAngles orientation = imu.getRobotYawPitchRollAngles();
        limelight.updateRobotOrientation(orientation.getYaw());
        LLResult llResult = limelight.getLatestResult();
        if (llResult != null && llResult.isValid()) {
        Pose3D botPose =llResult.getBotpose_MT2();
        }

        // drive variables
        double leftFrontPower;
        double rightFrontPower;
        double leftBackPower;
        double rightBackPower;

        LLResult result = limelight.getLatestResult();
        limelight.start();
        double tx = result.getTx();
        String limelight_telemetry = "Limelight Data";
        int pipeline = result.getPipelineIndex();
        double turningPower;
        if (!gamepad1.dpad_down){
            turningPower = gamepad1.right_stick_x;
        }
        else if ((result.getStaleness() < 100) && (llResult != null && llResult.isValid()) && tx  < 1 && tx > -1)
            turningPower = 0;
        else if (tx > 1 && tx < 10){
            turningPower = 0.5;
        }
        else if (tx < -1 && tx > -10){
            turningPower = -0.5;
        }
        else if (tx > 10 && tx < 50){
            turningPower = 1;
        }
        else if (tx < -10 && tx > -50){
            turningPower = -1;
        }
        else {
            turningPower = 0;
        }

//      y
//   x     b   << controller button layout
//      a
        // joystick controls
        double drive = -gamepad1.left_stick_y;
        double strafe = gamepad1.left_stick_x;
        double turn = turningPower;
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
        else if (gamepad2.left_bumper && shooter.getVelocity() > 1399) {
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
            limelight.stop();
            terminateOpModeNow();

        }
        //limelight stuff
        long staleness = result.getStaleness();
        if (staleness < 100) {
            telemetry.addData("data", "good");
        } else {
            telemetry.addData("data", "bad (" + staleness + " ms)");
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
        telemetry.addData("right front motor expected", "power = %.2f", rightFrontPower);
        telemetry.addData("left rear motor expected", "power = %.2f", leftBackPower);
        telemetry.addData("right rear motor expected", "power = %.2f", rightBackPower);
        telemetry.addData("shooter velocity", "velocity = %.2f", shooter.getVelocity());
        telemetry.addData("", limelight_telemetry);
        telemetry.addData("limelight x = ", tx);
        telemetry.addData("limelight pipeline = ", pipeline);
        List<LLResultTypes.FiducialResult> fiducialResults = result.getFiducialResults();
        for (LLResultTypes.FiducialResult fr : fiducialResults) {
            telemetry.addData("tag ", "ID: %d", fr.getFiducialId());
        }
    }


    @Override
    public void stop() {
        leftFrontDrive.setPower(0);
        rightFrontDrive.setPower(0);
        leftBackDrive.setPower(0);
        rightBackDrive.setPower(0);
        shooter.setPower(0);
        intakeForward.setPower(0);
        limelight.stop();


    }

}

