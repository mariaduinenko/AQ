package com.cococompany.android.aq;


import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.cococompany.android.aq.models.User;
import com.cococompany.android.aq.utils.AQService;
import com.cococompany.android.aq.utils.LoginPreferences;
import com.cococompany.android.aq.utils.RegistrationService;
import com.cococompany.android.aq.utils.UIutils;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    private Button log_in_button;
    private Button sign_in_button;
    private EditText email_edit;
    private EditText password_edit;
    private CheckBox remember_user_checkBox;
    private HashMap<String, String> temp;
    private Retrofit retrofit;
    private User currentUser;
    private RegistrationService registrationService;
    private AQService aqService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        UIutils.setToolbar(R.id.toolbar,this);
        retrofit = new Retrofit.Builder()
                .baseUrl(getResources().getString(R.string.project_url))
                .build();



        aqService = retrofit.create(AQService.class);
        registrationService = new RegistrationService(this);
        temp=  new HashMap<String, String>();
        temp.put("user1@gmail.com","pass1");
        temp.put("user2@gmail.com","pass2");
        temp.put("user3@gmail.com","pass3");
        remember_user_checkBox = (CheckBox) findViewById(R.id.remember_user);
        log_in_button = (Button) findViewById(R.id.log_in_button);
        sign_in_button =  (Button) findViewById(R.id.sign_in_button);
        email_edit= (EditText) findViewById(R.id.email_edit);
        email_edit.setText("mail@mail.ru");
        password_edit = (EditText) findViewById(R.id.password_edit);
        sign_in_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RegistrationActivity.class);
                startActivity(intent);
            }
        });

        log_in_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (UIutils.isValidEmail(email_edit.getText().toString())){
                    User user = null;
                    user = registrationService.login(email_edit.getText().toString(),password_edit.getText().toString());

                    if(user!=null){
                        Intent intent = new Intent(MainActivity.this, ContentActivity.class);
                        LoginPreferences loginPreferences = new LoginPreferences(MainActivity.this);
                        loginPreferences.setUserId(user.getId().toString());
                        /*if (remember_user_checkBox.isChecked())
                        loginPreferences.setUserPassword(password_edit.getText().toString());*/
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(MainActivity.this,"User doesn't exist",Toast.LENGTH_SHORT).show();
                    }

                }
                else{
                    Toast.makeText(MainActivity.this,"Wrong email",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        UIutils.setSearchBar(R.id.search, menu,this);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
