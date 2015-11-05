package engenheiro.rios.IRR;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;

import engenheiro.rios.GuardaRios;
import engenheiro.rios.Login;
import engenheiro.rios.R;

public class IRR_4_1 extends AppCompatActivity {


    protected LinearLayout linearLayout;
    ArrayList<SeekBar> list;
    ArrayList<TextView> list2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_irr_3_2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Novo Formulario");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView irr_textview_name_main= (TextView) this.findViewById(R.id.irr_textview_name_main);
        irr_textview_name_main.setText("Alterações Antrópicas");

        TextView irr_textview_name= (TextView) this.findViewById(R.id.irr_textview_name);
        irr_textview_name.setText("Património edificado Leito/margem [estado de conservação: 1 - Bom a 5- Mau]");

        linearLayout = (LinearLayout) this.findViewById(R.id.irr_linear);
        final String[] array=new String[]{
                "Moinho/azenhas",
                "Açude >2m",
                "Micro-Açude (1m)",
                "Micro-Açude (1-2m)",
                "Barragem (>10m)",
                "Levadas",
                "Pesqueiras",
                "Escadas de peixe",
                "Poldras",
                "Pontes/pontões sem pilar no canal",
                "Pontes/pontões com pilar no canal",
                "Passagem a vau",
                "Barcos",
                "Cais",
                "Igreja, capela, santuário <100m",
                "Solares ou casas agrícolas <100m",
                "Núcleo habitacional <100m"

        };
        ArrayList<ArrayList> arrayList=Form_functions.createSeekbar(array, linearLayout, this, 4);
        list=arrayList.get(0);
        list2=arrayList.get(1);

        for(int i=0;i<list.size();i++){
            final int finalI = i;
            list.get(finalI).setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    int num=progress+1;
                   list2.get(finalI).setText(array[finalI] + " Valor:" + num + "");
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });
        }



    }

    public void goto_next(View view){
        startActivity(new Intent(this, IRR_4_2.class));
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


