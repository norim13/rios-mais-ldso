package ldso.rios.Form.IRR.Perguntas;

import android.content.Context;
import android.graphics.Color;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;

import ldso.rios.Form.IRR.Pergunta;

/**
 * Created by filipe on 27/11/2015.
 */
public class complexPergunta extends Pergunta implements Serializable {

    private static final long serialVersionUID = 1902853229771784321L;
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
                this.perguntas.add(new checkPergunta(stringArray,"",single_subtitle,this.obly,single_other_option));
                single_options=new ArrayList<String>();
                i++;
                single_subtitle=options[i];
                single_other_option=false;
            }
            else if (options[i].equals("-")){
                i++;
                single_subtitle=options[i];
            }
            else if (options[i].equals("|"))
            {
                other_option=true;
            }
            else {
                single_options.add(options[i]);
            }

        }
        String[] stringArray= new String[single_options.size()];
        stringArray=single_options.toArray(stringArray);
        this.perguntas.add(new checkPergunta(stringArray, "", single_subtitle, this.obly, single_other_option));


    }

    @Override
    public void generate(LinearLayout linearLayout, Context context) {
        for(int i =0;i<perguntas.size();i++){
            perguntas.get(i).generate(linearLayout,context);
        }

    }

    @Override
    public void generateView(LinearLayout linearLayout, Context context) {

        LinearLayout.LayoutParams radioParams;
        radioParams = new LinearLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        radioParams.setMargins(0, 100, 0, 20);
        TextView textView=new TextView(context);
        textView.setText(this.title);
        textView.setTextColor(Color.BLACK);
        textView.setTextSize(20);
        textView.setLayoutParams(radioParams);
        linearLayout.addView(textView);

        for(int i =0;i<perguntas.size();i++){
            perguntas.get(i).generateView(linearLayout, context);
        }

    }

    @Override
    public void getAnswer() {
        ArrayList< ArrayList<Integer> > respostas = new ArrayList< ArrayList<Integer> >();
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
