package com.cococompany.android.aq;


import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.ShowableListMenu;
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

import com.cococompany.android.aq.models.Category;
import com.cococompany.android.aq.models.University;
import com.cococompany.android.aq.models.User;
import com.cococompany.android.aq.models.UserUniversityInfo;
import com.cococompany.android.aq.utils.AQService;
import com.cococompany.android.aq.utils.LoginPreferences;
import com.cococompany.android.aq.utils.RegistrationService;
import com.cococompany.android.aq.utils.UIutils;
import com.cococompany.android.aq.utils.UniversityService;
import com.cococompany.android.aq.utils.UserUniversityInfoService;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
    private UserUniversityInfoService uuiService;
    private AQService aqService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        UIutils.setToolbar(R.id.toolbar,this);
        retrofit = new Retrofit.Builder()
                .baseUrl(getResources().getString(R.string.project_url))
                .build();

        //services initialization
        aqService = retrofit.create(AQService.class);
        registrationService = new RegistrationService(this);
        uuiService = new UserUniversityInfoService(this);

        temp = new HashMap<String, String>();
        temp.put("user1@gmail.com","pass1");
        temp.put("user2@gmail.com","pass2");
        temp.put("user3@gmail.com","pass3");
        remember_user_checkBox = (CheckBox) findViewById(R.id.remember_user);
        log_in_button = (Button) findViewById(R.id.log_in_button);
        sign_in_button =  (Button) findViewById(R.id.sign_in_button);
        email_edit= (EditText) findViewById(R.id.email_edit);
        email_edit.setText("myagkiyalexandr@hotmail.com");
//        email_edit.setText("myagkiyalexandr@hotmail.com");
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
                    long b = System.currentTimeMillis();
                    User user = null;
                    user = registrationService.loginProfile(email_edit.getText().toString(),password_edit.getText().toString());

                    if(user!=null) {
                        Intent intent = new Intent(MainActivity.this, ContentActivity.class);

                        LoginPreferences preferences = new LoginPreferences(MainActivity.this);
                        user.setPassword(password_edit.getText().toString());
                        preferences.setUser(user);

                        System.out.println("Preferences successfully saved: id="+preferences.getUser().getId()+"; pass="+preferences.getUser().getPassword() + "; email="+preferences.getUser().getEmail() + "; bdate="+preferences.getUser().getBirthdate() + "; fname="+preferences.getUser().getFirstName() + "; lname="+preferences.getUser().getLastName() + "; mname="+preferences.getUser().getMiddleName() + "; nick="+preferences.getUser().getNickname() + "; avatar="+preferences.getUser().getAvatar() + "; categories="+preferences.getUser().getCategories());
                        startActivity(intent);
//                        Toast.makeText(MainActivity.this, ""+(System.currentTimeMillis()-b), Toast.LENGTH_SHORT).show();
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
