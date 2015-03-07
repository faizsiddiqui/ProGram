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
 * Created by FAIZ on 22-02-2015.
 */
public class CardView extends RecyclerView.Adapter<CardView.ViewHolder> {

    public String[] titles, descriptions, images;
    OnItemClickListener mItemClickListener;
    ImageLoader mImageLoader;

    public CardView(String[] titles, String[] descriptions, String[] images) {
        this.titles = titles;
        this.descriptions = descriptions;
        this.images = images;
    }

    @Override
    public CardView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view, parent, false);
        mImageLoader = VolleySingleton.getInstance().getImageLoader();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CardView.ViewHolder holder, int position) {
        holder.cardViewTitle.setText(titles[position]);
        holder.cardViewText.setText(descriptions[position]);
        holder.cardNetworkImageView.setImageUrl(images[position], mImageLoader);
    }

    @Override
    public int getItemCount() {
        return titles.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        NetworkImageView cardNetworkImageView;
        TextView cardViewTitle, cardViewText;

        public ViewHolder(View itemView) {
            super(itemView);
            cardNetworkImageView = (NetworkImageView) itemView.findViewById(R.id.cardViewImage);
            cardViewTitle = (TextView) itemView.findViewById(R.id.cardViewTitle);
            cardViewText = (TextView) itemView.findViewById(R.id.cardViewText);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(v, getPosition());
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
