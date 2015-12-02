package ldso.rios.DataBases;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;

import ldso.rios.MainActivities.Homepage;

/**
 * Created by filipe on 26/10/2015.
 */
public class User extends _Default {

    private static User user_singleton = new User( );

    private int id;
    private String name;
    private String email;
    private String authentication_token;
    private String telef;
    private String distrito;
    private String concelho;
    private String profissao;
    private String habilitacoes;
    private Boolean formacao;

    private User(){
        super();
        resetUser();
    }

    public void resetUser() {
        this.id=-1;
        this.name="";
        this.email="";
        this.authentication_token="";
        this.telef ="";
        this.distrito = "";
        this.concelho = "";
        this.profissao = "";
        this.habilitacoes = "";
        this.formacao = false;
    }

    /* Static 'instance' method */
    public static User getInstance( ) {
        return user_singleton;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public static String getToken(Context c){
        SharedPreferences settings = c.getSharedPreferences(Homepage.PREFS_NAME, 0);
        String token=settings.getString("token", "-1");
        return token;
    }

    public String getAuthentication_token() {
        return authentication_token;
    }

    public void setAuthentication_token(String authentication_token) {
        this.authentication_token = authentication_token;
    }

    public String getTelef() {
        return telef;
    }

    public void setTelef(String telef) {
        this.telef = telef;
    }

    public String getDistrito() {
        return distrito;
    }

    public void setDistrito(String distrito) {
        this.distrito = distrito;
    }

    public String getConcelho() {
        return concelho;
    }

    public void setConcelho(String concelho) {
        this.concelho = concelho;
    }

    public String getProfissao() {
        return profissao;
    }

    public void setProfissao(String profissao) {
        this.profissao = profissao;
    }

    public String getHabilitacoes() {
        return habilitacoes;
    }

    public void setHabilitacoes(String habilitacoes) {
        this.habilitacoes = habilitacoes;
    }

    public Boolean getFormacao() {
        return formacao;
    }

    public void setFormacao(Boolean formacao) {
        this.formacao = formacao;
    }
}
