package cibertec.usuariosjsonws;

import android.os.AsyncTask;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.BoolRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ListadoUsuario extends AppCompatActivity {
    private AdaptadorUsuario oAdaptadorUsuario=null;
    ListView oVistaLista=null;
    private ListarUsuarios mAuthTask = null;
    private EditText oedtResultado;
    private ArrayList<Usuario_BE> oListadoUsuarios=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_usuario);
        oedtResultado=(EditText)findViewById(R.id.edtResultado);
        oListadoUsuarios=new ArrayList<Usuario_BE>();
        oVistaLista=(ListView)findViewById(R.id.LstUsuarios);


        oVistaLista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent oIntento=new Intent(getApplication(),
                        NuevoUsuario.class);
                Usuario_BE oUsuario_BE=oListadoUsuarios.get(position) ;
                oIntento.putExtra("TIPOTRANSACCION", Funciones.ACTUALIZAREGISTRO);
                oIntento.putExtra("OBJUSUARIO", oUsuario_BE);
                startActivityForResult(oIntento,1);
            }
        });
        CargarUsuarios();
    }

    public void CargarUsuarios()
    {
        oAdaptadorUsuario=new AdaptadorUsuario(this,
                R.layout.activity_listado_usuario,
                oListadoUsuarios);
        oVistaLista.setAdapter(oAdaptadorUsuario);
    }

    protected void onActivityResult(int RequestCode, int ResultCode, Intent Data)
    {
        if (RequestCode==1)
        {
            if (ResultCode==RESULT_OK)
            {BuscarUsuarios(null);}
        }
    }


    public void CrearNuevoUsuario(View v)
    {
        Intent oIntento=new Intent(getApplication(), NuevoUsuario.class);
        Usuario_BE oUsuario_BE=new Usuario_BE();
        oIntento.putExtra("TIPOTRANSACCION", Funciones.NUEVOREGISTRO);
        oIntento.putExtra("OBJUSUARIO", oUsuario_BE);
        startActivityForResult(oIntento,1);
    }

    public void BuscarUsuarios(View v)
    {
        EditText oedtNombres=(EditText)findViewById(R.id.edtNombres);
        mAuthTask = new ListarUsuarios(oedtNombres.getText().toString());
        mAuthTask.execute((Void) null);
    }

    public class ListarUsuarios extends AsyncTask<Void,Void, Boolean>
    {
        private final String _Nombres;
        private String _MensajeError;
        private String _StrResultado;
        //http://cibertec201722.somee.com/usuario/FrmUsuario.aspx?Accion=C&Nombres={0}
        private final String _URLApp="http://cibertec201722.somee.com/USUARIO/FrmUsuario.aspx?Accion=C&Nombres={0}";

        ListarUsuarios(String pNombres)
        {
            _Nombres=pNombres;
        }
        protected void onPreExecute()
        {
            oListadoUsuarios=new ArrayList<Usuario_BE>();
            oedtResultado.setText("Iniciando...");
        }
        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.
            String URLValidacion=_URLApp.replace("{0}", _Nombres);
            try {
                _StrResultado= Funciones.DescargarPagina(URLValidacion);
            }
            catch (Exception ex)
            {
                _MensajeError=ex.toString();
            }
            return true;
        }
        @Override
        protected void onPostExecute(final Boolean success) {
            if (success) {
                //finish();
                try {
                    JSONObject ObjLeerResultado= new JSONObject(_StrResultado);
                    JSONArray ObjListaUsuario= ObjLeerResultado.getJSONArray("ObjListaUsuario");
                    for (int iFila=0;iFila<ObjListaUsuario.length();iFila++ )
                    {
                        JSONObject ObjUsuario= ObjListaUsuario.getJSONObject(iFila);
                        oListadoUsuarios.add(new Usuario_BE(
                        ObjUsuario.getInt("CodigoUsuario"),
                        ObjUsuario.getString("LoginUsuario"),
                        ObjUsuario.getInt("CodigoEstadoUsuario"),
                        ObjUsuario.getInt("CodigoRol"),
                        ObjUsuario.getString("Nombres"),
                        ObjUsuario.getString("Cargo"),
                        ObjUsuario.getString("Correo"),
                        ObjUsuario.getString("ContraseniaUsuario")
                        ));
                    }
                    CargarUsuarios();
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
                oedtResultado.setText(_StrResultado);
            }
        }
        @Override
        protected void onCancelled() {
            mAuthTask = null;
        }

    }
}
