package engenheiro.rios.Form.IRR.Perguntas;

import android.content.Context;
import android.graphics.Color;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;

import engenheiro.rios.Form.IRR.Pergunta;
import engenheiro.rios.Form.Form_functions;

/**
 * Created by filipe on 24/11/2015.
 */
public class seekPergunta extends Pergunta {

    int min;
    int max;
    protected ArrayList<SeekBar> seekList;
    protected ArrayList<TextView> seekListText;

    public seekPergunta(String[] options, String title, String subtitle, Boolean obly, Boolean other_option, int min, int max) {
        super(options, title, subtitle, obly, other_option);
        this.min = min;
        this.max = max;
    }

    @Override
    public void generate(LinearLayout linearLayout, Context context) {
        this.linearLayout=linearLayout;
        this.context=context;
        ArrayList<ArrayList> arrayList=Form_functions.createSeekbar(this.options,this.linearLayout,this.context, max);
        this.seekList=arrayList.get(0);
        this.seekListText=arrayList.get(1);
        for (int i=0;i<this.seekList.size();i++) {
            final int finalI = i;
            this.seekList.get(i).setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    // TODO Auto-generated method stub
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                    // TODO Auto-generated method stub
                }

                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    // TODO Auto-generated method stub
                    if (seekBar.getProgress()!=0)
                    seekListText.get(finalI).setText(options[finalI]+" "+seekBar.getProgress()+":");
                    else
                        seekListText.get(finalI).setText(options[finalI]+" N/A:");


                }
            });
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
        for(SeekBar s:this.seekList)
            s.setEnabled(false);
    }

    @Override
    public void getAnswer() {
        if (this.seekList==null)return;
        this.response=Form_functions.getSeekbar(this.seekList);

    }

    @Override
    public void forceresponse() {

    }

    @Override
    public void setAnswer() {
        ArrayList<Integer> numbers= (ArrayList<Integer>) this.response;

        if(numbers==null)
            return;
        for (int i=0;i<this.seekList.size();i++)
            this.seekList.get(i).setProgress(numbers.get(i));


    }

    @Override
    public Object getList() {
        return this.seekList;
    }


}
