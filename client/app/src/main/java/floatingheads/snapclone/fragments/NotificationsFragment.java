package floatingheads.snapclone.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;

import floatingheads.snapclone.R;
import floatingheads.snapclone.activities.CameraPreviewActivity;
import floatingheads.snapclone.objects.Contact;
import floatingheads.snapclone.objects.MessagesView;
import floatingheads.snapclone.objects.User;

/**
 * A simple {@link Fragment} subclass.
 */
public class NotificationsFragment extends Fragment {

    private User user;

    public NotificationsFragment() {
        user = new User();
    }

    /**
     * Return View which contains Message data from friends
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflatedView = inflater.inflate(R.layout.fragment_messages, container, false);

        Toolbar toolbar = (Toolbar) inflatedView.findViewById(R.id.tool_bar);
        toolbar.setNavigationIcon(R.mipmap.ic_add_box_white_24dp);
        toolbar.setTitle("Messages");
        toolbar.setTitleTextColor(Color.WHITE);

        user.setId(getArguments().getInt("uid"));
        user.setFirstName(getArguments().getString("firstName"));
        user.setLastName(getArguments().getString("lastName"));
        user.setUsername(getArguments().getString("username"));
        user.setEmail(getArguments().getString("email"));

        Context messagesFragmentContext = this.getContext();

        Button backtoCameraBtn = inflatedView.findViewById(R.id.cam_notis_btn);

        // TODO integrate with firebase
        ArrayList<Contact> contactArrayList = new ArrayList<>();
        contactArrayList.add(new Contact(1, "Quinn", "Salas", "yo wassup homie?"));
        contactArrayList.add(new Contact(2, "Akira", "Demoss", "New Multimedia Message"));
        contactArrayList.add(new Contact(4, "Simanta", "Mitra", "Congrats your team is green."));

        MessagesView friendsList = (MessagesView) inflatedView.findViewById(R.id.messagesListView);
        friendsList.setContents(contactArrayList);

        backtoCameraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(messagesFragmentContext, CameraPreviewActivity.class);
                i.putExtra("uid", user.getId());
                i.putExtra("firstName", user.getFirstName());
                i.putExtra("lastName", user.getLastName());
                i.putExtra("username", user.getUsername());
                i.putExtra("email", user.getEmail());
                startActivity(i);
            }
        });

        return inflatedView;
    }

}
