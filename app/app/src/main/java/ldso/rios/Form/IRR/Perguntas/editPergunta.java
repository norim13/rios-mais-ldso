package ldso.rios.Form.IRR.Perguntas;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;

import ldso.rios.Form.Form_functions;
import ldso.rios.Form.IRR.Pergunta;

/**
 * Created by filipe on 24/11/2015.
 */
public class EditPergunta extends Pergunta implements Serializable {

    private static final long serialVersionUID = -6544844231796807804L;
    ArrayList<Float[]> minmax;
    ArrayList<EditText> edit_list;
    Boolean range;
    TextView tv;
    TextView tv2;


    /**
     * Cria uma EditPergunta sem minmax
     * @param options
     * @param images
     * @param title
     * @param subtitle
     * @param obly
     * @param other_option
     */
    public EditPergunta(String[] options, int[]images, String title, String subtitle, Boolean obly, Boolean other_option) {
        super(options,images, title, subtitle, obly, other_option);
        range=false;
    }

    /**
     * Cria uma edit pergunta com valores min e max de range
     * @param options
     * @param images
     * @param title
     * @param subtitle
     * @param obly
     * @param minmax    ArrayList<Float[min,max]>
     * @param other_option
     */
    public EditPergunta(String[] options, int[] images, String title, String subtitle, Boolean obly, ArrayList<Float[]> minmax, boolean other_option) {
        super(options,images, title, subtitle, obly, other_option);
        range=true;
        this.minmax=minmax;
    }

    @Override
    public void generate(LinearLayout linearLayout, Context context) {
        this.linearLayout=linearLayout;
        this.context=context;

        Form_functions.createTitleSubtitle(this.title, this.subtitle, linearLayout, context);


        if(range)
            this.edit_list= Form_functions.createEditText(this.options,this.linearLayout,this.context,this.minmax);
        else
            this.edit_list= Form_functions.createEditText(this.options,this.linearLayout,this.context);




    }

    @Override
    public void generateView(LinearLayout linearLayout, Context context) {
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
        for(EditText edit: this.edit_list)
            edit.setEnabled(false);
    }

    public EditPergunta(String[] options, int[] images, String title, String subtitle, Boolean obly, Boolean other_option, ArrayList<Float[]> minmax) {
        super(options, images, title,subtitle, obly, other_option);
        this.minmax = minmax;
        range=true;
    }




    @Override
    public void getAnswer() {
        if (this.edit_list==null || Form_functions.getEditTexts(this.edit_list).get(0)==null){
            Log.e("getAnswerEdit","entrou no null---------");
            ArrayList<Float> resposta_null=new ArrayList<Float>();
            if(this.response==null) {
                if (range) {
                    for (Float[] f : minmax)
                        resposta_null.add(f[0]);

                } else {
                    for (String s : options)
                        resposta_null.add(0f);

                }

                this.response = resposta_null;
            }
        }
        else {
            Log.e("getAnswerEdit","nao entrou no null---------");
            this.response = Form_functions.getEditTexts(this.edit_list);
        }

    }

    @Override
    public void forceresponse() {

    }

    @Override
    public void setAnswer() {
        if(this.response!=null)
        {
            ArrayList<Float> al= (ArrayList<Float>) this.response;
            for (int i=0;i<al.size();i++)
                this.edit_list.get(i).setText(al.get(i)+"");
        }
    }

    @Override
    public Object getList() {
        return this.edit_list;
    }


}
