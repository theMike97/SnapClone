package floatingheads.snapclone.activities;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import floatingheads.snapclone.R;
import floatingheads.snapclone.adapters.helper.FilterType;
import floatingheads.snapclone.adapters.helper.FilterTypeHelper;
import floatingheads.snapclone.camera2VisionTools.GraphicOverlay;

/**
 * selects sticker
 * result - Integer, resource id of the sticker, bundled at key EXTRA_STICKER_ID
 * <p>
 * Stickers borrowed from : http://www.flaticon.com/packs/pokemon-go
 */


    //FILTERADAPTER CLASS, SUBCLASS OF RECYCLERVIEW.ADAPTER<>
    public class FilterAdapter extends RecyclerView.Adapter<FilterAdapter.FilterViewHolder> {

        private int selected = 0;
        private GraphicOverlay mGraphicOverlay;
        private Context mContext;

        FilterAdapter(GraphicOverlay overlay, Context context){
            this.mGraphicOverlay = overlay;
            this.mContext = context;
        }

        private final FilterType[] filters = new FilterType[]{
                FilterType.NONE,
                FilterType.GOOGLY,
                FilterType.CLEAR
        };

        @Override
        public FilterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            //FILTER_ITEM_LAYOUT XML
            View view = LayoutInflater.from(mContext).inflate(R.layout.test_filter_item_layout, parent, false);
            FilterViewHolder viewHolder = new FilterViewHolder(view);
            viewHolder.icon_img = (ImageButton) view.findViewById(R.id.filter_thumb_image);
            viewHolder.filter_name = view.findViewById(R.id.filter_thumb_name);
            viewHolder.root_button = view.findViewById(R.id.filter_root);
            viewHolder.icon_selected_indicator =  view.findViewById(R.id.filter_thumb_selected);
            viewHolder.icon_fill = view.findViewById(R.id.filter_thumb_selected_bg);
            return viewHolder;
        }

    @Override
    public void onBindViewHolder(FilterViewHolder holder, final int position) {

        //FILTER_ITEM_LAYOUT XML
        holder.icon_img.setImageResource(FilterTypeHelper.FilterType2Thumb(filters[position]));
        holder.filter_name.setText(FilterTypeHelper.FilterType2Name(filters[position]));
        holder.filter_name.setBackgroundColor(mContext.getResources().getColor(FilterTypeHelper.FilterType2Color(filters[position])));
        if(position == selected){
            holder.icon_selected_indicator.setVisibility(View.VISIBLE);
            holder.icon_fill.setBackgroundColor(mContext.getResources().getColor(FilterTypeHelper.FilterType2Color(filters[position])));
            holder.icon_fill.setAlpha(0.7f);
        }else {
            holder.icon_selected_indicator.setVisibility(View.GONE);
        }

        holder.root_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(selected == position)
                    return;
                int lastSelected = selected;
                selected = position;
                notifyItemChanged(lastSelected);
                notifyItemChanged(position);
                onFilterChangeListener.onFilterChanged(filters[position]);
            }
        });
    }

    @Override
    public int getItemCount() {
        return filters == null ? 0 : filters.length;
    }

    class FilterViewHolder extends RecyclerView.ViewHolder {
        ImageView icon_img;
        TextView filter_name;
        FrameLayout icon_selected_indicator;
        FrameLayout root_button;
        View icon_fill;

        public FilterViewHolder(View itemView) {
            super(itemView);
        }
    }

    public interface onFilterChangeListener{
        void onFilterChanged(FilterType filterType);
    }

    private onFilterChangeListener onFilterChangeListener;

    public void setOnFilterChangeListener(onFilterChangeListener onFilterChangeListener){
        this.onFilterChangeListener = onFilterChangeListener;
    }
}



