package ldso.rios.Form.IRR.Perguntas;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

import ldso.rios.Form.IRR.Pergunta;
import ldso.rios.Form.Form_functions;

/**
 * Created by filipe on 24/11/2015.
 */
public class radioPergunta extends Pergunta implements Serializable {

    private static final long serialVersionUID = 1325410386287654205L;
    protected ArrayList<RadioButton> radio_list;

    public radioPergunta(String[] options, String[] images, String title, String subtitle, Boolean obly, Boolean other_option) {
        super(options, images, title, subtitle, obly, other_option);
    }

    @Override
    public void generate(LinearLayout linearLayout, Context context) throws IOException {
        this.linearLayout=linearLayout;
        this.context=context;

        this.radio_list= Form_functions.createRadioButtons(this.options,this.images,this.linearLayout,this.context);

    }

    @Override
    public void generateView(LinearLayout linearLayout, Context context) throws IOException {

        LinearLayout.LayoutParams radioParams;
        radioParams = new LinearLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        radioParams.setMargins(0, 100, 0, 20);
        TextView textView=new TextView(context);
        textView.setText(this.title + " - " + this.subtitle);
        textView.setTextColor(Color.BLACK);
        textView.setTextSize(20);
        textView.setLayoutParams(radioParams);

        linearLayout.addView(textView);
        generate(linearLayout,context);
        for (RadioButton r:this.radio_list)
            r.setEnabled(false);
    }


    @Override
    public void getAnswer() {
        if (this.radio_list==null)
            this.response=0;
        else
            this.response=Form_functions.getRadioButtonOption(this.radio_list);
    }

    @Override
    public void forceresponse() {

    }

    @Override
    public void setAnswer() {
        if(this.response!=null)
        {
            Log.e("fomr","nao é nula");
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
