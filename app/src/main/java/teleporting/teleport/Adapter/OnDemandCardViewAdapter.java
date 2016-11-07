package teleporting.teleport.Adapter;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import teleporting.teleport.Data.OnDemandData;
import teleporting.teleport.R;
import teleporting.teleport.ui.ItemSelectionAddition;

/**
 * Created by Roshini on 29-10-2016.
 */

public class OnDemandCardViewAdapter extends RecyclerView.Adapter<OnDemandCardViewAdapter.MyViewHolder> {

    private Context mContext;
    private List<OnDemandData> mSegmentList;

    public OnDemandCardViewAdapter(Context mContext, List<OnDemandData> segmentList) {
        this.mContext = mContext;
        this.mSegmentList = segmentList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.category_cards, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        OnDemandData album = mSegmentList.get(position);
        holder.title.setText(album.getServiceName());
        holder.count.setText(album.getCategory());

        holder.thumbnail.setImageResource(album.getThumbnail());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ItemSelectionAddition dFragment = new ItemSelectionAddition();
                // Show DialogFragment
                FragmentManager fm = getSupportFragmentManager();
                dFragment.show(fm, "Dialog Fragment");

            }
        });
    }

    @Override
    public int getItemCount() {
        return mSegmentList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, count;
        public ImageView thumbnail, overflow;
        public CardView cardView;

        public MyViewHolder(View view) {
            super(view);
            cardView = (CardView)view.findViewById(R.id.card_view);
            title = (TextView) view.findViewById(R.id.title);
            count = (TextView) view.findViewById(R.id.count);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
            overflow = (ImageView) view.findViewById(R.id.overflow);
        }
    }

    public static interface ItemClickListener {
        void onCliclCardView();
    }
}
