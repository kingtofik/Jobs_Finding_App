package com.student.job.scc;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.student.job.scc.Prevalent.Prevalent;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class ApplyJobsActivity extends AppCompatActivity {


private StorageReference storageReference;
private EditText  apply_name,apply_phone,apply_city,apply_college,apply_email;
private Button    upload_resume,apply_btn;
private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_jobs);

        storageReference= FirebaseStorage.getInstance().getReference();

        loadingBar=new ProgressDialog(this);

        apply_name=(EditText)findViewById(R.id.et_apply_job_name);
        apply_phone=(EditText)findViewById(R.id.et_apply_job_phone);
        apply_city=(EditText)findViewById(R.id.et_apply_job_city);
        apply_college=(EditText)findViewById(R.id.et_apply_job_college);
        apply_email=(EditText)findViewById(R.id.et_apply_job_email);

        upload_resume=(Button)findViewById(R.id.btn_upload_resume);
        apply_btn=(Button)findViewById(R.id.btn_apply_job);


        upload_resume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                selectResumeFile();
            }
        });



        apply_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
              Chek();
            }
        });
    }

    private void selectResumeFile()
    {

        Intent intent=new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Resume PDF file"),1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==1 && resultCode== RESULT_OK && data!=null && data.getData()!=null)
        {
            uploadResume(data.getData());
        }
    }

    private void uploadResume(Uri data)
    {
        StorageReference reference=storageReference.child("Resume/" + ".pdf");
        reference.putFile(data).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>()
        {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
            {

            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>()
        {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot)
            {

            }
        });

    }


    private void Chek()
    {
        if (TextUtils.isEmpty(apply_name.getText().toString()))
        {
            Toast.makeText(this, "Please write your name", Toast.LENGTH_SHORT).show();
        }

        else if (TextUtils.isEmpty(apply_phone.getText().toString()))
        {
            Toast.makeText(this, "Please write your phone no.", Toast.LENGTH_SHORT).show();
        }

        else if (TextUtils.isEmpty(apply_city.getText().toString()))
        {
            Toast.makeText(this, "Please write your city name", Toast.LENGTH_SHORT).show();
        }

        else if (TextUtils.isEmpty(apply_college.getText().toString()))
        {
            Toast.makeText(this, "Please write your college name ", Toast.LENGTH_SHORT).show();
        }

        else if (TextUtils.isEmpty(apply_email.getText().toString()))
        {
            Toast.makeText(this, "Please write your email address", Toast.LENGTH_SHORT).show();
        }

        else
        {
            loadingBar.setTitle("Applying Jobs");
            loadingBar.setMessage("Please Wait");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            ConfirmApplyJob();
        }
    }


    private void ConfirmApplyJob()
    {
        final String saveCurruntTime,saveCurruntDate;

        Calendar calForDate=Calendar.getInstance();
        SimpleDateFormat currentDate= new SimpleDateFormat("MMMM dd,yyyy");
        saveCurruntDate=currentDate.format(calForDate.getTime());

        SimpleDateFormat currentTime= new SimpleDateFormat("HH:mm:ss a");
        saveCurruntTime=currentTime.format(calForDate.getTime());

        final DatabaseReference applyjobRef= FirebaseDatabase.getInstance().getReference()
                .child("Apply Jobs").child(Prevalent.currentOnlineUser.getPhone());

        HashMap<String,Object> applyjobMap=new HashMap<>();

        applyjobMap.put("date",saveCurruntDate);
        applyjobMap.put("time",saveCurruntTime);
        applyjobMap.put("name",apply_name.getText().toString());
        applyjobMap.put("phone",apply_phone.getText().toString());
        applyjobMap.put("city",apply_city.getText().toString());
        applyjobMap.put("college",apply_college.getText().toString());
        applyjobMap.put("email",apply_email.getText().toString());

        applyjobRef.updateChildren(applyjobMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task)
            {
               if(task.isSuccessful())
               {
                   loadingBar.dismiss();
                   Toast.makeText(ApplyJobsActivity.this, "Apply Job Successful", Toast.LENGTH_SHORT).show();
                   Intent intent=new Intent(ApplyJobsActivity.this,MainHomeActivity.class);
                   startActivity(intent);
                   finish();

               }
            }
        });
    }
}
