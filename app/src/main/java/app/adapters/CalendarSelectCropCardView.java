package app.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import app.library.VolleySingleton;
import app.program.R;

/**
 * Not for public use
 * Created by FAIZ on 18-04-2015.
 */
public class CalendarSelectCropCardView extends RecyclerView.Adapter<CalendarSelectCropCardView.ViewHolder> {

    public String[] cropNames, cropImages;
    ImageLoader mImageLoader;

    public CalendarSelectCropCardView(String[] names, String[] images) {
        this.cropNames = names;
        this.cropImages = images;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.s_calendar_select_crop_card_view_unused, parent, false);
        mImageLoader = VolleySingleton.getInstance().getImageLoader();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.cropName.setText(cropNames[position]);
        holder.cropImage.setImageUrl(cropImages[position], mImageLoader);
    }

    @Override
    public int getItemCount() {
        return cropNames.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView cropName;
        NetworkImageView cropImage;

        public ViewHolder(View itemView) {
            super(itemView);
            cropName = (TextView) itemView.findViewById(R.id.calendar_select_crop_name);
            cropImage = (NetworkImageView) itemView.findViewById(R.id.calendar_select_crop_image);
        }
    }
}
