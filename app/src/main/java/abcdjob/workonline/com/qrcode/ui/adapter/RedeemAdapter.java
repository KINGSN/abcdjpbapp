package abcdjob.workonline.com.qrcode.ui.adapter;

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

public class RedeemAdapter extends RecyclerView.Adapter<RedeemAdapter.MyViewHolder> {

    //private static final String TAG="RecyclerAdapter";
    List<Redeem_Data> redeem_data;
    private Context context;

    // int count=0;
    public RedeemAdapter(List<Redeem_Data> redeem_data, Context context) {
        this.redeem_data = redeem_data;
        this.context = context;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // Log.i(TAG, "onCreateViewHolder: " + count++);
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.redeem_data, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return new RedeemAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Redeem_Data redeemData = redeem_data.get(position);

        //Picasso.with(context)
        //      .load(listData
        //            .getImage_url())
        //  .into(holder.imageView);


        if (redeemData.getType().equals("PAID")){
            holder.amount.setTextColor(ContextCompat.getColor(context, R.color.green));
            holder.name.setText("Amount Redeemed from  wallet");
            holder.amount.setText(redeemData.getAmount()+" ₹");
            holder.type.setText(redeemData.getType());
            holder.type.setTextColor(ContextCompat.getColor(context, R.color.green));
            holder.date.setText(redeemData.getPaid_date());
            holder.date.setTextColor(ContextCompat.getColor(context, R.color.green));
            holder.txnt.setText("Paid Date");


        } else if (redeemData.getType().equals("PENDING")) {
            holder.amount.setTextColor(ContextCompat.getColor(context, R.color.red));
            holder.name.setText("Amount Redeemed ");
            holder.amount.setText(redeemData.getAmount()+" ₹");
            holder.type.setTextColor(ContextCompat.getColor(context, R.color.favcolour));
            holder.date.setText(redeemData.getReq_date());
            holder.date.setTextColor(ContextCompat.getColor(context, R.color.favcolour));
            holder.txnt.setText("Requested Date");
        }
        else if (redeemData.getType().equals("CANCELLED")) {
            holder.amount.setTextColor(ContextCompat.getColor(context, R.color.red));
            holder.name.setText("Amount Redeemed ");
            holder.amount.setText(redeemData.getAmount()+" ₹");
            holder.type.setTextColor(ContextCompat.getColor(context, R.color.favcolour));
            holder.date.setText(redeemData.getCancelled_date());
            holder.date.setTextColor(ContextCompat.getColor(context, R.color.BloddColour));
            holder.txnt.setText("Cancelled Date");
        }
        else if (redeemData.getType().equals("CREDIT") & (redeemData.getTxn().equals("MathQuiz"))) {
            holder.amount.setTextColor(ContextCompat.getColor(context, R.color.favcolour));
            holder.name.setText("Amount Earned Though Quiz");
            holder.amount.setText(" + " +redeemData.getAmount()+ " ₹");

        }

        else if (redeemData.getType().equals("CREDIT") & (redeemData.getTxn().equals("TASK_REWARD"))) {
            holder.amount.setTextColor(ContextCompat.getColor(context, R.color.favcolour));
            holder.name.setText("Amount Earned Though DailyTask");
            holder.amount.setText(" + " +redeemData.getAmount()+ " Amount");

        }
        else if (redeemData.getType().equals("CREDIT") & (redeemData.getTxn().equals("JOINING_BONUS"))) {
            holder.amount.setTextColor(ContextCompat.getColor(context, R.color.favcolour));
            holder.name.setText("Amount Earned Though Signup");
            holder.amount.setText(" + " +redeemData.getAmount()+ " Amount");

        }

        else if (redeemData.getType().equals("CREDIT") & (redeemData.getTxn().equals("Spin"))) {
            holder.amount.setTextColor(ContextCompat.getColor(context, R.color.favcolour));
            holder.name.setText("Amount Earned Though Spin");
            holder.amount.setText(" + " +redeemData.getAmount()+ " Amount");

        }


        holder.date.setText(redeemData.getReq_date());
        holder.paytm_no.setText(redeemData.getPaytm_no());
        holder.method_type.setText(redeemData.getPayment_method());




        //holder.status.setText(listData.getStatus());
        //holder.joining.setText(listData.getJoining());
    }

    @Override
    public int getItemCount() {
        return redeem_data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        //ImageView imageView;
        LinearLayout linearLayout;
        TextView amount,date,type,name,paytm_no,method_type,txnt;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            //imageView= itemView.findViewById(R.id.imageViewImageMedia);
            amount=itemView.findViewById(R.id.amount);
            date=itemView.findViewById(R.id.wallet_date);
            name=itemView.findViewById(R.id.name);
            paytm_no=itemView.findViewById(R.id.redeempaytmno);
            type=itemView.findViewById(R.id.redeemstatus);
            method_type=itemView.findViewById(R.id.method_type);
            txnt=itemView.findViewById(R.id.txnt);
            //joining=itemView.findViewById(R.id.joining);



        }
    }
}