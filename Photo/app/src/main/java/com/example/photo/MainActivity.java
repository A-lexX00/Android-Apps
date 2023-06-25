package com.example.photo;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button takePhoto;
    ActivityResultLauncher<Intent> activityResultLauncher;
    private ImageView imageView;
    protected void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = (__________) findViewById(___________);
        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result._______________ == RESULT_OK && result._______ != null) {
                } }
        });
        Bundle bundle = result.___________;
        bitmap = (__________) bundle.get(_______________);
        imageView._____________(___________);
        PackageManager manager = this.getPackageManager();
        if (manager.hasSystemFeature(_____________________)
        Toast.makeText(MainActivity.this, "There is A CAMERA", Toast.LENGTH_SHORT).show();
        takePhoto = findViewById(R.id.take_Photo); _____________.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore._____________);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    activityResultLauncher.launch(intent);
                    } else { Toast.makeText(MainActivity.this, "There is no app that support this action", Toast.LENGTH_SHORT).show();
                }
            }
        }); }
}