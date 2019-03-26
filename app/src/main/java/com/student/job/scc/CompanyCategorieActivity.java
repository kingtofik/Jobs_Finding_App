package com.student.job.scc;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import io.paperdb.Paper;

public class CompanyCategorieActivity extends AppCompatActivity {

 private TextView  maintitle,jobtitle,inrenshiptitle,freelancetitle,newtitle,interviewtitle;
 private ImageView jobs,inrenship,freelance,news,interview;
 private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_categorie);

        toolbar=(Toolbar)findViewById(R.id.category_toolbar);
        setSupportActionBar(toolbar);


        jobs=(ImageView)findViewById(R.id.image_jobs);
        inrenship=(ImageView)findViewById(R.id.image_inrenship);
        freelance=(ImageView)findViewById(R.id.image_freelancing);
        news=(ImageView)findViewById(R.id.image_news);
        interview=(ImageView)findViewById(R.id.image_interview);

        jobs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent=new Intent(CompanyCategorieActivity.this,CompanyAddNewJobsActivity.class);
                intent.putExtra("Category", "Jobs");
                startActivity(intent);
            }
        });

        jobs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent=new Intent(CompanyCategorieActivity.this,CompanyAddNewJobsActivity.class);
                intent.putExtra("Category", "Jobs");
                startActivity(intent);
            }
        });

        inrenship.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent=new Intent(CompanyCategorieActivity.this,CompanyAddNewJobsActivity.class);
                intent.putExtra("Category", "Internship");
                startActivity(intent);
            }
        });

        freelance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent=new Intent(CompanyCategorieActivity.this,CompanyAddNewJobsActivity.class);
                intent.putExtra("Category", "Freelance Projects");
                startActivity(intent);
            }
        });

        news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent=new Intent(CompanyCategorieActivity.this,CompanyAddNewJobsActivity.class);
                intent.putExtra("Category", "News");
                startActivity(intent);
            }
        });

        interview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent=new Intent(CompanyCategorieActivity.this,CompanyAddNewJobsActivity.class);
                intent.putExtra("Category", "Interview");
                startActivity(intent);
            }
        });


    }
    public void onBackPressed()
    {
       showAlertDialog();
    }
    private void showAlertDialog()
    {
        final AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setTitle("Exit");
        builder.setMessage("Are you sure you want to exit?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.company_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId()) {
            case R.id.company_status_reports:
                Intent intent=new Intent(CompanyCategorieActivity.this,Notification.class);
                startActivity(intent);
                break;

            case R.id.company_apply_job_list:
                Intent applyintent=new Intent(CompanyCategorieActivity.this,CompanyNewJobsListActivity.class);
                startActivity(applyintent);
                break;

            case R.id.company_profile:
                Intent pintent=new Intent(CompanyCategorieActivity.this,CompanyProfileActivity.class);
                startActivity(pintent);
                break;

            case R.id.company_logout:
                Intent logoutintent =new Intent(CompanyCategorieActivity.this,login.class);
                logoutintent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(logoutintent);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
