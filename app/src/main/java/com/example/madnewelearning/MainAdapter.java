package com.example.madnewelearning;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainAdapter extends FirebaseRecyclerAdapter<MainModel,MainAdapter.myViewHolder> {


    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public MainAdapter(@NonNull  FirebaseRecyclerOptions<MainModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, final int position, @NonNull  MainModel model) {
     holder.subject_name.setText(model.getSubject_name());
     holder.subject_title.setText(model.getSubject_title());
     holder.subject_description.setText(model.subject_description);

        Glide.with(holder.img.getContext())
                .load(model.getSurl())
                .placeholder(R.drawable.common_google_signin_btn_icon_dark)
                .error(R.drawable.common_google_signin_btn_icon_dark)
                .into(holder.img);

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.img.getContext())
                        .setContentHolder(new ViewHolder(R.layout.update_popup))
                        .setExpanded(true,1450)
                        .create();

                //dialogPlus.show();

                View view = dialogPlus.getHolderView();

                EditText name = view.findViewById(R.id.RsubName);
                EditText title = view.findViewById(R.id.RsubtitleName);
                EditText description = view.findViewById(R.id.Rsubdescription);
                EditText surl = view.findViewById(R.id.RimageUrl);

                Button btnUpdate = view.findViewById(R.id.RbtnUpdate);

               name.setText(model.getSubject_name());
               title.setText(model.getSubject_title());
               description.setText(model.getSubject_description());
               surl.setText(model.getSurl());

               dialogPlus.show();

               btnUpdate.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       Map<String,Object> map = new HashMap<>();
                       map.put("subject_name",name.getText().toString());
                       map.put("subject_title",title.getText().toString());
                       map.put("subject_description",description.getText().toString());
                       map.put("sul",surl.getText().toString());

                       FirebaseDatabase.getInstance().getReference().child("Subjects")
                               .child(getRef(position).getKey()).updateChildren(map)
                               .addOnSuccessListener(new OnSuccessListener<Void>() {
                                   @Override
                                   public void onSuccess(Void unused) {
                                       Toast.makeText(holder.subject_name.getContext(), "Data Updated Successfully", Toast.LENGTH_SHORT).show();
                                       dialogPlus.dismiss();
                                   }
                               })
                               .addOnFailureListener(new OnFailureListener() {
                                   @Override
                                   public void onFailure( Exception e) {
                                       Toast.makeText(holder.subject_name.getContext(), "Error While Updating", Toast.LENGTH_SHORT).show();
                                       dialogPlus.dismiss();
                                   }
                               });
                   }
               });
            }
        });
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.subject_name.getContext());
                builder.setTitle("Are You Sure?");
                builder.setMessage("Deleted data can't be undo.");

                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       FirebaseDatabase.getInstance().getReference().child("Subjects")
                       .child(getRef(position).getKey()).removeValue();

                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(holder.subject_name.getContext(), "Cancelled", Toast.LENGTH_SHORT).show();

                    }
                });
                builder.show();
            }
        });
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_item,parent, false);
        return new myViewHolder(view);

    }

    class myViewHolder extends RecyclerView.ViewHolder{


CircleImageView img;
TextView subject_name,subject_title,subject_description;

Button btnEdit,btnDelete;



        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            img = (CircleImageView)itemView.findViewById(R.id.Rimg1);
            subject_name = (TextView)itemView.findViewById(R.id.Rsubjectname);
            subject_title = (TextView)itemView.findViewById(R.id.Rsubjecttitle);
            subject_description = (TextView)itemView.findViewById(R.id.Rsubjectdescription);

            btnEdit = (Button)itemView.findViewById(R.id.RbtnEdit);
            btnDelete = (Button)itemView.findViewById(R.id.RbtnDelete);


        }
    }

}
