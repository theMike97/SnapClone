package floatingheads.snapclone.camera2VisionTools.Face;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.PorterDuff;


import floatingheads.snapclone.R;
import floatingheads.snapclone.camera2VisionTools.GraphicOverlay;

import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.Landmark;

/**
 * This class renders the graphic overlay of the facial landmarks.
 */
public class FaceGraphic extends GraphicOverlay.Graphic {
    private Bitmap marker;
    private Bitmap kakashi;
    private BitmapFactory.Options opt;
    private Resources resources;

    private int faceId;
    PointF facePosition;
    float faceWidth;
    float faceHeight;
    PointF faceCenter;
    float isSmilingProbability = -1;
    float eyeRightOpenProbability = -1;
    float eyeLeftOpenProbability = -1;
    float eulerZ;
    float eulerY;
    PointF leftEyePos = null;
    PointF rightEyePos = null;
    PointF noseBasePos = null;
    PointF leftMouthCorner = null;
    PointF rightMouthCorner = null;
    PointF mouthBase = null;
    PointF leftEar = null;
    PointF rightEar = null;
    PointF leftEarTip = null;
    PointF rightEarTip = null;
    PointF leftCheek = null;
    PointF rightCheek = null;
    public Context mContext;

    private volatile Face mFace;

    public FaceGraphic(GraphicOverlay overlay, Context context) {
        super(overlay);
        opt = new BitmapFactory.Options();
        opt.inScaled = false;
        resources = context.getResources();
        marker = BitmapFactory.decodeResource(resources, R.drawable.marker, opt);
        kakashi = BitmapFactory.decodeResource(resources, R.drawable.kakashi, opt);
    }

    public void setId(int id) {
        faceId = id;
    }

    //probability person is smiling
    public float getSmilingProbability() {
        return isSmilingProbability;
    }

    //probability right eye is open
    public float getEyeRightOpenProbability() {
        return eyeRightOpenProbability;
    }

    //probability left eye is open
    public float getEyeLeftOpenProbability() {
        return eyeLeftOpenProbability;
    }

    /**
     * Updates the face instance from the detection of the most recent frame.  Invalidates the
     * relevant portions of the overlay to trigger a redraw.
     */
    public void updateFace(Face face) {
        mFace = face;
        postInvalidate();
    }

    public void goneFace() {
        mFace = null;
    }

