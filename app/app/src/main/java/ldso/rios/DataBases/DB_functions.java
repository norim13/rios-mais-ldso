package ldso.rios.DataBases;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import ldso.rios.Form.IRR.Form_IRR;
import ldso.rios.MainActivities.Form_IRR_mainActivity;
import ldso.rios.Form.GuardaRios_form;
import ldso.rios.Form.Form_functions;
import ldso.rios.Form.IRR.Questions;
import ldso.rios.Autenticacao.Login;
import ldso.rios.Autenticacao.Register;
import ldso.rios.Form.Sos_rios;
import ldso.rios.MainActivities.Limpeza;
import ldso.rios.MainActivities.Profile;
import ldso.rios.MainActivities.ProfileEditActivity;

/**
 * Created by filipe on 02/11/2015.
 */

public class DB_functions {

    public static void saveUser(final String nome, final String email, final String password, final String password_confirmation, final String telef, final String profissao, final String habilitacoes, final Boolean formacao, final Register register_class) throws IOException, JSONException {

        new Thread(new Runnable() {
            public void run() {
                try {
                    String url = "http://riosmais.herokuapp.com/api/v1/sign_up";
                    URL object = new URL(url);
                    HttpURLConnection con = (HttpURLConnection) object.openConnection();
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
                        jsonObject.accumulate("telef", telef);
                        jsonObject.accumulate("habilitacoes", habilitacoes);
                        jsonObject.accumulate("profissao", profissao);
                        jsonObject.accumulate("formacao", formacao.toString());
                        jsonObject.accumulate("distrito_id", "");
                        jsonObject.accumulate("concelho_id", "");

                        user.accumulate("user", jsonObject);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    Log.e("user todo: ", user.toString());

                    OutputStream os = con.getOutputStream();
                    OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");
                    osw.write(user.toString());
                    osw.flush();
                    osw.close();
                    StringBuilder sb = new StringBuilder();
                    int HttpResult = con.getResponseCode();
                    if (HttpResult == HttpURLConnection.HTTP_CREATED) {
                        BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"));
                        String line = null;
                        while ((line = br.readLine()) != null) {
                            sb.append(line + "\n");
                        }

                        Log.e("register","resposta da api:" + sb.toString());

                        final String[] error_txt = {""};
                        final Boolean[] error = {false};

                        JSONObject obj = null;
                        try {
                            obj = new JSONObject(sb.toString());
                            error_txt[0] = obj.getString("error");
                            error[0] = true;
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("JSON Exception", "exception getting response on register");
                        }

                        register_class.register_response(error[0], error_txt[0], register_class,obj);

                        br.close();

                        System.out.println(sb.toString());

                    } else {
                        Log.e("register","Resposta da api n foi OK");
                        System.out.println(con.getResponseMessage());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public static void getUserData(final Profile profile, final String email, final String token) {

        new Thread(new Runnable() {
            public void run() {

                String url = "http://riosmais.herokuapp.com/api/v2/users?user_email="+email+"&user_token="+token;

                URL obj = null;
                try {
                    obj = new URL(url);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                HttpURLConnection con = null;
                try {
                    con = (HttpURLConnection) obj.openConnection();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                // optional default is GET
                try {
                    con.setRequestMethod("GET");
                } catch (ProtocolException e) {
                    e.printStackTrace();
                }

                //add request header
                con.setRequestProperty("Content-Type", "application/json");

                int responseCode = 0;
                try {
                    responseCode = con.getResponseCode();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Log.i("user","getting user");
                Log.i("user","sending GET request to URL: " + url);
                Log.i("user","response code: " + responseCode);

                BufferedReader in = null;
                try {
                    in = new BufferedReader(
                            new InputStreamReader(con.getInputStream()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String inputLine;
                StringBuffer response = new StringBuffer();

                try {
                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                //print result
                System.out.println(response.toString());
                Log.e("teste", response.toString());

                try {
                    JSONObject user_json = new JSONObject(response.toString());

                    User user = User.getInstance();
                    user.setId(Integer.parseInt(user_json.getString("id")));
                    user.setName(user_json.getString("nome"));
                    user.setEmail(user_json.getString("email"));
                    user.setAuthentication_token(user_json.getString("authentication_token"));
                    user.setTelef(user_json.getString("telef"));

                    String distrito_id = user_json.getString("distrito_id");
                    String concelho_id = user_json.getString("concelho_id");
                    user.setDistrito(distrito_id);
                    user.setConcelho(concelho_id);

                    user.setProfissao(user_json.getString("profissao"));
                    user.setHabilitacoes(user_json.getString("habilitacoes"));
                    user.setFormacao(Boolean.valueOf(user_json.getString("formacao")));

                    Log.e("profile","a seguir ao set user todo");

                    profile.afterGetUserData();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }

    public static void saveForm(final String token, final String email, final Form_IRR form_irr) throws IOException, JSONException {

        final ArrayList<Integer[]> values_irr= Questions.getValuesIRR();
        Log.e("form","entrou");
        Log.e("form","values_irr_size:"+values_irr.size());
        String values="";
        for (int i=0;i<values_irr.size();i++){
            values=values+" |"+i+":";
            for (int j=0;j<values_irr.get(i).length;j++)
                values=values+" "+values_irr.get(i)[j]+",";
        }
        Log.e("form",values);
        form_irr.fillAnswers();

        new Thread(new Runnable() {
            public void run() {
                try {
                    String url = "http://riosmais.herokuapp.com/api/v2/form_irrs?user_email="+email+"&user_token="+token;
                    Log.e("teste",url);
                    URL object = new URL(url);
                    HttpURLConnection con = (HttpURLConnection) object.openConnection();
                    con.setDoOutput(true);
                    con.setDoInput(true);
                    con.setRequestProperty("Content-Type", "application/json");
                    con.setRequestMethod("GET");

                    con.connect();
                    JSONObject jsonObject = new JSONObject();
                    JSONObject user = new JSONObject();

                    JSONObject obj_json=new JSONObject();
                    JSONObject response=new JSONObject();
                    ArrayList<Float> af;
                    ArrayList<Integer> ai;
                    ArrayList<String> as;

                    ArrayList<Integer> arrayList_Hidrogeomorfologia = new ArrayList<Integer> ();
                    ArrayList<Integer> arrayList_QualidadeDaAgua= new ArrayList<Integer>();
                    ArrayList<Integer> arrayList_AlteracoesAntropicas = new ArrayList<Integer>();
                    ArrayList<Integer> arrayList_CorredorEcologico = new ArrayList<Integer>();
                    ArrayList<Integer> arrayList_ParticipacaoPublica = new ArrayList<Integer>();
                    ArrayList<Integer> arrayList_OrganizacaoPlaneamento = new ArrayList<Integer>();

                    try {
                        response.accumulate("idRio", "201.04");
                        response.accumulate("margem", "1");
                        response.accumulate("lat", "1");
                        response.accumulate("lon", "1");
                        response.accumulate("nomeRio", "Rio Tinto");

                        response.accumulate("tipoDeVale", (int) form_irr.getRespostas().get(1));

                        response.accumulate("perfilDeMargens", (int) form_irr.getRespostas().get(2));

                        af= (ArrayList<Float>) form_irr.getRespostas().get(3);
                        response.accumulate("larguraDaSuperficieDaAgua",af.get(0));
                        response.accumulate("profundidadeMedia",af.get(1));
                        response.accumulate("velocidadeMedia",af.get(2));
                        Log.e("formirr", response.toString());
                        Float seccao=af.get(0)*af.get(1);
                        Float caudal=seccao*af.get(2);
                        response.accumulate("seccao",seccao);
                        response.accumulate("caudal",caudal);

                        ai= (ArrayList<Integer>) form_irr.getRespostas().get(4);
                        response.accumulate("substratoDasMargens_soloArgiloso",ai.get(0));
                        response.accumulate("substratoDasMargens_arenoso",ai.get(1));
                        response.accumulate("substratoDasMargens_pedregoso",ai.get(2));
                        response.accumulate("substratoDasMargens_rochoso",ai.get(3));
                        response.accumulate("substratoDasMargens_artificialPedra",ai.get(4));
                        response.accumulate("substratoDasMargens_artificialBetao",ai.get(5));

                        ai= (ArrayList<Integer>) form_irr.getRespostas().get(5);
                        response.accumulate("substratoDoLeito_blocoseRocha",ai.get(0));
                        response.accumulate("substratoDoLeito_calhaus",ai.get(1));
                        response.accumulate("substratoDoLeito_cascalho",ai.get(2));
                        response.accumulate("substratoDoLeito_areia",ai.get(3));
                        response.accumulate("substratoDoLeito_limo",ai.get(4));
                        response.accumulate("substratoDoLeito_solo",ai.get(5));
                        response.accumulate("substratoDoLeito_artificial",ai.get(6));
                        response.accumulate("substratoDoLeito_naoEVisivel",ai.get(7));

                        response.accumulate("estadoGeraldaLinhadeAgua",(int) form_irr.getRespostas().get(6));

                        ai= (ArrayList<Integer>) form_irr.getRespostas().get(7);
                        response.accumulate("erosao_semErosao",ai.get(0));
                        response.accumulate("erosao_formacaomais3",ai.get(1));
                        response.accumulate("erosao_formacao1a3",ai.get(2));
                        response.accumulate("erosao_quedamuros",ai.get(3));
                        response.accumulate("erosao_rombos", ai.get(4));
                        arrayList_Hidrogeomorfologia.add(Form_functions.getmax(ai, values_irr.get(7)));

                        ai= (ArrayList<Integer>) form_irr.getRespostas().get(8);
                        response.accumulate("sedimentacao_ausente", ai.get(0));
                        response.accumulate("sedimentacao_decomposicao",ai.get(1));
                        response.accumulate("sedimentacao_mouchoes",ai.get(2));
                        response.accumulate("sedimentacao_ilhassemveg",ai.get(3));
                        response.accumulate("sedimentacao_ilhascomveg",ai.get(4));
                        response.accumulate("sedimentacao_deposicaosemveg",ai.get(5));
                        response.accumulate("sedimentacao_deposicaocomveg",ai.get(6));
                        response.accumulate("sedimentacao_rochas", ai.get(7));
                        arrayList_Hidrogeomorfologia.add(Form_functions.getmax(ai, values_irr.get(8)));

                        Log.e("form", "Hidrogeomorfologia" + arrayList_Hidrogeomorfologia.get(0) + "," + arrayList_Hidrogeomorfologia.get(1));

                        af= (ArrayList<Float>) form_irr.getRespostas().get(9);

                        response.accumulate("pH",Float.parseFloat(String.valueOf(af.get(0))));
                        response.accumulate("condutividade",Float.parseFloat(String.valueOf(af.get(1))));
                        response.accumulate("temperatura",Float.parseFloat(String.valueOf(af.get(2))));
                        response.accumulate("nivelDeOxigenio",Float.parseFloat(String.valueOf(af.get(3))));
                        response.accumulate("percentagemDeOxigenio",Float.parseFloat(String.valueOf(af.get(4))));
                        response.accumulate("nitratos",Float.parseFloat(String.valueOf(af.get(5))));
                        response.accumulate("nitritos",Float.parseFloat(String.valueOf(af.get(6))));
                        response.accumulate("transparencia",Float.parseFloat(String.valueOf(af.get(7))));

                        ai= (ArrayList<Integer>) form_irr.getRespostas().get(10);
                        response.accumulate("oleo",ai.get(0));
                        response.accumulate("espuma",ai.get(1));
                        response.accumulate("esgotos",ai.get(2));
                        response.accumulate("impurezas",ai.get(3));
                        response.accumulate("sacosDePlastico",ai.get(4));
                        response.accumulate("latas",ai.get(5));
                        //response.accumulate("indiciosNaAgua_outros", "");
                        Log.e("form", "a ler:" + 10);
                        arrayList_QualidadeDaAgua.add(Form_functions.getmax(ai, values_irr.get(10)));

                        response.accumulate("corDaAgua", (int) form_irr.getRespostas().get(11));
                        Log.e("form", "a ler:" + 11);
                        arrayList_QualidadeDaAgua.add(Form_functions.getmax((int) form_irr.getRespostas().get(11), values_irr.get(11)));

                        response.accumulate("odorDaAgua", (int) form_irr.getRespostas().get(12));
                        Log.e("form", "a ler:" + 12);
                        arrayList_QualidadeDaAgua.add(Form_functions.getmax((int) form_irr.getRespostas().get(12), values_irr.get(12)));

                        ArrayList<ArrayList<Integer>> arrayListArrayList= (ArrayList<ArrayList<Integer>>) form_irr.getRespostas().get(13);
                        response.accumulate("planarias",arrayListArrayList.get(0).get(0));
                        response.accumulate("hirudineos",arrayListArrayList.get(1).get(0) );
                        response.accumulate("oligoquetas",arrayListArrayList.get(2).get(0) );
                        response.accumulate("simulideos",arrayListArrayList.get(2).get(1) );
                        response.accumulate("quironomideos",arrayListArrayList.get(2).get(2) );
                        response.accumulate("ancilideo",arrayListArrayList.get(3).get(0) );
                        response.accumulate("limnideo",arrayListArrayList.get(3).get(1) );
                        response.accumulate("bivalves",arrayListArrayList.get(3).get(2) );
                        response.accumulate("patasNadadoras",arrayListArrayList.get(4).get(0) );
                        response.accumulate("pataLocomotoras",arrayListArrayList.get(4).get(1) );
                        response.accumulate("trichopteroS",arrayListArrayList.get(5).get(0) );
                        response.accumulate("trichopteroC",arrayListArrayList.get(5).get(1) );
                        response.accumulate("odonata",arrayListArrayList.get(6).get(0) );
                        response.accumulate("heteropteros", arrayListArrayList.get(7).get(0) );
                        response.accumulate("plecopteros",arrayListArrayList.get(8).get(0) );
                        response.accumulate("baetideo",arrayListArrayList.get(9).get(0) );
                        response.accumulate("cabecaPlanar",arrayListArrayList.get(9).get(1) );
                        response.accumulate("crustaceos",arrayListArrayList.get(10).get(0));
                        response.accumulate("acaros",arrayListArrayList.get(11).get(0) );
                        response.accumulate("pulgaDeAgua",arrayListArrayList.get(12).get(0) );
                        response.accumulate("insetos",arrayListArrayList.get(13).get(0) );
                        response.accumulate("megalopteres", arrayListArrayList.get(14).get(0));
                        Log.e("form", "a ler:" + 13);
                        arrayList_QualidadeDaAgua.add(Form_functions.getmax2(arrayListArrayList, values_irr.get(13)));

                        Log.e("form", "QualidadeDaAgua" + arrayList_QualidadeDaAgua.get(0)
                                + "," + arrayList_QualidadeDaAgua.get(1)
                                + "," + arrayList_QualidadeDaAgua.get(2)
                                + "," + arrayList_QualidadeDaAgua.get(3));

                        ai= (ArrayList<Integer>) form_irr.getRespostas().get(14);
                        response.accumulate("intervencoes_edificios",ai.get(0));
                        response.accumulate("intervencoes_pontes",ai.get(1));
                        response.accumulate("intervencoes_limpezasDasMargens",ai.get(2));
                        response.accumulate("intervencoes_estabilizacaoDeMargens",ai.get(3));
                        response.accumulate("intervencoes_modelacaoDeMargensNatural",ai.get(4));
                        response.accumulate("intervencoes_modelacaoDeMargensArtificial",ai.get(5));
                        response.accumulate("intervencoes_barragem",ai.get(6));
                        response.accumulate("intervencoes_diques",ai.get(7));
                        response.accumulate("intervencoes_rioCanalizado",ai.get(8));
                        response.accumulate("intervencoes_rioEntubado",ai.get(9));
                        response.accumulate("intervencoes_esporoes", ai.get(10));
                        response.accumulate("intervencoes_paredoes",ai.get(11));
                        response.accumulate("intervencoes_tecnicasDeEngenhariaNatural",ai.get(12));

                        arrayList_AlteracoesAntropicas.add(Form_functions.getmax(ai, values_irr.get(14)));

                        response.accumulate("intervencoes_outras", "");

                        ai= (ArrayList<Integer>) form_irr.getRespostas().get(15);
                        response.accumulate("ocupacao_florestaNatural",ai.get(0));
                        response.accumulate("ocupacao_florestaPlantadas",ai.get(1));
                        response.accumulate("ocupacao_matoAlto",ai.get(2));
                        response.accumulate("ocupacao_matoRasteiro",ai.get(3));
                        response.accumulate("ocupacao_pastagem",ai.get(4));
                        response.accumulate("ocupacao_agricultura",ai.get(5));
                        response.accumulate("ocupacao_espacoAbandonado",ai.get(6));
                        response.accumulate("ocupacao_jardins",ai.get(7));
                        response.accumulate("ocupacao_zonaEdificada",ai.get(8));
                        response.accumulate("ocupacao_zonaIndustrial",ai.get(9));
                        response.accumulate("ocupacao_ruas",ai.get(10));
                        response.accumulate("ocupacao_entulho", ai.get(11));
                        arrayList_AlteracoesAntropicas.add(Form_functions.getmax(ai, values_irr.get(15)));

                        ai= (ArrayList<Integer>) form_irr.getRespostas().get(16);
                        response.accumulate("patrimonio_moinho",ai.get(0));
                        response.accumulate("patrimonio_acude",ai.get(1));
                        response.accumulate("patrimonio_microAcude1",ai.get(2));
                        response.accumulate("patrimonio_microAcude2",ai.get(3));
                        response.accumulate("patrimonio_barragem",ai.get(4));
                        response.accumulate("patrimonio_levadas",ai.get(5));
                        response.accumulate("patrimonio_pesqueiras",ai.get(6));
                        response.accumulate("patrimonio_escadasDePeixe",ai.get(7));
                        response.accumulate("patrimonio_poldras",ai.get(8));
                        response.accumulate("patrimonio_pontesSemPilar",ai.get(9));
                        response.accumulate("patrimonio_pontesComPilar",ai.get(10));
                        response.accumulate("patrimonio_passagemAVau",ai.get(11));
                        response.accumulate("patrimonio_barcos",ai.get(12));
                        response.accumulate("patrimonio_cais",ai.get(13));
                        response.accumulate("patrimonio_igreja",ai.get(14));
                        response.accumulate("patrimonio_solares",ai.get(15));
                        response.accumulate("patrimonio_nucleoHabitacional",ai.get(16));
                        response.accumulate("patrimonio_edificiosParticulares",ai.get(17));
                        response.accumulate("patrimonio_edificiosPublicos",ai.get(18));
                        response.accumulate("patrimonio_ETA",ai.get(19));
                        response.accumulate("patrimonio_descarregadoresDeAguasPluviais",ai.get(20));
                        response.accumulate("patrimonio_coletoresSaneamento",ai.get(21));
                        response.accumulate("patrimonio_defletoresArtificiais", ai.get(22));
                        response.accumulate("patrimonio_motaLateral", ai.get(23));
                        arrayList_AlteracoesAntropicas.add(Form_functions.getmax(ai, values_irr.get(16)));

                        ai= (ArrayList<Integer>) form_irr.getRespostas().get(17);
                        response.accumulate("poluicao_descargasDomesticas",ai.get(0));
                        response.accumulate("poluicao_descargasETAR",ai.get(1));
                        response.accumulate("poluicao_descargasIndustriais",ai.get(2));
                        response.accumulate("poluicao_descargasQuimicas",ai.get(3));
                        response.accumulate("poluicao_descargasAguasPluviais",ai.get(4));
                        response.accumulate("poluicao_presencaCriacaoAnimais",ai.get(5));
                        response.accumulate("poluicao_lixeiras",ai.get(6));
                        response.accumulate("poluicao_lixoDomestico",ai.get(7));
                        response.accumulate("poluicao_entulho",ai.get(8));
                        response.accumulate("poluicao_monstrosDomesticos",ai.get(9));
                        response.accumulate("poluicao_sacosDePlastico",ai.get(10));
                        response.accumulate("poluicao_latasMaterialFerroso", ai.get(11));
                        response.accumulate("poluicao_queimadas", ai.get(12));
                        arrayList_AlteracoesAntropicas.add(Form_functions.getmax(ai, values_irr.get(17)));

                        String st="";
                        for (int i=0;i<arrayList_AlteracoesAntropicas.size();i++){
                            st+=arrayList_AlteracoesAntropicas.get(i)+" , ";
                        }
                        Log.e("form","_AlteraçõesAntropicas: "+st);

                        ai= (ArrayList<Integer>) form_irr.getRespostas().get(18);
                        response.accumulate("salamandraLusitanica",ai.get(0));
                        response.accumulate("salamandraPintasAmarelas",ai.get(1));
                        response.accumulate("tritaoVentreLaranja", ai.get(2));
                        response.accumulate("raIberica", ai.get(3));
                        response.accumulate("raVerde", ai.get(4));
                        response.accumulate("sapoComum", ai.get(5));
                        arrayList_CorredorEcologico.add(Form_functions.getmax(ai, values_irr.get(18)));


                        ai= (ArrayList<Integer>) form_irr.getRespostas().get(19);
                        response.accumulate("lagartoDeAgua",ai.get(0));
                        response.accumulate("cobraAguaDeColar", ai.get(1));
                        response.accumulate("cagado", ai.get(2));
                        arrayList_CorredorEcologico.add(Form_functions.getmax(ai, values_irr.get(19)));

                        response.accumulate("repteis_outro", "");

                        ai= (ArrayList<Integer>) form_irr.getRespostas().get(20);
                        response.accumulate("guardaRios",ai.get(0));
                        response.accumulate("garcaReal",ai.get(1));
                        response.accumulate("melroDeAgua",ai.get(2));
                        response.accumulate("galinhaDeAgua", ai.get(3));
                        response.accumulate("patoReal", ai.get(4));
                        response.accumulate("tentilhaoComum", ai.get(5));
                        response.accumulate("chapimReal", ai.get(6));
                        arrayList_CorredorEcologico.add(Form_functions.getmax(ai, values_irr.get(20)));

                        response.accumulate("aves_outro", "");

                        ai= (ArrayList<Integer>) form_irr.getRespostas().get(21);
                        response.accumulate("lontras",ai.get(0));
                        response.accumulate("morcegosDeAgua",ai.get(1));
                        response.accumulate("toupeiraDaAgua", ai.get(2));
                        response.accumulate("ratoDeAgua", ai.get(3));
                        response.accumulate("ouricoCacheiro", ai.get(4));
                        response.accumulate("armilho", ai.get(5));
                        arrayList_CorredorEcologico.add(Form_functions.getmax(ai, values_irr.get(21)));

                        response.accumulate("mamiferos_outro", "");

                        ai= (ArrayList<Integer>) form_irr.getRespostas().get(22);
                        response.accumulate("enguia",ai.get(0));
                        response.accumulate("lampreia",ai.get(1));
                        response.accumulate("salmao", ai.get(2));
                        response.accumulate("truta", ai.get(3));
                        response.accumulate("bogaPortuguesa", ai.get(4));
                        response.accumulate("bogaDoNorte", ai.get(5));
                        arrayList_CorredorEcologico.add(Form_functions.getmax(ai, values_irr.get(22)));

                        response.accumulate("peixes_outro", "");

                        ai= (ArrayList<Integer>) form_irr.getRespostas().get(23);
                        response.accumulate("percaSol",ai.get(0));
                        response.accumulate("tartarugaDaFlorida",ai.get(1));
                        response.accumulate("caranguejoPeludoChines",ai.get(2));
                        response.accumulate("gambusia",ai.get(3));
                        response.accumulate("mustelaVison", ai.get(4));
                        response.accumulate("lagostimVermelho", ai.get(5));
                        response.accumulate("trutaArcoIris", ai.get(6));
                        response.accumulate("achiga", ai.get(7));
                        arrayList_CorredorEcologico.add(Form_functions.getmax(ai, values_irr.get(23)));

                        response.accumulate("fauna_outro", "");

                        ai= (ArrayList<Integer>) form_irr.getRespostas().get(24);
                        response.accumulate("salgueiral",ai.get(0));
                        response.accumulate("amial",ai.get(1));
                        response.accumulate("freixal",ai.get(2));
                        response.accumulate("choupal",ai.get(3));
                        response.accumulate("ulmeiral",ai.get(4));
                        response.accumulate("sanguinos",ai.get(5));
                        response.accumulate("ladual",ai.get(6));
                        response.accumulate("tramazeiras", ai.get(7));
                        response.accumulate("carvalhal", ai.get(8));
                        response.accumulate("sobreiral", ai.get(9));
                        response.accumulate("azinhal", ai.get(10));
                        arrayList_CorredorEcologico.add(Form_functions.getmax(ai, values_irr.get(24)));
                        response.accumulate("flora_outro", "");

                        response.accumulate("conservacaoBosqueRibeirinho",(int) form_irr.getRespostas().get(25));

                        ai= (ArrayList<Integer>) form_irr.getRespostas().get(26);
                        response.accumulate("silvas",ai.get(0));
                        response.accumulate("ervaDaFortuna",ai.get(1));
                        response.accumulate("plumas", ai.get(2));
                        response.accumulate("lentilhaDaAgua", ai.get(3));
                        response.accumulate("pinheirinha", ai.get(4));
                        response.accumulate("jacintoDeAgua", ai.get(5));
                        arrayList_CorredorEcologico.add(Form_functions.getmax(ai, values_irr.get(26)));
                        response.accumulate("vegetacaoInvasora_outro", "");

                        response.accumulate("obstrucaoDoLeitoMargens", (int) form_irr.getRespostas().get(27));
                        arrayList_CorredorEcologico.add(Form_functions.getmax((int) form_irr.getRespostas().get(27), values_irr.get(27)));

                        response.accumulate("disponibilizacaoDeInformacao", (int) form_irr.getRespostas().get(28));
                        arrayList_ParticipacaoPublica.add(Form_functions.getmax((int) form_irr.getRespostas().get(28), values_irr.get(28)));
                        response.accumulate("envolvimentoPublico", (int) form_irr.getRespostas().get(29));
                        arrayList_ParticipacaoPublica.add(Form_functions.getmax((int) form_irr.getRespostas().get(29), values_irr.get(29)));
                        response.accumulate("acao", (int) form_irr.getRespostas().get(30));
                        arrayList_ParticipacaoPublica.add(Form_functions.getmax((int) form_irr.getRespostas().get(30), values_irr.get(30)));

                        response.accumulate("legislacao", (int) form_irr.getRespostas().get(31));
                        arrayList_OrganizacaoPlaneamento.add(Form_functions.getmax((int) form_irr.getRespostas().get(31), values_irr.get(31)));
                        response.accumulate("estrategia", (int) form_irr.getRespostas().get(32));
                        arrayList_OrganizacaoPlaneamento.add(Form_functions.getmax((int) form_irr.getRespostas().get(32), values_irr.get(32)));
                        response.accumulate("gestaoDasIntervencoes", (int) form_irr.getRespostas().get(33));
                        arrayList_OrganizacaoPlaneamento.add(Form_functions.getmax((int) form_irr.getRespostas().get(33), values_irr.get(33)));

                        response.accumulate("irr_hidrogeomorfologia", Collections.max(arrayList_Hidrogeomorfologia));
                        response.accumulate("irr_qualidadedaagua",Collections.max(arrayList_QualidadeDaAgua));
                        response.accumulate("irr_alteracoesantropicas",Collections.max(arrayList_AlteracoesAntropicas));
                        response.accumulate("irr_corredorecologico",Collections.max(arrayList_CorredorEcologico));
                        response.accumulate("irr_participacaopublica", Collections.max(arrayList_ParticipacaoPublica));
                        response.accumulate("irr_organizacaoeplaneamento", Collections.max(arrayList_OrganizacaoPlaneamento));
                        ArrayList<Integer> array_final= new ArrayList<Integer>();
                        array_final.add(Collections.max(arrayList_Hidrogeomorfologia));
                        array_final.add(Collections.max(arrayList_QualidadeDaAgua));
                        array_final.add(Collections.max(arrayList_AlteracoesAntropicas));
                        array_final.add(Collections.max(arrayList_CorredorEcologico));
                        array_final.add(Collections.max(arrayList_ParticipacaoPublica));
                        array_final.add(Collections.max(arrayList_OrganizacaoPlaneamento));
                        response.accumulate("irr",Collections.max(array_final));

                        obj_json.accumulate("form_irr",response);
                    } catch (JSONException e) {
                        Log.e("teste","nao criou o json");
                        e.printStackTrace();
                    }

                    String veryLongString=obj_json.toString();
                    int maxLogSize = 1000;
                    for(int i = 0; i <= veryLongString.length() / maxLogSize; i++) {
                        int start = i * maxLogSize;
                        int end = (i+1) * maxLogSize;
                        end = end > veryLongString.length() ? veryLongString.length() : end;
                        Log.e("teste", veryLongString.substring(start, end));
                    }

                    OutputStream os = null;
                    os = con.getOutputStream();
                    OutputStreamWriter osw = null;
                    osw = new OutputStreamWriter(os, "UTF-8");
                    osw.write(obj_json.toString());
                    osw.flush();
                    osw.close();
                    int HttpResult = 0;
                    StringBuilder sb=null;
                    sb= new StringBuilder();
                    HttpResult = con.getResponseCode();
                    Log.e("teste","antes de ver se esta ok: "+HttpResult);
                    if (HttpResult == HttpURLConnection.HTTP_OK) {
                        BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"));
                        String line = null;
                        while ((line = br.readLine()) != null) {
                            sb.append(line + "\n");
                        }

                        br.close();
                        String erro_sb="";

                        try {
                            JSONObject obj = new JSONObject(sb.toString());
                            try {
                                erro_sb = obj.getString("error");
                            } catch (JSONException ignored) {
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.e("teste", "erro_sb:" + erro_sb);

                    } else {
                        Log.e("teste","dentro do else");
                        Log.e("teste","nao deu a conection");
                        Log.e("teste", con.getResponseMessage());
                        System.out.println("erro:" + con.getResponseMessage());
                        System.out.println("erro:"+con.getErrorStream());

                        BufferedReader br = new BufferedReader(new InputStreamReader(con.getErrorStream(), "utf-8"));
                        String line = null;
                        while ((line = br.readLine()) != null) {
                            sb.append(line + "\n");
                        }
                        br.close();
                        Log.e("teste","sb:"+sb.toString());

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

    public static void update(final String token, final String email, final Form_IRR form_irr) throws IOException, JSONException {

        final ArrayList<Integer[]> values_irr= Questions.getValuesIRR();
        Log.e("form","entrou");
        Log.e("form","values_irr_size:"+values_irr.size());
        String values="";
        for (int i=0;i<values_irr.size();i++){
            values=values+" |"+i+":";
            for (int j=0;j<values_irr.get(i).length;j++)
                values=values+" "+values_irr.get(i)[j]+",";

        }
        Log.e("form", values);

        new Thread(new Runnable() {
            public void run() {
                try {
                    String url = "http://riosmais.herokuapp.com/api/v2/form_irrs/4?user_email="+email+"&user_token="+token;
                    Log.e("teste",url);
                    URL object = null;
                    object = new URL(url);
                    HttpURLConnection con = null;
                    con = (HttpURLConnection) object.openConnection();
                    con.setDoOutput(true);
                    con.setDoInput(true);
                    con.setRequestProperty("Content-Type", "application/json");
                    con.setRequestMethod("PATCH");

                    con.connect();
                    JSONObject jsonObject = new JSONObject();
                    JSONObject user = new JSONObject();

                    JSONObject obj_json=new JSONObject();
                    JSONObject response=new JSONObject();
                    ArrayList<Float> af;
                    ArrayList<Integer> ai;
                    ArrayList<String> as;

                    ArrayList<Integer> arrayList_Hidrogeomorfologia = new ArrayList<Integer> ();
                    ArrayList<Integer> arrayList_QualidadeDaAgua= new ArrayList<Integer>();
                    ArrayList<Integer> arrayList_AlteracoesAntropicas = new ArrayList<Integer>();
                    ArrayList<Integer> arrayList_CorredorEcologico = new ArrayList<Integer>();
                    ArrayList<Integer> arrayList_ParticipacaoPublica = new ArrayList<Integer>();
                    ArrayList<Integer> arrayList_OrganizacaoPlaneamento = new ArrayList<Integer>();

                    try {
                        response.accumulate("idRio", "201.04");
                        response.accumulate("margem", "1");
                        response.accumulate("lat", "1");
                        response.accumulate("lon", "1");
                        response.accumulate("nomeRio", "Rio Tinto");

                        response.accumulate("tipoDeVale", (int) form_irr.getRespostas().get(1));

                        response.accumulate("perfilDeMargens", (int) form_irr.getRespostas().get(2));

                        af= (ArrayList<Float>) form_irr.getRespostas().get(3);
                        response.accumulate("larguraDaSuperficieDaAgua",af.get(0));
                        response.accumulate("profundidadeMedia",af.get(1));
                        response.accumulate("velocidadeMedia", af.get(2));
                        Log.e("formirr", response.toString());
                        Log.e("formirr", af.get(0) + "-" + af.get(1));
                        Float largura=Float.parseFloat(String.valueOf(af.get(0)));
                        Float profundidade=Float.parseFloat(String.valueOf(af.get(1)));
                        Float seccao=largura*profundidade;
                        Float caudal=seccao*Float.parseFloat(String.valueOf(af.get(2)));
                        response.accumulate("seccao",seccao);
                        response.accumulate("caudal",caudal);

                        ai= (ArrayList<Integer>) form_irr.getRespostas().get(4);
                        response.accumulate("substratoDasMargens_soloArgiloso",ai.get(0));
                        response.accumulate("substratoDasMargens_arenoso",ai.get(1));
                        response.accumulate("substratoDasMargens_pedregoso",ai.get(2));
                        response.accumulate("substratoDasMargens_rochoso",ai.get(3));
                        response.accumulate("substratoDasMargens_artificialPedra",ai.get(4));
                        response.accumulate("substratoDasMargens_artificialBetao",ai.get(5));

                        ai= (ArrayList<Integer>) form_irr.getRespostas().get(5);
                        response.accumulate("substratoDoLeito_blocoseRocha",ai.get(0));
                        response.accumulate("substratoDoLeito_calhaus",ai.get(1));
                        response.accumulate("substratoDoLeito_cascalho",ai.get(2));
                        response.accumulate("substratoDoLeito_areia",ai.get(3));
                        response.accumulate("substratoDoLeito_limo",ai.get(4));
                        response.accumulate("substratoDoLeito_solo",ai.get(5));
                        response.accumulate("substratoDoLeito_artificial",ai.get(6));
                        response.accumulate("substratoDoLeito_naoEVisivel",ai.get(7));

                        response.accumulate("estadoGeraldaLinhadeAgua",(int) form_irr.getRespostas().get(6));

                        ai= (ArrayList<Integer>) form_irr.getRespostas().get(7);
                        response.accumulate("erosao_semErosao",ai.get(0));
                        response.accumulate("erosao_formacaomais3",ai.get(1));
                        response.accumulate("erosao_formacao1a3",ai.get(2));
                        response.accumulate("erosao_quedamuros",ai.get(3));
                        response.accumulate("erosao_rombos", ai.get(4));
                        arrayList_Hidrogeomorfologia.add(Form_functions.getmax(ai, values_irr.get(7)));

                        ai= (ArrayList<Integer>) form_irr.getRespostas().get(8);
                        response.accumulate("sedimentacao_ausente", ai.get(0));
                        response.accumulate("sedimentacao_decomposicao",ai.get(1));
                        response.accumulate("sedimentacao_mouchoes",ai.get(2));
                        response.accumulate("sedimentacao_ilhassemveg",ai.get(3));
                        response.accumulate("sedimentacao_ilhascomveg",ai.get(4));
                        response.accumulate("sedimentacao_deposicaosemveg",ai.get(5));
                        response.accumulate("sedimentacao_deposicaocomveg",ai.get(6));
                        response.accumulate("sedimentacao_rochas", ai.get(7));
                        arrayList_Hidrogeomorfologia.add(Form_functions.getmax(ai, values_irr.get(8)));

                        Log.e("form", "Hidrogeomorfologia" + arrayList_Hidrogeomorfologia.get(0) + "," + arrayList_Hidrogeomorfologia.get(1));

                        af= (ArrayList<Float>) form_irr.getRespostas().get(9);

                        response.accumulate("pH",Float.parseFloat(String.valueOf(af.get(0))));
                        response.accumulate("condutividade",Float.parseFloat(String.valueOf(af.get(1))));
                        response.accumulate("temperatura",Float.parseFloat(String.valueOf(af.get(2))));
                        response.accumulate("nivelDeOxigenio",Float.parseFloat(String.valueOf(af.get(3))));
                        response.accumulate("percentagemDeOxigenio",Float.parseFloat(String.valueOf(af.get(4))));
                        response.accumulate("nitratos",Float.parseFloat(String.valueOf(af.get(5))));
                        response.accumulate("nitritos",Float.parseFloat(String.valueOf(af.get(6))));
                        response.accumulate("transparencia",Float.parseFloat(String.valueOf(af.get(7))));

                        ai= (ArrayList<Integer>) form_irr.getRespostas().get(10);
                        response.accumulate("oleo",ai.get(0));
                        response.accumulate("espuma",ai.get(1));
                        response.accumulate("esgotos",ai.get(2));
                        response.accumulate("impurezas",ai.get(3));
                        response.accumulate("sacosDePlastico",ai.get(4));
                        //response.accumulate("latas",ai.get(5));
                        response.accumulate("indiciosNaAgua_outros", "");
                        Log.e("form", "a ler:" + 10);
                        arrayList_QualidadeDaAgua.add(Form_functions.getmax(ai, values_irr.get(10)));

                        response.accumulate("corDaAgua", (int) form_irr.getRespostas().get(11));
                        Log.e("form", "a ler:" + 11);
                        arrayList_QualidadeDaAgua.add(Form_functions.getmax((int) form_irr.getRespostas().get(11), values_irr.get(11)));

                        response.accumulate("odorDaAgua", (int) form_irr.getRespostas().get(12));
                        Log.e("form", "a ler:" + 12);
                        arrayList_QualidadeDaAgua.add(Form_functions.getmax((int) form_irr.getRespostas().get(12), values_irr.get(12)));

                        ArrayList<ArrayList<Integer>> arrayListArrayList= (ArrayList<ArrayList<Integer>>) form_irr.getRespostas().get(13);
                        response.accumulate("planarias",arrayListArrayList.get(0).get(0));
                        response.accumulate("hirudineos",arrayListArrayList.get(1).get(0) );
                        response.accumulate("oligoquetas",arrayListArrayList.get(2).get(0) );
                        response.accumulate("simulideos",arrayListArrayList.get(2).get(1) );
                        response.accumulate("quironomideos",arrayListArrayList.get(2).get(2) );
                        response.accumulate("ancilideo",arrayListArrayList.get(3).get(0) );
                        response.accumulate("limnideo",arrayListArrayList.get(3).get(1) );
                        response.accumulate("bivalves",arrayListArrayList.get(3).get(2) );
                        response.accumulate("patasNadadoras",arrayListArrayList.get(4).get(0) );
                        response.accumulate("pataLocomotoras",arrayListArrayList.get(4).get(1) );
                        response.accumulate("trichopteroS",arrayListArrayList.get(5).get(0) );
                        response.accumulate("trichopteroC",arrayListArrayList.get(5).get(1) );
                        response.accumulate("odonata",arrayListArrayList.get(6).get(0) );
                        response.accumulate("heteropteros", arrayListArrayList.get(7).get(0) );
                        response.accumulate("plecopteros",arrayListArrayList.get(8).get(0) );
                        response.accumulate("baetideo",arrayListArrayList.get(9).get(0) );
                        response.accumulate("cabecaPlanar",arrayListArrayList.get(9).get(1) );
                        response.accumulate("crustaceos",arrayListArrayList.get(10).get(0));
                        response.accumulate("acaros",arrayListArrayList.get(11).get(0) );
                        response.accumulate("pulgaDeAgua",arrayListArrayList.get(12).get(0) );
                        response.accumulate("insetos",arrayListArrayList.get(13).get(0) );
                        response.accumulate("megalopteres", arrayListArrayList.get(14).get(0));
                        Log.e("form", "a ler:" + 13);
                        arrayList_QualidadeDaAgua.add(Form_functions.getmax2(arrayListArrayList, values_irr.get(13)));

                        Log.e("form", "QualidadeDaAgua" + arrayList_QualidadeDaAgua.get(0)
                                + "," + arrayList_QualidadeDaAgua.get(1)
                                + "," + arrayList_QualidadeDaAgua.get(2)
                                + "," + arrayList_QualidadeDaAgua.get(3));

                        ai= (ArrayList<Integer>) form_irr.getRespostas().get(14);
                        response.accumulate("intervencoes_edificios",ai.get(0));
                        response.accumulate("intervencoes_pontes",ai.get(1));
                        response.accumulate("intervencoes_limpezasDasMargens",ai.get(2));
                        response.accumulate("intervencoes_estabilizacaoDeMargens",ai.get(3));
                        response.accumulate("intervencoes_modelacaoDeMargensNatural",ai.get(4));
                        response.accumulate("intervencoes_modelacaoDeMargensArtificial",ai.get(5));
                        response.accumulate("intervencoes_barragem",ai.get(6));
                        response.accumulate("intervencoes_diques",ai.get(7));
                        response.accumulate("intervencoes_rioCanalizado",ai.get(8));
                        response.accumulate("intervencoes_rioEntubado",ai.get(9));
                        response.accumulate("intervencoes_esporoes",ai.get(10));
                        response.accumulate("intervencoes_paredoes",ai.get(11));
                        response.accumulate("intervencoes_tecnicasDeEngenhariaNatural",ai.get(12));
                        arrayList_AlteracoesAntropicas.add(Form_functions.getmax(ai, values_irr.get(14)));

                        response.accumulate("intervencoes_outras", "");

                        ai= (ArrayList<Integer>) form_irr.getRespostas().get(15);
                        response.accumulate("ocupacao_florestaNatural",ai.get(0));
                        response.accumulate("ocupacao_florestaPlantadas",ai.get(1));
                        response.accumulate("ocupacao_matoAlto",ai.get(2));
                        response.accumulate("ocupacao_matoRasteiro",ai.get(3));
                        response.accumulate("ocupacao_pastagem",ai.get(4));
                        response.accumulate("ocupacao_agricultura",ai.get(5));
                        response.accumulate("ocupacao_espacoAbandonado",ai.get(6));
                        response.accumulate("ocupacao_jardins",ai.get(7));
                        response.accumulate("ocupacao_zonaEdificada",ai.get(8));
                        response.accumulate("ocupacao_zonaIndustrial",ai.get(9));
                        response.accumulate("ocupacao_ruas",ai.get(10));
                        response.accumulate("ocupacao_entulho", ai.get(11));
                        arrayList_AlteracoesAntropicas.add(Form_functions.getmax(ai, values_irr.get(15)));

                        ai= (ArrayList<Integer>) form_irr.getRespostas().get(16);
                        response.accumulate("patrimonio_moinho",ai.get(0));
                        response.accumulate("patrimonio_acude",ai.get(1));
                        response.accumulate("patrimonio_microAcude1",ai.get(2));
                        response.accumulate("patrimonio_microAcude2",ai.get(3));
                        response.accumulate("patrimonio_barragem",ai.get(4));
                        response.accumulate("patrimonio_levadas",ai.get(5));
                        response.accumulate("patrimonio_pesqueiras",ai.get(6));
                        response.accumulate("patrimonio_escadasDePeixe",ai.get(7));
                        response.accumulate("patrimonio_poldras",ai.get(8));
                        response.accumulate("patrimonio_pontesSemPilar",ai.get(9));
                        response.accumulate("patrimonio_pontesComPilar",ai.get(10));
                        response.accumulate("patrimonio_passagemAVau",ai.get(11));
                        response.accumulate("patrimonio_barcos",ai.get(12));
                        response.accumulate("patrimonio_cais",ai.get(13));
                        response.accumulate("patrimonio_igreja",ai.get(14));
                        response.accumulate("patrimonio_solares",ai.get(15));
                        response.accumulate("patrimonio_nucleoHabitacional",ai.get(16));
                        response.accumulate("patrimonio_edificiosParticulares",ai.get(17));
                        response.accumulate("patrimonio_edificiosPublicos",ai.get(18));
                        response.accumulate("patrimonio_ETA",ai.get(19));
                        response.accumulate("patrimonio_descarregadoresDeAguasPluviais",ai.get(20));
                        response.accumulate("patrimonio_coletoresSaneamento",ai.get(21));
                        response.accumulate("patrimonio_defletoresArtificiais", ai.get(22));
                        response.accumulate("patrimonio_motaLateral", ai.get(23));
                        arrayList_AlteracoesAntropicas.add(Form_functions.getmax(ai, values_irr.get(16)));

                        ai= (ArrayList<Integer>) form_irr.getRespostas().get(17);
                        response.accumulate("poluicao_descargasDomesticas",ai.get(0));
                        response.accumulate("poluicao_descargasETAR",ai.get(1));
                        response.accumulate("poluicao_descargasIndustriais",ai.get(2));
                        response.accumulate("poluicao_descargasQuimicas",ai.get(3));
                        response.accumulate("poluicao_descargasAguasPluviais",ai.get(4));
                        response.accumulate("poluicao_presencaCriacaoAnimais",ai.get(5));
                        response.accumulate("poluicao_lixeiras",ai.get(6));
                        response.accumulate("poluicao_lixoDomestico",ai.get(7));
                        response.accumulate("poluicao_entulho",ai.get(8));
                        response.accumulate("poluicao_monstrosDomesticos",ai.get(9));
                        response.accumulate("poluicao_sacosDePlastico",ai.get(10));
                        response.accumulate("poluicao_latasMaterialFerroso", ai.get(11));
                        response.accumulate("poluicao_queimadas", ai.get(12));
                        arrayList_AlteracoesAntropicas.add(Form_functions.getmax(ai, values_irr.get(17)));

                        String st="";
                        for (int i=0;i<arrayList_AlteracoesAntropicas.size();i++){
                            st+=arrayList_AlteracoesAntropicas.get(i)+" , ";
                        }
                        Log.e("form","_AlteraçõesAntropicas: "+st);

                        ai= (ArrayList<Integer>) form_irr.getRespostas().get(18);
                        response.accumulate("salamandraLusitanica",ai.get(0));
                        response.accumulate("salamandraPintasAmarelas",ai.get(1));
                        response.accumulate("tritaoVentreLaranja", ai.get(2));
                        response.accumulate("raIberica", ai.get(3));
                        response.accumulate("raVerde", ai.get(4));
                        response.accumulate("sapoComum", ai.get(5));
                        arrayList_CorredorEcologico.add(Form_functions.getmax(ai, values_irr.get(18)));

                        ai= (ArrayList<Integer>) form_irr.getRespostas().get(19);
                        response.accumulate("lagartoDeAgua",ai.get(0));
                        response.accumulate("cobraAguaDeColar", ai.get(1));
                        response.accumulate("cagado", ai.get(2));
                        arrayList_CorredorEcologico.add(Form_functions.getmax(ai, values_irr.get(19)));

                        response.accumulate("repteis_outro", "");

                        ai= (ArrayList<Integer>) form_irr.getRespostas().get(20);
                        response.accumulate("guardaRios",ai.get(0));
                        response.accumulate("garcaReal",ai.get(1));
                        response.accumulate("melroDeAgua",ai.get(2));
                        response.accumulate("galinhaDeAgua", ai.get(3));
                        response.accumulate("patoReal", ai.get(4));
                        response.accumulate("tentilhaoComum", ai.get(5));
                        response.accumulate("chapimReal", ai.get(6));
                        arrayList_CorredorEcologico.add(Form_functions.getmax(ai, values_irr.get(20)));

                        response.accumulate("aves_outro", "");

                        ai= (ArrayList<Integer>) form_irr.getRespostas().get(21);
                        response.accumulate("lontras",ai.get(0));
                        response.accumulate("morcegosDeAgua",ai.get(1));
                        response.accumulate("toupeiraDaAgua", ai.get(2));
                        response.accumulate("ratoDeAgua", ai.get(3));
                        response.accumulate("ouricoCacheiro", ai.get(4));
                        response.accumulate("armilho", ai.get(5));
                        arrayList_CorredorEcologico.add(Form_functions.getmax(ai, values_irr.get(21)));

                        response.accumulate("mamiferos_outro", "");

                        ai= (ArrayList<Integer>) form_irr.getRespostas().get(22);
                        response.accumulate("enguia",ai.get(0));
                        response.accumulate("lampreia",ai.get(1));
                        response.accumulate("salmao", ai.get(2));
                        response.accumulate("truta", ai.get(3));
                        response.accumulate("bogaPortuguesa", ai.get(4));
                        response.accumulate("bogaDoNorte", ai.get(5));
                        arrayList_CorredorEcologico.add(Form_functions.getmax(ai, values_irr.get(22)));

                        response.accumulate("peixes_outro", "");

                        ai= (ArrayList<Integer>) form_irr.getRespostas().get(23);
                        response.accumulate("percaSol",ai.get(0));
                        response.accumulate("tartarugaDaFlorida",ai.get(1));
                        response.accumulate("caranguejoPeludoChines",ai.get(2));
                        response.accumulate("gambusia",ai.get(3));
                        response.accumulate("mustelaVison", ai.get(4));
                        response.accumulate("lagostimVermelho", ai.get(5));
                        response.accumulate("trutaArcoIris", ai.get(6));
                        response.accumulate("achiga", ai.get(7));
                        arrayList_CorredorEcologico.add(Form_functions.getmax(ai, values_irr.get(23)));

                        response.accumulate("fauna_outro", "");

                        ai= (ArrayList<Integer>) form_irr.getRespostas().get(24);
                        response.accumulate("salgueiral",ai.get(0));
                        response.accumulate("amial",ai.get(1));
                        response.accumulate("freixal",ai.get(2));
                        response.accumulate("choupal",ai.get(3));
                        response.accumulate("ulmeiral",ai.get(4));
                        response.accumulate("sanguinos",ai.get(5));
                        response.accumulate("ladual",ai.get(6));
                        response.accumulate("tramazeiras", ai.get(7));
                        response.accumulate("carvalhal", ai.get(8));
                        response.accumulate("sobreiral", ai.get(9));
                        response.accumulate("azinhal", ai.get(10));
                        arrayList_CorredorEcologico.add(Form_functions.getmax(ai, values_irr.get(24)));
                        response.accumulate("flora_outro", "");

                        response.accumulate("conservacaoBosqueRibeirinho",(int) form_irr.getRespostas().get(25));

                        ai= (ArrayList<Integer>) form_irr.getRespostas().get(26);
                        response.accumulate("silvas",ai.get(0));
                        response.accumulate("ervaDaFortuna",ai.get(1));
                        response.accumulate("plumas", ai.get(2));
                        response.accumulate("lentilhaDaAgua", ai.get(3));
                        response.accumulate("pinheirinha", ai.get(4));
                        response.accumulate("jacintoDeAgua", ai.get(5));
                        arrayList_CorredorEcologico.add(Form_functions.getmax(ai, values_irr.get(26)));
                        response.accumulate("vegetacaoInvasora_outro", "");

                        response.accumulate("obstrucaoDoLeitoMargens", (int) form_irr.getRespostas().get(27));
                        arrayList_CorredorEcologico.add(Form_functions.getmax((int) form_irr.getRespostas().get(27), values_irr.get(27)));

                        response.accumulate("disponibilizacaoDeInformacao", (int) form_irr.getRespostas().get(28));
                        arrayList_ParticipacaoPublica.add(Form_functions.getmax((int) form_irr.getRespostas().get(28), values_irr.get(28)));
                        response.accumulate("envolvimentoPublico", (int) form_irr.getRespostas().get(29));
                        arrayList_ParticipacaoPublica.add(Form_functions.getmax((int) form_irr.getRespostas().get(29), values_irr.get(29)));
                        response.accumulate("acao", (int) form_irr.getRespostas().get(30));
                        arrayList_ParticipacaoPublica.add(Form_functions.getmax((int) form_irr.getRespostas().get(30), values_irr.get(30)));

                        response.accumulate("legislacao", (int) form_irr.getRespostas().get(31));
                        arrayList_OrganizacaoPlaneamento.add(Form_functions.getmax((int) form_irr.getRespostas().get(31), values_irr.get(31)));
                        response.accumulate("estrategia", (int) form_irr.getRespostas().get(32));
                        arrayList_OrganizacaoPlaneamento.add(Form_functions.getmax((int) form_irr.getRespostas().get(32), values_irr.get(32)));
                        response.accumulate("gestaoDasIntervencoes", (int) form_irr.getRespostas().get(33));
                        arrayList_OrganizacaoPlaneamento.add(Form_functions.getmax((int) form_irr.getRespostas().get(33), values_irr.get(33)));

                        response.accumulate("irr_hidrogeomorfologia", Collections.max(arrayList_Hidrogeomorfologia));
                        response.accumulate("irr_qualidadedaagua",Collections.max(arrayList_QualidadeDaAgua));
                        response.accumulate("irr_alteracoesantropicas",Collections.max(arrayList_AlteracoesAntropicas));
                        response.accumulate("irr_corredorecologico",Collections.max(arrayList_CorredorEcologico));
                        response.accumulate("irr_participacaopublica", Collections.max(arrayList_ParticipacaoPublica));
                        response.accumulate("irr_organizacaoeplaneamento", Collections.max(arrayList_OrganizacaoPlaneamento));
                        ArrayList<Integer> array_final= new ArrayList<Integer>();
                        array_final.add(Collections.max(arrayList_Hidrogeomorfologia));
                        array_final.add(Collections.max(arrayList_QualidadeDaAgua));
                        array_final.add(Collections.max(arrayList_AlteracoesAntropicas));
                        array_final.add(Collections.max(arrayList_CorredorEcologico));
                        array_final.add(Collections.max(arrayList_ParticipacaoPublica));
                        array_final.add(Collections.max(arrayList_OrganizacaoPlaneamento));
                        response.accumulate("irr",Collections.max(array_final));

                        obj_json.accumulate("form_irr",response);
                    } catch (JSONException e) {
                        Log.e("teste","nao criou o json");
                        e.printStackTrace();
                    }

                    String veryLongString=obj_json.toString();
                    int maxLogSize = 1000;
                    for(int i = 0; i <= veryLongString.length() / maxLogSize; i++) {
                        int start = i * maxLogSize;
                        int end = (i+1) * maxLogSize;
                        end = end > veryLongString.length() ? veryLongString.length() : end;
                        Log.e("teste", veryLongString.substring(start, end));
                    }

                    OutputStream os = null;
                    os = con.getOutputStream();
                    OutputStreamWriter osw = null;
                    osw = new OutputStreamWriter(os, "UTF-8");
                    osw.write(obj_json.toString());
                    osw.flush();
                    osw.close();
                    int HttpResult = 0;
                    StringBuilder sb=null;
                    sb= new StringBuilder();
                    HttpResult = con.getResponseCode();
                    Log.e("teste","antes de ver se esta ok: "+HttpResult);
                    if (HttpResult == HttpURLConnection.HTTP_OK) {
                        BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"));
                        String line = null;
                        while ((line = br.readLine()) != null) {
                            sb.append(line + "\n");
                        }

                        br.close();
                        String erro_sb="";

                        try {
                            JSONObject obj = new JSONObject(sb.toString());
                            try {
                                erro_sb = obj.getString("error");
                            } catch (JSONException ignored) {
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.e("teste", "erro_sb:" + erro_sb);

                    } else {
                        Log.e("teste","dentro do else");
                        Log.e("teste","nao deu a conection");
                        Log.e("teste", con.getResponseMessage());
                        System.out.println("erro:" + con.getResponseMessage());
                        System.out.println("erro:"+con.getErrorStream());

                        BufferedReader br = new BufferedReader(new InputStreamReader(con.getErrorStream(), "utf-8"));
                        String line = null;
                        while ((line = br.readLine()) != null) {
                            sb.append(line + "\n");
                        }
                        br.close();
                        Log.e("teste","sb:"+sb.toString());

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

                        String authentication_token = "";
                        String error_txt = "";
                        Boolean error = false;

                        /*{"id":2,"nome":"Filipe Miranda","access":null,"created_at":"2015-11-11T19:21:13.255Z",
                        "updated_at":"2015-11-11T19:21:13.255Z","email":"fil.fmiranda@gmail.com",
                        "authentication_token":"muWniRNBN-NSdF5vJdHy","distrito":null,"concelho":null,
                        "telef":null,"habilitacoes":null,"profissao":null,"formacao":null}
                         */

                        try {
                            JSONObject user_json = new JSONObject(sb.toString());
                            if(user_json.has("error"))
                                error_txt = user_json.getString("error");

                            if(!error_txt.equals(""))
                                error = true;

                            if(!error) {
                                authentication_token = user_json.getString("authentication_token");

                                User user = User.getInstance();
                                user.setId(Integer.parseInt(user_json.getString("id")));
                                user.setName(user_json.getString("nome"));
                                user.setEmail(user_json.getString("email"));
                                user.setAuthentication_token(user_json.getString("authentication_token"));
                                user.setTelef(user_json.getString("telef"));

                                String distrito_id = user_json.getString("distrito_id");
                                String concelho_id = user_json.getString("concelho_id");
                                user.setDistrito(distrito_id);
                                user.setConcelho(concelho_id);

                                user.setProfissao(user_json.getString("profissao"));
                                user.setHabilitacoes(user_json.getString("habilitacoes"));
                                user.setFormacao(Boolean.valueOf(user_json.getString("formacao")));

                                Log.e("profile","a seguir ao set user todo");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.e("resposta:","a meio error:"+ error_txt +" autenticacao:"+ authentication_token);
                        login.login_response(error,error_txt);

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

    public static void getForms(final String token,final String email, final Form_IRR_mainActivity formIRR) throws IOException, JSONException {

        new Thread(new Runnable() {
            public void run() {

                String url = "http://riosmais.herokuapp.com/api/v2/form_irrs?user_email="+email+"&user_token="+token;

                URL obj = null;
                try {
                    obj = new URL(url);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                HttpURLConnection con = null;
                try {
                    con = (HttpURLConnection) obj.openConnection();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                // optional default is GET
                try {
                    con.setRequestMethod("GET");
                } catch (ProtocolException e) {
                    e.printStackTrace();
                }

                //add request header
                con.setRequestProperty("Content-Type", "application/json");

                int responseCode = 0;
                try {
                    responseCode = con.getResponseCode();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("\nSending 'GET' request to URL : " + url);
                System.out.println("Response Code : " + responseCode);

                BufferedReader in = null;
                try {
                    in = new BufferedReader(
                            new InputStreamReader(con.getInputStream()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String inputLine;
                StringBuffer response = new StringBuffer();

                try {
                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                //print result
                System.out.println(response.toString());
                Log.e("teste", response.toString());

                formIRR.formsFromUser(response.toString());
                try {
                    JSONArray jsonarray  = new JSONArray(response.toString());

                    Log.e("teste","tamanh:"+jsonarray.length());
                    for(int i=0; i<jsonarray.length(); i++){
                        JSONObject form_irr_json = jsonarray.getJSONObject(i);

                        String name = form_irr_json.getString("name");

                        System.out.println(name);
                        System.out.println(url);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }

    public static void saveGuardaRios(final GuardaRios_form guardaRios_form,final String token, final String q1, final String q2, final String q3, final String q4, final ArrayList<Integer> q5, final String q6) {
        new Thread(new Runnable() {
            public void run() {

                try {
                    String url = "http://riosmais.herokuapp.com/api/v2/guardarios?user_email="+"fil.fmiranda@gmail.com"+"&user_token="+token;
                    Log.e("teste",url);
                    URL object = new URL(url);
                    HttpURLConnection con = (HttpURLConnection) object.openConnection();
                    con.setDoOutput(true);
                    con.setDoInput(true);
                    con.setRequestProperty("Content-Type", "application/json");
                    con.setRequestMethod("POST");
                    con.connect();
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.accumulate("rio","201.02");
                    jsonObject.accumulate("local",q1);
                    jsonObject.accumulate("voar",q2);
                    jsonObject.accumulate("cantar",q3);
                    jsonObject.accumulate("alimentar",q4);
                    jsonObject.accumulate("parado",q5.get(0));
                    jsonObject.accumulate("beber",q5.get(1));
                    jsonObject.accumulate("cacar",q5.get(2));
                    jsonObject.accumulate("cuidarcrias",q5.get(3));
                    jsonObject.accumulate("outro",q6);
                    jsonObject.accumulate("lat","");
                    jsonObject.accumulate("lon","");
                    jsonObject.accumulate("nomeRio","");
                    JSONObject guardarios= new JSONObject();
                    guardarios.accumulate("guardario",jsonObject);

                    Log.w("teste", jsonObject.toString());
                    Log.e("teste",guardarios.toString());

                    OutputStream os = con.getOutputStream();
                    OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");
                    osw.write(guardarios.toString());
                    osw.flush();
                    osw.close();
                    StringBuilder sb = new StringBuilder();
                    int HttpResult = con.getResponseCode();
                    if (HttpResult == HttpURLConnection.HTTP_OK) {
                        BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"));
                        String line = null;
                        while ((line = br.readLine()) != null) {
                            sb.append(line).append("\n");
                        }

                        br.close();

                        // Log.e("resposta:","a meio     error:"+ error_txt[0] +" autenticacao:"+ authentication_token[0]);
                        //login.login_response(error[0],error_txt[0],authentication_token[0],name,email);

                        System.out.println("errozinho:" + sb.toString());
                        guardaRios_form.saveGuardaRiosDB();

                    } else {
                        Log.e("teste","error: "+con.getResponseMessage());
                        System.out.println(con.getResponseMessage());
                    }
                } catch (IOException e) {
                    Log.e("teste","stacktrace");
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public static void saveSOSRios(final Sos_rios sos_rios,final String token,final String q1,final String q2,final String q3) {

        new Thread(new Runnable() {
            public void run() {
                try {
                    String url = "http://riosmais.herokuapp.com/api/v2/reports?user_email="+"fil.fmiranda@gmail.com"+"&user_token="+token;
                    Log.e("teste",url);
                    URL object = new URL(url);
                    HttpURLConnection con = (HttpURLConnection) object.openConnection();
                    con.setDoOutput(true);
                    con.setDoInput(true);
                    con.setRequestProperty("Content-Type", "application/json");
                    con.setRequestMethod("POST");
                    con.connect();
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.accumulate("rio", "201.02");
                    jsonObject.accumulate("categoria",q1);
                    jsonObject.accumulate("motivo",q2);
                    jsonObject.accumulate("descricao", q3);

                    JSONObject guardarios= new JSONObject();
                    guardarios.accumulate("report", jsonObject);

                    Log.w("teste", jsonObject.toString());
                    Log.e("teste",guardarios.toString());

                    OutputStream os = con.getOutputStream();
                    OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");
                    osw.write(guardarios.toString());
                    osw.flush();
                    osw.close();
                    StringBuilder sb = new StringBuilder();
                    int HttpResult = con.getResponseCode();
                    if (HttpResult == HttpURLConnection.HTTP_OK) {
                        BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"));
                        String line = null;
                        while ((line = br.readLine()) != null) {
                            sb.append(line).append("\n");
                        }

                        br.close();

                        System.out.println("errozinho:" + sb.toString());
                        sos_rios.saveSOSDB();

                    } else {
                        sos_rios.errorSOSDB(con.getResponseMessage());
                        Log.e("teste","error: "+con.getResponseMessage());
                        System.out.println(con.getResponseMessage());
                    }
                } catch (IOException e) {
                    Log.e("teste","stacktrace");
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public static void saveLimpeza(final Limpeza limpeza, final String token, final String email, final String q1, final String q2, final String q3, final String q4,
                                   final String q5, final String q6,final String q7,final String q8,final String q9,final String q10, final String q11, final String q12, final String q13,
                                   final String q14, final String q15, final Integer q16, final String q17) {

        new Thread(new Runnable() {
            public void run() {
                try {
                    String url = "http://riosmais.herokuapp.com/api/v2/limpezas?user_email="+email+"&user_token="+token;
                    Log.e("teste limpeza", url);
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

                    jsonObject.accumulate("cheia_destruicao",q17);
                    jsonObject.accumulate("cheia_perdas_monetarias",q16);
                    jsonObject.accumulate("cheia_origem",q15);
                    jsonObject.accumulate("cheia_data",q14);
                    jsonObject.accumulate("problema13",q13);
                    jsonObject.accumulate("problema12",q12);
                    jsonObject.accumulate("problema11",q11);
                    jsonObject.accumulate("problema10",q10);
                    jsonObject.accumulate("problema9",q9);
                    jsonObject.accumulate("problema8",q8);
                    jsonObject.accumulate("problema7",q7);
                    jsonObject.accumulate("problema6",q6);
                    jsonObject.accumulate("problema5",q5);
                    jsonObject.accumulate("problema4",q4);
                    jsonObject.accumulate("problema3",q3);
                    jsonObject.accumulate("problema2",q2);
                    jsonObject.accumulate("problema1",q1);
/*
                    JSONObject limpezaObj = new JSONObject();
                    limpezaObj.accumulate("limpeza", jsonObject);
*/
                    Log.w("teste", jsonObject.toString());
//                    Log.e("teste",limpezaObj.toString());

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

                        System.out.println("errozinho:" + sb.toString());
                        limpeza.saveLimpezaDB(jsonObject);

                    } else {
                        limpeza.errorLimpezaDB(con.getResponseMessage());
                        Log.e("teste","error: "+con.getResponseMessage());
                        System.out.println(con.getResponseMessage());
                    }
                } catch (IOException e) {
                    Log.e("teste","stacktrace");
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    public static void editUser(final ProfileEditActivity profileEditActivity, final String email, final String token) {
        new Thread(new Runnable() {
            public void run() {
                try {
                    String url = "http://riosmais.herokuapp.com/api/v2/users?user_email=" + email + "&user_token=" + token;
                    URL object = new URL(url);
                    HttpURLConnection con = (HttpURLConnection) object.openConnection();
                    con.setDoOutput(true);
                    con.setDoInput(true);
                    con.setRequestProperty("Content-Type", "application/json");
                    con.setRequestMethod("PATCH");
                    con.connect();
                    JSONObject jsonObject = new JSONObject();
                    JSONObject user = new JSONObject();
                    try {
                        jsonObject.accumulate("nome", profileEditActivity.getName().getText());
                        jsonObject.accumulate("email", profileEditActivity.getEmail().getText());
                        jsonObject.accumulate("password", profileEditActivity.getPassword().getText());
                        jsonObject.accumulate("password_confirmation", profileEditActivity.getPasswordConfirmation());
                        jsonObject.accumulate("telef", profileEditActivity.getTelef().getText());
                        jsonObject.accumulate("habilitacoes", profileEditActivity.getHabilitacoes().getText());
                        jsonObject.accumulate("profissao", profileEditActivity.getProfissao().getText());
                        jsonObject.accumulate("formacao", profileEditActivity.getFormacao().isChecked()?"True":"False");
                        //jsonObject.accumulate("distrito_id", "");
                        //jsonObject.accumulate("concelho_id", "");

                        user.accumulate("user", jsonObject);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    Log.e("user todo: ", user.toString());

                    OutputStream os = con.getOutputStream();
                    OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");
                    osw.write(user.toString());
                    osw.flush();
                    osw.close();
                    StringBuilder sb = new StringBuilder();
                    int HttpResult = con.getResponseCode();
                    if (HttpResult == HttpURLConnection.HTTP_OK) {
                        BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"));
                        String line = null;
                        while ((line = br.readLine()) != null) {
                            sb.append(line + "\n");
                        }

                        Log.e("register", "resposta da api:" + sb.toString());

                        final String[] error_txt = {""};
                        final Boolean[] error = {false};

                        JSONObject obj = null;
                        try {
                            obj = new JSONObject(sb.toString());
                            error_txt[0] = obj.getString("error");
                            error[0] = true;
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("JSON Exception", "exception getting response on register");
                        }

                        profileEditActivity.edit_response(error[0], error_txt[0]);

                        br.close();

                        System.out.println(sb.toString());

                    } else {
                        Log.e("register", "Resposta da api n foi OK");
                        System.out.println(con.getResponseMessage());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public static void deleteUser(final ProfileEditActivity profileEditActivity, final String email, final String token) {

        new Thread(new Runnable() {
            public void run() {

                String url = "http://riosmais.herokuapp.com/api/v2/users?user_email="+email+"&user_token="+token;

                URL obj = null;
                try {
                    obj = new URL(url);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                HttpURLConnection con = null;
                try {
                    con = (HttpURLConnection) obj.openConnection();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                // optional default is GET
                try {
                    con.setRequestMethod("DELETE");
                } catch (ProtocolException e) {
                    e.printStackTrace();
                }

                //add request header
                con.setRequestProperty("Content-Type", "application/json");

                int responseCode = 0;
                try {
                    responseCode = con.getResponseCode();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Log.i("user","deleting user");
                Log.i("user","sending DELETE request to URL: " + url);
                Log.i("user","response code: " + responseCode);

                BufferedReader in = null;
                try {
                    in = new BufferedReader(
                            new InputStreamReader(con.getInputStream()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String inputLine;
                StringBuffer response = new StringBuffer();

                try {
                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                String error_txt = "";
                Boolean error = false;

                JSONObject obj1 = null;
                try {
                    obj1 = new JSONObject(response.toString());
                    error_txt = obj1.getString("error");
                    error = true;
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("JSON Exception", "exception getting response on register");
                }

                //print result
                System.out.println(response.toString());
                Log.e("teste", response.toString());

                try {
                    JSONObject user_json = new JSONObject(response.toString());

                    Log.e("edit profile","a seguir ao delete user todo");

                    profileEditActivity.afterDeletingUser(error,error_txt);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }
}
