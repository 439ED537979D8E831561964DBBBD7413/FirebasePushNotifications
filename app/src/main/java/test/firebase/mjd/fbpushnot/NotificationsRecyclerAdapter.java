package test.firebase.mjd.fbpushnot;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import de.hdodenhof.circleimageview.CircleImageView;

import java.util.List;

class NotificationsRecyclerAdapter extends RecyclerView.Adapter<NotificationsRecyclerAdapter.ViewHolder> {

    private List<Notifications> mNotifList;
    private Context context;

    private FirebaseFirestore mFirestore;

    public NotificationsRecyclerAdapter (Context context, List<Notifications> mNotifList) {
        this.mNotifList = mNotifList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.single_notification, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final NotificationsRecyclerAdapter.ViewHolder viewHolder, final int i) {

        mFirestore = FirebaseFirestore.getInstance();

        String from_id = mNotifList.get(i).getFrom();

        viewHolder.mNotifMessage.setText(mNotifList.get(i).getMessage());

        mFirestore.collection("Users").document(from_id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                String name = documentSnapshot.getString("name");
                String image = documentSnapshot.getString("image");

                viewHolder.mNotifName.setText(name);

                RequestOptions requestOptions = new RequestOptions();
                requestOptions.placeholder(R.mipmap.round_1);
                //Glide.with(context).load(image).into(viewHolder.mNotifImage);
                Glide.with(context).setDefaultRequestOptions(requestOptions).load(image).into(viewHolder.mNotifImage);

            }
        });
    }

    @Override
    public int getItemCount() {
        return mNotifList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        View mView;

        public CircleImageView mNotifImage;
        public TextView mNotifMessage;
        public TextView mNotifName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mView = itemView;

            mNotifImage = mView.findViewById(R.id.notif_image);
            mNotifName = mView.findViewById(R.id.notif_name);
            mNotifMessage = mView.findViewById(R.id.notif_message);

        }
    }
}
