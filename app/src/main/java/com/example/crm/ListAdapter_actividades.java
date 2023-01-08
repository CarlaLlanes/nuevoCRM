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

public class ListAdapter_actividades extends RecyclerView.Adapter<ListAdapter_actividades.ViewHolder> {
    private List<ListElement_actividades>mData;
    public LayoutInflater mInflater;
    private Context context;
    ListAdapter_actividades.OnItemClickListener listener;

    public interface OnItemClickListener{
        void onItemClick(ListElement_actividades item);

    }

    public ListAdapter_actividades(List<ListElement_actividades> itemList, Context context, ListAdapter_actividades.OnItemClickListener listener){
        this.mInflater=LayoutInflater.from(context);
        this.context=context;
        this.mData=itemList;
        this.listener=listener;
    }
    @Override
    public int getItemCount(){ return mData.size();}
    @Override
    public ListAdapter_actividades.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view=mInflater.inflate(R.layout.list_element_actividades, null);
        return new ListAdapter_actividades.ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(final ListAdapter_actividades.ViewHolder holder, final int position){
        holder.bindData(mData.get(position));
    }
    public void setItem(List<ListElement_actividades>items){mData=items;}
    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView iconImage;
        TextView tipo_Actividad,fecha_Vencimiento,hora_Inicio,hora_Termino,duracion,descripcion_Actividad,recordatorios,medio_Contacto;
        ViewHolder(View itemView){
            super(itemView);
            iconImage=itemView.findViewById(R.id.icon_ImageView);
            tipo_Actividad=itemView.findViewById(R.id.tipo_Actividad);
            fecha_Vencimiento=itemView.findViewById(R.id.fecha_vencimiento);
            hora_Inicio=itemView.findViewById(R.id.hora_Inicio);
            hora_Termino=itemView.findViewById(R.id.hora_Termino);
            duracion=itemView.findViewById(R.id.duracion);
            descripcion_Actividad=itemView.findViewById(R.id.descripcion_Actividad);
            recordatorios=itemView.findViewById(R.id.recordatorios);
            medio_Contacto=itemView.findViewById(R.id.medio_Contacto);
        }
        void bindData(final ListElement_actividades item){
            iconImage.setColorFilter(Color.parseColor(item.getColor()), PorterDuff.Mode.SRC_IN);
            tipo_Actividad.setText(item.getTipo_Actividad());
            fecha_Vencimiento.setText(item.getFecha_Vencimiento());
            hora_Inicio.setText(item.getHora_Inicio());
            hora_Termino.setText(item.getHora_Termino());
            duracion.setText(item.getDuracion());
            descripcion_Actividad.setText(item.getDescripcion_Actividad());
            recordatorios.setText(item.getRecordatorios());
            medio_Contacto.setText(item.getMedio_contacto());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(item);
                }
            });

        }
    }
}