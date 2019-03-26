package com.student.job.scc;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rey.material.widget.CheckBox;
import com.student.job.scc.Model.Company;
import com.student.job.scc.Model.Users;
import com.student.job.scc.Prevalent.Prevalent;

import java.awt.font.TextAttribute;

import io.paperdb.Paper;

public class login extends AppCompatActivity {
TextView notaccount,forgotpssword,company,notacompany;
EditText loginphone,loginpassword;
Button login;
private String parentDbName= "Users";
private CheckBox checkboxremember;
private ProgressDialog loadingBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginphone=(EditText)findViewById(R.id.etlogin_phone);
        loginpassword=(EditText)findViewById(R.id.etlogin_pass);
        login=(Button)findViewById(R.id.btn_login);
        forgotpssword=(TextView)findViewById(R.id.tv_forgotpassword);
        company=(TextView)findViewById(R.id.tv_company);
        notacompany=(TextView)findViewById(R.id.tv_notacompany);
        loadingBar=new ProgressDialog(this);

        checkboxremember=(CheckBox)findViewById(R.id.remember_chkbox);
        Paper.init(this);

        notaccount=(TextView)findViewById(R.id.tv_notaccount);
        notaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(login.this,Signup.class);
                startActivity(intent);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginUser();
            }
        });

        company.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login.setText("Login Company");
                company.setVisibility(View.INVISIBLE);
                notacompany.setVisibility(View.VISIBLE);
                parentDbName="Company";
            }
        });
        notacompany.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login.setText("Login");
                company.setVisibility(View.VISIBLE);
                notacompany.setVisibility(View.INVISIBLE);
                parentDbName="Users";
            }
        });
    }

    private void LoginUser()
    {
        String phone=loginphone.getText().toString().trim();
        String password=loginpassword.getText().toString().trim();

        if(TextUtils.isEmpty(phone))
        {
            Toast.makeText(this, "Please Enter Phone No", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(password))
        {
            Toast.makeText(this, "Please Enter Password", Toast.LENGTH_SHORT).show();
        }
        else

            {
                      loadingBar.setTitle("Login Account");
                      loadingBar.setMessage("Please Wait");
                      loadingBar.setCanceledOnTouchOutside(false);
                      loadingBar.show();

                AllowAccessAccount(phone,password);
            }
    }

    private void AllowAccessAccount(final String phone, final String password)
    {
        if(checkboxremember.isChecked())
        {
            Paper.book().write(Prevalent.UserPhoneKey,phone);
            Paper.book().write(Prevalent.UserPasswordKey,password);
        }
        final DatabaseReference RootRef;
        RootRef= FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                    if(dataSnapshot.child(parentDbName).child(phone).exists())
                    {
                        Users userData=dataSnapshot.child(parentDbName).child(phone).getValue(Users.class);

                     //   Company companyData=dataSnapshot.child(parentDbName).child(phone).getValue(Company.class);

                        if (userData.getPhone().equals(phone))
                        {
                            if(userData.getPassword().equals(password))
                            {
                                if(parentDbName.equals("Company"))
                                {
                                    Toast.makeText(login.this, "Logged in Successful", Toast.LENGTH_SHORT).show();
                                    loadingBar.dismiss();
                                    Intent intent=new Intent(com.student.job.scc.login.this,CompanyCategorieActivity.class);
                                    startActivity(intent);
                                    finish();
                                }

                                else if(parentDbName.equals("Users"))
                                {
                                    Toast.makeText(login.this, "Logged in Successful", Toast.LENGTH_SHORT).show();
                                    loadingBar.dismiss();
                                    Intent intent=new Intent(com.student.job.scc.login.this,MainHomeActivity.class);
                                    Prevalent.currentOnlineUser=userData;
                                    startActivity(intent);
                                    finish();
                                }
                            }
                            else
                            {
                                loadingBar.dismiss();
                                Toast.makeText(login.this, "Password is incorrect", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                    else
                    {
                        Toast.makeText(login.this, "Account  does not exists of this" + " " + phone + "Number", Toast.LENGTH_LONG).show();
                        loadingBar.dismiss();

                    }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {

            }
        });

    }

}
