package com.student.job.scc;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.student.job.scc.Prevalent.Prevalent;

import de.hdodenhof.circleimageview.CircleImageView;
import io.paperdb.Paper;

public class ProfileFragment extends Fragment implements View.OnClickListener {

/*private  Button profile_arrow;*/
private TextView username,userphone,notification, help, history, logout, invite, setting;
private CircleImageView profileImgae;

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        notification = (TextView) view.findViewById(R.id.tv_profile_notification);
        help = (TextView) view.findViewById(R.id.tv_profile_help);
        history = (TextView) view.findViewById(R.id.tv_profile_history);
        logout = (TextView) view.findViewById(R.id.tv_profile_logout);
        invite = (TextView) view.findViewById(R.id.tv_profile_invite);
        setting = (TextView) view.findViewById(R.id.tv_profile_settings);
        /*profile_arrow = (Button) view.findViewById(R.id.btn_profile_arrow);*/
        username = (TextView) view.findViewById(R.id.tv_profile_name);
        userphone = (TextView) view.findViewById(R.id.tv_profile_phone);

        profileImgae=(CircleImageView)view.findViewById(R.id.profile_pic);

        notification.setOnClickListener(this);
        help.setOnClickListener(this);
        history.setOnClickListener(this);
        logout.setOnClickListener(this);
        invite.setOnClickListener(this);
        setting.setOnClickListener(this);


        username=view.findViewById(R.id.tv_profile_name);
        userphone=view.findViewById(R.id.tv_profile_phone);
        CircleImageView profilepic=view.findViewById(R.id.profile_pic);

        username.setText(Prevalent.currentOnlineUser.getName());
        userphone.setText(Prevalent.currentOnlineUser.getPhone());
        Picasso.get().load(Prevalent.currentOnlineUser.getImage()).placeholder(R.drawable.profil_pic).into(profileImgae);
        return view;
    }


    @Override
    public void onClick(View v)
    {
        switch (v.getId()){
            case R.id.tv_profile_notification:
                Intent nintent=new Intent(getActivity(),Notification.class);
                startActivity(nintent);
                break;
            case R.id.tv_profile_help:
                Intent heintent=new Intent(getActivity(),Help.class);
                startActivity(heintent);
                break;
            case R.id.tv_profile_history:
                Intent hintent=new Intent(getActivity(),History.class);
                startActivity(hintent);
                break;
            case R.id.tv_profile_logout:
                 Paper.book().destroy();
                 Intent lintent=new Intent(getActivity(),login.class);
                 startActivity(lintent);
                 break;
            case R.id.tv_profile_invite:
                 Intent invintent=new Intent(Intent.ACTION_SEND);
                 invintent.setType("text/plain");
                 String shareBody= "Hey, I find new job app that is awesome its provide lots of features." +
                         "its provide job searching,freelancing and internship. So download this app and find your next job.";
                 String shareSub= "SCC-Job Easy";
                 invintent.putExtra(Intent.EXTRA_SUBJECT,shareSub);
                 invintent.putExtra(Intent.EXTRA_TEXT,shareBody);
                 startActivity(Intent.createChooser(invintent,"Share Using..."));
                 break;
            case R.id.tv_profile_settings:
                Intent fintent=new Intent(getActivity(), Settings.class);
                startActivity(fintent);
                break;

        }

    }

}

