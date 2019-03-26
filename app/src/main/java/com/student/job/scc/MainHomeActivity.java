package com.student.job.scc;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import de.hdodenhof.circleimageview.CircleImageView;
import io.paperdb.Paper;

public class MainHomeActivity extends AppCompatActivity {
    Toolbar toolbar;
    TextView about_title,about_desc,contact_title,contact_desc,contact_desc1,privacy_title,privacy_desc;
    Dialog mypopup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_home);

        Paper.init(this);

        mypopup=new Dialog(this);

        toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("SCC");

        about_title=(TextView)findViewById(R.id.about_popup_title);
        about_desc=(TextView)findViewById(R.id.about_popup_description);
        contact_title=(TextView)findViewById(R.id.contac_popup_title);
        contact_desc=(TextView)findViewById(R.id.contact_popup_description);
        contact_desc1=(TextView)findViewById(R.id.contact_popup_description1);
        privacy_title=(TextView)findViewById(R.id.privacy_popup_title);
        privacy_desc=(TextView)findViewById(R.id.privacy_popup_description);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navlistner);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new HomeFragment()).commit();

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


    //code to fetch(home fragment)


    private BottomNavigationView.OnNavigationItemSelectedListener navlistner=
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem)
                {
                    Fragment selctedFragment=null;
                    switch (menuItem.getItemId())
                    {
                        case R.id.bottom_home:
                            selctedFragment =new HomeFragment();
                            break;
                        case R.id.bottom_search:
                            selctedFragment =new SearchFragment();
                            break;
                        case R.id.bottom_favorite:
                            selctedFragment =new FavoriteFragment();
                            break;
                        case R.id.bottom_profile:
                            selctedFragment =new ProfileFragment();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selctedFragment).commit();
                    return true;
                }
            };

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.toolbar_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId()) {
            case R.id.app_notifications:
                Intent intent=new Intent(MainHomeActivity.this,Notification.class);
                startActivity(intent);
                break;
            case R.id.about_us:
                mypopup.setContentView(R.layout.about_us_popup);
                mypopup.show();
              break;
            case R.id.contact:
                mypopup.setContentView(R.layout.contac_us_popup);
                mypopup.show();
                break;
            case R.id.privacy:
               mypopup.setContentView(R.layout.privacy_policy_popup);
               mypopup.show();
               break;
            case R.id.logout:
               Paper.book().destroy();
               Intent logoutintent =new Intent(MainHomeActivity.this,login.class);
               startActivity(logoutintent);
               finish();
               break;
        }
        return super.onOptionsItemSelected(item);
    }

}
