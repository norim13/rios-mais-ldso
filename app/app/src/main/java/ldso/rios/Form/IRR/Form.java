package ldso.rios.Form.IRR;

import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by filipe on 24/11/2015.
 */
public class Form implements Serializable {

    private static final long serialVersionUID = 3308672663234178858L;

    public ArrayList<Pergunta> getPerguntas() {

        return perguntas;
    }


    public String getTeste() {
        return teste;
    }

    String teste;

    public ArrayList<Pergunta> perguntas;

    public HashMap<Integer,Object> respostas;

    public void Form(){
        perguntas=new ArrayList<Pergunta>();
        respostas=new HashMap<Integer, Object>();
        teste="mira";
    }


    public void fillAnswer(int number){
        this.perguntas.get(number).getAnswer();
        if(this.perguntas.get(number).getResponse()!=null && this.respostas!=null) {
            int novo_num=number+1;
            this.respostas.put((Integer) novo_num,this.perguntas.get(number).getResponse());
            Log.e("teste","a resposta nao é nula");

        }
        else
            Log.e("teste","a resposta é nula");

        //  else
          //  Log.e("form","error no fillAnswer");


    }

    public void setRespostas(HashMap<Integer, Object> respostas) {
        this.respostas = respostas;
        for(int i=0;i<32;i++)
            perguntas.get(i).setAnswer(respostas.get(i+1));
    }

    public void fillAnswers()
    {
        Log.e("Entrou aqui","entrou");
        for (int i=1;i<=this.getPerguntas().size();i++)
            try {
                fillAnswer(i-1);
                Log.e("resposta", i + "-" + this.respostas.get(i).toString());

            } catch (Exception e) {
                Log.e("resposta", i + "-");

            }
        Log.e("resposta","--------");

    }

    public HashMap<Integer, Object> getRespostas() {
        return respostas;
    }
}
