package com.student.job.scc.ViewHolder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.student.job.scc.Interface.JobClickLisner;
import com.student.job.scc.R;

public class FavoriteViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{
    public TextView job_title,job_description,job_salay,job_location;
    public ImageView company_logo,favorite_job_image;

    private JobClickLisner jobClickLisner;

    public FavoriteViewHolder(@NonNull View itemView)
    {
        super(itemView);

        job_title=(TextView)itemView.findViewById(R.id.tv_display_job_title);
        job_description=(TextView)itemView.findViewById(R.id.tv_display_job_desc);
        job_salay=(TextView)itemView.findViewById(R.id.tv_display_job_salary);
        job_location=(TextView)itemView.findViewById(R.id.tv_display_job_location);

        company_logo=(ImageView)itemView.findViewById(R.id.image_display_logo);
    }

    @Override
    public void onClick(View v)
    {
        jobClickLisner.onClick(v,getAdapterPosition(),false);
    }

    public void setJobClickLisner(JobClickLisner jobClickLisner)
    {
        this.jobClickLisner = jobClickLisner;
    }
}
