package floatingheads.snapclone.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.Date;
import java.util.Random;

import floatingheads.snapclone.R;
import floatingheads.snapclone.adapters.ChatAdapter;
import floatingheads.snapclone.controllers.SwipeController;
import floatingheads.snapclone.controllers.SwipeControllerActions;
import floatingheads.snapclone.objects.Message;
import floatingheads.snapclone.objects.User;

public class ChatActivity extends AppCompatActivity {

    private DatabaseReference databaseRef;
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference storageRef = storage.getReference();
    private StorageReference imgRef = storageRef.child("attachment" + new Random().nextInt(101) + ".jpg");

    private EditText chatInput;

    private SwipeController swipeController = null;
    private ChatAdapter chatAdapter = new ChatAdapter();

    private User user;

    private final int GALLERY_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_chat);

        Toolbar toolbar = (Toolbar) findViewById(R.id.chat_toolbar);
//        toolbar.setTitle("Chat");

        chatInput = findViewById(R.id.chat_input);
        ImageButton attachImage = findViewById(R.id.attach);

        user = new User(
                getIntent().getExtras().getInt("uid"),
                getIntent().getExtras().getString("firstName"),
                getIntent().getExtras().getString("lastName"),
                getIntent().getExtras().getString("username"),
                getIntent().getExtras().getString("email")
        );

        createConnection();
        setupRecyclerView();

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        chatInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                Message msg = new Message();
//                msg.setID(user.getId());
                msg.setID(1);
                msg.setUser(user.getFirstName());
//                msg.setUser("Quinn");
                msg.setMessage(chatInput.getText().toString());
                // msg.setImageURI(imageURI);
                msg.setTimestamp(System.currentTimeMillis());
                databaseRef.child(String.valueOf(new Date().getTime())).setValue(msg);
                return true;
            }
        });

        attachImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                galleryIntent.setType("image/* video/*");
                startActivityForResult(Intent.createChooser(galleryIntent, "Select Image"), GALLERY_REQUEST);
            }
        });

        RecyclerView recyclerView = findViewById(R.id.chat_messages);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK)
            switch (requestCode) {
                case GALLERY_REQUEST:
                    Uri selectedImage = data.getData();
                    try {
                        // Get Image
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);

                        // Prepare Image for Upload to Firebase
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                        byte[] img = baos.toByteArray();

                        // Upload Image to Firebase
                        UploadTask uploadTask = imgRef.putBytes(img);
                        uploadTask.addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                Log.d("UploadError", exception.toString());
                            }
                        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                Toast.makeText(getApplicationContext(), "Image Uploaded!", Toast.LENGTH_SHORT).show();
                                Uri downloadUrl = taskSnapshot.getDownloadUrl();
                                // TODO: Display Image
                            }
                        });
                    } catch (Exception e) {
                        Log.i("TAG", "Exception: " + e);
                    }
                    break;
            }
    }

    private void setupRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.chat_messages);
        recyclerView.setAdapter(chatAdapter);

        swipeController = new SwipeController(new SwipeControllerActions() {
            @Override
            public void onRightClicked(int position) {
                chatAdapter.remove(position);
                chatAdapter.notifyItemRemoved(position);
                chatAdapter.notifyItemRangeChanged(position, chatAdapter.getItemCount());
            }
        });

        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeController);
        itemTouchhelper.attachToRecyclerView(recyclerView);

        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                swipeController.onDraw(c);
            }
        });
    }


    private void createConnection() {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        databaseRef = db.getReference();
        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("FirebaseDatabase", "Suceess");
                handleEvent(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("FirebaseDatabase", "Failure" + databaseError.getMessage());
            }
        });
    }

    private void handleEvent(DataSnapshot dataSnapshot) {
        chatAdapter.clearData();
        chatInput.setText(null);

        for (DataSnapshot ds : dataSnapshot.getChildren()) {
            Message msg = ds.getValue(Message.class);
            chatAdapter.addData(msg);
        }
        chatAdapter.notifyDataSetChanged();
    }
}
