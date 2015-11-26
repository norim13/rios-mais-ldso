package engenheiro.rios.Form.Perguntas;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;

import engenheiro.rios.Form.Pergunta;
import engenheiro.rios.IRR.Form_functions;

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
    }

    @Override
    public void getAnswer() {
        this.response=Form_functions.getSeekbar(this.seekList);

    }

    @Override
    public void forceresponse() {

    }

    @Override
    public void setAnswer() {

    }

    @Override
    public Object getList() {
        return this.seekList;
    }


}
