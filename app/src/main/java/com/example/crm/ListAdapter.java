package com.example.crm;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
    private List<ListElement>elements;
    public LayoutInflater mInflater;
    private Context context;
    ListAdapter.OnItemClickListener listener;

    public interface OnItemClickListener{
        void onItemClick(ListElement item);

    }

    public ListAdapter(List<ListElement> itemList, Context context, ListAdapter.OnItemClickListener listener){
        this.mInflater=LayoutInflater.from(context);
        this.context=context;
        this.elements=itemList;
        this.listener=listener;
    }
    @Override
    public int getItemCount(){ return elements.size();}
    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view=mInflater.inflate(R.layout.list_element, null);
        return new ListAdapter.ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(final ListAdapter.ViewHolder holder, final int position){
        holder.bindData(elements.get(position));
    }
    public void setItem(List<ListElement>items){elements=items;}
    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView iconImage;
        TextView nombre_Completo,email,numero_Telefono,posible_Ganancia,estado;
        ViewHolder(View itemView){
            super(itemView);
            iconImage=itemView.findViewById(R.id.icon_ImageView);
            nombre_Completo=itemView.findViewById(R.id.nombre_Completo);
            email=itemView.findViewById(R.id.email);
            numero_Telefono=itemView.findViewById(R.id.numero_telefono);
            posible_Ganancia=itemView.findViewById(R.id.posible_Ganancia);
            estado=itemView.findViewById(R.id.estado);
        }
        void bindData(final ListElement item){
            iconImage.setColorFilter(Color.parseColor(item.getColor()), PorterDuff.Mode.SRC_IN);
            nombre_Completo.setText(item.getNombre_Completo());
            email.setText(item.getEmail());
            numero_Telefono.setText(item.getNumero_Telefono());
            posible_Ganancia.setText(item.getPosible_Ganancia());
            estado.setText(item.getEstado());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(item);
                }
            });

        }
    }
}