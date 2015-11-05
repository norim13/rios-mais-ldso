package engenheiro.rios.IRR;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import engenheiro.rios.GuardaRios;
import engenheiro.rios.Login;
import engenheiro.rios.R;

public class IRR_2_4 extends AppCompatActivity {
    protected LinearLayout linearLayout;
    ArrayList<CheckBox> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_irr_2_4);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Novo Formulario");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView irr_textview_name_main= (TextView) this.findViewById(R.id.irr_textview_name_main);
        irr_textview_name_main.setText("Qualidade da água");

        TextView irr_textview_name= (TextView) this.findViewById(R.id.irr_textview_name);
        irr_textview_name.setText("Tabela de Macroinvertebrados");

        linearLayout = (LinearLayout) this.findViewById(R.id.irr_linear);
        String[] array=new String[]{
                "1. Planárias",
                "2. Hidudíneros (Sanguessugas)",
                "3.1 Simulideos",
                "3.2 Quironomideos, Sirfídeos, Culidídeos, Tipulídeos (Larva de mosquitos)",
                "4.1 Ancilídeo",
                "4.2 Limnídeo; Physa",
                "5. Bivalves",
                "6.1 Patas Nadadoras (Dystiscidae)",
                "6.2 Pata Locomotoras (Hydraena)",
                "7.1 Trichóptero (mosca d’água) S/Casulo",
                "7.2 Trichóptero (mosca d’água) C/Casulo",
                "8. Odonata (Larva de Libelinhas)",
                "9. Heterópteros",
                "10. Plecópteros (mosca-de-pedra)",
                "11.1 Baetídeo",
                "11.2 Cabeça Planar (Ecdyonurus)",
                "12. Crustáceos",
                "13. Ácaros",
                "14. Pulga-de-água (Daphnia)",
                "15. Insetos – adultos (adultos na forma aérea)",
                "16. Mégalopteres"
        };
        list=Form_functions.createCheckboxes(array,linearLayout,this);



    }

    public void goto_next(View view){
        startActivity(new Intent(this, IRR_3_1.class));
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


