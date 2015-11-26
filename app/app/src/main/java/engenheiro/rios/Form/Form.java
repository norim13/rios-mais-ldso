package engenheiro.rios.Form;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by filipe on 24/11/2015.
 */
public class Form {

    public ArrayList<Pergunta> getPerguntas() {

        return perguntas;
    }

    public ArrayList<Pergunta> perguntas;

    public HashMap<Integer,Object> respostas;

    public void Form(){
        perguntas=new ArrayList<Pergunta>();
        respostas=new HashMap<Integer, Object>();
    }


    public void fillAnswer(int number){
        this.perguntas.get(number).getAnswer();
        if(this.perguntas.get(number).getResponse()!=null && this.respostas!=null) {
            int novo_num=number+1;
            this.respostas.put((Integer) novo_num,this.perguntas.get(number).getResponse());

        }
        else
            Log.e("form","error no fillAnswer");

        for (int i=1;i<10;i++)
        {
            try {
                Log.e("resposta",i+"-"+this.respostas.get(i).toString());

            }
            catch (Exception e){
                Log.e("resposta",i+"-");

            }
        }
        Log.e("resposta","--------");
    }



}
