package engenheiro.rios.IRR;

import android.content.Context;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

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
}
