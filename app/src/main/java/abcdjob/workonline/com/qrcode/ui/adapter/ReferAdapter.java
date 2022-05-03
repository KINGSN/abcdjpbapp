package abcdjob.workonline.com.qrcode.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import abcdjob.workonline.com.qrcode.R;

public class ReferAdapter extends RecyclerView.Adapter<ReferAdapter.MyViewHolder> {

    //private static final String TAG="RecyclerAdapter";
    List<Refer_Data> refer_data;
    private Context context;

    // int count=0;
    public ReferAdapter(List<Refer_Data> refer_data, Context context) {
        this.refer_data = refer_data;
        this.context = context;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // Log.i(TAG, "onCreateViewHolder: " + count++);
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.refer_data, parent, false);
        return new MyViewHolder(view);
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Refer_Data referData = refer_data.get(position);


        //Picasso.with(context)
        //      .load(listData
        //            .getImage_url())
        //  .into(holder.imageView);
        if (referData.getStatus_paid().equals("PAID")) {
            holder.status_paid.setText("Referal Status : \n \n"+referData.getStatus_paid());
            holder.status_paid.setTextColor(ContextCompat.getColor(context, R.color.green));
        }else
        {
            holder.status_paid.setText("Referal Status : \n \n"+"PENDING");
            holder.status_paid.setTextColor(ContextCompat.getColor(context, R.color.favcolour));
        }


        holder.name.setText(referData.getName());
      //  holder.status_paid.setText("Referal Status : \n "+referData.getStatus_paid());
        holder.joining_date.setText(referData.getJoining_date());
       // Log.d("SHUBHCODE", walletData.getAmount() + walletData.getDate());


        //holder.status.setText(listData.getStatus());
        //holder.joining.setText(listData.getJoining());
    }

    @Override
    public int getItemCount() {
        return refer_data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder  {
        LinearLayout linearLayout;
        TextView status_paid,joining_date,name;

       public MyViewHolder(@NonNull View itemView) {
           super(itemView);

           status_paid=itemView.findViewById(R.id.status_paid);
           joining_date=itemView.findViewById(R.id.joining_date);
           name=itemView.findViewById(R.id.name);
       }
   }
}
