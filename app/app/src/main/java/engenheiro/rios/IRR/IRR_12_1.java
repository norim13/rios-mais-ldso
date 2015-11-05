package engenheiro.rios.IRR;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;

import engenheiro.rios.GuardaRios;
import engenheiro.rios.Homepage;
import engenheiro.rios.Login;
import engenheiro.rios.R;

public class IRR_12_1 extends AppCompatActivity {
    protected LinearLayout linearLayout;
    ArrayList<RadioButton> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_irr_12_1);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Novo Formulario");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView irr_textview_name_main= (TextView) this.findViewById(R.id.irr_textview_name_main);
        irr_textview_name_main.setText("Organização e Planeamento");

        TextView irr_textview_name= (TextView) this.findViewById(R.id.irr_textview_name);
        irr_textview_name.setText("Gestão das intervenções de melhoria");

        linearLayout = (LinearLayout) this.findViewById(R.id.irr_linear);
        String[] array=new String[]{
                "Definição de objetivos claros de intervenção de melhoria, ações de monitorização com valores de referência, ações de fiscalização, plano de intervenção em caso de acidente ou catástrofe, plano de ações de intervenção de melhoria e ações de manutenção com envolvimento dos proprietários.",
                "Definição de objetivos claros de intervenção de melhoria, plano de ações de intervenção de melhoria, ações de monitorização com valores de referência e ações de manutenção com envolvimento dos proprietários.",
                "Definição de objetivos claros de intervenção de melhoria e plano de ações de intervenção de melhoria.",
                "Definição de objetivos claros de intervenção de melhoria, mas sem qualquer intervenção.",
                "Não existe nenhuma evidência de gestão das intervenções de melhoria."
        };
        list=Form_functions.createRadioButtons(array, linearLayout, this);




    }

    public void goto_next(View view){
        startActivity(new Intent(this, Homepage.class));
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

