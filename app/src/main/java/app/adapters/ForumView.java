package app.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import app.program.R;

/**
 * Not for public use
 * Created by FAIZ on 12-03-2015.
 */
public class ForumView extends RecyclerView.Adapter<ForumView.ViewHolder> {

    String[] forumTitle, forumText;
    int[] forumIcon;
    OnItemClickListener mItemClickListener;

    public ForumView(String[] forumTitle, String[] forumText, int[] forumIcon){
        this.forumTitle = forumTitle;
        this.forumText = forumText;
        this.forumIcon = forumIcon;
    }

    @Override
    public ForumView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.forum_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.forumTopTitle.setText("Latest");
        holder.forumTitle.setText(forumTitle[position]);
        holder.forumText.setText(forumText[position]);
        holder.forumIcon.setImageResource(forumIcon[position]);
    }

    @Override
    public int getItemCount() {
        return forumTitle.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView forumTitle, forumText, forumTopTitle;
        ImageView forumIcon;
        public ViewHolder(View itemView) {
            super(itemView);
            forumTopTitle = (TextView) itemView.findViewById(R.id.forumTopTitle);
            forumTitle = (TextView) itemView.findViewById(R.id.forumTitle);
            forumText = (TextView) itemView.findViewById(R.id.forumText);
            forumIcon = (ImageView) itemView.findViewById(R.id.forumIcon);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(mItemClickListener != null){
                mItemClickListener.onItemClick(v, getPosition());
            }
        }
    }

    public interface OnItemClickListener{
        public void onItemClick(View view, int position);
    }

    public void SetOnItemClickListener(OnItemClickListener mItemClickListener){
        this.mItemClickListener = mItemClickListener;
    }
}
