package engenheiro.rios.Form.IRR;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import java.util.HashMap;

import engenheiro.rios.R;

/*
View para mostrar um form irr já preenchido
 */
public class ViewFormIRR extends AppCompatActivity {

    LinearLayout linearLayout;
    Form_IRR form;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_form_irr);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        linearLayout = (LinearLayout) this.findViewById(R.id.linerLayout);

        linearLayout.setFocusable(false);
        linearLayout.setClickable(false);



        this.form = new Form_IRR();
        this.form.generate();

        if(getIntent().getSerializableExtra("form_irr")!= null) {
            Log.e("form", "entrou");
            this.form.setRespostas((HashMap<Integer, Object>) getIntent().getSerializableExtra("form_irr"));
        }


        for(int i=0;i<=32;i++)
        {
            this.form.getPerguntas().get(i).generateView(linearLayout, this);
            this.form.getPerguntas().get(i).setAnswer();
        }



    }



    /*
    se clicar no botão de edit, inicia um FormIRRSwipe para editar o formulário
     */
    public void edit_form(View view){
        Intent i;
        i = new Intent(this, FormIRRSwipe.class);
        i.putExtra("form_irr",form.getRespostas());
        startActivity(i);

    }
}
