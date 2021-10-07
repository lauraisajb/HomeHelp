package com.example.homehelp;

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

public class OperadoresAdapter extends FirebaseRecyclerAdapter<Operadores, OperadoresAdapter.myViewHolder>{

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public OperadoresAdapter(@NonNull FirebaseRecyclerOptions<Operadores> options) {
        super(options);
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

    }

    @NotNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder{

        CircleImageView img;
        TextView Username, Oficio, city;

        public myViewHolder(@NonNull View itemView){
            super(itemView);

           // img = (CircleImageView)itemView.findViewById(R.id.img1);
            Username = (TextView)itemView.findViewById(R.id.eUsername);
            Oficio = (TextView)itemView.findViewById(R.id.eOficio);
            city = (TextView)itemView.findViewById(R.id.eCity);
        }

    }


}
