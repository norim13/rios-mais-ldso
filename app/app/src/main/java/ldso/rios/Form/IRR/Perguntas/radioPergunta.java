package ldso.rios.Form.IRR.Perguntas;

import android.content.Context;
import android.graphics.Color;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

import ldso.rios.Form.Form_functions;
import ldso.rios.Form.IRR.Pergunta;

/**
 * Created by filipe on 24/11/2015.
 */
public class radioPergunta extends Pergunta implements Serializable {

    private static final long serialVersionUID = 1325410386287654205L;
    protected ArrayList<RadioButton> radio_list;

    public radioPergunta(String[] options, int[] images, String title, String subtitle, Boolean obly, Boolean other_option) {
        super(options, images, title, subtitle, obly, other_option);
    }

    @Override
    public void generate(LinearLayout linearLayout, Context context) throws IOException {
        this.linearLayout=linearLayout;
        this.context=context;

        Form_functions.createTitleSubtitle(this.title, this.subtitle, linearLayout, context);

        this.radio_list= Form_functions.createRadioButtons(this.options,this.images,this.linearLayout,this.context);

        if (this.other_option) {
            this.other = new EditText(context);
            this.other.setHint("Outro");
            linearLayout.addView(other);
        }

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
        this.linearLayout=linearLayout;
        this.context=context;

        this.radio_list= Form_functions.createRadioButtons(this.options,null,this.linearLayout,this.context);
        for (RadioButton r:this.radio_list)
            r.setEnabled(false);
    }


    @Override
    public void getAnswer() {
        if (this.radio_list==null){
            //Log.e("é nulla a radio lisr","fica a nula");
            if (this.response==null) {
                this.response = 0;
                if (other_option)
                    this.other_text = "";
            }
        }

        else {
            this.response = Form_functions.getRadioButtonOption(this.radio_list);
            //Log.e("a resposta sera",(int)this.response+"");
            if (this.other_option)
            this.other_text=this.other.getText().toString();
        }
    }

    @Override
    public void forceresponse() {

    }

    @Override
    public void setAnswer() {
        if(this.response!=null)
        {
            //Log.e("fomr","nao é nula");
            if((int)this.response!=0)
            {
                int num=(int)this.response -1;
                radio_list.get(num).setChecked(true);
            }
            if (this.other_option)
                this.other.setText(this.other_text);
        }

    }

    @Override
    public Object getList() {
        return this.radio_list;
    }

}
