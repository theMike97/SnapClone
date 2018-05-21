package floatingheads.snapclone.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import floatingheads.snapclone.ImageViewGestures.PhotoView;
import floatingheads.snapclone.R;

/*
 * Created by Akira on 4/15/2018.
 */
public class ImageViewActivity extends AppCompatActivity {

    private Bitmap screenshotBmp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Setup Resource Files
        //TESTING IMAGEVIEW
        PhotoView mPhotoView;
        ImageButton sendButton;
        ImageButton saveButton;

        final Bitmap bmp;
        setContentView(R.layout.activity_imageview);
        sendButton = findViewById(R.id.btn_send);
        saveButton = findViewById(R.id.btn_save);
        mPhotoView = findViewById(R.id.iv_photo);

        //Initialize variables
        screenshotBmp = null;

        //Getting the screenshot (overlay image) sent from the CameraPreviewActivity
        String fname = getIntent().getStringExtra("screenshot");
        try {
            FileInputStream is2 = this.openFileInput(fname);
            screenshotBmp = BitmapFactory.decodeStream(is2);
            is2.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //final bmp for use in savebutton
        bmp = screenshotBmp;


        Log.d("Screenshot Resolution", "Resolution ImageViewActivity Width: " + screenshotBmp.getWidth());
        Log.d("Screenshot Resolution", "Resolution ImageViewActivity Height: " + screenshotBmp.getHeight());

        //Get screenshot
        Drawable dScreenshot = new BitmapDrawable(getResources(), screenshotBmp);
        mPhotoView.setImageDrawable(dScreenshot);

        //Listener for Send Button
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Map to where you want to send the bitmap
                Intent i = new Intent(getApplicationContext(), FriendsActivity.class);
                startActivity(i);
            }
        });

        //Listener for Save Button
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    //Pop up message indicating image saved


                    //Functionality for saving image
                    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                    String path = Environment.getExternalStorageDirectory().toString()+File.separator+"Pictures"+File.separator+"SnapClone";
                    File file = new File(path, "SnapClone"+timeStamp+".jpeg");
                    FileOutputStream fileOut = new FileOutputStream(file);
                    bmp.compress(Bitmap.CompressFormat.PNG, 100, fileOut);
                    fileOut.flush(); // Not really required
                    fileOut.close(); // do not forget to close the stream
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Toast.makeText(ImageViewActivity.this, "Image Saved in folder root/sdcard/Pictures/Snapclone", Toast.LENGTH_SHORT).show();
                saveButton.setEnabled(false);
            }
        });
    }


    /**
     * On pause
     */
    @Override
    protected void onPause() {
        super.onPause();
        screenshotBmp.recycle();
    }

    /**
     * on destroy
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        screenshotBmp.recycle();
    }
}
