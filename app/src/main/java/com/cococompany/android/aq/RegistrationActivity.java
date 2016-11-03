package com.cococompany.android.aq;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.cococompany.android.aq.utils.UIutils;

public class RegistrationActivity extends AppCompatActivity {
    private Button register_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        UIutils.setToolbar(R.id.toolbar, this);
        register_button  = (Button) findViewById(R.id.register_button);
        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=  new Intent(RegistrationActivity.this, ContentActivity.class);
                startActivity(intent);
            }
        });
    }

}
