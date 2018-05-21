package floatingheads.snapclone.androidScreenUtils;

/**
 * Created by Akira on 2/26/2018.
 */

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.PointF;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import com.google.android.gms.common.images.Size;

/**
 * Utility class for pixel operations
 */
public class Utils {

    /**
     * Converts density independent pixels (relative to dpi) into pixels
     * @param dp This is the unit relative to a 160 dpi screen such that one dp is one pixel on a 160 dpi screen
     * @return actual pixels
     */
    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    /**
     * Gets the height of the screen
     * @param c context contains screen we would like to compute height of
     * @return returns the screen height
     */
    public static int getScreenHeight(Context c) {
        WindowManager wm = (WindowManager) c.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size.y;
    }

    /**
     * Gets the width of the screen
     * @param c context contains screen we would like to compute width
     * @return screen width
     */
    public static int getScreenWidth(Context c) {
        WindowManager wm = (WindowManager) c.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size.x;
    }

    /**
     * gets the aspect ratio of the screen
     * @param c context contains screen we would like to compute ratio of
     * @return aspect ratio of the screen
     */
    public static float getScreenRatio(Context c) {
        DisplayMetrics metrics = c.getResources().getDisplayMetrics();
        return ((float)metrics.heightPixels / (float)metrics.widthPixels);
    }

    /**
     * Computes the screen rotation
     * @param c conext contains screen we would like to obtain orientation of
     * @return rotation of the screen
     */
    public static int getScreenRotation(Context c) {
        WindowManager wm = (WindowManager) c.getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay().getRotation();
    }

    /**
     * Computes the distance between 2 points
     * @param p1 first arbitrary cartesian coordinate point
     * @param p2 second arbitrary cartesian coordinate point
     * @return distance between two points
     */
    public static int distancePointsF(PointF p1, PointF p2) {
        return (int) Math.sqrt((p1.x - p2.x) *  (p1.x - p2.x) + (p1.y - p2.y) *  (p1.y - p2.y));
    }

    /**
     * computes the midpoint of two given points
     * @param p1 first arbitrary cartesian coordinate point
     * @param p2 second arbitrary cartesian coordinate point
     * @return midpoint
     */
    public static PointF middlePoint(PointF p1, PointF p2) {
        if(p1 == null || p2 == null)
            return null;
        return new PointF((p1.x+p2.x)/2, (p1.y+p2.y)/2);
    }

    /**
     *
     * @param sizes
     * @return
     */
    public static Size[] sizeToSize(android.util.Size[] sizes) {
        Size[] size = new Size[sizes.length];
        for(int i=0; i<sizes.length; i++) {
            size[i] = new Size(sizes[i].getWidth(), sizes[i].getHeight());
        }
        return size;
    }

    /**
     * Detects if screen orientation is landscape or portrait mode
     * @param c context contains
     * @return returns false if the orientation is set to landscape, and true if orientation is set to portrait.
     */
    public static boolean isPortrait(Context c){
        int orientation = c.getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            return false;
        }
        else {
            return true;
        }
    }
}