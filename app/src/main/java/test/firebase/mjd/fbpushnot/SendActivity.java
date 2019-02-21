package test.firebase.mjd.fbpushnot;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.*;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SendActivity extends AppCompatActivity {

    private TextView userNameView;
    private  TextView userIdView;
    private EditText mMessageView;
    private Button mSendBtn;
    private ProgressBar mMessageProgress;

    private String mUserId;
    private  String mUserName;
    private String mCurrentId;

    private FirebaseFirestore mFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send);

        userNameView = findViewById(R.id.user_name_view);
        userIdView = findViewById(R.id.user_id_view);
        mMessageView = findViewById(R.id.message_view);
        mSendBtn = findViewById(R.id.send_btn);
        mMessageProgress = findViewById(R.id.messageProgress);

        mFirestore = FirebaseFirestore.getInstance();
        mCurrentId = FirebaseAuth.getInstance().getUid();

        mUserId = getIntent().getStringExtra("user_id");
        mUserName = getIntent().getStringExtra("user_name");
        userIdView.setText("UID: " + mUserId);
        userNameView.setText("Send to " + mUserName);


        mSendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String message = mMessageView.getText().toString();

                if(!TextUtils.isEmpty(message)) {
                    mMessageProgress.setVisibility(View.VISIBLE);

                    Map<String, Object> notificationMessage = new HashMap<>();
                    notificationMessage.put("message", message);
                    notificationMessage.put("from", mCurrentId);

                    mFirestore.collection("Users/" + mUserId + "/Notifications").add(notificationMessage).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Toast.makeText(SendActivity.this, "Notification Sent", Toast.LENGTH_SHORT).show();
                            mMessageView.setText("");
                            mMessageProgress.setVisibility(View.INVISIBLE);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(SendActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            mMessageProgress.setVisibility(View.INVISIBLE);
                        }
                    });

                }

            }
        });

    }
}
