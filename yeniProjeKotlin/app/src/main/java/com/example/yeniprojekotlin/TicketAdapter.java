package com.example.yeniprojekotlin;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TicketAdapter extends RecyclerView.Adapter<TicketAdapter.TicketViewHolder> {

    private final List<String> ticketList;
    private final OnTicketClickListener listener;

    public TicketAdapter(List<String> ticketList, OnTicketClickListener listener) {
        this.ticketList = ticketList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public TicketViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ticket_item, parent, false);
        return new TicketViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TicketViewHolder holder, int position) {
        holder.bind(ticketList.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return ticketList.size();
    }

    public interface OnTicketClickListener {
        void onDeleteClick(int position);
    }

    static class TicketViewHolder extends RecyclerView.ViewHolder {

        private final TextView textView;
        private final ImageView deleteImage;

        public TicketViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.ticket_text);
            deleteImage = itemView.findViewById(R.id.ticket_delete);
        }

        public void bind(String ticket, OnTicketClickListener listener) {
            textView.setText(ticket);

            deleteImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onDeleteClick(getAdapterPosition());
                }
            });
        }
    }
}
