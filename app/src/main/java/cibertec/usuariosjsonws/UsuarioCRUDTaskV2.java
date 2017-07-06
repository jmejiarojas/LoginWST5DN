package cibertec.usuariosjsonws;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by peextssolis on 26/06/2017.
 */


public class UsuarioCRUDTaskV2 extends AsyncTask<Void,Void, Boolean> {
    private ArrayList<Usuario_BE> oListadoUsuarios;
    private String _MensajeError;
    private String _StrResultado;
    private final String _URLApp = "http://cibertec201722.somee.com/usuario/FrmUsuario.aspx?";
    private final String _URLAppListar = "Accion=C&Nombres={0}";
    private final String _URLValidarAcceso = "Accion=V&pLogin={0}&pContrasenia={1}";
    private final String _URLAppListarKey = "Accion=K&CodigoUsuario={0}";
    private final String _URLAppRegistraModifica = "Accion={0}&CodigoUsuario={0}&LoginUsuario={1}&CodigoEstadoUsuario={2}&CodigoRol={3}&Nombres={4}&Cargo={5}&Correo={6}&ContraseniaUsuario={7}";
    private final String _URLAppElimina = "Accion=E&CodigoUsuario={0}";
    private Usuario_BE _Usuario;
    private String _Accion;
    private String _URLEjecutar;
    private String _MensajeResultado;
    private Context _ContextoApp;
    private  View _VistaLayoutActiva;

    UsuarioCRUDTaskV2(Context pConexto,
                    int pidVistaLayoutActiva,
                    Usuario_BE pUsuario,
                    String pAccion) {
        _Usuario = pUsuario;
        _Accion=pAccion;
        _URLEjecutar="";
        _MensajeResultado="";
        _ContextoApp=pConexto;
        LayoutInflater vi= LayoutInflater.from(pConexto);
        _VistaLayoutActiva= vi.inflate(pidVistaLayoutActiva,null);
    }

    protected void onPreExecute() {
        oListadoUsuarios = new ArrayList<Usuario_BE>();

        if (_Accion.equals(Funciones.CONSULTAREGISTRO))
        {
            // "Accion=C&Nombres={0}";
            _URLEjecutar=_URLAppListar.replace("{0}",_Usuario.getNombres());
        }
        else if (_Accion.equals(Funciones.CONSULTAREGISTROLLAVE))
        {
            // "Accion=k&CodigoUsuario={0}";;
            _URLEjecutar=_URLAppListarKey.replace("{0}",String.valueOf(_Usuario.getCodigoUsuario()));
        }
        else if (_Accion.equals(Funciones.NUEVOREGISTRO) ||
                _Accion.equals(Funciones.ACTUALIZAREGISTRO))
        {
            // "Accion={0}&CodigoUsuario={0}&LoginUsuario={1}&CodigoEstadoUsuario={2}&CodigoRol={3}&Nombres={4}&Cargo={5}&Correo={6}&ContraseniaUsuario={7}";
            _URLEjecutar=_URLAppRegistraModifica.replace("{0}",_Accion);
            _URLEjecutar=_URLEjecutar.replace("{1}",String.valueOf(_Usuario.getCodigoUsuario()));
            _URLEjecutar=_URLEjecutar.replace("{2}",String.valueOf(_Usuario.getCodigoEstadoUsuario()));
            _URLEjecutar=_URLEjecutar.replace("{3}",String.valueOf(_Usuario.getCodigoRol()));
            _URLEjecutar=_URLEjecutar.replace("{4}",String.valueOf(_Usuario.getNombres()));
            _URLEjecutar=_URLEjecutar.replace("{5}",String.valueOf(_Usuario.getCargo()));
            _URLEjecutar=_URLEjecutar.replace("{6}",String.valueOf(_Usuario.getCorreo()));
            _URLEjecutar=_URLEjecutar.replace("{7}",String.valueOf(_Usuario.getContraseniaUsuario()));
        }else if (_Accion.equals(Funciones.ELIMINAREGISTRO))
        {
            // "Accion=E&CodigoUsuario={0}";;
            _URLEjecutar=_URLAppElimina.replace("{0}",String.valueOf(_Usuario.getCodigoUsuario()));
        }
    }

    protected Boolean doInBackground(Void... params) {

        try
        {_MensajeResultado=Funciones.DescargarPagina(_URLEjecutar);}
        catch (Exception ex)
        { _MensajeError=ex.getMessage();}
        // TODO: register the new account here.
        return true;
    }

    protected void onPostExecute(final Boolean success) {

        if (success) {
            TextView _tvMensaje =(TextView)_VistaLayoutActiva.findViewById(R.id.tvMensaje);
            try {JSONObject objResponse = new JSONObject(_MensajeResultado);
                JSONObject ObjUsuario=objResponse.getJSONObject("ObjUsuario");
                if (_Accion.equals(Funciones.NUEVOREGISTRO))
                {
                    if (ObjUsuario.getInt("CodigoUsuario")>0)
                    {
                        Toast.makeText(_ContextoApp,
                                "Usuario:" +ObjUsuario.getString("Nombres") + ", creado correctamente"
                                , Toast.LENGTH_LONG).show();
                    }
                }
                else if (_Accion.equals(Funciones.ACTUALIZAREGISTRO)) {
                    if (ObjUsuario.getInt("CodigoUsuario")>0)
                    {
                        Toast.makeText(_ContextoApp,
                                "Actualizado correctamente "
                                , Toast.LENGTH_LONG).show();
                    }
                }
                _tvMensaje.setText(_MensajeResultado);
            }
            catch (Exception ex)
            {_tvMensaje.setText(ex.getMessage());}
        }
    }

    protected void onCancelled() {


    }
}

