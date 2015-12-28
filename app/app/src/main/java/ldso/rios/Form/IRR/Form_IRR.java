package ldso.rios.Form.IRR;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import ldso.rios.DataBases.DB_functions;
import ldso.rios.Form.Form_functions;
import ldso.rios.Form.IRR.Perguntas.checkPergunta;
import ldso.rios.Form.IRR.Perguntas.complexPergunta;
import ldso.rios.Form.IRR.Perguntas.editPergunta;
import ldso.rios.Form.IRR.Perguntas.radioPergunta;
import ldso.rios.Form.IRR.Perguntas.seekPergunta;

/**
 * Created by filipe on 25/11/2015.
 */
public class Form_IRR extends Form implements Serializable {


    private static final long serialVersionUID = -622335515469057949L;

    String file_name;

    public void setLat_curr(Float lat_curr) {
        this.lat_curr = lat_curr;
    }

    public void setLon_curr(Float lon_curr) {
        this.lon_curr = lon_curr;
    }

    public void setLat_sel(Float lat_sel) {
        this.lat_sel = lat_sel;
    }

    public void setLon_sel(Float lon_sel) {
        this.lon_sel = lon_sel;
    }
//public static ArrayList<Form_IRR> all_from_irrs;


    @Override
    public String toString() {
        String s="";
        s+="Perguntas:";
        if(this.perguntas==null)
            return s;
        for(Pergunta p: this.perguntas)
            s+=" "+p.getOptions()[0].toString()+",";
        s+="\n";
        return s;
    }

    public void generate(){
        this.perguntas= new ArrayList<Pergunta>();
        this.respostas= new HashMap<Integer,Object>();
        this.other_response= new HashMap<Integer,String>();
        arrayListURI=new ArrayList<String>();

        //{main_title , sub_title , type , required , options , max,value_irr}
        for(int i=1;i<=33;i++)
        {
            ArrayList<Object> options = Questions.getOptions(i);
            Pergunta nova = null;
            ArrayList<Float[]> maxmin= (ArrayList<Float[]>) options.get(7);
            switch ((int)options.get(2))
            {
                case 0:
                    nova= new radioPergunta((String[]) options.get(4),(int[]) options.get(8),(String) options.get(0),(String)options.get(1),(Boolean) options.get(3),(Boolean)options.get(9));
                    break;
                case 1:
                    String [] options_txt= (String[]) options.get(4);
                    if(options_txt[0].equals("-"))
                    {
                        nova=new complexPergunta(options_txt,(int[]) options.get(8),(String) options.get(0),(String)options.get(1),(Boolean) options.get(3),(Boolean)options.get(9));

                    }
                    else
                        nova= new checkPergunta((String[]) options.get(4),(int[]) options.get(8),(String) options.get(0),(String)options.get(1),(Boolean) options.get(3),(Boolean)options.get(9));
                    break;
                case 2:
                    if(maxmin.size()==0)
                    nova= new editPergunta((String[]) options.get(4),(int[]) options.get(8),(String) options.get(0),(String)options.get(1),(Boolean) options.get(3),(Boolean)options.get(9));
                    else
                    nova= new editPergunta((String[]) options.get(4),(int[]) options.get(8),(String) options.get(0),(String)options.get(1),(Boolean) options.get(3),maxmin,(Boolean)options.get(9));

                    break;
                case 3:
                    nova= new seekPergunta((String[]) options.get(4),(int[]) options.get(8),(String) options.get(0),(String)options.get(1),(Boolean) options.get(3),(Boolean)options.get(9),1,5);
            }
            this.perguntas.add(nova);

        }


    }


