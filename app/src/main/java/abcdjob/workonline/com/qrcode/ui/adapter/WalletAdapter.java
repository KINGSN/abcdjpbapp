package abcdjob.workonline.com.qrcode.ui.adapter;

import android.content.Context;
import android.util.Log;
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

public class WalletAdapter extends RecyclerView.Adapter<WalletAdapter.MyViewHolder> {

    //private static final String TAG="RecyclerAdapter";
    List<Wallet_Data> wallet_data;
    private Context context;

    // int count=0;
    public WalletAdapter(List<Wallet_Data> wallet_data, Context context) {
        this.wallet_data = wallet_data;
        this.context = context;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // Log.i(TAG, "onCreateViewHolder: " + count++);
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.wallet_data, parent, false);
        return new MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Wallet_Data walletData = wallet_data.get(position);

        //Picasso.with(context)
        //      .load(listData
        //            .getImage_url())
        //  .into(holder.imageView);


        if (walletData.getType().equals("PAID") || walletData.getType().equals("PENDING")) {
            holder.amount.setTextColor(ContextCompat.getColor(context, R.color.red));
            holder.name.setText("Coins Redeemed from  wallet");
            holder.amount.setText(" - " + walletData.getAmount());
        } else if (walletData.getType().equals("REDEEM")) {
            //holder.amount.setTextColor(ContextCompat.getColor(context, R.color.favcolour));
            holder.name.setText("Amount Redeemed From Wallet");
            holder.amount.setText("- " + walletData.getTxn()+" ₹");
            holder.amount.setTextColor(ContextCompat.getColor(context, R.color.green));
        }
        else if (walletData.getType().equals("CREDIT") & (walletData.getTxn().equals("MathQuiz"))) {
        holder.amount.setTextColor(ContextCompat.getColor(context, R.color.favcolour));
        holder.name.setText("Coins Earned Though Quiz");
        holder.amount.setText(" + " +walletData.getAmount()+ " Coins");

    }

        else if (walletData.getType().equals("CREDIT") & (walletData.getTxn().equals("TASK_REWARD"))) {
            holder.amount.setTextColor(ContextCompat.getColor(context, R.color.favcolour));
            holder.name.setText("Coins Earned Though DailyTask");
            holder.amount.setText(" + " +walletData.getAmount()+ " Coins");

        }
        else if (walletData.getType().equals("CREDIT") & (walletData.getTxn().equals("JOINING_BONUS"))) {
            holder.amount.setTextColor(ContextCompat.getColor(context, R.color.favcolour));
            holder.name.setText("Amount Credited For Signup");
            holder.amount.setText(" + " +walletData.getAmount()+ " Coins");

        }

        else if (walletData.getType().equals("CREDIT") & (walletData.getTxn().equals("Qr Code Generation"))) {
            holder.amount.setTextColor(ContextCompat.getColor(context, R.color.R));
            holder.name.setText("Amount Credited For Qr Code");
            holder.amount.setText(" + " +walletData.getAmount()+ " ₹");

        }
        else if (walletData.getType().equals("CREDIT") & (walletData.getTxn().equals("Referal Bonus"))) {
            holder.amount.setTextColor(ContextCompat.getColor(context, R.color.favcolour));
            holder.name.setText("Amount Credited For Refferal");
            holder.amount.setText(" + " +walletData.getAmount()+ " ₹");

        } else if (walletData.getType().equals("CREDIT") & (walletData.getTxn().equals("Codes Added By Admin"))) {
            holder.amount.setTextColor(ContextCompat.getColor(context, R.color.favcolour));
            holder.name.setText("Amount Added By Admin For Qrcode");
            holder.amount.setText(" + " +walletData.getAmount()+ " ₹");

        }


        holder.date.setText(walletData.getDate());
        Log.d("DARWINBARK", walletData.getAmount() + walletData.getDate());


        //holder.status.setText(listData.getStatus());
        //holder.joining.setText(listData.getJoining());
    }

    @Override
    public int getItemCount() {
        return wallet_data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder  {
        LinearLayout linearLayout;
        TextView amount,date,name;

       public MyViewHolder(@NonNull View itemView) {
           super(itemView);

           amount=itemView.findViewById(R.id.amount);
           date=itemView.findViewById(R.id.wallet_date);
           name=itemView.findViewById(R.id.name);
       }
   }
}
