package floatingheads.snapclone.activities;


import android.content.Intent;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;


import floatingheads.snapclone.R;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//        // Button to call OpenCV Camera Activity
//        Button cameraInit = findViewById(R.id.openCamera);
//        cameraInit.setOnClickListener(new View.OnClickListener() {
//            /**
//             * Opens CameraPreviewActivity.
//             * CameraPreviewActivity launches the camera
//             * @param v
//             */
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(getApplicationContext(),CameraPreviewActivity.class);
//                startActivity(i);
//            }
//        });

        // On Log In Button Click - Open Log In Screen
        Button login = findViewById(R.id.logIn);
        login.setOnClickListener(new View.OnClickListener() {
            /**
             * Opens LoginActivity
             * LoginActivity processes login entries and either logs the user in or returns an error
             * @param v
             */
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(i);
            }
        });

        // On Sign Up Button Click - Open Sign Up Screen
        Button signup = findViewById(R.id.signUp);
        signup.setOnClickListener(new View.OnClickListener() {
            /**
             * Opens SignUpActivity
             * SignUpActivity asks user for information which it then uploads to the USERS database
             * @param v
             */
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(i);
            }
        });
    }

}
