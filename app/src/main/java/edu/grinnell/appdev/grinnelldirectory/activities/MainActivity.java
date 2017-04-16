package edu.grinnell.appdev.grinnelldirectory.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import edu.grinnell.appdev.grinnelldirectory.R;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent testIntent = new Intent(this, LoginActivity.class);
        startActivity(testIntent);
    }

}
