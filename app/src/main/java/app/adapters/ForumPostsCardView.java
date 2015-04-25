package app.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import app.library.VolleySingleton;
import app.program.R;

/**
 * Not for public use
 * Created by FAIZ on 18-04-2015.
 */
public class ForumPostsCardView extends RecyclerView.Adapter<ForumPostsCardView.ViewHolder> {

    Context context;
    String[] schemesNames, schemesImages, schemesCategory, schemesReleaseDate;
    OnItemClickListener mItemClickListener;
    ImageLoader mImageLoader;

    public ForumPostsCardView(Context context, String[] names, String[] images, String[] category, String[] date) {
        this.context = context;
        this.schemesNames = names;
        this.schemesImages = images;
        this.schemesCategory = category;
        this.schemesReleaseDate = date;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.forum_post_card_view, parent, false);
        mImageLoader = VolleySingleton.getInstance().getImageLoader();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.schemeName.setText(schemesNames[position]);
        holder.schemeImage.setImageUrl(schemesImages[position], mImageLoader);
        holder.schemeCategory.setText(schemesCategory[position]);
        holder.schemeReleaseDate.setText(formatDateTime(schemesReleaseDate[position]));
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

    public String formatDateTime(String timeToFormat) {

        String finalDateTime = "";

        SimpleDateFormat iso8601Format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date date = null;
        if (timeToFormat != null) {
            try {
                date = iso8601Format.parse(timeToFormat);
            } catch (ParseException e) {
                date = null;
            }

            if (date != null) {
                long when = date.getTime();
                int flags = 0;
                //flags |= android.text.format.DateUtils.FORMAT_SHOW_TIME;
                flags |= android.text.format.DateUtils.FORMAT_SHOW_DATE;
                flags |= android.text.format.DateUtils.FORMAT_ABBREV_MONTH;
                flags |= android.text.format.DateUtils.FORMAT_SHOW_YEAR;

                finalDateTime = android.text.format.DateUtils.formatDateTime(context,
                        when + TimeZone.getDefault().getOffset(when), flags);
            }
        }
        return finalDateTime;
    }
}
