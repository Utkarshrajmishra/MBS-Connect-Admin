package com.example.mbsconnectadmin.department;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mbsconnectadmin.R;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;

import io.github.muddz.styleabletoast.StyleableToast;

public class teacher_adaopter extends RecyclerView.Adapter<teacher_adaopter.teacherviewadapoter> {

    private List<teacher_Data> list;
    private Context context;
    private String category;

    public teacher_adaopter(List<teacher_Data> list, Context context, String category) {
        this.list = list;
        this.context = context;
        this.category=category;
    }

    @NonNull
    @Override
    public teacherviewadapoter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.teacher_iten_layout,parent, false);
        return new teacherviewadapoter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull teacherviewadapoter holder, int position) {

        teacher_Data item=list.get(position);
        holder.name.setText(item.getName());
        holder.email.setText(item.getEmail());
        holder.post.setText(item.getPost());
        try {
            Picasso.get().load(item.getImage()).into(holder.image);
        } catch (Exception e) {
            e.printStackTrace();
        }

        holder.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, update_teacher.class);
                intent.putExtra("name", item.getName());
                intent.putExtra("mail",item.getEmail());
                intent.putExtra("post",item.getPost());
                intent.putExtra("image", item.getImage());
                intent.putExtra("key", item.getKey());
                intent.putExtra("category", category);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class teacherviewadapoter extends RecyclerView.ViewHolder {

        private TextView name,email,post;
        private Button update;
        private ImageView image;

        public teacherviewadapoter(@NonNull View itemView) {
            super(itemView);

            name=(TextView) itemView.findViewById(R.id.teacheranme);
            email=(TextView) itemView.findViewById(R.id.teacheremail2);
            post=(TextView)itemView.findViewById(R.id.teacherpost2);
            image=itemView.findViewById(R.id.teacherphoto2);
            update=itemView.findViewById(R.id.teaxherupdate);
        }
    }
}
