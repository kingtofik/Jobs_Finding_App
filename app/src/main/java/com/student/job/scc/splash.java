package com.student.job.scc;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.student.job.scc.Model.Users;
import com.student.job.scc.Prevalent.Prevalent;

import io.paperdb.Paper;


public class splash extends AppCompatActivity {
    ImageView imageView;
    TextView textView;
    Animation animation;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);


        imageView=(ImageView)findViewById(R.id.scclogo);
        textView=(TextView)findViewById(R.id.scclogoname);

        loadingBar=new ProgressDialog(this);
        Paper.init(this);

        /*String UserPhoneKey=Paper.book().read(Prevalent.UserPhoneKey);
        String UserPasswordKey=Paper.book().read(Prevalent.UserPasswordKey);

        if(UserPhoneKey!="" && UserPasswordKey!="")
        {
            if(!TextUtils.isEmpty(UserPhoneKey) && !TextUtils.isEmpty(UserPasswordKey));
            {
                AllowAccess(UserPhoneKey,UserPasswordKey);
                loadingBar.dismiss();
            }
        }*/

        animation= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.push_down);
        imageView.setAnimation(animation);

        animation=AnimationUtils.loadAnimation(getApplicationContext(),R.anim.push_right);
        textView.setAnimation(animation);
        Thread thread=new Thread(){

            @Override
            public void run() {
                try {
                    sleep(3500);
                    Intent intent=new Intent(splash.this,Signup.class);
                    startActivity(intent);
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
    }

      /*private void AllowAccess(final String phone, final String password)
    {
        final DatabaseReference RootRef;
        RootRef= FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if(dataSnapshot.child("Users").child(phone).exists())
                {
                    Users userData=dataSnapshot.child("Users").child(phone).getValue(Users.class);

                    if (userData.getPhone().equals(phone))
                    {
                        if(userData.getPassword().equals(password))
                        {
                            Toast.makeText(splash.this, "Logged in Successful", Toast.LENGTH_SHORT).show();
                            //loadingBar.dismiss();
                            Intent intent=new Intent(splash.this,MainHomeActivity.class);
                            Prevalent.currentOnlineUser=userData;
                            startActivity(intent);
                        }
                        else
                        {
                            //loadingBar.dismiss();
                            Toast.makeText(splash.this, "Password is incorrect", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                else
                {
                    Toast.makeText(splash.this, "Account not exists of this" + " " + phone + "Number", Toast.LENGTH_LONG).show();
                    //loadingBar.dismiss();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {

            }
        });

    }*/
}