package com.example.paysure.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.paysure.AjouterClientBottomSheet;
import com.example.paysure.R;
import com.example.paysure.model.Client;

import java.util.List;

public class ClientAdapter extends RecyclerView.Adapter<ClientAdapter.ViewHolder> {

    private final List<Client> clientList;

    public ClientAdapter(List<Client> clientList) {
        this.clientList = clientList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_client, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Client client = clientList.get(position);
        holder.textNom.setText(client.getNom());
        holder.textTelephone.setText(client.getTelephone());
        holder.textAdresse.setText(client.getAdresse());
    }

    @Override
    public int getItemCount() {
        return clientList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textNom, textTelephone, textAdresse;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textNom = itemView.findViewById(R.id.textNom);
            textTelephone = itemView.findViewById(R.id.textTelephone);
            textAdresse = itemView.findViewById(R.id.textAdresse);
        }
    }
    
}
