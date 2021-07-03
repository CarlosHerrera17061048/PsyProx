package com.charles.psyprox;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class Adaptador extends BaseAdapter {
    private static LayoutInflater inflater = null;

    Context contexto;
    String  Especialistas;

    public Adaptador(Context contexto, String especialistas) {
        this.contexto = contexto;
        Especialistas = especialistas;
        inflater = (LayoutInflater)contexto.getSystemService(contexto.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final View vista = inflater.inflate(R.layout.elemento_lista,null);
        TextView txtNombreC =vista.findViewById(R.id.txtNombreC);
        TextView txtApellidoC =vista.findViewById(R.id.txtApellidoC);
        TextView txtTelefono =vista.findViewById(R.id.txtTelefonoC);
        TextView txtEdadC =vista.findViewById(R.id.txtEdadC);

        txtNombreC.setText(Especialistas);
        /*txtApellidoC.setText(Especialistas[1]);
        txtTelefono.setText(Especialistas[2]);
        txtEdadC.setText(Especialistas[3]);*/

        return vista;
    }
    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


}
