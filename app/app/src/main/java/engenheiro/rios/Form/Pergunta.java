package engenheiro.rios.Form;

import android.content.Context;
import android.widget.EditText;
import android.widget.LinearLayout;

/**
 * Created by filipe on 24/11/2015.
 */
public abstract class Pergunta {

    protected String[] options;
    protected String title;
    protected String subtitle;
    protected EditText other;
    protected Boolean other_option;
    protected Boolean obly;
    protected Object response;
    protected LinearLayout linearLayout;
    protected Context context;

    public Pergunta(String[] options, String title, String subtitle, Boolean obly, Boolean other_option) {
        this.options = options;
        this.title = title;
        this.subtitle = subtitle;
        this.obly = obly;
        this.other_option = other_option;
    }

    public abstract void generate(LinearLayout linearLayout,Context context);          //generates the activity and questions
    public abstract void getAnswer();         //gets the answers
    public abstract void forceresponse();       //forces the answers
    public abstract void setAnswer();
    public abstract Object getList();

    public Object getResponse(){
        this.getAnswer();
        return this.response;
    }

    public String[] getOptions(){
        return this.options;
    }




}
