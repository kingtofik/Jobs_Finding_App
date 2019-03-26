package com.student.job.scc;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;
import com.student.job.scc.Model.CompanyJobList;

public class CompanyNewJobsListActivity extends AppCompatActivity {

private RecyclerView recyclerView;
private DatabaseReference applyJobListRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_new_jobs_list);

        applyJobListRef= FirebaseDatabase.getInstance().getReference().child("Apply Jobs");

        recyclerView=findViewById(R.id.recycler_company_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


    }


    @Override
    protected void onStart()
    {
        super.onStart();


        FirebaseRecyclerOptions<CompanyJobList> options =
                new FirebaseRecyclerOptions.Builder<CompanyJobList>()
                .setQuery(applyJobListRef,CompanyJobList.class)
                .build();

        FirebaseRecyclerAdapter<CompanyJobList,CompanyJobListViewHolder>adapter =
                new FirebaseRecyclerAdapter<CompanyJobList, CompanyJobListViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull final CompanyJobListViewHolder holder, int position, @NonNull final CompanyJobList model)
                    {
                        holder.list_name.setText("Name:" + " " + model.getName());
                        holder.list_phone.setText("Phone:" + " " +model.getPhone());
                        holder.list_collge.setText("College:" + " " +model.getCollege());
                        holder.list_email.setText("Email:" + " " +model.getEmail());
                        holder.list_city.setText("City:" + " " +model.getCity());
                        holder.list_datetime.setText("Date:" + " " +model.getDate() +"   " + model.getTime());

                        holder.send_mail.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v)
                            {
                                String s=model.getEmail();
                                Intent emailintent=new Intent(Intent.ACTION_SEND);
                                emailintent.setType("message/rfc822");
                                emailintent.putExtra(Intent.EXTRA_EMAIL,s);

                                try
                                {
                                    startActivity(Intent.createChooser(emailintent,"Send Email"));
                                }
                                catch (ActivityNotFoundException e)
                                {
                                    Toast.makeText(CompanyNewJobsListActivity.this, "Email Not Found", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                }

                    @NonNull
                    @Override
                    public CompanyJobListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
                    {
                        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.company_new_job_list_layout,viewGroup,false);
                        return new CompanyJobListViewHolder(view);
                    }
                };


        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }

    public static class CompanyJobListViewHolder extends RecyclerView.ViewHolder
    {
        public TextView list_name,list_phone,list_collge,list_city,list_email,list_datetime;
        public Button show_post,send_mail,show_resume;

        public CompanyJobListViewHolder(@NonNull View itemView)
        {
            super(itemView);

            list_name=itemView.findViewById(R.id.tv_display_job_list_name);
            list_phone=itemView.findViewById(R.id.tv_display_job_list_phone);
            list_collge=itemView.findViewById(R.id.tv_display_job_list_college);
            list_email=itemView.findViewById(R.id.tv_display_job_list_email);
            list_city=itemView.findViewById(R.id.tv_display_job_list_city);
            list_datetime=itemView.findViewById(R.id.tv_display_job_list_date_time);

            show_post=itemView.findViewById(R.id.btn_show_post);
            show_resume=itemView.findViewById(R.id.btn_show_resume);
            send_mail=itemView.findViewById(R.id.btn_send_mail);


        }

    }

}
