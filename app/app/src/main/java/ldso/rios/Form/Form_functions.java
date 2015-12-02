package ldso.rios.Form;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import ldso.rios.MainActivities.Homepage;
import ldso.rios.R;

/**
 * Created by filipe on 04/11/2015.
 * Class para guardar todas as funcoes relativas a criar e ler um formulário
 */
public class Form_functions {

    //CREATE

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
        RelativeLayout.LayoutParams radioParams;
        radioParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        radioParams.setMargins(10,10,10,10);
        RadioGroup rg= new RadioGroup(context);
        rg.setLayoutParams(radioParams);
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

    public static ArrayList<RadioButton> createRadioButtons(String[] array,String[] images, LinearLayout linearLayout, Context context) throws IOException {
        if(images==null)
            createRadioButtons(array,linearLayout,context);

        ArrayList<RadioButton> list = new ArrayList<RadioButton>();
        RelativeLayout.LayoutParams radioParams;
        radioParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        radioParams.setMargins(10,10,10,10);
        RadioGroup rg= new RadioGroup(context);
        rg.setLayoutParams(radioParams);

        for(int i=0;i<array.length;i++)
        {
            RadioButton cb = new RadioButton(context);
            cb.setText(array[i]);


            String imageUri = "drawable://tipovale_1.png";
            Drawable myDrawable = Drawable.createFromPath(imageUri);

            Log.e("nome ficheiro",""+ R.drawable.tipovale_1);

           // InputStream stream = context.getAssets().open("tipovale_1.png");
            //Drawable d = Drawable.createFromStream(stream, null);

            cb.setCompoundDrawablesWithIntrinsicBounds(null, null, myDrawable, null);
            list.add(cb);
            rg.addView(cb);
        }
        linearLayout.addView(rg);
        return list;
    };

    public static Bitmap getResizedBitmap(int targetW, int targetH,  String imagePath) {

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        //inJustDecodeBounds = true <-- will not load the bitmap into memory
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imagePath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(imagePath, bmOptions);
        return(bitmap);
    }

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
            tv.setText(array[i]+ " N/A:");
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

    public static ArrayList<EditText> createEditText(String[] array, LinearLayout linearLayout, Context context) {
        ArrayList<EditText> list = new ArrayList<EditText>();
        for(int i=0;i<array.length;i++)
        {

            EditText et = new EditText(context);
            TextView tv=new TextView(context);
            tv.setText(array[i]);
            LinearLayout ll=new LinearLayout(context);
            ll.setOrientation(LinearLayout.VERTICAL);
            ll.addView(tv);
            ll.addView(et);
            list.add(et);
            linearLayout.addView(ll);
        }
        return list;

    };

    public static ArrayList<EditText> createEditText(String[] array, LinearLayout linearLayout, Context context, final ArrayList<Float[]> minmax) {
        ArrayList<EditText> list = new ArrayList<EditText>();
        for(int i=0;i<array.length;i++)
        {

            final EditText et = new EditText(context);
            final int finalI = i;
            et.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
            et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                public void onFocusChange(View v, boolean hasFocus) {
                    if(et.getText().toString().equals(""))
                        return;
                    try {
                        Float.parseFloat(et.getText().toString());
                    }catch (Exception e)
                    {
                        et.setText(""+minmax.get(finalI)[0]);
                        return;
                    }
                    if (Float.parseFloat(et.getText().toString())<minmax.get(finalI)[0])
                        et.setText(""+minmax.get(finalI)[0]);
                    else if (Float.parseFloat(et.getText().toString())>minmax.get(finalI)[1])
                        et.setText(""+minmax.get(finalI)[1]);

                }
            });
            TextView tv=new TextView(context);
            tv.setText(array[i] +" ("+minmax.get(finalI)[0]+"-"+minmax.get(finalI)[1]+"):");
            LinearLayout ll=new LinearLayout(context);
            ll.setOrientation(LinearLayout.VERTICAL);
            ll.addView(tv);
            ll.addView(et);
            list.add(et);
            linearLayout.addView(ll);
        }
        return list;

    };



    //READ

    public static int getRadioButtonOption(ArrayList<RadioButton> arrayList){
        for (int i=0;i<arrayList.size();i++){
            if (arrayList.get(i).isChecked()){
                int r=i+1;
                return r;
            }
        }
        return 0;
    }

    public static String getRadioButtonOption_string(ArrayList<RadioButton> arrayList){
        for (int i=0;i<arrayList.size();i++){
            if (arrayList.get(i).isChecked()){
                int r=i+1;
                return arrayList.get(i).getText().toString();
            }
        }
        return "";
    }

    public static ArrayList<Integer> getCheckboxes(ArrayList<CheckBox> arrayList){
        ArrayList<Integer> al = new ArrayList<Integer>();
        for (int i=0;i<arrayList.size();i++){
            if(arrayList.get(i).isChecked())
                al.add(1);
            else
                al.add(0);
        }
        return al;
    }

    public static ArrayList<Integer> getSeekbar(ArrayList<SeekBar> arrayList){
        ArrayList<Integer> al = new ArrayList<Integer>();
        for (int i=0;i<arrayList.size();i++){
            al.add(arrayList.get(i).getProgress());
        }
        return al;
    }

    public static ArrayList<String> getEditTexts(ArrayList<EditText> arrayList){
        ArrayList<String> al = new ArrayList<String>();
        for (int i=0;i<arrayList.size();i++){
            al.add(arrayList.get(i).getText().toString());
        }
        return al;

    }



    //CALCULAR IRR

    public static int getmax(ArrayList<Integer> arrayList_questions, Integer[] values_irr ){
        ArrayList<Integer> final_options = new ArrayList<Integer>();
        for(int i=0;i<arrayList_questions.size();i++)
            if(arrayList_questions.get(i)==1 && values_irr[i]!=null ) {
                final_options.add(values_irr[i]);
                Log.e("form","value: "+values_irr[i]);
            }
        if(final_options.size()==0)
            return 0;
        return Collections.max(final_options);
    }

    public static int getmax(int question, Integer[] values_irr ){
        if (question==0)
            return 0;
        return values_irr[question-1];
    }

    public static Integer getmax2(ArrayList<ArrayList<Integer>> arrayListArrayList, Integer[] integers) {
        ArrayList<Integer> flatArrayList=new ArrayList<Integer>();
        for (ArrayList<Integer> al : arrayListArrayList)
            for (Integer i: al)
            flatArrayList.add(i);




        return getmax(flatArrayList,integers);
    }

    public static String[] getUser(Context c){
        SharedPreferences settings = c.getSharedPreferences(Homepage.PREFS_NAME, 0);
        String token = settings.getString("token", "-1");
        String email = settings.getString("email", "-1");
        String [] r= {token,email};
        return r;

    }


}
