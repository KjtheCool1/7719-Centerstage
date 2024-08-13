package org.firstinspires.ftc.teamcode.Testing.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.TouchSensor;
@Disabled
@TeleOp
public class Touch extends LinearOpMode {
    TouchSensor Touch;
    @Override
    public void runOpMode() {
        Touch = hardwareMap.get(TouchSensor.class, "Touch");

        waitForStart();

        while (opModeIsActive()){
            if (Touch.isPressed()){
                telemetry.addData("Touch Sensor", "Is Pressed");
            } else {
                telemetry.addData("Touch Sensor", "Is Not Pressed");
            }

            telemetry.update();
            }
        }
    }

