package test.firebase.mjd.fbpushnot;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import de.hdodenhof.circleimageview.CircleImageView;

import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    private CircleImageView mProfileImage;
    private TextView mProfileName;

    private Button mLogoutBtn;
    private FirebaseAuth mAuth;
    private FirebaseFirestore mFirestore;
    private String mUserId;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        mAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();
        mUserId = mAuth.getCurrentUser().getUid();

        mLogoutBtn = (Button) view.findViewById(R.id.logout_btn);
        mProfileImage = (CircleImageView) view.findViewById(R.id.profile_image);
        mProfileName = (TextView) view.findViewById(R.id.profile_name);

        mFirestore.collection("Users").document(mUserId).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String user_name = documentSnapshot.getString("name");
                String user_image = documentSnapshot.getString("image");

                mProfileName.setText(user_name);

                RequestOptions placeholderOption = new RequestOptions();
                placeholderOption.placeholder(R.mipmap.round_1);

                Glide.with(container.getContext()).setDefaultRequestOptions(placeholderOption).load(user_image).into(mProfileImage);
                //Glide.with(ProfileFragment.this).load("http://you-ps.ru/uploads/posts/2013-08/1376601606_1273.png").into(mProfileImage);
                //Glide.with(container.getContext()).load(user_image).into(mProfileImage);
            }
        });

        mLogoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Map<String, Object> tokenMapRemove = new HashMap<>();
                tokenMapRemove.put("token_id", FieldValue.delete());
                mFirestore.collection("Users").document(mUserId).update(tokenMapRemove).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        mAuth.signOut();
                        Intent loginIntent = new Intent(container.getContext(), MainActivity.class);
                        startActivity(loginIntent);
                    }
                });
            }
        });

        return view;
    }

}
