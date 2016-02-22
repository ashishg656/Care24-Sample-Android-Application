package ashish.sample.care24.com.care24sample.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

import ashish.sample.care24.com.care24sample.R;
import ashish.sample.care24.com.care24sample.objects.FeedObject;
import ashish.sample.care24.com.care24sample.serverApi.ImageRequestManager;

/**
 * Created by ashis_000 on 2/23/2016.
 */
public class HomeActivityListAdapter extends RecyclerView.Adapter<HomeActivityListAdapter.FeedHolder> {

    Context context;
    List<FeedObject.FeedSingleObject> mData;

    public HomeActivityListAdapter(Context context, List<FeedObject.FeedSingleObject> mData) {
        this.context = context;
        this.mData = mData;
    }

    @Override
    public FeedHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.home_activity_list_item_layout, parent, false);
        FeedHolder holder = new FeedHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(FeedHolder holder, int position) {
        holder.title.setText(mData.get(position).getTitle());
        ImageRequestManager.get(context).requestImage(context, holder.image, mData.get(position).getUri(), -1);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class FeedHolder extends RecyclerView.ViewHolder {

        RoundedImageView image;
        TextView title;

        public FeedHolder(View v) {
            super(v);
            image = (RoundedImageView) v.findViewById(R.id.imagehmome);
            title = (TextView) v.findViewById(R.id.title);
        }
    }
}
