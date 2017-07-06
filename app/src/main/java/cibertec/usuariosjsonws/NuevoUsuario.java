package cibertec.usuariosjsonws;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.ArrayList;

public class NuevoUsuario extends AppCompatActivity {
    UsuarioCRUDTask oUsuarioCRUDTask=null;
    String _TipoTranccion="N";
    Usuario_BE _Usuario_BE=null;
    Button oBtnGrabar=null;
    Button oBtnEliminar=null;
    TextView  _tvMensaje;
    EditText _edtLoginUsuarioNuevo;
    EditText _edtCargoUsuarioNuevo;
    EditText _edtNombresUsuarioNuevo;
    EditText _edtCotraseniaUsuarioNuevo;
    EditText _edtCorreoUsuarioNuevo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_usuario);
        oBtnGrabar=(Button)findViewById(R.id.BtnAgregarNuevo);
        oBtnEliminar=(Button)findViewById(R.id.BtnEliminar);
        _tvMensaje=(TextView) findViewById(R.id.tvMensaje);;
        _edtLoginUsuarioNuevo=(EditText) findViewById(R.id.edtLoginUsuarioNuevo);
        _edtCargoUsuarioNuevo=(EditText) findViewById(R.id.edtCargoUsuarioNuevo);
        _edtNombresUsuarioNuevo=(EditText) findViewById(R.id.edtNombresUsuarioNuevo);
        _edtCotraseniaUsuarioNuevo=(EditText) findViewById(R.id.edtCotraseniaUsuarioNuevo);
        _edtCorreoUsuarioNuevo=(EditText) findViewById(R.id.edtCorreoUsuarioNuevo);
        Bundle oDatos=getIntent().getExtras();
        _TipoTranccion=oDatos.get("TIPOTRANSACCION").toString();
        _Usuario_BE=(Usuario_BE) oDatos.getSerializable("OBJUSUARIO");
        MostrarDatos();
    }

    private void MostrarDatos()
    {
        if (_TipoTranccion.equals(Funciones.NUEVOREGISTRO))
        {
            oBtnEliminar.setVisibility(View.INVISIBLE);
            oBtnGrabar.setText("Grabar");
        }
        if (_TipoTranccion.equals(Funciones.ACTUALIZAREGISTRO))
        {
            oBtnEliminar.setVisibility(View.VISIBLE);
            oBtnGrabar.setText("Actualizar");
        }
        _edtLoginUsuarioNuevo.setText(_Usuario_BE.getLoginUsuario());
        _edtCargoUsuarioNuevo.setText(_Usuario_BE.getCargo());
        _edtNombresUsuarioNuevo.setText(_Usuario_BE.getNombres());
        _edtCotraseniaUsuarioNuevo.setText(_Usuario_BE.getContraseniaUsuario());
        _edtCorreoUsuarioNuevo.setText(_Usuario_BE.getCorreo());
    }

    public void EliminarUsuario(View v) {
        {
            AlertDialog.Builder oDialogo = new AlertDialog.Builder(this);
            oDialogo.setTitle("Confirmar Eliminación");
            oDialogo.setMessage("¿Estas seguro de eliminar el registro actual?");
            oDialogo.setCancelable(true);
            oDialogo.setPositiveButton(
                    "Si", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (oUsuarioCRUDTask != null) {
                                return;
                            }
                            Toast.makeText(getBaseContext(), "Ejecutando Eliminación",
                                    Toast.LENGTH_LONG).show();
                            oUsuarioCRUDTask = new UsuarioCRUDTask(getApplication(), _tvMensaje, R.layout.activity_nuevo_usuario, _Usuario_BE, Funciones.ELIMINAREGISTRO);
                            oUsuarioCRUDTask.execute((Void) null);
                            dialog.cancel();
                        }
                    }
            );
            oDialogo.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            AlertDialog oConfirmar = oDialogo.create();
            oConfirmar.show();
        }
    }
    public void AgregarUsuario(View v)
    {
        if (oUsuarioCRUDTask!=null)
        {
            return;
        }
        Toast.makeText(getBaseContext(),"Ejecutando",
                Toast.LENGTH_LONG).show();
        Usuario_BE oUsuario_BE=new Usuario_BE();

        oUsuario_BE=_Usuario_BE;
        oUsuario_BE.setNombres(_edtNombresUsuarioNuevo.getText().toString());
        oUsuario_BE.setCargo(_edtCargoUsuarioNuevo.getText().toString());
        oUsuario_BE.setLoginUsuario(_edtLoginUsuarioNuevo.getText().toString());
        oUsuario_BE.setCorreo(_edtCorreoUsuarioNuevo.getText().toString());
        oUsuario_BE.setContraseniaUsuario(_edtCotraseniaUsuarioNuevo.getText().toString());
         oUsuarioCRUDTask=new UsuarioCRUDTask(getApplication(),_tvMensaje,R.layout.activity_nuevo_usuario,oUsuario_BE, _TipoTranccion );
        oUsuarioCRUDTask.execute((Void) null);

    }



    public class UsuarioCRUDTask extends AsyncTask<Void,Void, Boolean> {
        private ArrayList<Usuario_BE> oListadoUsuarios;
        private String _MensajeError;
        private String _StrResultado;
        private final String _URLApp = "http://cibertec201722.somee.com/usuario/FrmUsuario.aspx?";
        private final String _URLAppListar = "Accion=C&Nombres={0}";
        private final String _URLValidarAcceso = "Accion=V&pLogin={0}&pContrasenia={1}";
        private final String _URLAppListarKey = "Accion=K&CodigoUsuario={0}";
        private final String _URLAppRegistraModifica = "Accion={0}&CodigoUsuario={1}&LoginUsuario={2}&CodigoEstadoUsuario={3}&CodigoRol={4}&Nombres={5}&Cargo={6}&Correo={7}&ContraseniaUsuario={8}";
        private final String _URLAppElimina = "Accion=E&CodigoUsuario={0}";
        private Usuario_BE _Usuario;
        private String _Accion;
        private String _URLEjecutar;
        private String _MensajeResultado;
        private Context _ContextoApp;
        private TextView _otvMensaje;
        private  View _VistaLayoutActiva;

        UsuarioCRUDTask(Context pConexto,
                        TextView potvMensaje,
                          int pidVistaLayoutActiva,
                          Usuario_BE pUsuario,
                          String pAccion) {
            _Usuario = pUsuario;
            _otvMensaje=potvMensaje;
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
                // "Accion={0}&CodigoUsuario={1}&LoginUsuario={2}&
                // CodigoEstadoUsuario={3}&CodigoRol={4}&Nombres={5}
                // &Cargo={6}&Correo={7}&ContraseniaUsuario={8}";
                _URLEjecutar=_URLAppRegistraModifica.replace("{0}",_Accion);
                _URLEjecutar=_URLEjecutar.replace("{1}",String.valueOf(_Usuario.getCodigoUsuario()));
                _URLEjecutar=_URLEjecutar.replace("{2}",String.valueOf(_Usuario.getLoginUsuario()));
                _URLEjecutar=_URLEjecutar.replace("{3}",String.valueOf(_Usuario.getCodigoEstadoUsuario()));
                _URLEjecutar=_URLEjecutar.replace("{4}",String.valueOf(_Usuario.getCodigoRol()));
                _URLEjecutar=_URLEjecutar.replace("{5}",String.valueOf(_Usuario.getNombres()));
                _URLEjecutar=_URLEjecutar.replace("{6}",String.valueOf(_Usuario.getCargo()));
                _URLEjecutar=_URLEjecutar.replace("{7}",String.valueOf(_Usuario.getCorreo()));
                _URLEjecutar=_URLEjecutar.replace("{8}",String.valueOf(_Usuario.getContraseniaUsuario()));
            }else if (_Accion.equals(Funciones.ELIMINAREGISTRO))
            {
                // "Accion=E&CodigoUsuario={0}";;
                _URLEjecutar=_URLAppElimina.replace("{0}",String.valueOf(_Usuario.getCodigoUsuario()));
            }
            _URLEjecutar =_URLApp + _URLEjecutar;
            Log.d("xxx", "onPreExecute: " + _URLEjecutar);
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
                //TextView _tvMensaje =(TextView)_VistaLayoutActiva.findViewById(R.id.tvMensaje);
                _otvMensaje.setText(_MensajeResultado);
                Log.d("xxx", "onPreExecute: " + _MensajeResultado);
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
                    else if (_Accion.equals(Funciones.ELIMINAREGISTRO)) {
                        {
                            if (objResponse.getInt("CodigoError")==0) {
                                Toast.makeText(_ContextoApp,
                                        "Eliminado correctamente "
                                        , Toast.LENGTH_LONG).show();
                            }
                            else
                            {
                                Toast.makeText(_ContextoApp,
                                        "Error al eliminar el registro, vuelva a intentarlo más tarde, detalle del error: " + objResponse.getString("DescripcionError")
                                        , Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                    _otvMensaje.setText(_MensajeResultado);

                    Intent oRetorno=new Intent();
                    setResult(RESULT_OK,oRetorno);
                    finish();
                }
                catch (Exception ex)
                {_otvMensaje.setText(ex.getMessage());}
            }
        }

        protected void onCancelled() {


        }
    }

}
