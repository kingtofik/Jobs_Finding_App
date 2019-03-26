package com.student.job.scc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.student.job.scc.Prevalent.Prevalent;
import com.student.job.scc.PrevalentCompany.PrevalentCompany;

import de.hdodenhof.circleimageview.CircleImageView;
import io.paperdb.Paper;

public class CompanyProfileActivity extends AppCompatActivity {


private TextView cpny_profile_name,cpny_profile_phone,cpny_profile_applylist,cpny_profile_statusreports,
                 cpny_profile_settings,cpny_profile_help,cpny_profile_logout;

private CircleImageView companyprofileImgae;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_profile);

        Paper.init(this);

        cpny_profile_name=(TextView)findViewById(R.id.tv_company_profile_name);
        cpny_profile_phone=(TextView)findViewById(R.id.tv_company_profile_phone);
        cpny_profile_applylist=(TextView)findViewById(R.id.tv_company_profile_Apply_List);
        cpny_profile_statusreports=(TextView)findViewById(R.id.tv_company_profile_Status_Reports);
        cpny_profile_settings=(TextView)findViewById(R.id.tv_company_profile_settings);
        cpny_profile_help=(TextView)findViewById(R.id.tv_company_profile_help);
        cpny_profile_logout=(TextView)findViewById(R.id.tv_company_profile_logout);

        companyprofileImgae=(CircleImageView)findViewById(R.id.company_profile_pic);

       //  cpny_profile_name.setText(PrevalentCompany.currentOnlineUser.getName());
       //  cpny_profile_phone.setText(PrevalentCompany.currentOnlineUser.getPhone());

       //  Picasso.get().load(PrevalentCompany.currentOnlineUser.getImage()).placeholder(R.drawable.profil_pic).into(companyprofileImgae);

        cpny_profile_applylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent=new Intent(CompanyProfileActivity.this,CompanyNewJobsListActivity.class);
                startActivity(intent);
            }
        });

        cpny_profile_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Paper.book().destroy();
                Intent intent=new Intent(CompanyProfileActivity.this,login.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();

            }
        });

        cpny_profile_help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
             Intent intent=new Intent(CompanyProfileActivity.this,Help.class);
             startActivity(intent);
            }
        });
    }
}
