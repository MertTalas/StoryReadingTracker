package com.imposterstech.storyreadingtracker.FaceTracking;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;

import com.google.mlkit.vision.face.Face;
import com.google.mlkit.vision.face.FaceContour;
import com.google.mlkit.vision.face.FaceLandmark;
import com.imposterstech.storyreadingtracker.Model.Contour;

import java.util.Locale;

public class NonFaceGraphic extends GraphicOverlay.Graphic{
    private static final float FACE_POSITION_RADIUS = 8.0f;
    private static final float ID_TEXT_SIZE = 30.0f;
    private static final float ID_Y_OFFSET = 40.0f;
    private static final float BOX_STROKE_WIDTH = 5.0f;
    private static final int NUM_COLORS = 10;
    private static final int[][] COLORS =
            new int[][] {
                    // {Text color, background color}
                    {Color.BLACK, Color.WHITE},
                    {Color.WHITE, Color.MAGENTA},
                    {Color.BLACK, Color.LTGRAY},
                    {Color.WHITE, Color.RED},
                    {Color.WHITE, Color.BLUE},
                    {Color.WHITE, Color.DKGRAY},
                    {Color.BLACK, Color.CYAN},
                    {Color.BLACK, Color.YELLOW},
                    {Color.WHITE, Color.BLACK},
                    {Color.BLACK, Color.GREEN}
            };

    private final Paint facePositionPaint;
    private final Paint[] idPaints;
    private final Paint[] boxPaints;
    private final Paint[] labelPaints;

    private volatile Contour contour;

    public NonFaceGraphic(GraphicOverlay overlay, Contour contour) {
        super(overlay);
        this.contour = contour;
        final int selectedColor = Color.WHITE;

        facePositionPaint = new Paint();
        facePositionPaint.setColor(selectedColor);

        int numColors = COLORS.length;
        idPaints = new Paint[numColors];
        boxPaints = new Paint[numColors];
        labelPaints = new Paint[numColors];
        for (int i = 0; i < numColors; i++) {
            idPaints[i] = new Paint();
            idPaints[i].setColor(COLORS[i][0] /* text color */);
            idPaints[i].setTextSize(ID_TEXT_SIZE);

            boxPaints[i] = new Paint();
            boxPaints[i].setColor(COLORS[i][1] /* background color */);
            boxPaints[i].setStyle(Paint.Style.STROKE);
            boxPaints[i].setStrokeWidth(BOX_STROKE_WIDTH);

            labelPaints[i] = new Paint();
            labelPaints[i].setColor(COLORS[i][1] /* background color */);
            labelPaints[i].setStyle(Paint.Style.FILL);
        }

    }

    @Override
    public void draw(Canvas canvas) {
        Contour contour = this.contour;
        if (contour == null) {
            return;
        }
        //center x center y kaydet
        // Draws a circle at the position of the detected face, with the face's track id below.
        float x = translateX(contour.getCenterX());
        float y = translateY(contour.getCenterY());
        canvas.drawCircle(x, y, FACE_POSITION_RADIUS, facePositionPaint);



        for(PointF point:contour.getFaceOvalContour()){
            canvas.drawCircle(
                    translateX(point.x), translateY(point.y), FACE_POSITION_RADIUS, facePositionPaint);
        }
        for(PointF point:contour.getUpperLipBottomContour()){
            canvas.drawCircle(
                    translateX(point.x), translateY(point.y), FACE_POSITION_RADIUS, facePositionPaint);
        }
        for(PointF point:contour.getLeftEyeContour()){
            canvas.drawCircle(
                    translateX(point.x), translateY(point.y), FACE_POSITION_RADIUS, facePositionPaint);
        }
        for(PointF point:contour.getLeftCheekContour()){
            canvas.drawCircle(
                    translateX(point.x), translateY(point.y), FACE_POSITION_RADIUS, facePositionPaint);
        }
        for(PointF point:contour.getLeftEyebrowBotContour()){
            canvas.drawCircle(
                    translateX(point.x), translateY(point.y), FACE_POSITION_RADIUS, facePositionPaint);
        }
        for(PointF point:contour.getRightEyeContour()){
            canvas.drawCircle(
                    translateX(point.x), translateY(point.y), FACE_POSITION_RADIUS, facePositionPaint);
        }
        for(PointF point:contour.getLeftEyebrowTopContour()){
            canvas.drawCircle(
                    translateX(point.x), translateY(point.y), FACE_POSITION_RADIUS, facePositionPaint);
        }
        for(PointF point:contour.getLowerLipBotContour()){
            canvas.drawCircle(
                    translateX(point.x), translateY(point.y), FACE_POSITION_RADIUS, facePositionPaint);
        }
        for(PointF point:contour.getLowerLipTopContour()){
            canvas.drawCircle(
                    translateX(point.x), translateY(point.y), FACE_POSITION_RADIUS, facePositionPaint);
        }
        for(PointF point:contour.getNoseBotContour()){
            canvas.drawCircle(
                    translateX(point.x), translateY(point.y), FACE_POSITION_RADIUS, facePositionPaint);
        }
        for(PointF point:contour.getNoseBridgeContour()){
            canvas.drawCircle(
                    translateX(point.x), translateY(point.y), FACE_POSITION_RADIUS, facePositionPaint);
        }
        for(PointF point:contour.getUpperLipTopContour()){
            canvas.drawCircle(
                    translateX(point.x), translateY(point.y), FACE_POSITION_RADIUS, facePositionPaint);
        }
        for(PointF point:contour.getRightCeekContour()){
            canvas.drawCircle(
                    translateX(point.x), translateY(point.y), FACE_POSITION_RADIUS, facePositionPaint);
        }
        for(PointF point:contour.getRightEyebrowBotContour()){
            canvas.drawCircle(
                    translateX(point.x), translateY(point.y), FACE_POSITION_RADIUS, facePositionPaint);
        }
        for(PointF point:contour.getRightEyebrowTopContour()){
            canvas.drawCircle(
                    translateX(point.x), translateY(point.y), FACE_POSITION_RADIUS, facePositionPaint);
        }





/*
        // Draws all face contours.
        for (FaceContour contour : face.getAllContours()) {
            for (PointF point : contour.getPoints()) {
                canvas.drawCircle(
                        translateX(point.x), translateY(point.y), FACE_POSITION_RADIUS, facePositionPaint);
            }
        }*/



    }
}
