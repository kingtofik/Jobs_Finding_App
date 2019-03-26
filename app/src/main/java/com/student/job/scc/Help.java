package com.student.job.scc;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Help extends AppCompatActivity {

private TextView help_about,help_privacy,help_contact,help_feedback,help_rate;
Dialog popup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        popup=new Dialog(this);

        help_about=(TextView)findViewById(R.id.tv_help_aboutus);
        help_privacy=(TextView)findViewById(R.id.tv_help_Privacypolycy);
        help_feedback=(TextView)findViewById(R.id.tv_help_feedback);
        help_contact=(TextView)findViewById(R.id.tv_help_Contactus);
        help_rate=(TextView)findViewById(R.id.tv_help_rateus);


        help_about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
             popup.setContentView(R.layout.about_us_popup);
             popup.show();
             popup.setCanceledOnTouchOutside(true);
            }
        });

        help_privacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
               popup.setContentView(R.layout.privacy_policy_popup);
               popup.show();
               popup.setCanceledOnTouchOutside(true);
            }
        });

        help_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
              popup.setContentView(R.layout.contac_us_popup);
              popup.show();
              popup.setCanceledOnTouchOutside(true);
            }
        });


    }
}
