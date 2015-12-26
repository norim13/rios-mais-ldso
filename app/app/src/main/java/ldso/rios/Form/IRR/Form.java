package ldso.rios.Form.IRR;

import android.util.Log;
import android.widget.EditText;
import android.widget.RadioButton;

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


    RadioButton margEsquerda;
    RadioButton margDireita;
    RadioButton currLoc;
    RadioButton selctLoc;
    EditText nomeRioEditText;

    Float lat_curr,lon_curr;
    Float lat_sel,lon_sel;
    public Float lat_final,lon_final;
    public String nomeRio="";
    public int margem=1;
    Boolean current_location;

    public ArrayList<String> getArrayListURI() {
        return arrayListURI;
    }

    ArrayList<String> arrayListURI;





    public void Form(){
        perguntas=new ArrayList<Pergunta>();
        respostas=new HashMap<Integer, Object>();
        lat_curr=lon_curr=lat_sel=lon_sel=0f;
        lat_sel=0f;
        teste="mira";
        arrayListURI=new ArrayList<String>();
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
        Log.e("entrou aqui","");
        this.respostas = respostas;
        this.other_response=respotas_outros;

        Log.e("setLocation","");
        ArrayList<Float> arrayLocation = (ArrayList<Float>) respostas.get(0);
        if(arrayLocation!=null) {
            this.lat_curr = arrayLocation.get(0);
            this.lon_curr = arrayLocation.get(1);
            this.lat_sel = arrayLocation.get(2);
            this.lon_sel = arrayLocation.get(3);
            Float tempCurrent_location = arrayLocation.get(4);
            if (tempCurrent_location == 0f) {
                this.current_location = null;
            } else if (tempCurrent_location == 1f)
                this.current_location = true;
            else
                this.current_location = false;
        }

        Log.e("setNome e Margem","");
        ArrayList<Object> arrayNomeEMargem = (ArrayList<Object>) respostas.get(-1);
        if(arrayNomeEMargem!=null) {
            this.nomeRio = (String) arrayNomeEMargem.get(0);
            this.margem = (int) arrayNomeEMargem.get(1);
        }

        Log.e("setPerguntas","");

        for(int i=0;i<32;i++) {
            String show="";
            show+="i="+i+" pergunta:"+perguntas.get(i).subtitle;

            try {
                Log.e("entrou no for","");
                perguntas.get(i).setAnswer(respostas.get(i + 1), respotas_outros.get(i + 1));


            }catch (Exception e){
                perguntas.get(i).setAnswer(respostas.get(i + 1), "");
            }
            Log.e("",show);
        }
        Log.e("acbou","");
    }

    public void fillAnswers()
    {

        try {
            if(currLoc.isChecked() || selctLoc.isChecked())
            {
                if (currLoc.isChecked())
                {
                    lat_curr = Float.valueOf(currLoc.getText().toString().split("Atual: ")[1].split(";")[0]);
                    lon_curr = Float.valueOf(currLoc.getText().toString().split("Atual: ")[1].split(";")[1]);
                    current_location=true;

                }
                else {
                    Log.e("lat_sel",(selctLoc.getText().toString()));
                    lat_sel = Float.valueOf(selctLoc.getText().toString().split("Escolhida: ")[1].split(";")[0]);
                    lon_sel = Float.valueOf(selctLoc.getText().toString().split("Escolhida: ")[1].split(";")[1]);
                    current_location=false;
                }
            }
            else {
                lat_curr=lon_curr=lat_sel=lon_sel=0f;
                current_location=null;

            }
        }
        catch (Exception e){

        }

        try {
            if(margEsquerda.isChecked() || margDireita.isChecked())
            {
                if (margEsquerda.isChecked())
                {
                    margem=1;

                }
                else {
                    margem=2;
                }
            }
        }
        catch (Exception e){

        }
        try {
            this.nomeRio = this.nomeRioEditText.getText().toString();
        }
        catch (Exception e){

        }
        try {
            if(margEsquerda.isChecked() || margDireita.isChecked())
            {
                if (margEsquerda.isChecked())
                {
                    margem=0;
                    current_location=true;

                }
                else {
                    Log.e("lat_sel",(selctLoc.getText().toString()));
                    lat_sel = Float.valueOf(selctLoc.getText().toString().split("Escolhida: ")[1].split(";")[0]);
                    lon_sel = Float.valueOf(selctLoc.getText().toString().split("Escolhida: ")[1].split(";")[1]);
                    current_location=false;
                }
            }
            else {
                lat_curr=lon_curr=lat_sel=lon_sel=0f;
                current_location=null;

            }
        }
        catch (Exception e){

        }



    for (int i=1;i<=this.getPerguntas().size();i++)
            try {
        fillAnswer(i-1);
        //Log.e("resposta", i + "-" + this.respostas.get(i).toString()+"-" + this.other_response.get(i).toString());

    } catch (Exception e) {
        //Log.e("resposta", i + "-");

    }
    //Log.e("resposta","--------");

        ArrayList<Float> arrayLocation = new ArrayList<Float>();
        arrayLocation.add(this.lat_curr);
        arrayLocation.add(this.lon_curr);
        arrayLocation.add(this.lat_sel);
        arrayLocation.add(this.lon_sel);
        lat_final=lon_final=0f;
        if(this.current_location==null)
            arrayLocation.add(0f);
        else if (this.current_location){
            arrayLocation.add(1f);
            this.lat_final=this.lat_curr;
            this.lon_final=this.lon_curr;
        }

        else {
            arrayLocation.add(2f);
            this.lat_final=this.lat_sel;
            this.lon_final=this.lon_sel;
        }




        this.respostas.put(0,arrayLocation);


        ArrayList<Object> arrayNomeEMargem= new ArrayList<Object>();
        arrayNomeEMargem.add(this.nomeRio);
        arrayNomeEMargem.add(this.margem);

        this.respostas.put(-1,arrayNomeEMargem);

}

    public HashMap<Integer, Object> getRespostas() {
        return respostas;
    }



}
