package floatingheads.snapclone.camera2VisionTools;

import android.content.Context;

import com.google.android.gms.vision.MultiProcessor;
import com.google.android.gms.vision.Tracker;
import com.google.android.gms.vision.face.Face;

import floatingheads.snapclone.camera2VisionTools.Face.GraphicFaceTracker;
import floatingheads.snapclone.camera2VisionTools.GraphicOverlay;

/**
 * Created by Akira on 3/3/2018.
 */

public class GraphicFaceTrackerFactory implements MultiProcessor.Factory<Face> {
    private GraphicOverlay mOverlay;
    private Context mcontext;
    public GraphicFaceTrackerFactory(GraphicOverlay overlay, Context context){
        mOverlay = overlay;
        mcontext = context;
    }
    @Override
    public Tracker<Face> create(Face face) {
        return new GraphicFaceTracker(mOverlay, mcontext);
    }
}
