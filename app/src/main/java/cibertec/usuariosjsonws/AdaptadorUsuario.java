package cibertec.usuariosjsonws;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by cibertec on 27/05/2017.
 */

public class AdaptadorUsuario extends ArrayAdapter<Usuario_BE>
{
    private ArrayList<Usuario_BE> ListaInterna;
    public AdaptadorUsuario(Context context,
                                int textViewResourceId, ArrayList<Usuario_BE> items)
    {
        super(context, textViewResourceId, items);
        this.ListaInterna = items;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View oLayoutRegistro = convertView;
        if (oLayoutRegistro == null)
        {
            LayoutInflater vi= LayoutInflater.from(getContext());
            oLayoutRegistro = vi.inflate(R.layout.registrousuarios,
                    null);
        }
        Usuario_BE oUsuario_BE= ListaInterna.get(position);
        if (oUsuario_BE!= null) {
            TextView oTxtCorreo= (TextView)oLayoutRegistro.findViewById(R.id.TxtCorreo);
            TextView oTxtLoginUsuario=(TextView)oLayoutRegistro.findViewById(R.id.TxtLoginUsuario);
            TextView oTxtNombresUsuario=(TextView)oLayoutRegistro.findViewById(R.id.TxtNombresUsuario);

            oTxtCorreo.setText(oUsuario_BE.getCorreo());
            oTxtLoginUsuario.setText(oUsuario_BE.getLoginUsuario());
            oTxtNombresUsuario.setText(oUsuario_BE.getNombres());
        }
        return oLayoutRegistro;
    }

}
