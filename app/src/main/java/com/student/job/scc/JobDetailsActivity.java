package com.student.job.scc;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.student.job.scc.Model.Favorite;
import com.student.job.scc.Model.Jobs;
import com.student.job.scc.Prevalent.Prevalent;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class JobDetailsActivity extends AppCompatActivity {

private ImageView display_company_logo,imageShare;
private Button favbtn,applybtn;
private TextView displayjobtitle,displayjobsalary,displayjoblocation,displayjobdate,displayjobtextdetails,displayjobcompanyname,
        displayjobcompanydesc,displayjobtextdescription,displayjobexperience,displayjobdescription;

private String JobId="";

private Uri imageUri;
private String dwonloadImageUrl;

private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_details);

        loadingBar=new ProgressDialog(this);

        JobId=getIntent().getStringExtra("jid");

        display_company_logo=(ImageView)findViewById(R.id.image_job_details_logo);
        imageShare=(ImageView)findViewById(R.id.image_job_details_share);

        favbtn=(Button)findViewById(R.id.btn_jobs_fav);
        applybtn=(Button)findViewById(R.id.btn_jobs_apply);

        displayjobtitle=(TextView)findViewById(R.id.tv_job_details_jobtitle);
        displayjobsalary=(TextView)findViewById(R.id.tv_job_details_jobsalary);
        displayjoblocation=(TextView)findViewById(R.id.tv_job_details_joblocation);
        displayjobdate=(TextView)findViewById(R.id.tv_job_details_jobdate);
        displayjobcompanyname=(TextView)findViewById(R.id.tv_job_details_companyname);
        displayjobcompanydesc=(TextView)findViewById(R.id.tv_job_details_companydesc);
        displayjobexperience=(TextView)findViewById(R.id.tv_job_details_jobexp);
        displayjobdescription=(TextView)findViewById(R.id.tv_job_details_jobdesc);

        displayjobtextdetails=(TextView)findViewById(R.id.text_jobdetails);
        displayjobtextdescription=(TextView)findViewById(R.id.text_jobdesc);


        getJobDetailes(JobId);


        applybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent=new Intent(JobDetailsActivity.this,ApplyJobsActivity.class);
                startActivity(intent);
            }
        });

        favbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                loadingBar.setTitle("Adding In Favorite");
                loadingBar.setMessage("Please Wait");
                loadingBar.setCanceledOnTouchOutside(false);
                loadingBar.show();

                addingToFavorite();
            }
        });
    }

    private void addingToFavorite()

    {
        String saveCurruntTime,saveCurruntDate;

        Calendar calForDate=Calendar.getInstance();
        SimpleDateFormat currentDate= new SimpleDateFormat("MMMM dd,yyyy");
        saveCurruntDate=currentDate.format(calForDate.getTime());

        SimpleDateFormat currentTime= new SimpleDateFormat("HH:mm:ss a");
        saveCurruntTime=currentTime.format(calForDate.getTime());

        final DatabaseReference favoriteListref=FirebaseDatabase.getInstance().getReference().child("Favorite");

        final HashMap<String,Object> favoriteMap=new HashMap<>();
        favoriteMap.put("jid",JobId);
        favoriteMap.put("date",saveCurruntDate);
        favoriteMap.put("time",saveCurruntTime);
        favoriteMap.put("Job_Title",displayjobtitle.getText().toString());
        favoriteMap.put("Job_Desc",displayjobdescription.getText().toString());
        favoriteMap.put("Job_Salary",displayjobsalary.getText().toString());
        favoriteMap.put("Job_Location",displayjoblocation.getText().toString());



        favoriteListref.child("User View").child(Prevalent.currentOnlineUser.getPhone())
                .child("All Jobs").child(JobId).updateChildren(favoriteMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task)
                    {
                        if(task.isSuccessful())
                        {
                            favoriteListref.child("Admin View").child(Prevalent.currentOnlineUser.getPhone())
                                    .child("All Jobs").child(JobId).updateChildren(favoriteMap)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task)
                                        {
                                            if(task.isSuccessful())
                                            {
                                                loadingBar.dismiss();
                                                Toast.makeText(JobDetailsActivity.this, "Added to favorite list successful", Toast.LENGTH_SHORT).show();
                                                Intent intent=new Intent(JobDetailsActivity.this,MainHomeActivity.class);
                                                startActivity(intent);
                                                finish();
                                            }
                                        }
                                    });
                        }
                    }
                });
    }



    private void getJobDetailes(String jobId)
    {
        DatabaseReference jobRef= FirebaseDatabase.getInstance().getReference().child("All Jobs");
        jobRef.child(JobId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
              if(dataSnapshot.exists())
              {
                  Jobs jobs=dataSnapshot.getValue(Jobs.class);

                  displayjobtitle.setText(jobs.getJob_Title());
                  displayjobsalary.setText(jobs.getJob_Salary());
                  displayjoblocation.setText(jobs.getJob_Location());
                  displayjobdate.setText(jobs.getDate());
                  displayjobcompanyname.setText(jobs.getCompany_name());
                  displayjobcompanydesc.setText(jobs.getCompany_Desc());
                  displayjobexperience.setText(jobs.getJob_Exp());
                  displayjobdescription.setText(jobs.getJob_Desc());

                  Picasso.get().load(jobs.getImage()).into(display_company_logo);
              }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}
