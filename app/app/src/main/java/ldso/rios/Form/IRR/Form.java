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

    public HashMap<Integer, String> getOther_response() {
        return other_response;
    }

    public String getTeste() {
        return teste;
    }

    String teste;

    public ArrayList<Pergunta> perguntas;

    public HashMap<Integer,Object> respostas;
    protected HashMap<Integer,String> other_response;


    public void Form(){
        perguntas=new ArrayList<Pergunta>();
        respostas=new HashMap<Integer, Object>();
        teste="mira";
    }


    public void fillAnswer(int number){

        //this.perguntas.get(number).getResponse();
        //Log.e("resposta:",this.perguntas.get(number).getResponse().toString()+"");
        int novo_num=number+1;

        if(this.perguntas.get(number).getResponse()!=null && this.respostas!=null) {
            this.respostas.put((Integer) novo_num, this.perguntas.get(number).getResponse());
           // Log.e("a reposta é portanto:",this.perguntas.get(number).getResponse()+"");
           // Log.e("teste", "a resposta nao é nula");
            if (this.perguntas.get(number).other_option) {
                this.other_response.put(novo_num, this.perguntas.get(number).other_text);
                Log.e("fillAnswer no form IRR",novo_num+" "+this.perguntas.get(number).other_text);
            }

        }
        else
            Log.e("teste","a resposta é nula");

        //  else
          //  Log.e("form","error no fillAnswer");


    }

    public void setRespostas(HashMap<Integer, Object> respostas,HashMap<Integer, String> respotas_outros) {
        this.respostas = respostas;
        this.other_response=respotas_outros;
        for(int i=0;i<32;i++) {
            try {
                perguntas.get(i).setAnswer(respostas.get(i + 1), respotas_outros.get(i + 1));

            }catch (Exception e){
                perguntas.get(i).setAnswer(respostas.get(i + 1), "");
            }
        }
        Log.e("acbou","");
    }

    public void fillAnswers()
    {
        for (int i=1;i<=this.getPerguntas().size();i++)
            try {
                fillAnswer(i-1);
                //Log.e("resposta", i + "-" + this.respostas.get(i).toString()+"-" + this.other_response.get(i).toString());

            } catch (Exception e) {
                Log.e("resposta", i + "-");

            }
        Log.e("resposta","--------");

    }

    public HashMap<Integer, Object> getRespostas() {
        return respostas;
    }



}
