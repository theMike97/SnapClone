package floatingheads.snapclone.camera2VisionTools.Clear;



import com.google.android.gms.vision.Tracker;
import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.FaceDetector;



import floatingheads.snapclone.camera2VisionTools.GraphicOverlay;

/**
 * Created by Akira on 4/23/2018.
 */

public class ClearFaceTracker extends Tracker<Face> {
    private GraphicOverlay mOverlay;
    private ClearGraphic mClearGraphic;

    @Override
    public void onNewItem(int id, Face face) {
        mClearGraphic = new ClearGraphic(mOverlay);
    }

    @Override
    public void onUpdate(FaceDetector.Detections<Face> detectionResults, Face face) {
        mOverlay.add(mClearGraphic);
    }

    @Override
    public void onMissing(FaceDetector.Detections<Face> detectionResults) {
        mOverlay.remove(mClearGraphic);
    }

    public void setOverlay(GraphicOverlay overlay){
        mOverlay = overlay;
    }
}
