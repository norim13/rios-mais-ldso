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

import java.util.ArrayList;

import engenheiro.rios.GuardaRios;
import engenheiro.rios.Login;
import engenheiro.rios.R;

public class IRR_1_3 extends AppCompatActivity {

    protected EditText mL;
    protected  EditText mP;
    protected EditText mV;
    protected EditText mS;
    protected EditText mC;
    protected ArrayList<ArrayList<Object>> answers;
    protected Integer question_num;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        answers= (ArrayList<ArrayList<Object>>) getIntent().getSerializableExtra("answers");
        Log.e("teste", "size:" + answers.size());
        question_num=0;
        question_num= (Integer) getIntent().getSerializableExtra("question_num");

        setContentView(R.layout.activity_irr_1_3);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Novo Formulario");
        setSupportActionBar(toolbar);
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

        boolean error=false;

        if(mL.getText().length()==0)
        {
            mL.setError("Preencha o campo");
            error=true;
        }
        if(mP.getText().length()==0)
        {
            mP.setError("Preencha o campo");
            error=true;
        }
        if(mV.getText().length()==0)
        {
            mV.setError("Preencha o campo");
            error=true;
        }
        if (error)
        {
            return;
        }



        ArrayList<Float> array_float=new ArrayList<Float>();
        Float v1=Float.parseFloat(mL.getText().toString());
        Float v2=Float.parseFloat(mP.getText().toString());
        Float v3=Float.parseFloat(mV.getText().toString());
        Float v4=v1*v2;
        Float v5=v4*v3;
        array_float.add(v1);
        array_float.add(v2);
        array_float.add(v3);
        array_float.add(v4);
        array_float.add(v5);
        ArrayList<Object> aObj=new ArrayList<Object>();



        if(answers.get(answers.size()-1).get(0)==question_num){
            answers.get(answers.size()-1).remove(1);
            answers.get(answers.size()-1).add(array_float);
        }
        else {
            aObj.add(question_num);
            aObj.add(array_float);
            answers.add(aObj);
        }



        Intent i=new Intent(this, IRR_question.class);
        i.putExtra("main_title","Hidrogeomorfologia");
        i.putExtra("sub_title", "Substrato das margens (selecionar os que tem mais de 35%)");
        i.putExtra("answers", answers);
        i.putExtra("type", 1);
        i.putExtra("required", true);
        String[] options= new String[]{"Solo argiloso",
                "Arenoso",
                "Pedregoso",
                "Rochoso",
                "Artificial pedra",
                "Artificial Betão(5)"};
        i.putExtra("options", options);
        i.putExtra("question_num", 4);
        Log.e("teste", "size depois:" + answers.size());
        startActivity(i);
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
