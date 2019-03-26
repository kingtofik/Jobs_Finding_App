package com.student.job.scc;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;
import com.student.job.scc.Model.Favorite;
import com.student.job.scc.Prevalent.Prevalent;
import com.student.job.scc.ViewHolder.FavoriteViewHolder;
import com.student.job.scc.ViewHolder.JobViewHolder;

public class FavoriteFragment extends Fragment implements View.OnClickListener{

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view =inflater.inflate(R.layout.fragment_favorites,container,false);

        recyclerView=view.findViewById(R.id.recycler_fav_menu);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this.getActivity());
        recyclerView.setLayoutManager(layoutManager);


        return view;
    }


    @Override
    public void onStart()
    {
        super.onStart();

        final DatabaseReference favListRef= FirebaseDatabase.getInstance().getReference().child("Favorite");

        FirebaseRecyclerOptions<Favorite> options=new FirebaseRecyclerOptions.Builder<Favorite>()
                .setQuery(favListRef.child("User View").child(Prevalent.currentOnlineUser.getPhone()).child("All Jobs"),Favorite.class)
                .build();


        FirebaseRecyclerAdapter<Favorite, FavoriteViewHolder> adapter=
                new FirebaseRecyclerAdapter<Favorite, FavoriteViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull FavoriteViewHolder holder, int position, @NonNull final Favorite model)
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
                                CharSequence options[]=new CharSequence[]
                                        {
                                          "View",
                                          "Delete"
                                        };
                                AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
                                builder.setTitle("Choose Options");

                                builder.setItems(options, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which)
                                    {
                                       if(which==0)
                                       {
                                           Intent intent=new Intent(getActivity(),JobDetailsActivity.class);
                                           intent.putExtra("jid",model.getJid());
                                           startActivity(intent);
                                       }
                                       if(which==1)
                                       {
                                           favListRef.child("User View").child(Prevalent.currentOnlineUser.getPhone())
                                                   .child("All Jobs").child(model.getJid()).removeValue()
                                                   .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                       @Override
                                                       public void onComplete(@NonNull Task<Void> task)
                                                       {
                                                         if(task.isSuccessful())
                                                         {
                                                             favListRef.child("Admin View").child(Prevalent.currentOnlineUser.getPhone())
                                                             .child("All Jobs").child(model.getJid()).removeValue()
                                                             .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                 @Override
                                                                 public void onComplete(@NonNull Task<Void> task)
                                                                 {
                                                                  if(task.isSuccessful())
                                                                  {
                                                                      Toast.makeText(getContext(), "Un Favorite Successful", Toast.LENGTH_SHORT).show();
                                                                      Intent intent=new Intent(getActivity(),MainHomeActivity.class);
                                                                      startActivity(intent);
                                                                      getActivity().finish();
                                                                  }
                                                                 }
                                                             });
                                                         }
                                                       }
                                                   });
                                       }
                                    }

                                });
                                builder.show();
                            }
                        });

                    }

                    @NonNull
                    @Override
                    public FavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
                    {
                        View view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.display_job_layout,viewGroup,false);
                        FavoriteViewHolder holder=new FavoriteViewHolder(view);
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
