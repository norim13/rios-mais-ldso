package engenheiro.rios.Form.Perguntas;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import java.util.ArrayList;

import engenheiro.rios.Form.Pergunta;
import engenheiro.rios.IRR.Form_functions;

/**
 * Created by filipe on 24/11/2015.
 */
public class radioPergunta extends Pergunta {

    protected ArrayList<RadioButton> radio_list;

    public radioPergunta(String[] options, String title, String subtitle, Boolean obly, Boolean other_option) {
        super(options, title, subtitle, obly, other_option);
    }

    @Override
    public void generate(LinearLayout linearLayout, Context context) {
        this.linearLayout=linearLayout;
        this.context=context;

        this.radio_list= Form_functions.createRadioButtons(this.options,this.linearLayout,this.context);

    }


    @Override
    public void getAnswer() {
        if (this.radio_list==null)return;
        this.response=Form_functions.getRadioButtonOption(this.radio_list);
    }

    @Override
    public void forceresponse() {

    }

    @Override
    public void setAnswer() {
        if(this.response!=null)
        {
            if((int)this.response!=0)
            {
                int num=(int)this.response -1;
                radio_list.get(num).setChecked(true);
            }
        }

    }

    @Override
    public Object getList() {
        return this.radio_list;
    }

}
