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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.HashMap;

import io.paperdb.Paper;

public class Signup extends AppCompatActivity {
 TextView alreadyuser,company_register,user_register;
 EditText signupname,signuppassword,signupphone;
 Button signup,company_signup;

 private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

         signupname=(EditText)findViewById(R.id.signup_name);
         signuppassword=(EditText)findViewById(R.id.signup_password);
         signupphone=(EditText)findViewById(R.id.signup_phone);

         signup=(Button)findViewById(R.id.btn_signup);
         company_signup=(Button)findViewById(R.id.btn_company_signup);    // new add

         loadingBar=new ProgressDialog(this);

         company_register=(TextView)findViewById(R.id.tv_signup_company);  //new add
         user_register=(TextView)findViewById(R.id.tv_signup_user);        //new add

        alreadyuser=(TextView)findViewById(R.id.tv_alreadyuser);
        alreadyuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Signup.this,login.class);
                startActivity(intent);
            }
        });


        company_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                company_signup.setVisibility(View.VISIBLE);
                signup.setVisibility(View.INVISIBLE);
                company_register.setVisibility(View.INVISIBLE);
                user_register.setVisibility(View.VISIBLE);

            }
        }); //new add

        user_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                signup.setVisibility(View.VISIBLE);
                company_signup.setVisibility(View.INVISIBLE);
                company_register.setVisibility(View.VISIBLE);
                user_register.setVisibility(View.INVISIBLE);
            }
        });  // new add


        company_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
             CreateCompanyAccount();
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                CreateUserAccount();
            }
        });

    }

    private void CreateCompanyAccount()
    {
        String name=signupname.getText().toString().trim();
        String password=signuppassword.getText().toString().trim();
        String phone=signupphone.getText().toString().trim();

        if(TextUtils.isEmpty(name))
        {
            Toast.makeText(this,"Please write your name",Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(password))
        {
            Toast.makeText(this,"Please write Password",Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(phone))
        {
            Toast.makeText(this,"Please write your Phone No",Toast.LENGTH_SHORT).show();
        }
        else if(phone.length()<10)
        {
            Toast.makeText(this, "Please Enter Valid Mobile No.", Toast.LENGTH_SHORT).show();
        }
        else
        {
            loadingBar.setTitle("Creating Account");
            loadingBar.setMessage("Please Wait");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();
            ValidateCompanyPhone(name,password,phone);
        }
    }

    private void ValidateCompanyPhone(final String name, final String password, final String phone)
    {
        final DatabaseReference RootRef;
        RootRef= FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if(!(dataSnapshot.child("Company").child(phone).exists()))
                {
                    HashMap<String, Object> companydataMap=new HashMap<>();
                    companydataMap.put("phone",phone);
                    companydataMap.put("name",name);
                    companydataMap.put("password",password);

                    RootRef.child("Company").child(phone).updateChildren(companydataMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task)
                                {
                                    if(task.isSuccessful())
                                    {
                                        Toast.makeText(Signup.this, "Account Successfully Created", Toast.LENGTH_SHORT).show();
                                        loadingBar.dismiss();
                                        Intent intent=new Intent(Signup.this,login.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                    else
                                    {
                                        loadingBar.dismiss();
                                        Toast.makeText(Signup.this, "Network error", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
                else
                {
                    Toast.makeText(Signup.this, "This"+ " " + phone + " " + "already exists", Toast.LENGTH_LONG).show();
                    loadingBar.dismiss();
                    Intent intent=new Intent(Signup.this,login.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {

            }
        });
    }


    private void CreateUserAccount()
    {
        String name=signupname.getText().toString().trim();
        String password=signuppassword.getText().toString().trim();
        String phone=signupphone.getText().toString().trim();

        if(TextUtils.isEmpty(name))
        {
            Toast.makeText(this,"Please write your name",Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(password))
        {
            Toast.makeText(this,"Please write Password",Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(phone))
        {
            Toast.makeText(this,"Please write your Phone No",Toast.LENGTH_SHORT).show();
        }
        else if(phone.length()<10)
        {
            Toast.makeText(this, "Please Enter Valid Mobile No.", Toast.LENGTH_SHORT).show();
        }
        else
        {
            loadingBar.setTitle("Creating Account");
            loadingBar.setMessage("Please Wait");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();
            ValidateUserPhone(name,password,phone);
        }
    }

    private void ValidateUserPhone(final String name, final String password, final String phone)
    {
        final DatabaseReference RootRef;
        RootRef= FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if(!(dataSnapshot.child("Users").child(phone).exists()))
                {
                    HashMap<String, Object> userdataMap=new HashMap<>();
                    userdataMap.put("phone",phone);
                    userdataMap.put("name",name);
                    userdataMap.put("password",password);

                    RootRef.child("Users").child(phone).updateChildren(userdataMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task)
                                {
                                    if(task.isSuccessful())
                                    {
                                        Toast.makeText(Signup.this, "Account Successfully Created", Toast.LENGTH_SHORT).show();
                                        loadingBar.dismiss();
                                        Intent intent=new Intent(Signup.this,login.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                    else
                                    {
                                        loadingBar.dismiss();
                                        Toast.makeText(Signup.this, "Network error", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
                else
                {
                    Toast.makeText(Signup.this, "This"+ " " + phone + " " + "already exists", Toast.LENGTH_LONG).show();
                    loadingBar.dismiss();
                    Intent intent=new Intent(Signup.this,login.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {

            }
        });
    }

}
