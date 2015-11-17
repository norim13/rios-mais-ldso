package engenheiro.rios.IRR;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import engenheiro.rios.GuardaRios;
import engenheiro.rios.Login;
import engenheiro.rios.R;

public class IRR_question extends AppCompatActivity {
    protected LinearLayout linearLayout;
    protected ArrayList<RadioButton> radio_list;
    protected ArrayList<CheckBox> check_list;
    protected ArrayList<EditText> edit_list;
    protected ArrayList<SeekBar> seek_list;
    protected ArrayList<TextView> seek_list_text;
    protected int min,max;                      //values of min and max on seekbar and edit text. null if there is none
    protected ArrayList<String> image_list;     //null if there is no image
    protected HashMap<Integer,Object> answers2;
    protected Integer type;                     //type=0 RadioButton, type=1 CheckBox, type=2 EditText, type=3 SeekBar
    protected Boolean required;
    Integer question_num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_irr_question);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Novo Formulario");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        TextView irr_textview_name_main= (TextView) this.findViewById(R.id.irr_textview_name_main);
        String main_title= (String) getIntent().getSerializableExtra("main_title");
        irr_textview_name_main.setText(main_title);

        TextView irr_textview_name= (TextView) this.findViewById(R.id.irr_textview_name);
        String sub_title= (String) getIntent().getSerializableExtra("sub_title");
        irr_textview_name.setText(sub_title);

        linearLayout = (LinearLayout) this.findViewById(R.id.irr_linear);

//        answers= (ArrayList<ArrayList<Object>>) getIntent().getSerializableExtra("answers");
        answers2= (HashMap<Integer, Object>) getIntent().getSerializableExtra("answers2");
        Log.e("teste", "size2:" + answers2.size());
//        Log.e("teste", "size:" + answers.size());
        type= (Integer) getIntent().getSerializableExtra("type");
        required=(Boolean) getIntent().getSerializableExtra("required");
        String [] options= (String[]) getIntent().getSerializableExtra("options");
        question_num=0;
        question_num= (Integer) getIntent().getSerializableExtra("question_num");
        int t= Integer.parseInt(type.toString());


        switch (type){
            case 0:                     //RadioButton
                radio_list=Form_functions.createRadioButtons(options, linearLayout, this);

                break;

            case 1:                     //CheckBox
                check_list=Form_functions.createCheckboxes(options,linearLayout,this);

                break;

            case 2:                     //EditText

                edit_list=Form_functions.createEditText(options,linearLayout,this);

                break;

            case 3:                     //SeekBar
                max= (int) getIntent().getSerializableExtra("max");
                ArrayList<ArrayList> arrayList=Form_functions.createSeekbar(options,linearLayout,this, (int) max);
                seek_list=arrayList.get(0);
                seek_list_text=arrayList.get(1);
                break;

            default:
                break;
        }




    }

    public void goto_next(View view){
        Intent i=new Intent(this, IRR_question.class);
        ArrayList<Object> al=new ArrayList<Object>();
        al.add(question_num);
        Object obj = null;
        switch (type){
            case 0:
                obj = Form_functions.getRadioButtonOption(radio_list);
                break;
            case 1:
                obj = Form_functions.getCheckboxes(check_list);
                break;
            case 2:
                obj = Form_functions.getEditTexts(edit_list);
                break;

        }

        answers2.put(question_num, obj);

        int next_question=question_num+1;
        Log.e("teste", "num pergunta:" + question_num + " next:" + next_question);

        Questions.getQuestion(next_question, i, this);
        i.putExtra("answers2", answers2);
        this.startActivity(i);

        this.overridePendingTransition(0, 0);

    }

    public void goto_previous(View view){
        this.finish();
        this.overridePendingTransition(0, 0);
    }




    //menu action bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_homepage, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id==R.id.navigate_guardarios)
            startActivity(new Intent(this,GuardaRios.class));
        if(id==R.id.navigate_account)
            startActivity(new Intent(this,Login.class));
        return super.onOptionsItemSelected(item);
    }

}