    public void readResponseJson(JSONObject jsonObject) throws JSONException {

        respostas=new HashMap<Integer, Object>();
        other_response=new HashMap<Integer,String>();
        ArrayList<String> as= new ArrayList<String>();
        ArrayList<Integer> ai= new ArrayList<Integer>();
        ArrayList<Float> af= new ArrayList<Float>();

        this.lat_sel= Float.parseFloat(jsonObject.get("lat").toString());
        this.lon_sel=  Float.parseFloat( jsonObject.get("lon").toString());
        this.nomeRio= (String) jsonObject.get("nomeRio") +" id:"+ jsonObject.get("id").toString();

        ArrayList<Float> arrayLocation = new ArrayList<Float>();
        arrayLocation.add(0f);
        arrayLocation.add(0f);
        arrayLocation.add(this.lat_sel);
        arrayLocation.add(this.lon_sel);
        arrayLocation.add(2f);           //set radiobutton to select the "selected place"
        this.respostas.put(0,arrayLocation);

        this.margem=jsonObject.getInt("margem");

        ArrayList<Object> arrayNomeEMargem= new ArrayList<Object>();
        arrayNomeEMargem.add((String) jsonObject.get("nomeRio") +" id:"+jsonObject.get("id").toString());
        arrayNomeEMargem.add(jsonObject.getInt("margem"));

        this.respostas.put(-1,arrayNomeEMargem);

        Log.e("form", "size:" + jsonObject.toString());
        //Tipo de Vale
        this.respostas.put(1, jsonObject.get("tipoDeVale"));
        //Perfil de margens
        this.respostas.put(2, jsonObject.get("perfilDeMargens"));

        //Volume de Água
        as.add((String) jsonObject.get("larguraDaSuperficieDaAgua").toString());
        as.add((String) jsonObject.get("profundidadeMedia").toString());
        as.add((String) jsonObject.get("velocidadeMedia").toString());
        this.respostas.put(3, as);
        as= new ArrayList<String>();

        //Substrato das margens (selecionar os que tem mais de 35%)
        ai.add(jsonObject.getBoolean("substratoDasMargens_soloArgiloso")? 1:0);
        ai.add(jsonObject.getBoolean("substratoDasMargens_arenoso")? 1:0);
        ai.add(jsonObject.getBoolean("substratoDasMargens_pedregoso")? 1:0);
        ai.add(jsonObject.getBoolean("substratoDasMargens_rochoso")? 1:0);
        ai.add(jsonObject.getBoolean("substratoDasMargens_artificialPedra")? 1:0);
        ai.add(jsonObject.getBoolean("substratoDasMargens_artificialBetao") ? 1 : 0);
        this.respostas.put(4, ai);
        ai=new ArrayList<Integer>();

        //Substrato do leito (selecionar os que tem mais de 35%)
        ai.add(jsonObject.getBoolean("substratoDoLeito_blocoseRocha") ? 1 : 0);
        ai.add(jsonObject.getBoolean("substratoDoLeito_calhaus")? 1:0);
        ai.add(jsonObject.getBoolean("substratoDoLeito_cascalho") ? 1 : 0);
        ai.add(jsonObject.getBoolean("substratoDoLeito_areia")? 1:0);
        ai.add(jsonObject.getBoolean("substratoDoLeito_limo")? 1:0);
        ai.add(jsonObject.getBoolean("substratoDoLeito_solo")? 1:0);
        ai.add(jsonObject.getBoolean("substratoDoLeito_artificial")? 1:0);
        ai.add(jsonObject.getBoolean("substratoDoLeito_artificial") ? 1 : 0);
        this.respostas.put(5, ai);
        ai=new ArrayList<Integer>();


        //Estado geral da linha de água
        this.respostas.put(6, jsonObject.getInt("estadoGeraldaLinhadeAgua"));

        //Erosão
        ai.add(jsonObject.getBoolean("erosao_semErosao")? 1:0);
        ai.add(jsonObject.getBoolean("erosao_formacaomais3")? 1:0);
        ai.add(jsonObject.getBoolean("erosao_formacao1a3")? 1:0);
        ai.add(jsonObject.getBoolean("erosao_quedamuros")? 1:0);
        ai.add(jsonObject.getBoolean("erosao_rombos") ? 1 : 0);
        this.respostas.put(7, ai);
        ai=new ArrayList<Integer>();

        //Sedimentação
        ai.add(jsonObject.getBoolean("sedimentacao_ausente") ? 1 : 0);
        ai.add(jsonObject.getBoolean("sedimentacao_decomposicao")? 1:0);
        ai.add(jsonObject.getBoolean("sedimentacao_mouchoes")? 1:0);
        ai.add(jsonObject.getBoolean("sedimentacao_ilhassemveg")? 1:0);
        ai.add(jsonObject.getBoolean("sedimentacao_ilhascomveg")? 1:0);
        ai.add(jsonObject.getBoolean("sedimentacao_deposicaosemveg")? 1:0);
        ai.add(jsonObject.getBoolean("sedimentacao_deposicaocomveg")? 1:0);
        ai.add(jsonObject.getBoolean("sedimentacao_rochas") ? 1 : 0);
        this.respostas.put(8, ai);
        ai=new ArrayList<Integer>();

        //Qualidade da água
        af.add(Float.parseFloat(jsonObject.get("pH").toString()));
        af.add(Float.parseFloat(jsonObject.get("condutividade").toString()));
        af.add(Float.parseFloat(jsonObject.get("temperatura").toString()));
        af.add(Float.parseFloat(jsonObject.get("nivelDeOxigenio").toString()));
        af.add(Float.parseFloat(jsonObject.get("percentagemDeOxigenio").toString()));
        af.add(Float.parseFloat(jsonObject.get("nitratos").toString()));
        af.add(Float.parseFloat(jsonObject.get("nitritos").toString()));
        af.add(Float.parseFloat(jsonObject.get("transparencia").toString()));
        this.respostas.put(9, af);
        af=new ArrayList<Float>();
        Log.e("parse","passou "+9);

        //Indícios na água
        ai.add(jsonObject.getBoolean("oleo")? 1:0);
        ai.add(jsonObject.getBoolean("espuma")? 1:0);
        ai.add(jsonObject.getBoolean("esgotos")? 1:0);
        ai.add(jsonObject.getBoolean("impurezas")? 1:0);
        ai.add(jsonObject.getBoolean("sacosDePlastico")? 1:0);
        ai.add(jsonObject.getBoolean("latas") ? 1 : 0);

        this.respostas.put(10, ai);
        this.other_response.put(10,jsonObject.getString("indiciosNaAgua_outros"));
        ai=new ArrayList<Integer>();
        Log.e("parse","passou "+10);


        //A cor da água
        this.respostas.put(11, jsonObject.getInt("corDaAgua"));

        //O odor (cheiro) da água
        this.respostas.put(12, jsonObject.getInt("odorDaAgua"));

        //Corredor Ecologico
        ArrayList<ArrayList<Integer>> all=new ArrayList<ArrayList<Integer>>();

        ai.add(jsonObject.getBoolean("planarias")?1:0);
        all.add(ai);ai=new ArrayList<Integer>();

        ai.add(jsonObject.getBoolean("hirudineos")?1:0);
        all.add(ai);ai=new ArrayList<Integer>();

        ai.add(jsonObject.getBoolean("oligoquetas")?1:0);
        ai.add(jsonObject.getBoolean("simulideos")?1:0);
        ai.add(jsonObject.getBoolean("quironomideos")?1:0);
        all.add(ai);ai=new ArrayList<Integer>();

        ai.add(jsonObject.getBoolean("ancilideo")?1:0);
        ai.add(jsonObject.getBoolean("limnideo")?1:0);
        ai.add(jsonObject.getBoolean("bivalves")?1:0);
        all.add(ai);ai=new ArrayList<Integer>();

        ai.add(jsonObject.getBoolean("patasNadadoras")?1:0);
        ai.add(jsonObject.getBoolean("pataLocomotoras")?1:0);
        all.add(ai);ai=new ArrayList<Integer>();

        ai.add(jsonObject.getBoolean("trichopteroS")?1:0);
        ai.add(jsonObject.getBoolean("trichopteroC")?1:0);
        all.add(ai);ai=new ArrayList<Integer>();

        ai.add(jsonObject.getBoolean("odonata")?1:0);
        all.add(ai);ai=new ArrayList<Integer>();

        ai.add(jsonObject.getBoolean("heteropteros")?1:0);
        all.add(ai);ai=new ArrayList<Integer>();

        ai.add(jsonObject.getBoolean("plecopteros")?1:0);
        all.add(ai);ai=new ArrayList<Integer>();

        ai.add(jsonObject.getBoolean("baetideo")?1:0);
        ai.add(jsonObject.getBoolean("cabecaPlanar")?1:0);
        all.add(ai);ai=new ArrayList<Integer>();

        ai.add(jsonObject.getBoolean("crustaceos")?1:0);
        all.add(ai);ai=new ArrayList<Integer>();

        ai.add(jsonObject.getBoolean("acaros")?1:0);
        all.add(ai);ai=new ArrayList<Integer>();

        ai.add(jsonObject.getBoolean("pulgaDeAgua")?1:0);
        all.add(ai);ai=new ArrayList<Integer>();

        ai.add(jsonObject.getBoolean("insetos")?1:0);
        all.add(ai);ai=new ArrayList<Integer>();

        ai.add(jsonObject.getBoolean("megalopteres")?1:0);
        all.add(ai);ai=new ArrayList<Integer>();

        this.respostas.put(13, all);

        //Intervenções presentes
        ai.add(jsonObject.getBoolean("intervencoes_edificios")?1:0);
        ai.add(jsonObject.getBoolean("intervencoes_pontes")?1:0);
        ai.add(jsonObject.getBoolean("intervencoes_limpezasDasMargens")?1:0);
        ai.add(jsonObject.getBoolean("intervencoes_estabilizacaoDeMargens")?1:0);
        ai.add(jsonObject.getBoolean("intervencoes_estabilizacaoDeMargens")?1:0);
        ai.add(jsonObject.getBoolean("intervencoes_modelacaoDeMargensNatural")?1:0);
        ai.add(jsonObject.getBoolean("intervencoes_modelacaoDeMargensArtificial")?1:0);
        ai.add(jsonObject.getBoolean("intervencoes_barragem")?1:0);
        ai.add(jsonObject.getBoolean("intervencoes_diques")?1:0);
        ai.add(jsonObject.getBoolean("intervencoes_rioCanalizado")?1:0);
        ai.add(jsonObject.getBoolean("intervencoes_rioEntubado")?1:0);
        ai.add(jsonObject.getBoolean("intervencoes_esporoes")?1:0);
        ai.add(jsonObject.getBoolean("intervencoes_paredoes")?1:0);
        ai.add(jsonObject.getBoolean("intervencoes_tecnicasDeEngenhariaNatural")?1:0);
        this.respostas.put(14, ai);
        this.other_response.put(14,jsonObject.getString("intervencoes_outras"));
        ai=new ArrayList<Integer>();

        //Ocupação das margens [<10 m]
        ai.add(jsonObject.getBoolean("ocupacao_florestaNatural")?1:0);
        ai.add(jsonObject.getBoolean("ocupacao_florestaPlantadas")?1:0);
        ai.add(jsonObject.getBoolean("ocupacao_matoAlto")?1:0);
        ai.add(jsonObject.getBoolean("ocupacao_matoRasteiro")?1:0);
        ai.add(jsonObject.getBoolean("ocupacao_pastagem")?1:0);
        ai.add(jsonObject.getBoolean("ocupacao_agricultura")?1:0);
        ai.add(jsonObject.getBoolean("ocupacao_espacoAbandonado")?1:0);
        ai.add(jsonObject.getBoolean("ocupacao_jardins")?1:0);
        ai.add(jsonObject.getBoolean("ocupacao_zonaEdificada")?1:0);
        ai.add(jsonObject.getBoolean("ocupacao_zonaIndustrial")?1:0);
        ai.add(jsonObject.getBoolean("ocupacao_ruas")?1:0);
        ai.add(jsonObject.getBoolean("ocupacao_entulho")?1:0);
        this.respostas.put(15, ai);
        ai=new ArrayList<Integer>();

        //Património edificado Leito/margem [estado de conservação: 1 - Bom a 5- Mau]
        ai.add(jsonObject.getInt("patrimonio_moinho"));
        ai.add(jsonObject.getInt("patrimonio_acude"));
        ai.add(jsonObject.getInt("patrimonio_microAcude1"));
        ai.add(jsonObject.getInt("patrimonio_microAcude2"));
        ai.add(jsonObject.getInt("patrimonio_barragem"));
        ai.add(jsonObject.getInt("patrimonio_levadas"));
        ai.add(jsonObject.getInt("patrimonio_pesqueiras"));
        ai.add(jsonObject.getInt("patrimonio_escadasDePeixe"));
        ai.add(jsonObject.getInt("patrimonio_poldras"));
        ai.add(jsonObject.getInt("patrimonio_pontesSemPilar"));
        ai.add(jsonObject.getInt("patrimonio_pontesComPilar"));
        ai.add(jsonObject.getInt("patrimonio_passagemAVau"));
        ai.add(jsonObject.getInt("patrimonio_barcos"));
        ai.add(jsonObject.getInt("patrimonio_cais"));
        ai.add(jsonObject.getInt("patrimonio_igreja"));
        ai.add(jsonObject.getInt("patrimonio_solares"));
        ai.add(jsonObject.getInt("patrimonio_nucleoHabitacional"));
        ai.add(jsonObject.getInt("patrimonio_edificiosParticulares"));
        ai.add(jsonObject.getInt("patrimonio_edificiosPublicos"));
        ai.add(jsonObject.getInt("patrimonio_ETA"));
        ai.add(jsonObject.getInt("patrimonio_descarregadoresDeAguasPluviais"));
        ai.add(jsonObject.getInt("patrimonio_coletoresSaneamento"));
        ai.add(jsonObject.getInt("patrimonio_defletoresArtificiais"));
        ai.add(jsonObject.getInt("patrimonio_motaLateral"));
        this.respostas.put(16, ai);
        ai=new ArrayList<Integer>();

        //Poluição
        ai.add(jsonObject.getBoolean("poluicao_descargasDomesticas")?1:0);
        ai.add(jsonObject.getBoolean("poluicao_descargasETAR")?1:0);
        ai.add(jsonObject.getBoolean("poluicao_descargasIndustriais")?1:0);
        ai.add(jsonObject.getBoolean("poluicao_descargasQuimicas")?1:0);
        ai.add(jsonObject.getBoolean("poluicao_descargasAguasPluviais")?1:0);
        ai.add(jsonObject.getBoolean("poluicao_presencaCriacaoAnimais")?1:0);
        ai.add(jsonObject.getBoolean("poluicao_lixeiras")?1:0);
        ai.add(jsonObject.getBoolean("poluicao_lixoDomestico")?1:0);
        ai.add(jsonObject.getBoolean("poluicao_entulho")?1:0);
        ai.add(jsonObject.getBoolean("poluicao_monstrosDomesticos")?1:0);
        ai.add(jsonObject.getBoolean("poluicao_sacosDePlastico")?1:0);
        ai.add(jsonObject.getBoolean("poluicao_latasMaterialFerroso")?1:0);
        ai.add(jsonObject.getBoolean("poluicao_queimadas")?1:0);
        this.respostas.put(17, ai);
        ai=new ArrayList<Integer>();

        //Fauna - Anfíbios autoctones
        ai.add(jsonObject.getBoolean("salamandraLusitanica")?1:0);
        ai.add(jsonObject.getBoolean("salamandraPintasAmarelas")?1:0);
        ai.add(jsonObject.getBoolean("tritaoVentreLaranja")?1:0);
        ai.add(jsonObject.getBoolean("raIberica")?1:0);
        ai.add(jsonObject.getBoolean("raVerde")?1:0);
        ai.add(jsonObject.getBoolean("sapoComum")?1:0);
        this.respostas.put(18, ai);
        ai=new ArrayList<Integer>();

        //Fauna - Répteis Autoctones
        ai.add(jsonObject.getBoolean("lagartoDeAgua")?1:0);
        ai.add(jsonObject.getBoolean("cobraAguaDeColar")?1:0);
        ai.add(jsonObject.getBoolean("cagado")?1:0);
        this.respostas.put(19, ai);
        this.other_response.put(19, jsonObject.getString("repteis_outro"));
        ai=new ArrayList<Integer>();

        //Fauna - Aves Autoctones
        ai.add(jsonObject.getBoolean("guardaRios")?1:0);
        ai.add(jsonObject.getBoolean("garcaReal")?1:0);
        ai.add(jsonObject.getBoolean("melroDeAgua")?1:0);
        ai.add(jsonObject.getBoolean("galinhaDeAgua")?1:0);
        ai.add(jsonObject.getBoolean("patoReal")?1:0);
        ai.add(jsonObject.getBoolean("tentilhaoComum")?1:0);
        ai.add(jsonObject.getBoolean("chapimReal")?1:0);
        this.respostas.put(20, ai);
        this.other_response.put(20, jsonObject.getString("aves_outro"));
        ai=new ArrayList<Integer>();

        //Fauna - Mamíferos Autoctones
        ai.add(jsonObject.getBoolean("lontras")?1:0);
        ai.add(jsonObject.getBoolean("morcegosDeAgua")?1:0);
        ai.add(jsonObject.getBoolean("toupeiraDaAgua")?1:0);
        ai.add(jsonObject.getBoolean("ratoDeAgua")?1:0);
        ai.add(jsonObject.getBoolean("ouricoCacheiro")?1:0);
        ai.add(jsonObject.getBoolean("armilho")?1:0);
        this.respostas.put(21, ai);
        this.other_response.put(21, jsonObject.getString("mamiferos_outro"));
        ai=new ArrayList<Integer>();

        //Fauna - Peixes Autoctones
        ai.add(jsonObject.getBoolean("enguia")?1:0);
        ai.add(jsonObject.getBoolean("lampreia")?1:0);
        ai.add(jsonObject.getBoolean("salmao")?1:0);
        ai.add(jsonObject.getBoolean("truta")?1:0);
        ai.add(jsonObject.getBoolean("bogaPortuguesa")?1:0);
        ai.add(jsonObject.getBoolean("bogaDoNorte")?1:0);
        this.respostas.put(22, ai);
        this.other_response.put(22, jsonObject.getString("peixes_outro"));
        ai=new ArrayList<Integer>();

        //Fauna Exótica
        ai.add(jsonObject.getBoolean("percaSol")?1:0);
        ai.add(jsonObject.getBoolean("tartarugaDaFlorida")?1:0);
        ai.add(jsonObject.getBoolean("caranguejoPeludoChines")?1:0);
        ai.add(jsonObject.getBoolean("gambusia")?1:0);
        ai.add(jsonObject.getBoolean("mustelaVison")?1:0);
        ai.add(jsonObject.getBoolean("lagostimVermelho")?1:0);
        ai.add(jsonObject.getBoolean("trutaArcoIris")?1:0);
        ai.add(jsonObject.getBoolean("achiga")?1:0);
        this.respostas.put(23, ai);
        this.other_response.put(23, jsonObject.getString("fauna_outro"));
        ai=new ArrayList<Integer>();

        //Flora
        ai.add(jsonObject.getBoolean("salgueiral")?1:0);
        ai.add(jsonObject.getBoolean("amial")?1:0);
        ai.add(jsonObject.getBoolean("freixal")?1:0);
        ai.add(jsonObject.getBoolean("choupal")?1:0);
        ai.add(jsonObject.getBoolean("ulmeiral")?1:0);
        ai.add(jsonObject.getBoolean("sanguinos")?1:0);
        ai.add(jsonObject.getBoolean("ladual")?1:0);
        ai.add(jsonObject.getBoolean("tramazeiras")?1:0);
        ai.add(jsonObject.getBoolean("carvalhal")?1:0);
        ai.add(jsonObject.getBoolean("sobreiral")?1:0);
        ai.add(jsonObject.getBoolean("azinhal")?1:0);
        this.respostas.put(24, ai);
        this.other_response.put(24, jsonObject.getString("flora_outro"));
        ai =new ArrayList<Integer>();

        //Estado de conservação do bosque ribeirinho (10m*10m)
        this.respostas.put(25, jsonObject.getInt("conservacaoBosqueRibeirinho"));

        //Espécies vegetação invasora
        ai.add(jsonObject.getBoolean("silvas")?1:0);
        ai.add(jsonObject.getBoolean("ervaDaFortuna")?1:0);
        ai.add(jsonObject.getBoolean("plumas")?1:0);
        ai.add(jsonObject.getBoolean("lentilhaDaAgua")?1:0);
        ai.add(jsonObject.getBoolean("pinheirinha")?1:0);
        ai.add(jsonObject.getBoolean("jacintoDeAgua")?1:0);
        this.respostas.put(26, ai);
        this.other_response.put(26, jsonObject.getString("vegetacaoInvasora_outro"));
        ai=new ArrayList<Integer>();

        //Obstrução do leito e margens (vegetação)
        this.respostas.put(27, jsonObject.getInt("obstrucaoDoLeitoMargens"));

        //Disponibilização de informação
        this.respostas.put(28, jsonObject.getInt("disponibilizacaoDeInformacao"));

        //Envolvimento público
        this.respostas.put(29, jsonObject.getInt("envolvimentoPublico"));

        //Acção
        this.respostas.put(30, jsonObject.getInt("acao"));

        //Legislação
        this.respostas.put(31, jsonObject.getInt("legislacao"));

        //Estratégia, planos de ordenamento e gestão
        this.respostas.put(32, jsonObject.getInt("estrategia"));


        //Gestão das intervenções de melhoria
        this.respostas.put(33, jsonObject.getInt("gestaoDasIntervencoes"));



        Log.e("chegou ao final","final");






    }

