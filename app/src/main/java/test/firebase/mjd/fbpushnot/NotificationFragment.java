package test.firebase.mjd.fbpushnot;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.*;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class NotificationFragment extends Fragment {

    private RecyclerView mNotifListView;
    private NotificationsRecyclerAdapter notificationsRecyclerAdapter;

    private List<Notifications> mNotifList;
    private FirebaseFirestore mFirestore;

    public NotificationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notification, container, false);

        mFirestore = FirebaseFirestore.getInstance();
        mNotifListView = view.findViewById(R.id.notif_list_view);

        mNotifList = new ArrayList<>();
        notificationsRecyclerAdapter = new NotificationsRecyclerAdapter(container.getContext(), mNotifList);

        mNotifListView.setHasFixedSize(true);
        mNotifListView.setLayoutManager(new LinearLayoutManager(container.getContext()));
        mNotifListView.setAdapter(notificationsRecyclerAdapter);

        //String current_user_id = FirebaseAuth.getInstance().getCurrentUser().getUid();

        //Toast.makeText(container.getContext(), "User_ID: " + current_user_id, Toast.LENGTH_LONG).show();

        /*mFirestore.collection("Users").document(current_user_id).collection("Notifications").addSnapshotListener(getActivity(), new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                for (DocumentChange doc: queryDocumentSnapshots.getDocumentChanges()) {

                    Notifications  notifications = doc.getDocument().toObject(Notifications.class);
                    mNotifList.add(notifications);

                    notificationsRecyclerAdapter.notifyDataSetChanged();

                }
            }
        });*/

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        mNotifList.clear();

        String current_user_id = FirebaseAuth.getInstance().getCurrentUser().getUid();

        Toast.makeText(getContext(), "User_ID: " + current_user_id, Toast.LENGTH_LONG).show();

        mFirestore.collection("Users").document(current_user_id).collection("Notifications").addSnapshotListener(getActivity(), new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                for (DocumentChange doc: queryDocumentSnapshots.getDocumentChanges()) {

                    Notifications  notifications = doc.getDocument().toObject(Notifications.class);
                    mNotifList.add(notifications);

                    notificationsRecyclerAdapter.notifyDataSetChanged();

                }
            }
        });
    }
}
