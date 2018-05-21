package floatingheads.snapclone.camera2VisionTools.Clear;

import android.content.Context;
import android.support.annotation.NonNull;

import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.MultiProcessor;
import com.google.android.gms.vision.Tracker;
import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.FaceDetector;
import com.google.android.gms.vision.face.LargestFaceFocusingProcessor;

import floatingheads.snapclone.camera2VisionTools.Eyes.GooglyEyesFaceTracker;
import floatingheads.snapclone.camera2VisionTools.GraphicOverlay;

/**
 * Created by Akira on 4/23/2018.
 */

public class ClearOverlay {

    private boolean usingFrontCamera;
    private GraphicOverlay mGraphicOverlay;

    public ClearOverlay(boolean usingFront, GraphicOverlay overlay){
        this.usingFrontCamera = usingFront;
        this.mGraphicOverlay = overlay;
    }

    /**
     * Creates the face detector and associated processing pipeline to support either front facing
     * mode or rear facing mode.  Checks if the detector is ready to use, and displays a low storage
     * warning if it was not possible to download the face library.
     */
    @NonNull
    public FaceDetector createFaceDetector(Context context) {
        // For both front facing and rear facing modes, the detector is initialized to do eye landmark classification.
        // We are using fast mode, and tracking 1 face in front camera view, and multiple faces in rear camera view.
        // Setting PromentnFaceOnly as true when usingFrontCamera will stop scanning for faces when single largest face is found
        // The former results in greater efficiency, we also increase minfacesize from default for further optimizations
        FaceDetector detector = new FaceDetector.Builder(context)
                .setLandmarkType(FaceDetector.ALL_LANDMARKS)
                .setClassificationType(FaceDetector.ALL_CLASSIFICATIONS)
                .setTrackingEnabled(true)
                .setMode(FaceDetector.FAST_MODE)
                .setProminentFaceOnly(usingFrontCamera)
                .setMinFaceSize(usingFrontCamera ? 0.35f : 0.15f)
                .build();

        Detector.Processor<Face> processor;
        if (usingFrontCamera) {
            // For front facing mode, a single tracker instance is used with an associated focusing processor
            ClearFaceTracker clear = new ClearFaceTracker();
            clear.setOverlay(mGraphicOverlay);
            Tracker<Face> tracker = clear;
            processor = new LargestFaceFocusingProcessor.Builder(detector, tracker).build();
        } else {
            // Multiprocessor mode set for rear camera, tracker is created for each face and is maintained as long as the same face is visible
            MultiProcessor.Factory<Face> factory = new MultiProcessor.Factory<Face>() {
                @Override public Tracker<Face> create(Face face) {
                    ClearFaceTracker clear = new ClearFaceTracker();
                    clear.setOverlay(mGraphicOverlay);
                    return clear;
                }
            };
            processor = new MultiProcessor.Builder<>(factory).build();
        }
        detector.setProcessor(processor);
        return detector;
    }

}
