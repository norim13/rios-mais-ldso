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
import java.util.ArrayList;
import java.util.Calendar;
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
    Float lat_curr,lon_curr;
    Float lat_sel,lon_sel;

    String file_name;

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
        //{main_title , sub_title , type , required , options , max,value_irr}
        for(int i=1;i<=33;i++)
        {
            ArrayList<Object> options = Questions.getOptions(i);
            Pergunta nova = null;
            ArrayList<Float[]> maxmin= (ArrayList<Float[]>) options.get(7);
            switch ((int)options.get(2))
            {
                case 0:
                    nova= new radioPergunta((String[]) options.get(4),(String) options.get(0),(String)options.get(1),(Boolean) options.get(3),false);
                    break;
                case 1:
                    String [] options_txt= (String[]) options.get(4);
                    if(options_txt[0].equals("-"))
                    {
                        nova=new complexPergunta(options_txt,(String) options.get(0),(String)options.get(1),(Boolean) options.get(3),false);

                    }
                    else
                        nova= new checkPergunta((String[]) options.get(4),(String) options.get(0),(String)options.get(1),(Boolean) options.get(3),false);
                    break;
                case 2:
                    if(maxmin.size()==0)
                    nova= new editPergunta((String[]) options.get(4),(String) options.get(0),(String)options.get(1),(Boolean) options.get(3),false);
                    else
                    nova= new editPergunta((String[]) options.get(4),(String) options.get(0),(String)options.get(1),(Boolean) options.get(3),maxmin,false);

                    break;
                case 3:
                    nova= new seekPergunta((String[]) options.get(4),(String) options.get(0),(String)options.get(1),(Boolean) options.get(3),false,1,5);
            }
            this.perguntas.add(nova);

        }


    }


    public void readResponseJson(JSONObject jsonObject) throws JSONException {

        respostas=new HashMap<Integer, Object>();
        ArrayList<String> as= new ArrayList<String>();
        ArrayList<Integer> ai= new ArrayList<Integer>();
        ArrayList<Float> af= new ArrayList<Float>();

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
        ai.add(jsonObject.getInt("substratoDasMargens_soloArgiloso"));
        ai.add(jsonObject.getInt("substratoDasMargens_arenoso"));
        ai.add(jsonObject.getInt("substratoDasMargens_pedregoso"));
        ai.add(jsonObject.getInt("substratoDasMargens_rochoso"));
        ai.add(jsonObject.getInt("substratoDasMargens_artificialPedra"));
        ai.add(jsonObject.getInt("substratoDasMargens_artificialBetao"));
        this.respostas.put(4, ai);
        ai=new ArrayList<Integer>();

        //Substrato do leito (selecionar os que tem mais de 35%)
        ai.add(jsonObject.getInt("substratoDoLeito_blocoseRocha"));
        ai.add(jsonObject.getInt("substratoDoLeito_calhaus"));
        ai.add(jsonObject.getInt("substratoDoLeito_cascalho"));
        ai.add(jsonObject.getInt("substratoDoLeito_areia"));
        ai.add(jsonObject.getInt("substratoDoLeito_limo"));
        ai.add(jsonObject.getInt("substratoDoLeito_solo"));
        ai.add(jsonObject.getInt("substratoDoLeito_artificial"));
        ai.add(jsonObject.getInt("substratoDoLeito_artificial"));
        this.respostas.put(5, ai);
        ai=new ArrayList<Integer>();


        //Estado geral da linha de água
        this.respostas.put(6, jsonObject.getInt("estadoGeraldaLinhadeAgua"));

        //Erosão
        ai.add(jsonObject.getInt("erosao_semErosao"));
        ai.add(jsonObject.getInt("erosao_formacaomais3"));
        ai.add(jsonObject.getInt("erosao_formacao1a3"));
        ai.add(jsonObject.getInt("erosao_quedamuros"));
        ai.add(jsonObject.getInt("erosao_rombos"));
        this.respostas.put(7, ai);
        ai=new ArrayList<Integer>();

        //Sedimentação
        ai.add(jsonObject.getInt("sedimentacao_ausente"));
        ai.add(jsonObject.getInt("sedimentacao_decomposicao"));
        ai.add(jsonObject.getInt("sedimentacao_mouchoes"));
        ai.add(jsonObject.getInt("sedimentacao_ilhassemveg"));
        ai.add(jsonObject.getInt("sedimentacao_ilhascomveg"));
        ai.add(jsonObject.getInt("sedimentacao_deposicaosemveg"));
        ai.add(jsonObject.getInt("sedimentacao_deposicaocomveg"));
        ai.add(jsonObject.getInt("sedimentacao_rochas"));
        this.respostas.put(8, ai);
        ai=new ArrayList<Integer>();

        //Qualidade da água
        af.add((Float) jsonObject.get("pH"));
        af.add((Float) jsonObject.get("condutividade"));
        af.add((Float) jsonObject.get("temperatura"));
        af.add((Float) jsonObject.get("nivelDeOxigenio"));
        af.add((Float) jsonObject.get("percentagemDeOxigenio"));
        af.add((Float) jsonObject.get("nitratos"));
        af.add((Float) jsonObject.get("nitritos"));
        af.add((Float) jsonObject.get("transparencia"));
        this.respostas.put(9, af);
        af=new ArrayList<Float>();

        //Indícios na água
        ai.add(jsonObject.getInt("oleo"));
        ai.add(jsonObject.getInt("espuma"));
        ai.add(jsonObject.getInt("esgotos"));
        ai.add(jsonObject.getInt("impurezas"));
        ai.add(jsonObject.getInt("sacosDePlastico"));

        this.respostas.put(10, ai);
        ai=new ArrayList<Integer>();

        //A cor da água
        this.respostas.put(11, jsonObject.getInt("corDaAgua"));

        //O odor (cheiro) da água
        this.respostas.put(12, jsonObject.getInt("odorDaAgua"));

        //Corredor Ecologico
        ArrayList<ArrayList<Integer>> all=new ArrayList<ArrayList<Integer>>();

        ai.add(jsonObject.getInt("planarias"));
        all.add(ai);ai=new ArrayList<Integer>();

        ai.add(jsonObject.getInt("hirudineos"));
        all.add(ai);ai=new ArrayList<Integer>();

        ai.add(jsonObject.getInt("oligoquetas"));
        ai.add(jsonObject.getInt("simulideos"));
        ai.add(jsonObject.getInt("quironomideos"));
        all.add(ai);ai=new ArrayList<Integer>();

        ai.add(jsonObject.getInt("ancilideo"));
        ai.add(jsonObject.getInt("limnideo"));
        ai.add(jsonObject.getInt("bivalves"));
        all.add(ai);ai=new ArrayList<Integer>();

        ai.add(jsonObject.getInt("patasNadadoras"));
        ai.add(jsonObject.getInt("pataLocomotoras"));
        all.add(ai);ai=new ArrayList<Integer>();

        ai.add(jsonObject.getInt("trichopteroS"));
        ai.add(jsonObject.getInt("trichopteroC"));
        all.add(ai);ai=new ArrayList<Integer>();

        ai.add(jsonObject.getInt("odonata"));
        all.add(ai);ai=new ArrayList<Integer>();

        ai.add(jsonObject.getInt("heteropteros"));
        all.add(ai);ai=new ArrayList<Integer>();

        ai.add(jsonObject.getInt("plecopteros"));
        all.add(ai);ai=new ArrayList<Integer>();

        ai.add(jsonObject.getInt("baetideo"));
        ai.add(jsonObject.getInt("cabecaPlanar"));
        all.add(ai);ai=new ArrayList<Integer>();

        ai.add(jsonObject.getInt("crustaceos"));
        all.add(ai);ai=new ArrayList<Integer>();

        ai.add(jsonObject.getInt("acaros"));
        all.add(ai);ai=new ArrayList<Integer>();

        ai.add(jsonObject.getInt("pulgaDeAgua"));
        all.add(ai);ai=new ArrayList<Integer>();

        ai.add(jsonObject.getInt("insetos"));
        all.add(ai);ai=new ArrayList<Integer>();

        ai.add(jsonObject.getInt("megalopteres"));
        all.add(ai);ai=new ArrayList<Integer>();

        this.respostas.put(13, all);

        //Intervenções presentes
        ai.add(jsonObject.getInt("intervencoes_edificios"));
        ai.add(jsonObject.getInt("intervencoes_pontes"));
        ai.add(jsonObject.getInt("intervencoes_limpezasDasMargens"));
        ai.add(jsonObject.getInt("intervencoes_estabilizacaoDeMargens"));
        ai.add(jsonObject.getInt("intervencoes_estabilizacaoDeMargens"));
        ai.add(jsonObject.getInt("intervencoes_modelacaoDeMargensNatural"));
        ai.add(jsonObject.getInt("intervencoes_modelacaoDeMargensArtificial"));
        ai.add(jsonObject.getInt("intervencoes_barragem"));
        ai.add(jsonObject.getInt("intervencoes_diques"));
        ai.add(jsonObject.getInt("intervencoes_rioCanalizado"));
        ai.add(jsonObject.getInt("intervencoes_rioEntubado"));
        ai.add(jsonObject.getInt("intervencoes_esporoes"));
        ai.add(jsonObject.getInt("intervencoes_paredoes"));
        ai.add(jsonObject.getInt("intervencoes_tecnicasDeEngenhariaNatural"));
        this.respostas.put(14, ai);
        ai=new ArrayList<Integer>();

        //Ocupação das margens [<10 m]
        ai.add(jsonObject.getInt("ocupacao_florestaNatural"));
        ai.add(jsonObject.getInt("ocupacao_florestaPlantadas"));
        ai.add(jsonObject.getInt("ocupacao_matoAlto"));
        ai.add(jsonObject.getInt("ocupacao_matoRasteiro"));
        ai.add(jsonObject.getInt("ocupacao_pastagem"));
        ai.add(jsonObject.getInt("ocupacao_agricultura"));
        ai.add(jsonObject.getInt("ocupacao_espacoAbandonado"));
        ai.add(jsonObject.getInt("ocupacao_jardins"));
        ai.add(jsonObject.getInt("ocupacao_zonaEdificada"));
        ai.add(jsonObject.getInt("ocupacao_zonaIndustrial"));
        ai.add(jsonObject.getInt("ocupacao_ruas"));
        ai.add(jsonObject.getInt("ocupacao_entulho"));
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
        ai.add(jsonObject.getInt("poluicao_descargasDomesticas"));
        ai.add(jsonObject.getInt("poluicao_descargasETAR"));
        ai.add(jsonObject.getInt("poluicao_descargasIndustriais"));
        ai.add(jsonObject.getInt("poluicao_descargasQuimicas"));
        ai.add(jsonObject.getInt("poluicao_descargasAguasPluviais"));
        ai.add(jsonObject.getInt("poluicao_presencaCriacaoAnimais"));
        ai.add(jsonObject.getInt("poluicao_lixeiras"));
        ai.add(jsonObject.getInt("poluicao_lixoDomestico"));
        ai.add(jsonObject.getInt("poluicao_entulho"));
        ai.add(jsonObject.getInt("poluicao_monstrosDomesticos"));
        ai.add(jsonObject.getInt("poluicao_sacosDePlastico"));
        ai.add(jsonObject.getInt("poluicao_latasMaterialFerroso"));
        ai.add(jsonObject.getInt("poluicao_queimadas"));
        this.respostas.put(17, ai);
        ai=new ArrayList<Integer>();

        //Fauna - Anfíbios autoctones
        ai.add(jsonObject.getInt("salamandraLusitanica"));
        ai.add(jsonObject.getInt("salamandraPintasAmarelas"));
        ai.add(jsonObject.getInt("tritaoVentreLaranja"));
        ai.add(jsonObject.getInt("raIberica"));
        ai.add(jsonObject.getInt("raVerde"));
        ai.add(jsonObject.getInt("sapoComum"));
        this.respostas.put(18, ai);
        ai=new ArrayList<Integer>();

        //Fauna - Répteis Autoctones
        ai.add(jsonObject.getInt("lagartoDeAgua"));
        ai.add(jsonObject.getInt("cobraAguaDeColar"));
        ai.add(jsonObject.getInt("cagado"));
        this.respostas.put(19, ai);
        ai=new ArrayList<Integer>();

        //Fauna - Aves Autoctones
        ai.add(jsonObject.getInt("guardaRios"));
        ai.add(jsonObject.getInt("garcaReal"));
        ai.add(jsonObject.getInt("melroDeAgua"));
        ai.add(jsonObject.getInt("galinhaDeAgua"));
        ai.add(jsonObject.getInt("patoReal"));
        ai.add(jsonObject.getInt("tentilhaoComum"));
        ai.add(jsonObject.getInt("chapimReal"));
        this.respostas.put(20, ai);
        ai=new ArrayList<Integer>();

        //Fauna - Mamíferos Autoctones
        ai.add(jsonObject.getInt("lontras"));
        ai.add(jsonObject.getInt("morcegosDeAgua"));
        ai.add(jsonObject.getInt("toupeiraDaAgua"));
        ai.add(jsonObject.getInt("ratoDeAgua"));
        ai.add(jsonObject.getInt("ouricoCacheiro"));
        ai.add(jsonObject.getInt("armilho"));
        this.respostas.put(21, ai);
        ai=new ArrayList<Integer>();

        //Fauna - Peixes Autoctones
        ai.add(jsonObject.getInt("enguia"));
        ai.add(jsonObject.getInt("lampreia"));
        ai.add(jsonObject.getInt("salmao"));
        ai.add(jsonObject.getInt("truta"));
        ai.add(jsonObject.getInt("bogaPortuguesa"));
        ai.add(jsonObject.getInt("bogaDoNorte"));
        this.respostas.put(22, ai);
        ai=new ArrayList<Integer>();

        //Fauna Exótica
        ai.add(jsonObject.getInt("percaSol"));
        ai.add(jsonObject.getInt("tartarugaDaFlorida"));
        ai.add(jsonObject.getInt("caranguejoPeludoChines"));
        ai.add(jsonObject.getInt("gambusia"));
        ai.add(jsonObject.getInt("mustelaVison"));
        ai.add(jsonObject.getInt("lagostimVermelho"));
        ai.add(jsonObject.getInt("trutaArcoIris"));
        ai.add(jsonObject.getInt("achiga"));
        this.respostas.put(23, ai);
        ai=new ArrayList<Integer>();

        //Flora
        ai.add(jsonObject.getInt("salgueiral"));
        ai.add(jsonObject.getInt("amial"));
        ai.add(jsonObject.getInt("freixal"));
        ai.add(jsonObject.getInt("choupal"));
        ai.add(jsonObject.getInt("ulmeiral"));
        ai.add(jsonObject.getInt("sanguinos"));
        ai.add(jsonObject.getInt("ladual"));
        ai.add(jsonObject.getInt("tramazeiras"));
        ai.add(jsonObject.getInt("carvalhal"));
        ai.add(jsonObject.getInt("sobreiral"));
        ai.add(jsonObject.getInt("azinhal"));
        this.respostas.put(24, ai);
        ai=new ArrayList<Integer>();

        //Estado de conservação do bosque ribeirinho (10m*10m)
        this.respostas.put(25, jsonObject.getInt("conservacaoBosqueRibeirinho"));

        //Espécies vegetação invasora
        ai.add(jsonObject.getInt("silvas"));
        ai.add(jsonObject.getInt("ervaDaFortuna"));
        ai.add(jsonObject.getInt("plumas"));
        ai.add(jsonObject.getInt("lentilhaDaAgua"));
        ai.add(jsonObject.getInt("pinheirinha"));
        ai.add(jsonObject.getInt("jacintoDeAgua"));
        this.respostas.put(26, ai);
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
        this.respostas.put(-1,calendar.MINUTE+"-"+calendar.HOUR+"-"+calendar.DAY_OF_MONTH+"-"+calendar.MONTH+"-"+calendar.YEAR);

    }

    /**
     * retorna um arraylist de todos os forms
     * @param c
     * @return
     * @throws IOException
     */
    public static ArrayList<HashMap<Integer,Object>> loadFromIRR(Context c) throws IOException {


        File f = new File(c.getFilesDir()+File.separator+"filipe");
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


        File f = new File(c.getFilesDir()+File.separator+"filipe");
        if(!f.exists())
            f.mkdirs();

        Calendar calendar = Calendar.getInstance();
        File files = new File(f.getAbsolutePath(),"form-"+System.currentTimeMillis()+".dat");
        if(!files.exists())
            files.createNewFile();
        Log.d("Files", "Size: " + files.getAbsolutePath());

        form_irr.setDate();
        form_irr.respostas.put(-2, files.getName());

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



    public static boolean uploadFormIRR(Context c, Form_IRR form_irr){

        File f = new File(c.getFilesDir()+File.separator+"filipe");
        if(!f.exists())
            f.mkdirs();


        try {
            DB_functions.saveForm(Form_functions.getUser(c)[0],
                    Form_functions.getUser(c)[1],
                    form_irr);
            File file = new File(f.getAbsolutePath(),(String) form_irr.getRespostas().get(-2));
            boolean deleted = file.delete();
            return deleted;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }
}
