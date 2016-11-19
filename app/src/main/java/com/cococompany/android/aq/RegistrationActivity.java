package com.cococompany.android.aq;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.cococompany.android.aq.models.User;
import com.cococompany.android.aq.utils.AQService;
import com.cococompany.android.aq.utils.RegistrationService;
import com.cococompany.android.aq.utils.UIutils;

import java.sql.Date;
import java.text.SimpleDateFormat;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegistrationActivity extends AppCompatActivity {
    private Button register_button;
    private EditText name_et;
    private EditText nickname_et;
    private EditText email_et;
    private EditText password_et;
    private EditText confirm_password_et;
    private RegistrationService registrationService;
    private int hintColor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        registrationService = new RegistrationService(this);
        UIutils.setToolbar(R.id.toolbar, this);
        register_button  = (Button) findViewById(R.id.register_button);
        name_et = (EditText) findViewById(R.id.name_edit_text);
        nickname_et = (EditText) findViewById(R.id.nickname_edit_text);
        email_et = (EditText) findViewById(R.id.email_edit_text);
        password_et = (EditText) findViewById(R.id.password_edit_text);
        confirm_password_et = (EditText) findViewById(R.id.confirm_password_edit_text);
        hintColor =  name_et.getCurrentHintTextColor();

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().length()>0){
                name_et.setHint(getResources().getString(R.string.enter_name));
                name_et.setHintTextColor(hintColor);
                nickname_et.setHint(getResources().getString(R.string.enter_nickname));
                nickname_et.setHintTextColor(hintColor);
                email_et.setHint(getResources().getString(R.string.enter_email));
                email_et.setHintTextColor(hintColor);
                password_et.setHint(getResources().getString(R.string.enter_password));
                password_et.setHintTextColor(hintColor);
                }
            }
        };

        name_et.addTextChangedListener(textWatcher);
        nickname_et.addTextChangedListener(textWatcher);
        email_et.addTextChangedListener(textWatcher);
        password_et.addTextChangedListener(textWatcher);
        confirm_password_et.addTextChangedListener(textWatcher);


        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isAllCorrect = true;
                if (!UIutils.isValidName(name_et.getText().toString())){
                    name_et.setText("");
                    name_et.setHint(getString(R.string.wrong_name_format));
                    name_et.setHintTextColor(Color.RED);
                    isAllCorrect = false;
                }
                if (nickname_et.getText().toString().equals("")){
                    nickname_et.setHintTextColor(Color.RED);
                    isAllCorrect = false;
                }
                if (!UIutils.isValidEmail(email_et.getText().toString())){
                    email_et.setHintTextColor(Color.RED);
                    email_et.setText("");
                    email_et.setHint(getString(R.string.wrong_email));
                    isAllCorrect = false;
                }
                if(password_et.getText().toString().equals("")){
                    password_et.setHintTextColor(Color.RED);
                    isAllCorrect = false;
                }
                if (!password_et.getText().toString().equals(confirm_password_et.getText().toString())){
                    password_et.setHintTextColor(Color.RED);
                    password_et.setHint(getString(R.string.different_passwords));
                    password_et.setText("");
                    confirm_password_et.setText("");
                    isAllCorrect = false;
                }

                if(isAllCorrect){
                    User newUser = new User();
                    String[] names = name_et.getText().toString().split(" ");
                    newUser.setFirstName(names[0]);
                    newUser.setMiddleName(names[1]);
                    newUser.setLastName(names[2]);
                    newUser.setEmail(email_et.getText().toString());
                    newUser.setPassword(password_et.getText().toString());
                    newUser.setNickname(nickname_et.getText().toString());
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                    String currentDateandTime = sdf.format(new java.util.Date());
                    newUser.setCreationTime(currentDateandTime);
                    newUser.setPasswordConfirm(confirm_password_et.getText().toString());
                    newUser.setBirthdate(currentDateandTime);
                    newUser.setAvatar("https://encrypted-tbn3.gstatic.com/images?q=tbn:ANd9GcQPJM4YvjzSCCDoQdLrpE1OP4CjT6kr8P-HMy8FfTjfhMgLpjPE03WXtw");
                    System.out.println(newUser.toString());
                    //registrationService.register(newUser);
                    //Intent intent=  new Intent(RegistrationActivity.this, ContentActivity.class);
                    //startActivity(intent);
                }

            }
        });
    }

}