    public  static  byte[] serialize(Object obj) throws IOException {
        ByteArrayOutputStream b= new ByteArrayOutputStream();
        ObjectOutputStream o = new ObjectOutputStream(b);
        o.writeObject(o);
        return b.toByteArray();
    }

    public static Object deserialize (byte[] bytes) throws IOException, ClassNotFoundException {
        ByteArrayInputStream b = new ByteArrayInputStream(bytes);
        ObjectInputStream o= new ObjectInputStream(b);
        return o.readObject();
    }



    public void setDate(){

        Calendar calendar = Calendar.getInstance();
        this.respostas.put(-4, DateFormat.getDateTimeInstance().format(new Date()));

    }

    /**
     * retorna um arraylist de todos os forms
     * @param c
     * @return
     * @throws IOException
     */
    public static ArrayList<HashMap<Integer,Object>> loadFromIRR(Context c) throws IOException {


        File f = new File(c.getFilesDir()+File.separator+"irrs");
        if(!f.exists())
            f.mkdirs();
        Log.e("Files", "path:" + c.getFilesDir());
        Log.d("Files", "Size: " + f.getAbsolutePath());
        File file[] = c.getFilesDir().listFiles();
        for (File fifi: file)
            Log.e("form",fifi.getName());

        if (f.isDirectory())
            Log.e("form","e diretorio");
        else
            Log.e("form", "nao e diretorio");



        ArrayList<HashMap<Integer,Object>> form_irr_arraylist = new ArrayList<HashMap<Integer,Object>>();
        file = f.listFiles();
        Integer i=0;
        Integer j=0;
        for (File fifi: file){
            Log.e("form_pastas",fifi.getName());
            HashMap<Integer, Object> form_irr;
            try {
                FileInputStream fis = c.openFileInput(fifi.getName());
                ObjectInputStream ois = new ObjectInputStream(fis);
                form_irr = (HashMap<Integer,Object>) ois.readObject();
                form_irr.put(-2,fifi.getName());
                form_irr_arraylist.add(form_irr);
                ois.close();
                i++;
            } catch (Exception e) {
                e.printStackTrace();
            }

            j++;

        }

        Log.e("teste","tamanho do arrayist:"+j+"la dentro:"+i);
        return form_irr_arraylist;
    }



