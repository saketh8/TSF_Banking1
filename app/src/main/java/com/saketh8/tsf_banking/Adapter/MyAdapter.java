package com.saketh8.tsf_banking.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.saketh8.tsf_banking.Activity.CustomerActivity;
import com.saketh8.tsf_banking.db.DbEntity;
import com.saketh8.tsf_banking.R;

import java.util.List;

import static android.view.View.*;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ListViewHolder> {

    List<DbEntity> arr;
    Context context;
    int send, receive;

    public MyAdapter(List<DbEntity> strings, Context ctx, int s, int r) {
        arr = strings;
        context = ctx;
        send = s;
        receive = r;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflater = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);
        return new ListViewHolder(inflater);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, final int position) {

        holder.name.setText(arr.get(position).name);
        holder.id.setText(arr.get(position).id + "");
        holder.balance.setText("Rs. " + arr.get(position).balance);

        holder.card.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, CustomerActivity.class);
                intent.putExtra("id", arr.get(position).id);
                intent.putExtra("sender",send);
                intent.putExtra("receiver",receive);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arr.size();
    }

    public static class ListViewHolder extends RecyclerView.ViewHolder {

        public TextView id, name, balance;
        CardView card;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.cust_id);
            name = itemView.findViewById(R.id.cust_name);
            balance = itemView.findViewById(R.id.cust_balance);
            card = itemView.findViewById(R.id.single_card);
        }
    }
}
