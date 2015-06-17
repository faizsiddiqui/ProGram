package app.adapters;

/**
 * Not for public use
 * Created by FAIZ on 09-05-2015.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;

import app.library.VolleySingleton;
import app.program.R;
import app.widgets.BezelNetworkImageView;

/**
 * Not for public use
 * Created by FAIZ on 18-04-2015.
 */
public class CalendarCropView extends RecyclerView.Adapter<CalendarCropView.ViewHolder> {

    Context context;
    String[] cropsNames, cropsImages;
    double[] cropsMarks;
    OnItemClickListener mItemClickListener;
    ImageLoader mImageLoader;

    public CalendarCropView(Context context, String[] names, String[] images, double[] marks) {
        this.context = context;
        this.cropsNames = names;
        this.cropsImages = images;
        this.cropsMarks = marks;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.calendar_crop_view, parent, false);
        mImageLoader = VolleySingleton.getInstance().getImageLoader();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.cropName.setText(cropsNames[position]);
        holder.cropImage.setImageUrl(cropsImages[position], mImageLoader);
        holder.cropMarks.setText(String.valueOf(cropsMarks[position]));
    }

    @Override
    public int getItemCount() {
        return cropsNames.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView cropName, cropMarks;
        BezelNetworkImageView cropImage;

        public ViewHolder(View itemView) {
            super(itemView);
            cropName = (TextView) itemView.findViewById(R.id.crops_names);
            cropImage = (BezelNetworkImageView) itemView.findViewById(R.id.crops_image);
            cropMarks = (TextView) itemView.findViewById(R.id.crops_marks);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(view, getPosition());
            }
        }
    }

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    public void SetOnItemClickListener(OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

}
