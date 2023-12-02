package org.firstinspires.ftc.teamcode.analog.Teleop;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
//testing
@TeleOp
public class Analog extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {

        DcMotor fL = hardwareMap.dcMotor.get("frontLeft");
        DcMotor bL = hardwareMap.dcMotor.get("backLeft");
        DcMotor fR = hardwareMap.dcMotor.get("frontRight");
        DcMotor bR= hardwareMap.dcMotor.get("backRight");
        DcMotor Lift = hardwareMap.dcMotor.get("Lift");
        DcMotor PullUp = hardwareMap.dcMotor.get("UP");
        DcMotor Intake = hardwareMap.dcMotor.get("Intake");
        DcMotor Conveyor = hardwareMap.dcMotor.get("Conveyor");
        Servo IW = hardwareMap.servo.get("Wrist");
        Servo PW = hardwareMap.servo.get("PullUpServo");
        Servo Outtake = hardwareMap.servo.get("Outtake");
        boolean IntakeOpen = false;

        fR.setDirection(DcMotorSimple.Direction.REVERSE);
        bR.setDirection(DcMotorSimple.Direction.REVERSE);

        Lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        IMU imu = hardwareMap.get(IMU.class, "imu");

        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.RIGHT,
                RevHubOrientationOnRobot.UsbFacingDirection.BACKWARD));

        imu.initialize(parameters);

        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive()) {

            //--------------------------------------Drivetrain------------------------------------//
            double y = -gamepad1.left_stick_y;
            double x = gamepad1.left_stick_x;
            double rx = gamepad1.right_stick_x;

            if (gamepad1.x) {
                imu.resetYaw();
            }

            double botHeading = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);

            double rotX = x * Math.cos(-botHeading) - y * Math.sin(-botHeading);
            double rotY = x * Math.sin(-botHeading) + y * Math.cos(-botHeading);

            rotX = rotX * 1.1;

            double denominator = Math.max(Math.abs(rotY) + Math.abs(rotX) + Math.abs(rx), 1);
            double frontLeftPower = (rotY + rotX + rx) / denominator;
            double backLeftPower = (rotY - rotX + rx) / denominator;
            double frontRightPower = (rotY - rotX - rx) / denominator;
            double backRightPower = (rotY + rotX - rx) / denominator;

            fL.setPower(frontLeftPower);
            bL.setPower(backLeftPower);
            fR.setPower(frontRightPower);
            bR.setPower(backRightPower);

            if (gamepad1.right_bumper){
                fL.setPower(frontLeftPower / 4.0);
                bL.setPower(backLeftPower / 4.0);
                fR.setPower(frontRightPower / 4.0);
                bR.setPower(backRightPower / 4.0);
            }else{
                fL.setPower(frontLeftPower);
                bL.setPower(backLeftPower);
                fR.setPower(frontRightPower);
                bR.setPower(backRightPower);
            }

            //-------------------------------------Lift-------------------------------------------//
            Lift.setPower(gamepad2.left_stick_y);

            Lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


            //------------------------------------Intake------------------------------------------//
            Intake.setPower(-gamepad1.right_trigger);
            Conveyor.setPower(gamepad1.right_trigger);

            if (gamepad2.y){
                IW.setPosition(0.078);
            }
            if (gamepad2.a){
                IW.setPosition(0.05);
            }

            //-----------------------------------------PullUp-------------------------------------//
            PullUp.setPower(gamepad2.right_trigger);
            PullUp.setPower(-gamepad2.left_trigger);
            if (gamepad2.right_bumper){
                PW.setPosition(0);
            }
            if (gamepad2.left_bumper){
                PW.setPosition(0.05);
            }
            //----------------------------------Outtake------------------------------------------//
            //Will probaby be an issue, if it is then take out the booleans
            if (gamepad1.y){
                Outtake.setPosition(0);
                IntakeOpen = true;
            }else if (gamepad1.a){
                Outtake.setPosition(0.1);
                IntakeOpen = false;
            }
        }
    }
}