    public static void saveFormIRR(Form_IRR form_irr, Context c) throws IOException {


        File f = new File(c.getFilesDir()+File.separator+"irrs");
        if(!f.exists())
            f.mkdirs();

        Calendar calendar = Calendar.getInstance();
        File files = new File(f.getAbsolutePath(),"form-"+System.currentTimeMillis()+".dat");
        if (!files.exists())
            files.createNewFile();
        Log.d("Files", "Size: " + files.getAbsolutePath());

        form_irr.setDate();

        form_irr.respostas.put(-2, files.getName());
        form_irr.respostas.put(-3, form_irr.other_response);
        form_irr.respostas.put(-5,form_irr.getArrayListURI());



        //all_from_irrs.add(form_irr);

            try {
                FileOutputStream fos = c.openFileOutput(files.getName(),
                        c.MODE_WORLD_READABLE);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(form_irr.getRespostas());
                oos.close();
            } catch (Exception e) {
                e.printStackTrace();
        }
    }






    public static boolean uploadFormIRR(Object activity,Context c, Form_IRR form_irr){

        File f = new File(c.getFilesDir()+File.separator+"irrs");
        if(!f.exists())
            f.mkdirs();




        try {
            DB_functions.saveForm(activity,Form_functions.getUser(c)[0],
                    Form_functions.getUser(c)[1],
                    form_irr);
            File file = new File(f.getAbsolutePath(),(String) form_irr.getRespostas().get(-2));
            file.delete();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }
}
