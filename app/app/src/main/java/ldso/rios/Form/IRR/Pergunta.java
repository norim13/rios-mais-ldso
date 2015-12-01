package ldso.rios.Form.IRR;

import android.content.Context;
import android.widget.EditText;
import android.widget.LinearLayout;

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
    protected Boolean other_option;                 //se é preciso ter o campo de "outros"
    protected Boolean obly;                         //se é obrigatório responder
    protected Object response;                      //resposta
    protected LinearLayout linearLayout;            //layout onde se inserem as perguntas
    protected Context context;                      //context do layout (activity)

    public Pergunta(String[] options, String title, String subtitle, Boolean obly, Boolean other_option) {
        this.options = options;
        this.title = title;
        this.subtitle = subtitle;
        this.obly = obly;
        this.other_option = other_option;
    }

    public abstract void generate(LinearLayout linearLayout,Context context);           //generates the activity and questions
    public abstract void generateView(LinearLayout linearLayout,Context context);       //generates the activity and questions

    public abstract void getAnswer();                                                   //gets the answers
    public abstract void forceresponse();                                               //forces the answers
    public abstract Object getList();                                                   //return list of edittex/radiobuttons/checkboxes/seek/complex

    public Object getResponse(){
        this.getAnswer();
        return this.response;
    }                                                     //returns the answers
    public String[] getOptions(){
        return this.options;
    }

    public abstract void setAnswer();                                                   //set the answers (for edit forms porpuses)
    public void setAnswer(Object o){
        this.response=o;

    };
}
