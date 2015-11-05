package engenheiro.rios.DataBases;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by filipe on 02/11/2015.
 */

public class DB_functions {

    public static void saveUser(final String nome, final String email, final String password, final String password_confirmation) throws IOException, JSONException {

        new Thread(new Runnable() {
            public void run() {
                try {
                String url = "http://riosmais.herokuapp.com/users";
                URL object = null;
                object = new URL(url);
                HttpURLConnection con = null;
                con = (HttpURLConnection) object.openConnection();
                con.setDoOutput(true);
                con.setDoInput(true);
                con.setRequestProperty("Content-Type", "application/json");
                con.setRequestMethod("POST");
                con.connect();
                JSONObject jsonObject = new JSONObject();
                JSONObject user = new JSONObject();
                    try {
                        jsonObject.accumulate("nome", nome);
                        jsonObject.accumulate("email", email);
                        jsonObject.accumulate("password", password);
                        jsonObject.accumulate("password_confirmation", password_confirmation);
                        user.accumulate("user", jsonObject);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                Log.w("teste", user.toString());

                OutputStream os = null;
                os = con.getOutputStream();
                OutputStreamWriter osw = null;
                osw = new OutputStreamWriter(os, "UTF-8");
                osw.write(user.toString());
                osw.flush();
                osw.close();
                int HttpResult = 0;
                StringBuilder sb=null;
                sb= new StringBuilder();
                HttpResult = con.getResponseCode();
                if (HttpResult == HttpURLConnection.HTTP_OK) {
                    BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"));
                    String line = null;
                    while ((line = br.readLine()) != null) {
                        sb.append(line + "\n");
                    }

                    br.close();

                    System.out.println("" + sb.toString());

                } else {
                    System.out.println(con.getResponseMessage());
                }


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }


    public static void saveForm() throws IOException, JSONException {

        new Thread(new Runnable() {
            public void run() {
                try {
                    String url = "http://riosmais.herokuapp.com/form_irrs";
                    URL object = null;
                    object = new URL(url);
                    HttpURLConnection con = null;
                    con = (HttpURLConnection) object.openConnection();
                    con.setDoOutput(true);
                    con.setDoInput(true);
                    con.setRequestProperty("Content-Type", "application/json");
                    con.setRequestMethod("POST");
                    con.connect();
                    JSONObject jsonObject = new JSONObject();
                    JSONObject user = new JSONObject();
                    try {
                        jsonObject.accumulate("tipoDeVale", 1);
                        user.accumulate("form_irr", jsonObject);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    Log.w("teste", user.toString());

                    OutputStream os = null;
                    os = con.getOutputStream();
                    OutputStreamWriter osw = null;
                    osw = new OutputStreamWriter(os, "UTF-8");
                    osw.write(user.toString());
                    osw.flush();
                    osw.close();
                    int HttpResult = 0;
                    StringBuilder sb=null;
                    sb= new StringBuilder();
                    HttpResult = con.getResponseCode();
                    if (HttpResult == HttpURLConnection.HTTP_OK) {
                        BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"));
                        String line = null;
                        while ((line = br.readLine()) != null) {
                            sb.append(line + "\n");
                        }

                        br.close();

                        System.out.println("" + sb.toString());

                    } else {
                        System.out.println(con.getResponseMessage());
                    }


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }


}



