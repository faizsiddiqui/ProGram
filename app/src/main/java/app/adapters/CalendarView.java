package app.adapters;

import android.support.v7.widget.RecyclerView;

/**
 * Created by apple on 4/10/2015.
 */


    import android.support.v7.widget.RecyclerView;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.ImageView;
    import android.widget.TextView;

    import app.program.R;

    /**
     * Created by apple on 3/28/2015.
     */
    public class CalendarView {

        String[] calendarTitle, calendarText;
        int[] calendarIcon;
        OnItemClickListener mItemClickListener;

        public CalendarView(String[] calendarTitle, String[] calendarText, int[] calendarIcon){
            this.calendarTitle = calendarTitle;
            this.calendarText = calendarText;
            this.calendarIcon = calendarIcon;
        }


        public CalendarView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.forum_row, parent, false);
            return new ViewHolder(view);
        }


        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.calendarTopTitle.setText("Tabs");
            holder.calendarTitle.setText(calendarTitle[position]);
            holder.calendarText.setText(calendarText[position]);
            holder.calendarIcon.setImageResource(calendarIcon[position]);
        }


        public int getItemCount() {
            return calendarTitle.length;
        }

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            TextView calendarTitle, calendarText, calendarTopTitle;
            ImageView calendarIcon;
            public ViewHolder(View itemView) {
                super(itemView);
                calendarTopTitle   = (TextView) itemView.findViewById(R.id.calendarTopTitle);
                calendarTitle = (TextView) itemView.findViewById(R.id.calendarTitle);
                calendarText  = (TextView) itemView.findViewById(R.id.calendarText);
                calendarIcon = (ImageView) itemView.findViewById(R.id.calendarIcon);
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



}
