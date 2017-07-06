package cibertec.usuariosjsonws;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static android.app.PendingIntent.getActivity;

/**
 * Created by USER on 15/11/2015.
 */
//extends Activity
public class Funciones {
private static String DEBUG_TAG="Funciones";
    public static String CONSULTAREGISTRO="C";
    public static String CONSULTAREGISTROLLAVE="K";
    public static String NUEVOREGISTRO="N";
    public static String ACTUALIZAREGISTRO="A";
    public static String ELIMINAREGISTRO="E";

    public static String VALIDAREGISTRO="V";

    public static boolean ValidarServicioRed(final Context pContexto)
    {
        Funciones ofunciones=new Funciones();
        return ofunciones.ValidarConexionRed(pContexto);
    }

    private boolean ValidarConexionRed(final Context pContexto)
    {
        Boolean BolResul=false;
        ConnectivityManager connMgr = (ConnectivityManager) pContexto.getSystemService(pContexto.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            BolResul=true;
        } else {
            BolResul=false;
        }
        return BolResul;
    }

    public static String DescargarPagina(String pUrl) throws IOException {
        InputStream is = null;
        // Only display the first 500 characters of the retrieved
        // web page content.
        int len = 50000;

        try {
            URL url = new URL(pUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            // Starts the query
            conn.connect();
            int response = conn.getResponseCode();
            Log.d(DEBUG_TAG, "La respuesta es: " + response);
            is = conn.getInputStream();

            // Convert the InputStream into a string
            String contentAsString = LeerContenido(is, len);
            return contentAsString;

            // Makes sure that the InputStream is closed after the app is
            // finished using it.
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }

    public static String LeerContenido(InputStream stream, int len) throws IOException, UnsupportedEncodingException {
        Reader reader = null;
        reader = new InputStreamReader(stream, "UTF-8");
        char[] buffer = new char[len];
        reader.read(buffer);
        return new String(buffer);
    }

    public void CargarCombo()
    {
        List<String> list = new ArrayList<String>();
        list.add("Necesita POS");
        list.add("No tienen internet");
        list.add("Noi llego el técnico");
        //ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,list);
        //dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //ocboMotivo.setAdapter(dataAdapter);
    }

    public static void showAlertDialog(final Context context,final String pTitulo, final String pMessage) {
        new AlertDialog.Builder(context)
                .setMessage(pMessage)
                .setTitle(pTitulo)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

}
