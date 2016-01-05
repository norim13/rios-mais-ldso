package ldso.rios.Form;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.InputType;
import android.util.Log;
import android.util.TypedValue;
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

/**
 * Created by filipe on 04/11/2015.
 * Class para guardar todas as funcoes relativas a criar e ler um formulário
 */
public class Form_functions {
    //Funcoes Create -> Criam no ecra no layout linearLayout com as opcoes em Stirng[]array
    //Algumas recebem um int[] images com R.drawable.X relativo a imagem de uma opcao

    //CREATE

    public static ArrayList<CheckBox> createCheckboxes(String[] array, LinearLayout linearLayout, Context context){
        ArrayList<CheckBox> list = new ArrayList<CheckBox>();
        RadioGroup.LayoutParams params_rb = new RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT,100);

        for(int i=0;i<array.length;i++)
        {
            CheckBox cb = new CheckBox(context);
            cb.setText(array[i]);
            cb.setLayoutParams(params_rb);
            list.add(cb);
            linearLayout.addView(cb);
        }
        return list;

    };

    public static ArrayList<CheckBox> createCheckboxes(String[] array,int[] images,LinearLayout linearLayout, Context context){

        float px_float = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 120, context.getResources().getDisplayMetrics());
        int px = (int) px_float;

        RelativeLayout.LayoutParams radioParams_images;
        radioParams_images = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        radioParams_images.setMargins(0,10,0,10);

        if (images==null)
            return createCheckboxes(array,linearLayout,context);

        RadioGroup.LayoutParams params_rb = new RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT,RadioGroup.LayoutParams.WRAP_CONTENT);


        ArrayList<CheckBox> list = new ArrayList<CheckBox>();
        for(int i=0;i<array.length;i++)
        {
            CheckBox cb = new CheckBox(context);
            cb.setText(array[i]);
            if(images[i]!=0) {
                Drawable myDrawable;
                Bitmap mapa = BitmapFactory.decodeResource(context.getResources(), images[i]);
                int width= mapa.getWidth();
                int height= mapa.getHeight();
                int novo= (height*px)/width;
                mapa = Bitmap.createScaledBitmap(mapa, px,novo, true);
                myDrawable=new BitmapDrawable(context.getResources(), mapa);

                params_rb = new RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT,novo+80);

                cb.setCompoundDrawablesWithIntrinsicBounds(null, null, myDrawable, null);
                radioParams_images.setMargins(0, 300, 0, 300);
                cb.setLayoutParams(params_rb);

            }
            params_rb = new RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT,RadioGroup.LayoutParams.WRAP_CONTENT);
            //cb.setLayoutParams(radioParams_images);
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


        RadioGroup.LayoutParams params_rb = new RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT,100);

        for(int i=0;i<array.length;i++)
        {
            RadioButton cb = new RadioButton(context);
            cb.setText(array[i]);
            cb.setLayoutParams(params_rb);
            list.add(cb);
            rg.addView(cb);
        }
        linearLayout.addView(rg);
        return list;
    };

    public static ArrayList<RadioButton> createRadioButtons(String[] array,int[] images, LinearLayout linearLayout, Context context) throws IOException {
        if(images==null)
            return createRadioButtons(array,linearLayout,context);

        ArrayList<RadioButton> list = new ArrayList<RadioButton>();
        RelativeLayout.LayoutParams radioParams;
        radioParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        radioParams.setMargins(10,10,10,10);

        RelativeLayout.LayoutParams radioParams_images;
        radioParams_images = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        radioParams_images.setMargins(0,10,0,10);

        RadioGroup rg= new RadioGroup(context);
        rg.setLayoutParams(radioParams);
        Resources r = context.getResources();
        float px_float = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, r.getDisplayMetrics());
        int px = (int) px_float;
        RadioGroup.LayoutParams params_rb;
        for(int i=0;i<array.length;i++)
        {
            RadioButton cb = new RadioButton(context);
            cb.setText(array[i]);


            try{
                if(images[i]!=0) {
                    Drawable myDrawable = context.getResources().getDrawable(images[i]);
                    Bitmap mapa = BitmapFactory.decodeResource(context.getResources(), images[i]);
                    int width= mapa.getWidth();
                    int height= mapa.getHeight();
                    int novo= (height*px)/width;
                    mapa = Bitmap.createScaledBitmap(mapa, px,novo, true);
                    myDrawable=new BitmapDrawable(context.getResources(), mapa);
                    cb.setCompoundDrawablesWithIntrinsicBounds(null, null, myDrawable, null);
                    params_rb = new RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT,novo+80);
                    cb.setLayoutParams(params_rb);


                }

            }
            catch (Exception e){

            }

            //cb.setLayoutParams(radioParams_images);
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

    public static void createTitleSubtitle(String title,String subtitle, LinearLayout linearLayout,Context context){
        LinearLayout.LayoutParams radioParams;
        radioParams = new LinearLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        radioParams.setMargins(0, 20, 0, 20);
        TextView textView=new TextView(context);
        textView.setText(title );
        textView.setTextColor(Color.BLACK);
        textView.setTextSize(25);
        textView.setLayoutParams(radioParams);
        linearLayout.addView(textView);

        TextView textView1=new TextView(context);
        textView1.setText(subtitle);
        textView1.setTextColor(Color.BLACK);
        textView1.setTextSize(20);
        radioParams.setMargins(0, 0, 0, 60);
        textView1.setLayoutParams(radioParams);
        linearLayout.addView(textView1);
    }

    public static void createTitle(String title, LinearLayout linearLayout,Context context){
        LinearLayout.LayoutParams radioParams;
        radioParams = new LinearLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        radioParams.setMargins(0, 10, 0, 10);
        TextView textView=new TextView(context);
        textView.setText(title );
        textView.setTextColor(Color.BLACK);
        textView.setTextSize(20);
        textView.setLayoutParams(radioParams);
        linearLayout.addView(textView);

    }


    //READ
    //Devolve as respostas dos vários tipos de perguntas

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
