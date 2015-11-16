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
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import engenheiro.rios.Login;

/**
 * Created by filipe on 02/11/2015.
 */

public class DB_functions {

    public static void saveUser(final String nome, final String email, final String password, final String password_confirmation) throws IOException, JSONException {

        new Thread(new Runnable() {
            public void run() {
                try {
                String url = "http://riosmais.herokuapp.com/api/v1/sign_up";
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
                    con.setRequestProperty("Authorization", "Basic " +"EXbFj1G7O/ZmZPLLhf7X3nJTLjV/8fH2ZlkcTDr26wE4KgcmOqDWgwGj3enKxztBvIMpC6wFbmerJywyD0aYHQ==");
                    con.addRequestProperty("x-csrf-token", "fetch");
                    con.setRequestMethod("POST");
                    Log.e("teste", "antes da conexao");
                    con.connect();
                    JSONObject jsonObject = new JSONObject();
                    JSONObject user = new JSONObject();
                    try {
                        jsonObject.accumulate("tipoDeVale", 1);
                        user.accumulate("form_irr", jsonObject);
                    } catch (JSONException e) {
                        Log.e("teste","nao criou o json");
                        e.printStackTrace();
                    }

                    Log.e("teste","depois");
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
                    Log.e("teste","antes do if");
                    if (HttpResult == HttpURLConnection.HTTP_OK) {
                        BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"));
                        String line = null;
                        while ((line = br.readLine()) != null) {
                            sb.append(line + "\n");
                        }

                        br.close();
                        Log.e("teste", "dentro do if");
                        System.out.println("output:" + sb.toString());

                    } else {
                        Log.e("teste","dentro do else");
                        Log.e("teste","nao deu a conection");
                        Log.e("teste",con.getResponseMessage());
                        System.out.println("erro:" + con.getResponseMessage());
                        System.out.println("erro:"+con.getErrorStream());

                        Log.e("teste", "fodeu");

                        StringBuilder builder = new StringBuilder();
                        builder.append(con.getResponseCode())
                                .append(" ")
                                .append(con.getResponseMessage())
                                .append("\n");

                        Map<String, List<String>> map = con.getHeaderFields();
                        for (Map.Entry<String, List<String>> entry : map.entrySet())
                        {
                            if (entry.getKey() == null)
                                continue;
                            builder.append( entry.getKey())
                                    .append(": ");

                            List<String> headerValues = entry.getValue();
                            Iterator<String> it = headerValues.iterator();
                            if (it.hasNext()) {
                                builder.append(it.next());

                                while (it.hasNext()) {
                                    builder.append(", ")
                                            .append(it.next());
                                }
                            }

                            builder.append("\n");
                        }
                        Log.e("teste","fodeu");
                        System.out.println(builder);

                    }


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }



    public static void login(final String email, final String password, final Login login) throws IOException, JSONException {


        new Thread(new Runnable() {
            public void run() {

                try {
                    String url = "http://riosmais.herokuapp.com/api/v1/sign_in";
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
                    try {
                        jsonObject.accumulate("user_login", email);
                        jsonObject.accumulate("password", password);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    Log.w("teste", jsonObject.toString());

                    OutputStream os = null;
                    os = con.getOutputStream();
                    OutputStreamWriter osw = null;
                    osw = new OutputStreamWriter(os, "UTF-8");
                    osw.write(jsonObject.toString());
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
                            sb.append(line).append("\n");
                        }

                        br.close();

                        final String[] authentication_token = {""};
                        final String[] error_txt = {""};
                        final Boolean[] error = {false};
                        String name="";
                        String email="";

                        /*{"id":2,"nome":"Filipe Miranda","access":null,"created_at":"2015-11-11T19:21:13.255Z",
                        "updated_at":"2015-11-11T19:21:13.255Z","email":"fil.fmiranda@gmail.com",
                        "authentication_token":"muWniRNBN-NSdF5vJdHy","distrito":null,"concelho":null,
                        "telef":null,"habilitacoes":null,"profissao":null,"formacao":null}
                         */

                        try {
                            JSONObject obj = new JSONObject(sb.toString());
                            try {
                                error_txt[0] = obj.getString("error");
                                error[0] =true;
                            } catch (JSONException ignored) {
                            }
                            try {
                                if(!error[0]) {
                                    authentication_token[0] = obj.getString("authentication_token");
                                    name=obj.getString("nome");
                                    email=obj.getString("email");
                                }
                            }
                            catch (JSONException ignored){
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.e("resposta:","a meio     error:"+ error_txt[0] +" autenticacao:"+ authentication_token[0]);
                        login.login_response(error[0],error_txt[0],authentication_token[0],name,email);




                        System.out.println("errozinho:" + sb.toString());

                    } else {
                        Log.e("teste","error: "+con.getResponseMessage());
                        System.out.println(con.getResponseMessage());
                    }


                } catch (IOException e) {
                    Log.e("teste","stacktrace");
                    e.printStackTrace();
                }
            }
        }).start();

    }




}