    //Overriding GraphicOverlay's draw method
    @Override
    public void draw(Canvas canvas) {
        Face face = mFace;
        if(face == null) {
            canvas.drawColor(0, PorterDuff.Mode.CLEAR);
            isSmilingProbability = -1;
            eyeRightOpenProbability= -1;
            eyeLeftOpenProbability = -1;
            return;
        }

        //face.getPosition() returns the top left position of the face within the image
        //create point called faceposition here
        facePosition = new PointF(translateX(face.getPosition().x), translateY(face.getPosition().y));

        //height and width
        faceWidth = face.getWidth() * 4;
        faceHeight = face.getHeight() * 4;

        //Center of the face coordinate calculated
        faceCenter = new PointF(translateX(face.getPosition().x + faceWidth/8), translateY(face.getPosition().y + faceHeight/8));
        isSmilingProbability = face.getIsSmilingProbability();
        eyeRightOpenProbability = face.getIsRightEyeOpenProbability();
        eyeLeftOpenProbability = face.getIsLeftEyeOpenProbability();
        eulerY = face.getEulerY();
        eulerZ = face.getEulerZ();


        //DO NOT SET TO NULL THE NON EXISTENT LANDMARKS. USE OLDER ONES INSTEAD.
        for(Landmark landmark : face.getLandmarks()) {
            switch (landmark.getType()) {
                //x and y coordinates of left eye
                case Landmark.LEFT_EYE:
                    //created point
                    leftEyePos = new PointF(translateX(landmark.getPosition().x), translateY(landmark.getPosition().y));
                    break;
                //x and y coordinates of Right eye
                case Landmark.RIGHT_EYE:
                    //created point
                    rightEyePos = new PointF(translateX(landmark.getPosition().x), translateY(landmark.getPosition().y));
                    break;
                //x and y coordinates of Nose base
                case Landmark.NOSE_BASE:
                    //created point
                    noseBasePos = new PointF(translateX(landmark.getPosition().x), translateY(landmark.getPosition().y));
                    break;
                //x and y coordinates of left mouth
                case Landmark.LEFT_MOUTH:
                    //created point
                    leftMouthCorner = new PointF(translateX(landmark.getPosition().x), translateY(landmark.getPosition().y));
                    break;
                //x and y coordinates of right mouth
                case Landmark.RIGHT_MOUTH:
                    //created point
                    rightMouthCorner = new PointF(translateX(landmark.getPosition().x), translateY(landmark.getPosition().y));
                    break;
                //x and y coordinates of bottom mouth
                case Landmark.BOTTOM_MOUTH:
                    //created point
                    mouthBase = new PointF(translateX(landmark.getPosition().x), translateY(landmark.getPosition().y));
                    break;
                //x and y coordinates of left ear
                case Landmark.LEFT_EAR:
                    //created point
                    leftEar = new PointF(translateX(landmark.getPosition().x), translateY(landmark.getPosition().y));
                    break;
                //x and y coordinates of right eye
                case Landmark.RIGHT_EAR:
                    //created point
                    rightEar = new PointF(translateX(landmark.getPosition().x), translateY(landmark.getPosition().y));
                    break;
                //x and y coordinates of left ear tip
                case Landmark.LEFT_EAR_TIP:
                    //created point
                    leftEarTip = new PointF(translateX(landmark.getPosition().x), translateY(landmark.getPosition().y));
                    break;
                //x and y coordinates of right ear tip
                case Landmark.RIGHT_EAR_TIP:
                    //created point
                    rightEarTip = new PointF(translateX(landmark.getPosition().x), translateY(landmark.getPosition().y));
                    break;
                //x and y coordinates of left cheek
                case Landmark.LEFT_CHEEK:
                    //created point
                    leftCheek = new PointF(translateX(landmark.getPosition().x), translateY(landmark.getPosition().y));
                    break;
                //x and y coordinates of right cheek
                case Landmark.RIGHT_CHEEK:
                    //created point
                    rightCheek = new PointF(translateX(landmark.getPosition().x), translateY(landmark.getPosition().y));
                    break;
            }
        }

        Paint mPaint = new Paint();
        mPaint.setColor(Color.WHITE);
        mPaint.setStrokeWidth(4);
        if(faceCenter != null)
            canvas.drawBitmap(marker, faceCenter.x, faceCenter.y, null);
        if(noseBasePos != null)
            canvas.drawBitmap(marker, noseBasePos.x, noseBasePos.y, null);
        if(leftEyePos != null)
            canvas.drawBitmap(marker, leftEyePos.x, leftEyePos.y, null);
        if(rightEyePos != null)
            canvas.drawBitmap(marker, rightEyePos.x, rightEyePos.y, null);
        if(mouthBase != null)
            canvas.drawBitmap(marker, mouthBase.x, mouthBase.y, null);
        if(leftMouthCorner != null)
            canvas.drawBitmap(marker, leftMouthCorner.x, leftMouthCorner.y, null);
        if(rightMouthCorner != null)
            canvas.drawBitmap(marker, rightMouthCorner.x, rightMouthCorner.y, null);
        if(leftEar != null)
            canvas.drawBitmap(marker, leftEar.x, leftEar.y, null);
        if(rightEar != null)
            canvas.drawBitmap(marker, rightEar.x, rightEar.y, null);
        if(leftEarTip != null)
            canvas.drawBitmap(marker, leftEarTip.x, leftEarTip.y, null);
        if(rightEarTip != null)
            canvas.drawBitmap(marker, rightEarTip.x, rightEarTip.y, null);
        if(leftCheek != null)
            canvas.drawBitmap(marker, leftCheek.x, leftCheek.y, null);
        if(rightCheek != null)
            canvas.drawBitmap(marker, rightCheek.x, rightCheek.y, null);
    }

