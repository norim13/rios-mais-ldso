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
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import engenheiro.rios.GuardaRios;
import engenheiro.rios.Login;
import engenheiro.rios.R;

public class IRR_1_8 extends AppCompatActivity {


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
        irr_textview_name_main.setText("Hidrogeomorfologia");

        TextView irr_textview_name= (TextView) this.findViewById(R.id.irr_textview_name);
        irr_textview_name.setText("Sedimentação");

        linearLayout = (LinearLayout) this.findViewById(R.id.irr_linear);
        String[] array=new String[]{
                "Ausente",
                "Decomposição na margem em curva",
                "Mouchões (deposição de areia no leito",
                "Ilhas sem vegetação",
                "Ilhas com vegetação",
                "Deposição nas margens (s/vegetação",
                "Deposição nas margens (c/vegetação",
                "Rochas expostas no leito"
        };
        list=Form_functions.createCheckboxes(array,linearLayout,this);




        list.get(0).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                                   @Override
                                                   public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                                       for (int i = 0; i < list.size(); i++) {
                                                           if (list.get(i).isChecked()){
                                                               Log.v("teste", "id:" + i);
                                                           }
                                                       }
                                                   }
                                               }
        );







    }




    public void goto_next(View view){
        startActivity(new Intent(this, IRR_2_1.class));
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
