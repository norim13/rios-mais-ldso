package ldso.rios.Form.IRR;

import android.content.Context;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.io.IOException;
import java.io.Serializable;

/**
 * Created by filipe on 24/11/2015.
 */
public abstract class Pergunta implements Serializable {

    private static final long serialVersionUID = -1519284721455962759L;
    protected String[] options;                     //opcoes de resposta
    protected String title;
    protected String subtitle;
    protected EditText other;                       //Edit text para escrever "outros"
    protected String other_text;
    protected Boolean other_option;                 //se é preciso ter o campo de "outros"
    protected Boolean obly;                         //se é obrigatório responder
    protected Object response;                      //resposta
    protected LinearLayout linearLayout;            //layout onde se inserem as perguntas
    protected Context context;                      //context do layout (activity)
    protected int[] images;

    /**
     * Cria pergunta
     * @param options Array de string de opções
     * @param images Array de int (R.drawable.X) de imagens
     * @param title String Titulo
     * @param subtitle String subtitulo
     * @param obly Booleano utilizador par averificar se uma pergunta é obrigatoria
     * @param other_option  Booleano para verificar se ha um campo extra para input de texto "outros"
     */
    public Pergunta(String[] options, int[] images,String title, String subtitle, Boolean obly, Boolean other_option) {
        this.options = options;
        this.title = title;
        this.subtitle = subtitle;
        this.obly = obly;
        this.other_option = other_option;
        this.images=images;
    }

    /**
     * Gera os componentes no ecra de uma pergunta no ecra de responder/editar
     * @param linearLayout
     * @param context
     * @throws IOException
     */
    public abstract void generate(LinearLayout linearLayout,Context context) throws IOException;           //generates the activity and questions

    /**
     * Gera os componentes no ecra de uma pergunta no ecra de ver
     * @param linearLayout
     * @param context
     * @throws IOException
     */
    public abstract void generateView(LinearLayout linearLayout,Context context) throws IOException;       //generates the activity and questions

    /**
     * Retorna as repsostas
     */
    public abstract void getAnswer();                                                    //gets the answers

    /**
     * Força a resposta a uma pergunta
     */
    public abstract void forceresponse();                                               //forces the answers

    /**
     *
     * @return list of edittex/radiobuttons/checkboxes/seek/complex
     */
    public abstract Object getList();                                                   //return list of edittex/radiobuttons/checkboxes/seek/complex

    /**
     * Obter resposta de uma pergunta
     * @return Resposta
     */
    public Object getResponse(){
        this.getAnswer();
     //   Log.e("respota no pergunta",this.response.toString());
        return this.response;
    }

    /**
     * Devolve as opcoes de uma pergunta
     * @return Opcoes de uma pergunta
     */
    public String[] getOptions(){
        return this.options;
    }

    /**
     * setAnswer
     */
    public abstract void setAnswer();                                                   //set the answers (for edit forms porpuses)

    /**
     * setAnswers
     * @param o repsota
     * @param other "other"
     */
    public void setAnswer(Object o, String other){
        this.response=o;
        this.other_text=other;

    };
}