    public void getRotated(Canvas canvas){
        Face face = mFace;
        if(face == null) {
            canvas.drawColor(0, PorterDuff.Mode.CLEAR);
            isSmilingProbability = -1;
            eyeRightOpenProbability= -1;
            eyeLeftOpenProbability = -1;
            return;
        }
        //face.getPosition() returns the top left position of the face within the image
        //create point called faceposition here
        facePosition = new PointF(translateX(face.getPosition().x), translateY(face.getPosition().y));

        //height and width
        faceWidth = face.getWidth() * 4;
        faceHeight = face.getHeight() * 4;

        //Center of the face coordinate calculated
        faceCenter = new PointF(translateX(face.getPosition().x + faceHeight/8), translateY(face.getPosition().y + faceWidth/8));
        isSmilingProbability = face.getIsSmilingProbability();
        eyeRightOpenProbability = face.getIsRightEyeOpenProbability();
        eyeLeftOpenProbability = face.getIsLeftEyeOpenProbability();
        eulerY = face.getEulerY();
        eulerZ = face.getEulerZ();


        //DO NOT SET TO NULL THE NON EXISTENT LANDMARKS. USE OLDER ONES INSTEAD.
        for(Landmark landmark : face.getLandmarks()) {
            switch (landmark.getType()) {
                //x and y coordinates of left eye
                case Landmark.LEFT_EYE:
                    //created point
                    leftEyePos = new PointF(translateX(landmark.getPosition().x), translateY(landmark.getPosition().y));
                    break;
                //x and y coordinates of Right eye
                case Landmark.RIGHT_EYE:
                    //created point
                    rightEyePos = new PointF(translateX(landmark.getPosition().x), translateY(landmark.getPosition().y));
                    break;
                //x and y coordinates of Nose base
                case Landmark.NOSE_BASE:
                    //created point
                    noseBasePos = new PointF(translateX(landmark.getPosition().x), translateY(landmark.getPosition().y));
                    break;
                //x and y coordinates of left mouth
                case Landmark.LEFT_MOUTH:
                    //created point
                    leftMouthCorner = new PointF(translateX(landmark.getPosition().x), translateY(landmark.getPosition().y));
                    break;
                //x and y coordinates of right mouth
                case Landmark.RIGHT_MOUTH:
                    //created point
                    rightMouthCorner = new PointF(translateX(landmark.getPosition().x), translateY(landmark.getPosition().y));
                    break;
                //x and y coordinates of bottom mouth
                case Landmark.BOTTOM_MOUTH:
                    //created point
                    mouthBase = new PointF(translateX(landmark.getPosition().x), translateY(landmark.getPosition().y));
                    break;
                //x and y coordinates of left ear
                case Landmark.LEFT_EAR:
                    //created point
                    leftEar = new PointF(translateX(landmark.getPosition().x), translateY(landmark.getPosition().y));
                    break;
                //x and y coordinates of right eye
                case Landmark.RIGHT_EAR:
                    //created point
                    rightEar = new PointF(translateX(landmark.getPosition().x), translateY(landmark.getPosition().y));
                    break;
                //x and y coordinates of left ear tip
                case Landmark.LEFT_EAR_TIP:
                    //created point
                    leftEarTip = new PointF(translateX(landmark.getPosition().x), translateY(landmark.getPosition().y));
                    break;
                //x and y coordinates of right ear tip
                case Landmark.RIGHT_EAR_TIP:
                    //created point
                    rightEarTip = new PointF(translateX(landmark.getPosition().x), translateY(landmark.getPosition().y));
                    break;
                //x and y coordinates of left cheek
                case Landmark.LEFT_CHEEK:
                    //created point
                    leftCheek = new PointF(translateX(landmark.getPosition().x), translateY(landmark.getPosition().y));
                    break;
                //x and y coordinates of right cheek
                case Landmark.RIGHT_CHEEK:
                    //created point
                    rightCheek = new PointF(translateX(landmark.getPosition().x), translateY(landmark.getPosition().y));
                    break;
            }
        }


        Paint mPaint = new Paint();
        mPaint.setColor(Color.WHITE);
        mPaint.setStrokeWidth(4);
       // canvas.drawBitmap(marker, facePosition.x, facePosition.y, null);
        if(faceCenter != null)
            canvas.drawBitmap(marker, faceCenter.x, faceCenter.y, null);
        if(noseBasePos != null)
            canvas.drawBitmap(marker, noseBasePos.x, noseBasePos.y, null);
        if(leftEyePos != null)
            canvas.drawBitmap(marker, leftEyePos.x, leftEyePos.y, null);
        if(rightEyePos != null)
            canvas.drawBitmap(marker, rightEyePos.x, rightEyePos.y, null);
        if(mouthBase != null)
            canvas.drawBitmap(marker, mouthBase.x, mouthBase.y, null);
        if(leftMouthCorner != null)
            canvas.drawBitmap(marker, leftMouthCorner.x, leftMouthCorner.y, null);
        if(rightMouthCorner != null)
            canvas.drawBitmap(marker, rightMouthCorner.x, rightMouthCorner.y, null);
        if(leftEar != null)
            canvas.drawBitmap(marker, leftEar.x, leftEar.y, null);
        if(rightEar != null)
            canvas.drawBitmap(marker, rightEar.x, rightEar.y, null);
        if(leftEarTip != null)
            canvas.drawBitmap(marker, leftEarTip.x, leftEarTip.y, null);
        if(rightEarTip != null)
            canvas.drawBitmap(marker, rightEarTip.x, rightEarTip.y, null);
        if(leftCheek != null)
            canvas.drawBitmap(marker, leftCheek.x, leftCheek.y, null);
        if(rightCheek != null)
            canvas.drawBitmap(marker, rightCheek.x, rightCheek.y, null);
    }


}