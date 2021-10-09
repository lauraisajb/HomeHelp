package com.example.homehelp;


import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import  com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import org.jetbrains.annotations.NotNull;

import de.hdodenhof.circleimageview.CircleImageView;

import static androidx.core.content.ContextCompat.startActivity;

public class OperadoresAdapter extends FirebaseRecyclerAdapter<Operadores, OperadoresAdapter.myViewHolder> {

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */

    Context activity;
    public OperadoresAdapter(@NonNull FirebaseRecyclerOptions<Operadores> options, Context activity) {
        super(options);
        this.activity=activity;
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull Operadores model) {
        //datos
        holder.Username.setText(model.getUserName());
        holder.city.setText(model.getCiudad());
        holder.Oficio.setText(model.getOficio());

        /*Glide.with(holder.img.getContext())
                .load(model.getSurl())
                .placeholder(R.drawable.common_google_signin_btn_icon_dark)
                .circleCrop()
                .error(R.drawable.common_google_signin_btn_icon_dark_normal)
                .into(holder.img);*/

        holder.setOnClickListener(position);

    }

    @NotNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder{

        //context
        Context context;

        CircleImageView img;
        TextView Username, Oficio, city;



        public myViewHolder(@NonNull View itemView){
            super(itemView);
            context = itemView.getContext();

           // img = (CircleImageView)itemView.findViewById(R.id.img1);
            Username = (TextView)itemView.findViewById(R.id.eUsername);
            Oficio = (TextView)itemView.findViewById(R.id.eOficio);
            city = (TextView)itemView.findViewById(R.id.eCity);
        }

        void setOnClickListener(int position){
             itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String seeWorkerId = getRef(position).getKey();
                    System.out.println(seeWorkerId);

                    Intent intent = new  Intent(context, see_worker.class);
                    intent.putExtra("seeWorkerId", seeWorkerId);
                    context.startActivity(intent);

                }
            });
        }

    }


}
