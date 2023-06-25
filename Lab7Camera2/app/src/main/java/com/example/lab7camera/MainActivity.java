package com.example.lab7camera;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;

import android.provider.MediaStore;
import android.view.View;

import androidx.core.content.FileProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.lab7camera.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    Button takePhoto;
    ActivityResultLauncher<Intent> activityResultLauncher;
    private ImageView imageView;
    private Bitmap bitmap; //??? Line 50
    private Button b1;
    private BitmapGrayer grayer;
    private SeekBar redBar, greenBar, blueBar;
    private TextView redTV, greenTV, blueTV;
    Uri uri;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = (ImageView) findViewById(R.id.picture);
        b1 = (Button) findViewById(R.id.take_Photo);  // ??? findViewById
        redBar = (SeekBar) findViewById(R.id.red_bar);
        greenBar = (SeekBar) findViewById(R.id.green_bar);
        blueBar = (SeekBar) findViewById(R.id.blue_bar);

        redTV = (TextView) findViewById(R.id.red_tv);
        greenTV = (TextView) findViewById(R.id.green_tv);
        blueTV = (TextView) findViewById(R.id.blue_tv);

        GrayChangeHandler gch = new GrayChangeHandler(); //class (line 104)
        redBar.setOnSeekBarChangeListener(gch);
        greenBar.setOnSeekBarChangeListener(gch);
        blueBar.setOnSeekBarChangeListener(gch);

            activityResultLauncher = registerForActivityResult(new
                ActivityResultContracts.StartActivityForResult(), new
                ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                            Bundle bundle = result.getData().getExtras();
                            bitmap = (Bitmap) bundle.get("data");
                            grayer = new BitmapGrayer(bitmap,0.34f, 0.33f, 0.33f);
                            bitmap = grayer.grayScale();
                            imageView.setImageBitmap(bitmap);
                        }
                    }
                });



        PackageManager manager = this.getPackageManager();
        if (manager.hasSystemFeature(PackageManager.FEATURE_CAMERA) )
        Toast.makeText(MainActivity.this, "There is A CAMERA",
                Toast.LENGTH_SHORT).show();
        takePhoto = findViewById(R.id.take_Photo);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    activityResultLauncher.launch(intent);
                } else { Toast.makeText(MainActivity.this, "There is no app that support this action",
                    Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    private class GrayChangeHandler implements SeekBar.OnSeekBarChangeListener {
        public void onProgressChanged(SeekBar seekBar,
                                      int progress, boolean fromUser) {
            if (fromUser) {
                if (seekBar == redBar) {
                    grayer.setRedCoeff(progress / 100.0f);
                    redBar.setProgress((int) (100 * grayer.getRedCoeff()));
                    redTV.setText( "" + MathRounding.keepTwoDigits(
                            grayer.getRedCoeff( ) ) );
                } else if (seekBar == greenBar) {
                    grayer.setGreenCoeff(progress / 100.0f);
                    greenBar.setProgress((int) (100 * grayer.getGreenCoeff()));
                    greenTV.setText( "" + MathRounding.keepTwoDigits(
                            grayer.getGreenCoeff( ) ) );
                } else if (seekBar == blueBar) {
                    grayer.setBlueCoeff(progress / 100.0f);
                    blueBar.setProgress((int) (100 * grayer.getBlueCoeff()));
                    blueTV.setText( ""+ MathRounding.keepTwoDigits(
                            grayer.getBlueCoeff( ) ) );
                }
                bitmap = grayer.grayScale();
                imageView.setImageBitmap(bitmap);
            }
        }
        public void onStartTrackingTouch(SeekBar seekBar) {
        }
        public void onStopTrackingTouch(SeekBar seekBar) {
        }
    }

    public void send(View view) {
        try {
            File file = StorageUtility.writeToExternalStorage( this, bitmap );

            Intent emailIntent = new Intent(Intent.ACTION_SEND);
            emailIntent.setType("image/png");
            emailIntent.putExtra(Intent.EXTRA_SUBJECT,
                    "Photo sent from my Android");
            Uri uri = FileProvider.getUriForFile(MainActivity.this,
                    "com.android.example.photo.fileprovider", file);
            emailIntent.putExtra(Intent.EXTRA_STREAM, uri);
            startActivity(Intent.createChooser(emailIntent, "Share your picture"));
                    Toast.makeText(this, "EMAIL PICTURE",
                            Toast.LENGTH_LONG).show();
        } catch (IOException ioe) {
            Toast.makeText(this, ioe.getMessage()
                    + "; could not send it", Toast.LENGTH_LONG).show();
        }
    }


}
