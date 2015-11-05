package engenheiro.rios.IRR;

import android.content.Context;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by filipe on 04/11/2015.
 */
public class Form_functions {


    public static ArrayList<CheckBox> createCheckboxes(String[] array, LinearLayout linearLayout, Context context){
        ArrayList<CheckBox> list = new ArrayList<CheckBox>();
        for(int i=0;i<array.length;i++)
        {
            CheckBox cb = new CheckBox(context);
            cb.setText(array[i]);
            list.add(cb);
            linearLayout.addView(cb);
        }
        return list;

    };

    public static ArrayList<RadioButton> createRadioButtons(String[] array, LinearLayout linearLayout, Context context){
        ArrayList<RadioButton> list = new ArrayList<RadioButton>();
        RadioGroup rg= new RadioGroup(context);
        for(int i=0;i<array.length;i++)
        {
            RadioButton cb = new RadioButton(context);
            cb.setText(array[i]);
            list.add(cb);
            rg.addView(cb);

        }
        linearLayout.addView(rg);
        return list;

    };


    public static ArrayList<ArrayList> createSeekbar(String[] array, LinearLayout linearLayout, Context context,int max){
        ArrayList<ArrayList> al= new ArrayList<ArrayList>();
        ArrayList<SeekBar> list = new ArrayList<SeekBar>();
        ArrayList<TextView> list2 = new ArrayList<TextView>();
        RadioGroup rg= new RadioGroup(context);
        for(int i=0;i<array.length;i++)
        {

            SeekBar sb = new SeekBar(context);
            sb.setMax(max);
            sb.setProgress(0);
            TextView tv=new TextView(context);
            tv.setText(array[i]+" Valor:"+1);
            LinearLayout ll=new LinearLayout(context);
            ll.setOrientation(LinearLayout.VERTICAL);
            ll.addView(tv);
            ll.addView(sb);
            list.add(sb);
            list2.add(tv);
            linearLayout.addView(ll);

        }
        al.add(list);
        al.add(list2);
        return al;

    };


    public static int getRadioButtonOption(ArrayList<RadioButton> arrayList){
        for (int i=0;i<arrayList.size();i++){
            if (arrayList.get(i).isChecked()){
                return i;
            }
        }
        return -1;
    }

    public static ArrayList<Boolean> getCheckboxes(ArrayList<CheckBox> arrayList){
        ArrayList<Boolean> al = new ArrayList<Boolean>();
        for (int i=0;i<arrayList.size();i++){
            if(arrayList.get(i).isChecked())
                al.add(true);
            else
                al.add(false);
        }
        return al;
    }
}
