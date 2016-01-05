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
    public ArrayList<Pergunta> perguntas;               //array de perguntas
    public HashMap<Integer, Object> respostas;           //hashmap com as repsostas numero->resposta
    protected HashMap<Integer, String> other_response;   //hasmap com as repsostas "outros" numero->resposta
    RadioButton margEsquerda;
    RadioButton margDireita;
    RadioButton currLoc;
    RadioButton selctLoc;
    EditText nomeRioEditText;
    Float lat_curr, lon_curr;
    Float lat_sel, lon_sel;
    public Float lat_final, lon_final;
    public String nomeRio = "";
    public int margem = 1;
    Boolean current_location;                           //boolean true->currlocation false->selectedlocation null->nao foi selecionado
    ArrayList<String> arrayListURI;                     //arraylist de URI (imagens escolhidas)


    /**
     * Cria e inicializa um Form
     */
    public void Form() {
        perguntas = new ArrayList<Pergunta>();
        respostas = new HashMap<Integer, Object>();
        lat_curr = lon_curr = lat_sel = lon_sel = 0f;
        lat_sel = 0f;
        arrayListURI = new ArrayList<String>();
    }


    /**
     * Preenche uma pergunda
     *
     * @param number numero da pergunta
     */
    public void fillAnswer(int number) {

        int novo_num = number + 1;

        if (this.perguntas.get(number).getResponse() != null && this.respostas != null) {
            this.respostas.put((Integer) novo_num, this.perguntas.get(number).getResponse());
            if (this.perguntas.get(number).other_option)
                this.other_response.put(novo_num, this.perguntas.get(number).other_text);
        }
    }

    /**
     * Faz set das respostas
     *
     * @param respostas       Hashmap com as reposstas
     * @param respotas_outros Hashmap com as repostas "Outros"
     */
    public void setRespostas(HashMap<Integer, Object> respostas, HashMap<Integer, String> respotas_outros) {
        this.respostas = respostas;
        this.other_response = respotas_outros;
        if (arrayListURI == null)
            arrayListURI = new ArrayList<String>();

        ArrayList<Float> arrayLocation = (ArrayList<Float>) respostas.get(0);
        if (arrayLocation != null) {
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

        ArrayList<Object> arrayNomeEMargem = (ArrayList<Object>) respostas.get(-1);
        if (arrayNomeEMargem != null) {
            this.nomeRio = (String) arrayNomeEMargem.get(0);
            this.margem = (int) arrayNomeEMargem.get(1);
        }
        for (int i = 0; i < 32; i++) {
            try {
                perguntas.get(i).setAnswer(respostas.get(i + 1), respotas_outros.get(i + 1));
            } catch (Exception e) {
                perguntas.get(i).setAnswer(respostas.get(i + 1), "");
            }
        }
    }

    /**
     * Preenche todas as perguntas
     */
    public void fillAnswers() {

        try {
            if (currLoc.isChecked() || selctLoc.isChecked()) {
                if (currLoc.isChecked()) {
                    lat_curr = Float.valueOf(currLoc.getText().toString().split("Atual: ")[1].split(";")[0]);
                    lon_curr = Float.valueOf(currLoc.getText().toString().split("Atual: ")[1].split(";")[1]);
                    current_location = true;
                } else {
                    Log.e("lat_sel", (selctLoc.getText().toString()));
                    lat_sel = Float.valueOf(selctLoc.getText().toString().split("Escolhida: ")[1].split(";")[0]);
                    lon_sel = Float.valueOf(selctLoc.getText().toString().split("Escolhida: ")[1].split(";")[1]);
                    current_location = false;
                }
            } else {
                lat_curr = lon_curr = lat_sel = lon_sel = 0f;
                current_location = null;

            }
        } catch (Exception e) {

        }

        try {
            if (margEsquerda.isChecked() || margDireita.isChecked()) {
                if (margEsquerda.isChecked()) {
                    margem = 1;

                } else {
                    margem = 2;
                }
            }
        } catch (Exception e) {
        }

        try {
            this.nomeRio = this.nomeRioEditText.getText().toString();
        } catch (Exception e) {
        }

        try {
            if (margEsquerda.isChecked() || margDireita.isChecked()) {
                if (margEsquerda.isChecked()) {
                    margem = 0;
                    current_location = true;
                } else {
                    lat_sel = Float.valueOf(selctLoc.getText().toString().split("Escolhida: ")[1].split(";")[0]);
                    lon_sel = Float.valueOf(selctLoc.getText().toString().split("Escolhida: ")[1].split(";")[1]);
                    current_location = false;
                }
            } else {
                lat_curr = lon_curr = lat_sel = lon_sel = 0f;
                current_location = null;
            }
        } catch (Exception e) {
        }


        for (int i = 1; i <= this.getPerguntas().size(); i++)
            try {
                fillAnswer(i - 1);
            } catch (Exception e) {
            }

        ArrayList<Float> arrayLocation = new ArrayList<Float>();
        arrayLocation.add(this.lat_curr);
        arrayLocation.add(this.lon_curr);
        arrayLocation.add(this.lat_sel);
        arrayLocation.add(this.lon_sel);
        lat_final = lon_final = 0f;
        if (this.current_location == null)
            arrayLocation.add(0f);
        else if (this.current_location) {
            arrayLocation.add(1f);
            this.lat_final = this.lat_curr;
            this.lon_final = this.lon_curr;
        } else {
            arrayLocation.add(2f);
            this.lat_final = this.lat_sel;
            this.lon_final = this.lon_sel;
        }

        this.respostas.put(0, arrayLocation);                               //localização fica no 0

        ArrayList<Object> arrayNomeEMargem = new ArrayList<Object>();
        arrayNomeEMargem.add(this.nomeRio);
        arrayNomeEMargem.add(this.margem);

        this.respostas.put(-1, arrayNomeEMargem);                           //nome da margem fica no -1
    }




    public HashMap<Integer, Object> getRespostas() {
        return respostas;
    }

    public ArrayList<String> getArrayListURI() {
        return arrayListURI;
    }

    public ArrayList<Pergunta> getPerguntas() {
        return perguntas;
    }

    public HashMap<Integer, String> getOther_response() {
        return other_response;
    }

}
