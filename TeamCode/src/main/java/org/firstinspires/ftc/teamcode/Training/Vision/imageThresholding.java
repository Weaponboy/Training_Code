package org.firstinspires.ftc.teamcode.Training.Vision;

import static org.opencv.core.CvType.CV_8U;
import static org.opencv.imgproc.Imgproc.dilate;
import static org.opencv.imgproc.Imgproc.erode;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import org.firstinspires.ftc.robotcore.internal.camera.calibration.CameraCalibration;
import org.firstinspires.ftc.vision.VisionProcessor;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

public class imageThresholding implements VisionProcessor {

    Mat modifiedMat = new Mat();

    public Scalar MIN_THRESH_BLUE = new Scalar(5, 0, 0);
    public Scalar MAX_THRESH_BLUE = new Scalar(200, 255, 255);

    @Override
    public void init(int width, int height, CameraCalibration calibration) {

    }

    @Override
    public Object processFrame(Mat frame, long captureTimeNanos) {

        modifiedMat = frame.clone();

        Imgproc.cvtColor(modifiedMat, modifiedMat, Imgproc.COLOR_BGR2HSV);

        Core.inRange(frame, MIN_THRESH_BLUE, MAX_THRESH_BLUE, modifiedMat);

        erode(modifiedMat, modifiedMat, new Mat(5, 5, CV_8U));

        dilate(modifiedMat, modifiedMat, new Mat(5, 5, CV_8U));

        modifiedMat.copyTo(frame);

        int RightPixels = Core.countNonZero(modifiedMat);

        return null;
    }

    private android.graphics.Rect makeGraphicsRect(Rect rect, float scaleBmpPxToCanvasPx) {

        int left = Math.round(rect.x * scaleBmpPxToCanvasPx);
        int top = Math.round(rect.y * scaleBmpPxToCanvasPx);
        int right = left + Math.round(rect.width * scaleBmpPxToCanvasPx);

        int bottom = top + Math.round(rect.height * scaleBmpPxToCanvasPx);

        return new android.graphics.Rect(left, top, right, bottom);
    }

    @Override
    public void onDrawFrame(Canvas canvas, int onscreenWidth, int onscreenHeight, float scaleBmpPxToCanvasPx, float scaleCanvasDensity, Object userContext) {
        Paint rectPaint = new Paint();
        rectPaint.setColor(Color.RED);
        rectPaint.setStyle(Paint.Style.STROKE);
        rectPaint.setStrokeWidth(scaleCanvasDensity * 4);

        canvas.drawRect(makeGraphicsRect(new Rect(90, 200, 40, 60), scaleBmpPxToCanvasPx), rectPaint);
    }
}
