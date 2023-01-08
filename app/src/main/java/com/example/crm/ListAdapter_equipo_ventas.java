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

public class ListAdapter_equipo_ventas extends RecyclerView.Adapter<ListAdapter_equipo_ventas.ViewHolder> {
    private List<ListElement_equipo_ventas>mData;
    public LayoutInflater mInflater;
    private Context context;
    ListAdapter_equipo_ventas.OnItemClickListener listener;

    public interface OnItemClickListener{
        void onItemClick(ListElement_equipo_ventas item);

    }

    public ListAdapter_equipo_ventas(List<ListElement_equipo_ventas> itemList, Context context, ListAdapter_equipo_ventas.OnItemClickListener listener){
        this.mInflater=LayoutInflater.from(context);
        this.context=context;
        this.mData=itemList;
        this.listener=listener;
    }
    @Override
    public int getItemCount(){ return mData.size();}
    @Override
    public ListAdapter_equipo_ventas.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view=mInflater.inflate(R.layout.list_element_equipo_ventas, null);
        return new ListAdapter_equipo_ventas.ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(final ListAdapter_equipo_ventas.ViewHolder holder, final int position){
        holder.bindData(mData.get(position));
    }
    public void setItem(List<ListElement_equipo_ventas>items){mData=items;}
    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView iconImage;
        TextView nombre_Completo,email,numero_Telefono;
        ViewHolder(View itemView){
            super(itemView);
            iconImage=itemView.findViewById(R.id.icon_ImageView);
            nombre_Completo=itemView.findViewById(R.id.nombre_Completo);
            email=itemView.findViewById(R.id.email);
            numero_Telefono=itemView.findViewById(R.id.numero_telefono);
        }
        void bindData(final ListElement_equipo_ventas item){
            iconImage.setColorFilter(Color.parseColor(item.getColor()), PorterDuff.Mode.SRC_IN);
            nombre_Completo.setText(item.getNombre_Completo());
            email.setText(item.getEmail());
            numero_Telefono.setText(item.getNumero_Telefono());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(item);
                }
            });

        }
    }
}