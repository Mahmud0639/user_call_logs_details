package com.manuni.usercalldetails;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.manuni.usercalldetails.databinding.CallSampleBinding;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CallAdapter extends RecyclerView.Adapter<CallAdapter.CallViewHolder> {

    private Context context;
    private List<CallData> list;

    public CallAdapter(Context context, List<CallData> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public CallViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.call_sample,parent,false);
        return new CallViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CallViewHolder holder, int position) {

        CallData data = list.get(position);

        holder.binding.number.setText(data.getPhoneNumber());

        holder.binding.userName.setText(data.getNameUser());

        // Convert call date to a readable format
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy, HH:mm:ss", Locale.getDefault());
        String formattedDate = dateFormat.format(new Date(data.getCallDate()));
        holder.binding.date.setText(formattedDate);

        // Convert call duration to a readable format
        String formattedDuration = formatCallDuration(data.getCallDuration());
        holder.binding.duration.setText(formattedDuration);



        int callType = data.getCallType();
        if (callType == 1){
            holder.binding.callType.setText("Incoming call");
            holder.binding.callType.setTextColor(Color.BLUE);
        }else if (callType==2){
            holder.binding.callType.setText("Outgoing call");
            holder.binding.callType.setTextColor(Color.GREEN);
        }else if (callType==3){
            holder.binding.callType.setText("Missed call");
            holder.binding.callType.setTextColor(Color.RED);
        }else if (callType == 5){
            holder.binding.callType.setText("Rejected call");
            holder.binding.callType.setTextColor(Color.RED);
        }else if (callType==6){
            holder.binding.callType.setText("Blocked call");
            holder.binding.callType.setTextColor(Color.RED);
        }



    }

    private String formatCallDuration(long duration) {
        int minutes = (int) (duration / 60);
        int seconds = (int) (duration % 60);
        return String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class CallViewHolder extends RecyclerView.ViewHolder{
        CallSampleBinding binding;

        public CallViewHolder(@NonNull View itemView) {
            super(itemView);

            binding = CallSampleBinding.bind(itemView);
        }
    }
}
