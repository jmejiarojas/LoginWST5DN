package cibertec.usuariosjsonws;

import java.io.Serializable;

/**
 * Created by peextssolis on 24/06/2017.
 */

public class Usuario_BE implements Serializable {

    public enum CAMPOS {
        CodigoUsuario,
        LoginUsuario,
        CodigoEstadoUsuario,
        CodigoRol,
        Nombres,
        Cargo,
        Correo,
        ContraseniaUsuario,
    }

    private int _CodigoUsuario;
    private String _LoginUsuario;
    private int _CodigoEstadoUsuario;
    private int _CodigoRol;
    private String _Nombres;
    private String _Cargo;
    private String _Correo;
    private String _ContraseniaUsuario;

    public int getCodigoUsuario() {
        return this._CodigoUsuario;
    }

    public void setCodigoUsuario(int pint) {
        this._CodigoUsuario = pint;
    }

    public String getLoginUsuario() {
        return this._LoginUsuario;
    }

    public void setLoginUsuario(String pString) {
        this._LoginUsuario = pString;
    }

    public int getCodigoEstadoUsuario() {
        return this._CodigoEstadoUsuario;
    }

    public void setCodigoEstadoUsuario(int pint) {
        this._CodigoEstadoUsuario = pint;
    }

    public int getCodigoRol() {
        return this._CodigoRol;
    }

    public void setCodigoRol(int pint) {
        this._CodigoRol = pint;
    }

    public String getNombres() {
        return this._Nombres;
    }

    public void setNombres(String pString) {
        this._Nombres = pString;
    }

    public String getCargo() {
        return this._Cargo;
    }

    public void setCargo(String pString) {
        this._Cargo = pString;
    }

    public String getCorreo() {
        return this._Correo;
    }

    public void setCorreo(String pString) {
        this._Correo = pString;
    }

    public String getContraseniaUsuario() {
        return this._ContraseniaUsuario;
    }

    public void setContraseniaUsuario(String pString) {
        this._ContraseniaUsuario = pString;
    }

    public Usuario_BE() {
        this.LimpiarPropiedades();
    }

    public Usuario_BE(
            int PCodigoUsuario,
            String PLoginUsuario,
            int PCodigoEstadoUsuario,
            int PCodigoRol,
            String PNombres,
            String PCargo,
            String PCorreo,
            String PContraseniaUsuario) {
        this.LimpiarPropiedades();
        this._CodigoUsuario = PCodigoUsuario;
        this._LoginUsuario = PLoginUsuario;
        this._CodigoEstadoUsuario = PCodigoEstadoUsuario;
        this._CodigoRol = PCodigoRol;
        this._Nombres = PNombres;
        this._Cargo = PCargo;
        this._Correo = PCorreo;
        this._ContraseniaUsuario = PContraseniaUsuario;
    }

    public Usuario_BE(
            int PCodigoUsuario,
            String PLoginUsuario,
            String PNombres,
            String PCorreo) {
        this.LimpiarPropiedades();
        this._CodigoUsuario = PCodigoUsuario;
        this._LoginUsuario = PLoginUsuario;
        this._Nombres = PNombres;
        this._Correo = PCorreo;
    }


    public void LimpiarPropiedades() {
        this._CodigoUsuario = 0;
        this._LoginUsuario = "";
        this._CodigoEstadoUsuario = 0;
        this._CodigoRol = 0;
        this._Nombres = "";
        this._Cargo = "";
        this._Correo = "";
        this._ContraseniaUsuario = "";
    }
}


