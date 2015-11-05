package engenheiro.rios.IRR;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import engenheiro.rios.GuardaRios;
import engenheiro.rios.Login;
import engenheiro.rios.R;

public class IRR_2_2 extends AppCompatActivity {

    protected LinearLayout linearLayout;
    ArrayList<CheckBox> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_irr_1_8);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Novo Formulario");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView irr_textview_name_main= (TextView) this.findViewById(R.id.irr_textview_name_main);
        irr_textview_name_main.setText("Qualidade da água");

        TextView irr_textview_name= (TextView) this.findViewById(R.id.irr_textview_name);
        irr_textview_name.setText("Indícios na água");

        linearLayout = (LinearLayout) this.findViewById(R.id.irr_linear);
        String[] array=new String[]{
                "Óleo (reflexos multicolores)",
                "Espuma",
                "Esgotos",
                "Impurezas e lixos orgânicos",
                "Sacos de plástico e embalagens",
                "Latas ou material ferroso",
                "Outros"
        };
        list=Form_functions.createCheckboxes(array,linearLayout,this);

        final EditText outro=  new EditText(this);
        outro.setWidth(linearLayout.getWidth());
        outro.setFocusable(false);
        outro.setFocusableInTouchMode(false);
        outro.setClickable(false);
        linearLayout.addView(outro);


        list.get(6).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
               @Override
               public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                   if(list.get(list.size()+1).isChecked()){
                       outro.setFocusable(true);
                       outro.setFocusableInTouchMode(true);
                       outro.setClickable(true);
                   }
                   else
                   {
                       outro.setFocusable(false);
                       outro.setFocusableInTouchMode(false);
                       outro.setClickable(false);
                   }

               }
           }
        );

    }

    public void goto_next(View view){
        startActivity(new Intent(this, IRR_2_3.class));
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


