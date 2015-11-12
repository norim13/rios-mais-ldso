package engenheiro.rios.IRR;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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

import engenheiro.rios.GuardaRios;
import engenheiro.rios.Homepage;
import engenheiro.rios.Login;
import engenheiro.rios.R;

public class IRR_question extends AppCompatActivity {
    protected LinearLayout linearLayout;
    protected ArrayList<RadioButton> radio_list;
    protected ArrayList<CheckBox> check_list;
    protected ArrayList<EditText> edit_list;
    protected ArrayList<SeekBar> seek_list;
    protected ArrayList<TextView> seek_list_text;
    protected float min,max;                      //values of min and max on seekbar and edit text. null if there is none
    protected ArrayList<String> image_list;     //null if there is no image
    protected ArrayList<Object> answers;        //array to pass the answers for one activity to another
    protected Integer type;                     //type=0 RadioButton, type=1 CheckBox, type=2 EditText, type=3 SeekBar
    protected Boolean required;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_irr_question);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Novo Formulario");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView irr_textview_name_main= (TextView) this.findViewById(R.id.irr_textview_name_main);
        irr_textview_name_main.setText("Organização e Planeamento");

        TextView irr_textview_name= (TextView) this.findViewById(R.id.irr_textview_name);
        irr_textview_name.setText("Gestão das intervenções de melhoria");

        linearLayout = (LinearLayout) this.findViewById(R.id.irr_linear);

        answers= (ArrayList<Object>) getIntent().getSerializableExtra("response");
        type= (Integer) getIntent().getSerializableExtra("type");
        required=(Boolean) getIntent().getSerializableExtra("required");
        String [] options_txt= (String[]) getIntent().getSerializableExtra("options");

        switch (type){
            case 0:                     //RadioButton
                radio_list=Form_functions.createRadioButtons(options_txt, linearLayout, this);

                break;

            case 1:                     //CheckBox
                check_list=Form_functions.createCheckboxes(options_txt,linearLayout,this);

                break;

            case 2:                     //EditText



                break;

            case 3:                     //SeekBar
                ArrayList<ArrayList> arrayList=Form_functions.createSeekbar(options_txt,linearLayout,this, (int) max);
                seek_list=arrayList.get(0);
                seek_list_text=arrayList.get(1);
                break;

            default:
                break;
        }




    }

    public void goto_next(View view){
        startActivity(new Intent(this, Homepage.class));

        switch (type){
            case 0:
                Form_functions.getRadioButtonOption(radio_list);
                break;
        }
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

