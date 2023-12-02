package org.firstinspires.ftc.teamcode.analog.subsystems;

import android.util.Size;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

@TeleOp
public class Vision extends LinearOpMode  {

    @Override
    public void runOpMode() throws InterruptedException {

        AprilTagProcessor TagProssesor = new AprilTagProcessor.Builder()
                .setDrawAxes(true)
                .setDrawCubeProjection(true)
                .setDrawTagID(true)
                .setDrawTagOutline(true)
                .build();

         VisionPortal visionPortal = new VisionPortal.Builder()
                 .addProcessor(TagProssesor)
                 .setCamera(hardwareMap.get(WebcamName.class,  "Webcam 1"))
                 .setCameraResolution(new Size(640, 480 ))
                 .build();

        waitForStart();

        while (!isStopRequested()   && opModeIsActive())

            if  (TagProssesor.getDetections().size() > 0)  {
                AprilTagDetection tag = TagProssesor.getDetections().get(0);

                        telemetry.addData("x", tag.ftcPose.x);
                telemetry.addData("y", tag.ftcPose.y);
                telemetry.addData("z", tag.ftcPose.z);
                telemetry.addData("roll", tag.ftcPose.roll);
                telemetry.addData("pitch", tag.ftcPose.pitch);
                telemetry.addData("yaw", tag.ftcPose.yaw);

                        telemetry.update();
            }
    }

}