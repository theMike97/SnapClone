package floatingheads.snapclone.camera2VisionTools.Clear;

import android.graphics.Canvas;


import floatingheads.snapclone.camera2VisionTools.GraphicOverlay;

/**
 * Created by Akira on 4/23/2018.
 */

public class ClearGraphic extends GraphicOverlay.Graphic {


    /**
     * @param overlay
     */
    public ClearGraphic(GraphicOverlay overlay) {
        super(overlay);
    }

    /**
     * Draws the current eye state to the supplied canvas.  This will draw the eyes at the last
     * reported position from the tracker, and the iris positions according to the physics
     * simulations for each iris given motion and other forces.
     * @param canvas drawing canvas
     */
    @Override
    public void draw(Canvas canvas) {
    }

    @Override
    public void getRotated(Canvas canvas) {

    }
}
