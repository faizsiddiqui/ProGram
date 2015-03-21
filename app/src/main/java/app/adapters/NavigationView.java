package app.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import app.program.R;

/**
 * Not for public use
 * Created by FAIZ on 20-02-2015.
 */
public class NavigationView extends RecyclerView.Adapter<NavigationView.ViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    public String[] navigationRowText;
    public Integer[] navigationRowImage;
    OnItemClickListener mItemClickListener;

    public NavigationView(Context context, String[] navigationRowText, Integer[] navigationRowImage) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.navigationRowText = navigationRowText;
        this.navigationRowImage = navigationRowImage;

    }

    @Override
    public NavigationView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.base_navigation_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.navigationRowText.setText(navigationRowText[position]);
        holder.navigationRowImage.setImageResource(navigationRowImage[position]);
    }

    @Override
    public int getItemCount() {
        return navigationRowText.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView navigationRowText;
        ImageView navigationRowImage;

        public ViewHolder(View itemView) {
            super(itemView);
            navigationRowText = (TextView) itemView.findViewById(R.id.navigationRowText);
            navigationRowImage = (ImageView) itemView.findViewById(R.id.navigationRowImage);
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
