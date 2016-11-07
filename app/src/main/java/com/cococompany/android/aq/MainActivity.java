package com.cococompany.android.aq;


import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.cococompany.android.aq.models.User;
import com.cococompany.android.aq.utils.AQService;
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
    private HashMap<String, String> temp;
    private Pattern p;
    private Retrofit retrofit;
    private User currentUser;

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
        temp=  new HashMap<String, String>();
        temp.put("user1@gmail.com","pass1");
        temp.put("user2@gmail.com","pass2");
        temp.put("user3@gmail.com","pass3");
        log_in_button = (Button) findViewById(R.id.log_in_button);
        sign_in_button =  (Button) findViewById(R.id.sign_in_button);
        email_edit= (EditText) findViewById(R.id.email_edit);
        password_edit = (EditText) findViewById(R.id.password_edit);
        p = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
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
                if (isValidEmail(email_edit.getText().toString())){
                    Call<User> user = aqService.getRegisteredUser(email_edit.getText().toString());
                    user.enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            currentUser = response.body();
                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {

                        }
                    });
                    if(temp.containsKey(email_edit.getText().toString())){
                        if (temp.get(email_edit.getText().toString()).equals(password_edit.getText().toString())){
                          Toast.makeText(MainActivity.this,"Login Completed",Toast.LENGTH_SHORT).show();
                        }
                        else{
                          Toast.makeText(MainActivity.this,"Wrong Password",Toast.LENGTH_SHORT).show();
                        }
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

    public boolean isValidEmail(String email){
        Matcher m = p.matcher(email);
        return m.matches();
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
