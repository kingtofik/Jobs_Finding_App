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
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class CompanyAddNewJobsActivity extends AppCompatActivity {

    private String categoryName,cName,cDesc,jTitle,jDesc,jExp,jSalry,jLocation,saveCurruntDate,saveCurruntTime,jobRandomeKey;
    private ImageView select_image;
    private EditText  companyName,companyDesc,jobTitle,jobDesc,jobExperience,jobSalary,jobLocation;
    private Button addJobs;
    private ProgressDialog loadingBar;
    private static final int GalleryPick=1;
    private String dwonloadImageUrl;
    private Uri ImageUri;
    private StorageReference CompanyImageLogo;
    private DatabaseReference jobsRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_add_new_jobs);

        categoryName=getIntent().getExtras().get("Category").toString();
        Toast.makeText(this, categoryName, Toast.LENGTH_SHORT).show();
        CompanyImageLogo= FirebaseStorage.getInstance().getReference().child("Company Logo");
        jobsRef= FirebaseDatabase.getInstance().getReference().child("All Jobs");

        select_image=(ImageView)findViewById(R.id.select_image);
        companyName=(EditText)findViewById(R.id.et_company_name);
        companyDesc=(EditText)findViewById(R.id.et_company_desc);
        jobTitle=(EditText)findViewById(R.id.et_company_job_title);
        jobDesc=(EditText)findViewById(R.id.et_company_job_desc);
        jobExperience=(EditText)findViewById(R.id.et_company_job_experience);
        jobSalary=(EditText)findViewById(R.id.et_company_job_salary);
        jobLocation=(EditText)findViewById(R.id.et_company_job_location);
        addJobs=(Button)findViewById(R.id.btn_add_new_jobs);

        loadingBar=new ProgressDialog(this);


        select_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                OpenGallary();
            }
        });

        addJobs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
              ValidateJobData();
            }
        });

    }


    private  void OpenGallary()
    {
        Intent gallaryIntent=new Intent();
        gallaryIntent.setAction(Intent.ACTION_GET_CONTENT);
        gallaryIntent.setType("image/*");
        startActivityForResult(gallaryIntent,GalleryPick);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==GalleryPick && resultCode==RESULT_OK && data!=null);
        {
            ImageUri=data.getData();
            select_image.setImageURI(ImageUri);
        }

    }
    private void ValidateJobData()
    {
        cName=companyName.getText().toString();
        cDesc=companyDesc.getText().toString();
        jTitle=jobTitle.getText().toString();
        jDesc=jobDesc.getText().toString();
        jExp=jobExperience.getText().toString();
        jSalry=jobSalary.getText().toString();
        jLocation=jobLocation.getText().toString();

        if(ImageUri==null)
        {
            Toast.makeText(this, "Please Add Company Logo", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(cName))
        {
            Toast.makeText(this, "Please Enter Company Name ", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(cDesc))
        {
            Toast.makeText(this, "Please Enter Description", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(jTitle))
        {
            Toast.makeText(this, "Please Enter Job Title", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(jDesc))
        {
            Toast.makeText(this, "Please Enter Job Description", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(jExp))
        {
            Toast.makeText(this, "Please Enter Job Experience", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(jSalry))
        {
            Toast.makeText(this, "Please Enter Salary", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(jLocation))
        {
            Toast.makeText(this, "Please Enter Location", Toast.LENGTH_SHORT).show();
        }
        else
        {
            storeJobsInformation();
        }
    }

    private void storeJobsInformation()
    {
        loadingBar.setTitle("Adding New Jobs..");
        loadingBar.setMessage("Please Wait");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();

        Calendar calendar=Calendar.getInstance();
        SimpleDateFormat curruntDate=new SimpleDateFormat("MMM dd,yyyy");
        saveCurruntDate = curruntDate.format(calendar.getTime());

        SimpleDateFormat curruntTime=new SimpleDateFormat("HH:mm:ss a");
        saveCurruntTime=curruntTime.format(calendar.getTime());

        jobRandomeKey=saveCurruntDate+saveCurruntTime;

        final StorageReference filepath=CompanyImageLogo.child(ImageUri.getLastPathSegment()+ jobRandomeKey);
        final UploadTask uploadTask=filepath.putFile(ImageUri);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e)
            {
                String message=e.toString();
                Toast.makeText(CompanyAddNewJobsActivity.this, "Error"+ message, Toast.LENGTH_SHORT).show();
                loadingBar.dismiss();

            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
            {
                Toast.makeText(CompanyAddNewJobsActivity.this, "Image Upload Success", Toast.LENGTH_SHORT).show();

                Task<Uri> urlTask=uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception
                    {
                        if(!task.isSuccessful())
                        {
                            throw task.getException();
                        }
                        dwonloadImageUrl=filepath.getDownloadUrl().toString();
                        return filepath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task)
                    {
                        if(task.isSuccessful())
                        {
                            dwonloadImageUrl=task.getResult().toString();
                            Toast.makeText(CompanyAddNewJobsActivity.this, "Got Company Logo Url..", Toast.LENGTH_SHORT).show();
                            SaveJobInfoToDatabse();
                        }
                    }
                });
            }
        });
    }

    private void SaveJobInfoToDatabse()
    {
        HashMap<String,Object> jobMap=new HashMap<>();
        jobMap.put("jid",jobRandomeKey);
        jobMap.put("date",saveCurruntDate);
        jobMap.put("time",saveCurruntTime);
        jobMap.put("image",dwonloadImageUrl);
        jobMap.put("Category",categoryName);
        jobMap.put("Company_name",cName);
        jobMap.put("Company_Desc",cDesc);
        jobMap.put("Job_Title",jTitle);
        jobMap.put("Job_Desc",jDesc);
        jobMap.put("Job_Exp",jExp);
        jobMap.put("Job_Salary",jSalry);
        jobMap.put("Job_Location",jLocation);

        jobsRef.child(jobRandomeKey).updateChildren(jobMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task)
                    {
                      if(task.isSuccessful())
                      {
                          loadingBar.dismiss();
                          Toast.makeText(CompanyAddNewJobsActivity.this, "Job Added Successful", Toast.LENGTH_SHORT).show();
                          Intent intent=new Intent(CompanyAddNewJobsActivity.this,CompanyCategorieActivity.class);
                          startActivity(intent);
                          finish();
                      }
                      else
                      {
                          loadingBar.dismiss();
                          String message=task.getException().toString();
                          Toast.makeText(CompanyAddNewJobsActivity.this, "Error"+ message, Toast.LENGTH_SHORT).show();
                      }
                    }
                });
    }
}
