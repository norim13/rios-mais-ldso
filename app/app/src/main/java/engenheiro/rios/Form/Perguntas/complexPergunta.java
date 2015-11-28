package engenheiro.rios.Form.Perguntas;

import android.content.Context;
import android.util.Log;
import android.widget.LinearLayout;

import java.util.ArrayList;

import engenheiro.rios.Form.Pergunta;

/**
 * Created by filipe on 27/11/2015.
 */
public class complexPergunta extends Pergunta {

    ArrayList<Pergunta> perguntas;

    /**
     *
     * @param options   complex string
     * @param title
     * @param subtitle
     * @param obly
     * @param other_option
     *
     *  "-" indicates that the following string is the subtitle
     *  "|" indicates if there is or not "other option" usually an Edit Text Box to write other options
     *  string of options example:  {"-","Fauna","Salamandra","Sapo","|","-","Repteis","Lagarto","Cobra","Cagado"}
     *  subtitle: Fauna   options: Salamandra, Sapo   other_option: true
     *  subtitle: Repteis options: Lagarto, Cobra, Cagado
     *
     */

    public complexPergunta(String[] options, String title, String subtitle, Boolean obly, Boolean other_option) {
        super(new String[]{"ola"}, title, subtitle, obly, other_option);
        perguntas=new ArrayList<Pergunta>();
        readOptions(options);
    }

    private void readOptions(String[] options) {
        ArrayList<String> single_options= new ArrayList<String>();
        String single_subtitle="";
        Boolean single_other_option=false;
        for (int i =0;i< options.length;i++)
        {
            if(options[i].equals("-")&&single_options.size()!=0){
                String[] stringArray= new String[single_options.size()];
                stringArray=single_options.toArray(stringArray);
                Log.e("formirr", stringArray[0]);
                this.perguntas.add(new checkPergunta(stringArray,"",single_subtitle,this.obly,single_other_option));
                single_options=new ArrayList<String>();
                i++;
                single_subtitle=options[i];
                single_other_option=false;
                Log.e("formirr","i"+i+" Option:"+options[i]+" 0");
            }
            else if (options[i].equals("-")){
                Log.e("formirr","i"+i+" Option:"+options[i]+" 1 antes");
                i++;
                single_subtitle=options[i];
                Log.e("formirr","i"+i+" Option:"+options[i]+" 1 depois");
            }
            else if (options[i].equals("|"))
            {
                other_option=true;
                Log.e("formirr","i"+i+" Option:"+options[i]+" 2");
            }
            else {
                single_options.add(options[i]);
                Log.e("formirr", "i" + i + " Option:" + options[i] + " 3");
            }

        }
        String[] stringArray= new String[single_options.size()];
        stringArray=single_options.toArray(stringArray);
        this.perguntas.add(new checkPergunta(stringArray, "", single_subtitle, this.obly, single_other_option));

        Log.e("formirr", "size:" + this.perguntas.size());
        for(Pergunta p :this.perguntas)
        {
            String s="";
            for(String op : p.getOptions())
                s+=op+" ";
            Log.e("formirr",s);
        }
    }

    @Override
    public void generate(LinearLayout linearLayout, Context context) {
        Log.e("formirr","Entrou no generate");
        for(int i =0;i<perguntas.size();i++){
            perguntas.get(i).generate(linearLayout,context);
        }

    }

    @Override
    public void getAnswer() {
        ArrayList< ArrayList<Integer> > respostas = new ArrayList< ArrayList<Integer> >();
        if (respostas==null) return;
        for (int i=0;i<this.perguntas.size();i++){
            this.perguntas.get(i).getAnswer();
            respostas.add((ArrayList<Integer>) this.perguntas.get(i).getResponse());
        }
        this.response=respostas;
        return;
    }

    @Override
    public void forceresponse() {

    }

    @Override
    public void setAnswer() {
        ArrayList< ArrayList<Integer> > respostas= (ArrayList<ArrayList<Integer>>) this.response;
        if(respostas==null)
            return;
        for(Pergunta p:this.perguntas)
            p.setAnswer();
    }

    @Override
    public Object getList() {
        return this.perguntas;
    }
}
