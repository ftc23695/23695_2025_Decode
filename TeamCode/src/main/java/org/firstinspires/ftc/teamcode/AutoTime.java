package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorControllerEx;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;


public class AutoTime {

    LinearOpMode opMode = null;
    public ElapsedTime  runtime = new ElapsedTime();
    public AutoTime(LinearOpMode opMode){
        this.opMode = opMode;
    }
    public final int FORWARD = 1;
    public final int BACKWARD = 2;
    public final int STRAFELEFT = 3;
    public final int STRAFERIGHT = 4;
    public final int TURNLEFT = 5;
    public final int TURNRIGHT = 6;
    public final int SHOOT = 7;
    public final int INTAKE = 8;
    DcMotor rightFrontDrive,rightBackDrive, leftFrontDrive, leftBackDrive, intakeForward, intakeBack;
    DcMotorEx shooter;

    public void initHardwareMap()
    {
        leftFrontDrive = opMode.hardwareMap.get(DcMotor.class, "left_front");
        rightFrontDrive = opMode.hardwareMap.get(DcMotor.class, "right_front");
        leftBackDrive = opMode.hardwareMap.get(DcMotor.class, "left_back");
        rightBackDrive = opMode.hardwareMap.get(DcMotor.class, "right_back");
        shooter = opMode.hardwareMap.get(DcMotorEx.class, "shooter");
        intakeForward = opMode.hardwareMap.get(DcMotor.class, "intakeForward");
        intakeBack = opMode.hardwareMap.get(DcMotor.class, "intakeForward");

        leftFrontDrive.setDirection(DcMotorSimple.Direction.REVERSE);
        rightFrontDrive.setDirection(DcMotorSimple.Direction.FORWARD);
        leftBackDrive.setDirection(DcMotorSimple.Direction.REVERSE);
        rightBackDrive.setDirection(DcMotorSimple.Direction.FORWARD);
        shooter.setDirection(DcMotor.Direction.REVERSE);
        intakeForward.setDirection(DcMotor.Direction.REVERSE);
        intakeBack.setDirection(DcMotor.Direction.FORWARD);

        leftFrontDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFrontDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftBackDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBackDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        shooter.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        intakeForward.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        intakeBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);



    }



    public void drive(int time,double power,int direction){
        if (direction == FORWARD) {
            leftFrontDrive.setPower(power);
            leftBackDrive.setPower(power);
            rightFrontDrive.setPower(power);
            rightBackDrive.setPower(power);
            opMode.sleep(time);
            leftFrontDrive.setPower(0);
            leftBackDrive.setPower(0);
            rightFrontDrive.setPower(0);
            rightBackDrive.setPower(0);
        }

        else if (direction == BACKWARD) {
            leftFrontDrive.setPower(power * -1);
            leftBackDrive.setPower(power * -1);
            rightFrontDrive.setPower(power * -1);
            rightBackDrive.setPower(power * -1);
            opMode.sleep(time);
            leftFrontDrive.setPower(0);
            leftBackDrive.setPower(0);
            rightFrontDrive.setPower(0);
            rightBackDrive.setPower(0);
        }

        else if (direction == STRAFELEFT) {
            leftFrontDrive.setPower(power * -1);
            leftBackDrive.setPower(power * 1);
            rightFrontDrive.setPower(power * 1);
            rightBackDrive.setPower(power * -1);
            opMode.sleep(time);
            leftFrontDrive.setPower(0);
            leftBackDrive.setPower(0);
            rightFrontDrive.setPower(0);
            rightBackDrive.setPower(0);
        }

        else if (direction == STRAFERIGHT) {


            leftFrontDrive.setPower(power * 1);
            leftBackDrive.setPower(power * -1);
            rightFrontDrive.setPower(power * -1);
            rightBackDrive.setPower(power * 1);
            opMode.sleep(time);
            leftFrontDrive.setPower(0);
            leftBackDrive.setPower(0);
            rightFrontDrive.setPower(0);
            rightBackDrive.setPower(0);
        }

        else if (direction == TURNLEFT) {


            leftFrontDrive.setPower(power * -1);
            rightFrontDrive.setPower(power * 1);
            leftBackDrive.setPower(power * -1);
            rightBackDrive.setPower(power * 1);
            opMode.sleep(time);
            leftFrontDrive.setPower(0);
            leftBackDrive.setPower(0);
            rightFrontDrive.setPower(0);
            rightBackDrive.setPower(0);

        }


        else if (direction == TURNRIGHT) {


            leftFrontDrive.setPower(power * 1);
            rightFrontDrive.setPower(power * -1);
            leftBackDrive.setPower(power * 1);
            rightBackDrive.setPower(power * -1);
            opMode.sleep(time);
            leftFrontDrive.setPower(0);
            leftBackDrive.setPower(0);
            rightFrontDrive.setPower(0);
            rightBackDrive.setPower(0);

        }


        else if (direction == SHOOT) {


            shooter.setVelocity(power * 2800);
            opMode.sleep(time);
            shooter.setVelocity(0);

        }


        else if (direction == INTAKE) {


            intakeForward.setPower(power * 1);
            intakeBack.setPower(power * 1);
            opMode.sleep(time);
            intakeForward.setPower(0);
            intakeBack.setPower(0);

        }
        drive(1000, 0.5, FORWARD);
        opMode.sleep(500);
        drive(500, 0.7, TURNLEFT);
        opMode.sleep(500);
        drive(3000, 0.5, SHOOT);
        drive(3000, 1, INTAKE);

    }

}
