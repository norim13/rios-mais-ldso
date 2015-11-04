package engenheiro.rios.IRR;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import engenheiro.rios.GuardaRios;
import engenheiro.rios.Login;
import engenheiro.rios.R;

public class IRR_1_3 extends AppCompatActivity {

    protected EditText mL;
    protected  EditText mP;
    protected EditText mV;
    protected EditText mS;
    protected EditText mC;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_irr_1_3);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Novo Formulario");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mL=(EditText)findViewById(R.id.irr_1_3_1);
        mP=(EditText)findViewById(R.id.irr_1_3_2);
        mV=(EditText)findViewById(R.id.irr_1_3_3);
        mS=(EditText)findViewById(R.id.irr_1_3_4);
        mC=(EditText)findViewById(R.id.irr_1_3_5);

        //listeners to write mS and mC in runtime

        mL.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                calculate();
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
        mP.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                calculate();
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
        mV.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                calculate();
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });



    }

    public void calculate(){
        String l= mL.getText().toString();
        String p= mP.getText().toString();
        String v= mV.getText().toString();
        Log.v("teste",l+":"+p+":"+v);
        if(l.length()>0 && p.length()>0){
            float s= Float.parseFloat(l)*Float.parseFloat(p);
            mS.setText(Html.fromHtml(s + " m<sup>2</sup>"));

            if(v.length()>0)
            {
                float c=s*Float.parseFloat(v);
                mC.setText(Html.fromHtml(c + " m<sup>3</sup>/s"));
            }
        }



    }




    public void goto_next(View view){
        startActivity(new Intent(this, IRR_1_3.class));
        this.overridePendingTransition(0, 0);

    }

    public void goto_previous(View view){
        startActivity(new Intent(this, IRR_1_1.class));
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
