package com.student.job.scc;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;
import com.student.job.scc.Model.Jobs;
import com.student.job.scc.ViewHolder.JobViewHolder;

public class HomeFragment extends Fragment implements View.OnClickListener {

    private DatabaseReference JobsRef;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view =inflater.inflate(R.layout.fragment_home,container,false);

        JobsRef= FirebaseDatabase.getInstance().getReference().child("All Jobs");

        recyclerView=view.findViewById(R.id.recycler_menu);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this.getActivity());
        recyclerView.setLayoutManager(layoutManager);

        return view;

    }

    @Override
    public void onStart()
    {
        super.onStart();

        FirebaseRecyclerOptions<Jobs> options=
                new FirebaseRecyclerOptions.Builder<Jobs>()
                        .setQuery(JobsRef,Jobs.class).build();

        FirebaseRecyclerAdapter<Jobs, JobViewHolder> adapter=
                new FirebaseRecyclerAdapter<Jobs, JobViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull JobViewHolder holder, int position, @NonNull final Jobs model)
                    {
                        holder.job_title.setText(model.getJob_Title());
                        holder.job_description.setText(model.getJob_Desc());
                        holder.job_salay.setText(model.getJob_Salary());
                        holder.job_location.setText(model.getJob_Location());
                        Picasso.get().load(model.getImage()).into(holder.company_logo);

                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v)
                            {
                                Intent intent=new Intent(getActivity(),JobDetailsActivity.class);
                                intent.putExtra("jid",model.getJid());
                                startActivity(intent);
                            }
                        });
                    }

                    @NonNull
                    @Override
                    public JobViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
                    {
                        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.display_job_layout,viewGroup,false);
                        JobViewHolder holder=new JobViewHolder(view);
                        return holder;
                    }
                };

        recyclerView.setAdapter(adapter);
        adapter.startListening();

    }


    @Override
    public void onClick(View v)
    {

    }
}
