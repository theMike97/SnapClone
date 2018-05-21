package floatingheads.snapclone.adapters.helper;



import floatingheads.snapclone.R;


/*
 * Created by Akira on 4/24/2018.
 */

public class FilterTypeHelper {



    public static int FilterType2Color(FilterType filterType){
        switch (filterType) {
            case NONE:
                return R.color.black;
            case GOOGLY:
                return R.color.black;
            case CLEAR:
                return R.color.black;
            default:
                return R.color.black;
        }
    }

    public static int FilterType2Thumb(FilterType filterType){
        switch (filterType) {
            case NONE:
                return R.drawable.googly;
            case GOOGLY:
                return R.drawable.googly;
            case CLEAR:
                return R.drawable.googly;
            default:
                return R.drawable.googly;
        }
    }

    public static int FilterType2Name(FilterType filterType){
        switch (filterType) {
            case NONE:
                return R.string.add_filter;
            case GOOGLY:
                return R.string.add_filter;
            case CLEAR:
                return R.string.add_filter;
            default:
                return R.string.add_filter;
        }
    }
}
