package floatingheads.snapclone.adapters.helper;

import com.google.android.gms.vision.Tracker;
import com.google.android.gms.vision.face.Face;

import floatingheads.snapclone.camera2VisionTools.Clear.ClearFaceTracker;
import floatingheads.snapclone.camera2VisionTools.Eyes.GooglyEyesFaceTracker;
import floatingheads.snapclone.camera2VisionTools.GraphicOverlay;

/*
 * Created by Akira on 4/24/2018.
 */

public class FilterTypeFactory {

    private static FilterType filterType = FilterType.NONE;

    public static Tracker<Face> initFilters(FilterType type, GraphicOverlay mGraphicOverlay) {
        filterType = type;
        switch (type) {
            case CLEAR:
                ClearFaceTracker clear = new ClearFaceTracker();
                clear.setOverlay(mGraphicOverlay);
                return clear;
            case GOOGLY:
                GooglyEyesFaceTracker googly =new GooglyEyesFaceTracker();
                googly.setOverlay(mGraphicOverlay);
                return googly;
            default:
                return null;
        }
    }

        public FilterType getCurrentFilterType(){
            return filterType;
        }

}
