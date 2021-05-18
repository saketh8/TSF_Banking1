package com.saketh8.tsf_banking.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.saketh8.tsf_banking.R;
import com.saketh8.tsf_banking.db.TransacEntity;

import java.util.Collections;
import java.util.List;

public class TransacAdapter extends RecyclerView.Adapter<TransacAdapter.TransacView> {

    List<TransacEntity> list;

    public TransacAdapter(List<TransacEntity> l){
        list=l;
        Collections.reverse(list);
    }

    @NonNull
    @Override
    public TransacView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.transac_item,parent,false);
        return new TransacView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TransacView holder, int position) {
        System.out.println("Time is "+list.get(position).key);
        holder.sendId.setText("Sender ID:"+list.get(position).sendId);
        holder.recId.setText("Receiver ID:"+list.get(position).recId);
        holder.sendName.setText(""+list.get(position).sendName);
        holder.recName.setText(""+list.get(position).recName);
        holder.amt.setText("Amount transferred: "+list.get(position).amt);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class TransacView extends RecyclerView.ViewHolder{

        TextView sendId,recId,sendName,recName,amt;

        public TransacView(@NonNull View itemView) {
            super(itemView);
            sendId=itemView.findViewById(R.id.send_id);
            recId=itemView.findViewById(R.id.rec_id);
            sendName=itemView.findViewById(R.id.send_name);
            recName=itemView.findViewById(R.id.rec_name);
            amt=itemView.findViewById(R.id.amtTxt);
        }
    }
}
