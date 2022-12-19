package com.example.pertemuan5_recycleview;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;

import android.widget.Toast;

import com.example.pertemuan5_recycleview.SessionManager;
import com.example.pertemuan5_recycleview.databinding.ActivityMain5Binding;
public class MainActivity5 extends AppCompatActivity implements View.OnClickListener {
    MyDbHelper myDbHelper;
    private ActivityMain5Binding binding;
    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setup view binding
        binding = ActivityMain5Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        session = new SessionManager(getApplicationContext());
        if (session.isLoggedIn()) {
            Intent intent = new Intent(MainActivity5.this, ProfileActivity.class);
            startActivity(intent);
            finish();
        }
        session = new SessionManager(getApplicationContext());
        myDbHelper = new MyDbHelper(this);
        SQLiteDatabase sqLiteDatabase = myDbHelper.getWritableDatabase();
        binding.Signinbuttonid.setOnClickListener(this);
        binding.SignUpHerebuttonid.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String username = binding.signinusernameEditText.getText().toString();
        String password = binding.signinpasswordEditText.getText().toString();
        if (v.getId() == R.id.Signinbuttonid) {
            Boolean result = myDbHelper.findPassword(username, password);
            if (result == true) {
                Intent intent = new Intent(MainActivity5.this, MainActivity.class);
                startActivity(intent);
                session.setLogin(true);
                finish();
            } else {
                Toast.makeText(this, "username and password didn`t match", Toast.LENGTH_SHORT).show();
            }
        } else if (v.getId() == R.id.SignUpHerebuttonid) {
            Intent intent = new Intent(MainActivity5.this,
                    RegisterActivity.class);
            startActivity(intent);
        }
    }
}