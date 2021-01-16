package project.estateWatch;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.myViewHolder> {

    Context mContext;
    List<item> mData;

    public Adapter(Context mContext, List<item> mData)
    {
        this.mContext = mContext;
        this.mData = mData;
    }


    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        LayoutInflater inflater = LayoutInflater.from(mContext);
        View v = inflater.inflate(R.layout.card_item,parent,false);
        return new myViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder myViewHolder, int position) {

        myViewHolder.background_img.setImageResource(mData.get(position).getBackground());
        myViewHolder.profile_photo.setImageResource(mData.get(position).getProfilePhoto());
        myViewHolder.tv_title.setText(mData.get(position).getProfileName());
        myViewHolder.tv_nbCall.setText(mData.get(position).getNbCall());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder {


        ImageView profile_photo, background_img;
        TextView tv_title, tv_nbCall;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            profile_photo = itemView.findViewById(R.id.profile_img);
            background_img = itemView.findViewById(R.id.card_background);
            tv_title = itemView.findViewById(R.id.card_title);
            tv_nbCall = itemView.findViewById(R.id.card_nb_call);
        }
    }
}
