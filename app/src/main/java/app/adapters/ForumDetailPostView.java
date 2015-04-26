package app.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import app.program.R;

/**
 * Not for public use
 * Created by FAIZ on 26-04-2015.
 */
public class ForumDetailPostView extends RecyclerView.Adapter<ForumDetailPostView.ViewHolder> {

    private String[] titles, descriptions;

    public ForumDetailPostView(String[] titles, String[] descriptions){
        this.titles = titles;
        this.descriptions = descriptions;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.forum_post_detail_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.detailTitle.setText(titles[position]);
        holder.detailDescription.setText(descriptions[position].replace("\\n", "\n"));
    }

    @Override
    public int getItemCount() {
        return titles.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView detailTitle, detailDescription;

        public ViewHolder(View itemView) {
            super(itemView);
            detailTitle = (TextView) itemView.findViewById(R.id.detail_title);
            detailDescription = (TextView) itemView.findViewById(R.id.detail_description);
        }
    }
}
