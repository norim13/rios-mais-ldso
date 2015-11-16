package engenheiro.rios.IRR;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import engenheiro.rios.GuardaRios;
import engenheiro.rios.Login;
import engenheiro.rios.R;

public class IRR_2_1 extends AppCompatActivity {

    protected LinearLayout linearLayout;
    ArrayList<CheckBox> list;
    EditText ph,condutividade,temperatura,o2,o2_percentagem,nitratos,nitritos,transparencia;

    protected ArrayList<ArrayList<Object>> answers;
    protected HashMap<Integer,Object> answers2;


    protected Integer question_num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_irr_2_1);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Novo Formulario");
        setSupportActionBar(toolbar);


        answers= (ArrayList<ArrayList<Object>>) getIntent().getSerializableExtra("answers");
        answers2= (HashMap<Integer, Object>) getIntent().getSerializableExtra("answers2");

        Log.e("teste", "size:" + answers.size());
        question_num=0;
        question_num= (Integer) getIntent().getSerializableExtra("question_num");


        TextView irr_textview_name_main= (TextView) this.findViewById(R.id.irr_textview_name_main);
        irr_textview_name_main.setText("Qualidade da água");

        TextView irr_textview_name= (TextView) this.findViewById(R.id.irr_textview_name);
        irr_textview_name.setText("Parâmetros físico-químicos");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ph= (EditText) findViewById(R.id.ph);
        condutividade= (EditText) findViewById(R.id.condutividade);
        temperatura=(EditText) findViewById(R.id.temperatura);
        o2=(EditText) findViewById(R.id.o2);
        o2_percentagem=(EditText) findViewById(R.id.o2_percentagem);
        nitratos=(EditText) findViewById(R.id.nitratos);
        nitritos=(EditText) findViewById(R.id.nitritos);
        transparencia=(EditText) findViewById(R.id.transparencia);

        ph.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override
            public void afterTextChanged(Editable s) {
                if (Float.parseFloat(s.toString())<1)
                    ph.setText("1");
                else if (Float.parseFloat(s.toString())>14)
                    ph.setText("14");
            }
        });

        condutividade.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override
            public void afterTextChanged(Editable s) {
                if (Float.parseFloat(s.toString())<50)
                    condutividade.setText("50");
                else if (Float.parseFloat(s.toString())>1500)
                    condutividade.setText("1500");
            }
        });

        temperatura.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override
            public void afterTextChanged(Editable s) {
                if (Float.parseFloat(s.toString())<0)
                    temperatura.setText("0");
                else if (Float.parseFloat(s.toString())>40)
                    temperatura.setText("40");
            }
        });

        o2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override
            public void afterTextChanged(Editable s) {
                if (Float.parseFloat(s.toString())<0)
                    o2.setText("0");
                else if (Float.parseFloat(s.toString())>15)
                    o2.setText("15");
            }
        });

        o2_percentagem.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (Float.parseFloat(s.toString()) < 0)
                    o2_percentagem.setText("0");
                else if (Float.parseFloat(s.toString()) > 200)
                    o2_percentagem.setText("200");
            }
        });

        nitratos.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (Float.parseFloat(s.toString()) < 0)
                    nitratos.setText("0");
                else if (Float.parseFloat(s.toString()) > 80)
                    nitratos.setText("40");
            }
        });

        nitritos.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override
            public void afterTextChanged(Editable s) {
                if (Float.parseFloat(s.toString())<0)
                    nitritos.setText("0");
                else if (Float.parseFloat(s.toString())>4)
                    nitritos.setText("4");
            }
        });

        transparencia.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override
            public void afterTextChanged(Editable s) {
                if (Float.parseFloat(s.toString())<1)
                    transparencia.setText("0");
                else if (Float.parseFloat(s.toString())>4)
                    transparencia.setText("4");
            }
        });



    }


    public void goto_next(View view) {
        boolean error=false;
        if(ph.getText().length()==0)
        {
            ph.setError("Preencha o campo");
            error=true;
        }
        if(condutividade.getText().length()==0)
        {
            condutividade.setError("Preencha o campo");
            error=true;
        }
        if(temperatura.getText().length()==0)
        {
            temperatura.setError("Preencha o campo");
            error=true;
        }
        if(o2.getText().length()==0)
        {
            o2.setError("Preencha o campo");
            error=true;
        }
        if(o2_percentagem.getText().length()==0)
        {
            o2_percentagem.setError("Preencha o campo");
            error=true;
        }
        if(nitratos.getText().length()==0)
        {
            nitratos.setError("Preencha o campo");
            error=true;
        }
        if(nitritos.getText().length()==0)
        {
            nitritos.setError("Preencha o campo");
            error=true;
        }
        if(transparencia.getText().length()==0)
        {
            transparencia.setError("Preencha o campo");
            error=true;
        }
        if (error)
        {
            return;
        }


        ArrayList<Float> array_float=new ArrayList<Float>();
        array_float.add(Float.parseFloat(String.valueOf(ph.getText())));
        array_float.add(Float.parseFloat(String.valueOf(condutividade.getText())));
        array_float.add(Float.parseFloat(String.valueOf(o2.getText())));
        array_float.add(Float.parseFloat(String.valueOf(o2_percentagem.getText())));
        array_float.add(Float.parseFloat(String.valueOf(nitratos.getText())));
        array_float.add(Float.parseFloat(String.valueOf(nitritos.getText())));
        array_float.add(Float.parseFloat(String.valueOf(transparencia.getText())));

        if(answers2.get(question_num)!=null){
            answers2.put(question_num,array_float);
        }
        else {
            answers2.remove(question_num);
            answers2.put(question_num,array_float);
        }




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
        i.putExtra("main_title","Qualidade da água");
        i.putExtra("sub_title", "Indícios na água :))))");
        i.putExtra("answers", answers);
        i.putExtra("answers2", answers2);
        i.putExtra("type", 1);
        i.putExtra("required", true);
        String[] options= new String[]{"Óleo (reflexos multicolores)",
                "Espuma",
                "Esgotos",
                "Impurezas e lixos orgânicos",
                "Sacos de plástico e embalagens",
                "Latas ou material ferroso",
                "Outros"};
        i.putExtra("options", options);
        i.putExtra("question_num", 10);
        Log.e("teste", "size depois:" + answers.size());
        startActivity(i);
        this.overridePendingTransition(0, 0);

    }

    public void goto_previous(View view) {


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
        if (id == R.id.navigate_guardarios)
            startActivity(new Intent(this, GuardaRios.class));
        if (id == R.id.navigate_account)
            startActivity(new Intent(this, Login.class));
        return super.onOptionsItemSelected(item);
    }
}