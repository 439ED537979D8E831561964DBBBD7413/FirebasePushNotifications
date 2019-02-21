package test.firebase.mjd.fbpushnot;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import de.hdodenhof.circleimageview.CircleImageView;

import java.util.List;

public class UsersRecyclerAdapter extends RecyclerView.Adapter<UsersRecyclerAdapter.ViewHolder> {

    private List<Users> usersList;
    private Context context;

    public UsersRecyclerAdapter(Context context, List<Users> usersList) {
        this.usersList = usersList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.user_list_item, viewGroup, false);

        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.user_name_view.setText(usersList.get(i).getName());

        CircleImageView user_image_view = viewHolder.user_image_view;
        Glide.with(context).load(usersList.get(i).getImage()).into(user_image_view);

        final String user_id = usersList.get(i).userID;
        final String user_name = usersList.get(i).getName();

        viewHolder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent sendIntent = new Intent(context, SendActivity.class);
                sendIntent.putExtra("user_id", user_id);
                sendIntent.putExtra("user_name", user_name);
                context.startActivity(sendIntent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return usersList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private View mView;

        private CircleImageView user_image_view;
        private TextView user_name_view;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mView = itemView;

            user_image_view = mView.findViewById(R.id.user_list_image);
            user_name_view =  mView.findViewById(R.id.user_list_name);
        }
    }
}
