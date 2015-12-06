package ldso.rios.Form.IRR.Perguntas;

import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;

import ldso.rios.Form.IRR.Pergunta;
import ldso.rios.Form.Form_functions;

/**
 * Created by filipe on 24/11/2015.
 */
public class editPergunta extends Pergunta implements Serializable {

    private static final long serialVersionUID = -6544844231796807804L;
    ArrayList<Float[]> minmax;
    ArrayList<EditText> edit_list;
    Boolean range;
    TextView tv;
    TextView tv2;



    public editPergunta(String[] options,int[]images, String title, String subtitle, Boolean obly, Boolean other_option) {
        super(options,images, title, subtitle, obly, other_option);
        range=false;
    }

    public editPergunta(String[] options, int[] images, String title, String subtitle, Boolean obly, ArrayList<Float[]> minmax, boolean other_option) {
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


        if(this.options[0].equals("Largura da superfície da água (L) (m):"))
        {

            String[] extra={"Seccao","Caudlal"};

            for(EditText e :this.edit_list)
            e.setText(""+0);





            tv= new TextView(this.context);
            tv2= new TextView(this.context);

            tv.setText("Mira");
            tv2.setText("Mira");
            tv2.setId(tv.getId()+1);

            this.linearLayout.addView(tv);
            this.linearLayout.addView(tv2);




            for (int i=0;i<this.edit_list.size();i++)
            {
                this.edit_list.get(i).addTextChangedListener(new TextWatcher() {
                    public void afterTextChanged(Editable str) {
                        Log.e("form", "Editable: " + str.toString());
                        tv.setText("Olaaaaaaa" + edit_list.get(1).toString());

                        if(edit_list.get(0).toString().length()>0 && edit_list.get(1).toString().length()>0){
                            //Log.e("form","dentro do if");
                            try {
                                Log.e("form","dentro do try "+edit_list.get(0).toString()+" "+edit_list.get(1).toString()+" "+edit_list.get(2).toString());
                                float s= Float.parseFloat(edit_list.get(0).toString())*Float.parseFloat(edit_list.get(1).toString());
                                Log.e("form","dentro do try ainda");
                                tv.setText("Olaaaaaaa");
                                tv2.setText("olaaaaaaaaaaaaaaaaaaaaaaa");



                                try {
                                    if(edit_list.get(2).toString().length()>0)
                                    {
                                        float c=s*Float.parseFloat(edit_list.get(2).toString());
                                        //editText.get(1).setText(Html.fromHtml(c + " m<sup>3</sup>/s"));
                                    }
                                }
                                catch (Exception e){
                                    e.printStackTrace();

                                }
                            }
                            catch (Exception e){
                                e.printStackTrace();

                            }

                        }
                        Log.e("form", "entrou no changed");
                    }

                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                    }
                });
            }

        }

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

    public editPergunta(String[] options,int[] images, String title, String subtitle, Boolean obly, Boolean other_option, ArrayList<Float[]> minmax) {
        super(options, images, title,subtitle, obly, other_option);
        this.minmax = minmax;
        range=true;
    }




    @Override
    public void getAnswer() {
        if (this.edit_list==null || Form_functions.getEditTexts(this.edit_list).get(0)==null){
            ArrayList<Float> resposta_null=new ArrayList<Float>();
            if(range){
                for(Float[] f :minmax)
                    resposta_null.add(f[0]);

            }
            else {
                for (String s : options)
                    resposta_null.add(0f);

            }

            this.response= resposta_null;
        }
        else {
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
