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
public class SchemesCardView extends RecyclerView.Adapter<SchemesCardView.ViewHolder> {

    String[] schemesNames, schemesImages,schemesCategory, schemesReleaseDate;
    OnItemClickListener mItemClickListener;
    ImageLoader mImageLoader;

    public SchemesCardView(String[] names, String[] images, String[] category, String[] date){
        this.schemesNames = names;
        this.schemesImages = images;
        this.schemesCategory = category;
        this.schemesReleaseDate = date;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.schemes_card_view, parent, false);
        mImageLoader = VolleySingleton.getInstance().getImageLoader();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.schemeName.setText(schemesNames[position]);
        holder.schemeImage.setImageUrl(schemesImages[position], mImageLoader);
        holder.schemeCategory.setText(schemesCategory[position]);
        holder.schemeReleaseDate.setText(schemesReleaseDate[position]);
    }

    @Override
    public int getItemCount() {
        return schemesNames.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView schemeName, schemeCategory, schemeReleaseDate;
        NetworkImageView schemeImage;

        public ViewHolder(View itemView) {
            super(itemView);
            schemeName = (TextView) itemView.findViewById(R.id.schemes_names);
            schemeImage = (NetworkImageView) itemView.findViewById(R.id.schemes_image);
            schemeCategory = (TextView) itemView.findViewById(R.id.schemes_category);
            schemeReleaseDate = (TextView) itemView.findViewById(R.id.schemes_release_date);
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
