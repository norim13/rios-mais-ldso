package engenheiro.rios.Form;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import engenheiro.rios.Autenticacao.Login;
import engenheiro.rios.DataBases.DB_functions;
import engenheiro.rios.DataBases.User;
import engenheiro.rios.MainActivities.GuardaRios;
import engenheiro.rios.R;

public class Sos_rios extends AppCompatActivity {

    LinearLayout linearLayout;
    protected ArrayList<RadioButton> question1;
    protected ArrayList<RadioButton> question2;
    protected EditText question3;
    protected ProgressBar progressbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sos_rios);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("SOS Rios+");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        linearLayout = (LinearLayout) this.findViewById(R.id.irr_linear);
        progressbar= (ProgressBar) this.findViewById(R.id.progressBar);

        Resources r = getResources();
        float px_float = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, r.getDisplayMetrics());
        int px= (int) px_float;
        px_float = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15, r.getDisplayMetrics());
        int px2= (int) px_float;



        LinearLayout.LayoutParams radioParams;
        radioParams = new LinearLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        radioParams.setMargins(0, px2, 0, px);

        LinearLayout ll=new LinearLayout(this);
        ll.setLayoutParams(radioParams);
        TextView tv1=new TextView(this);
        tv1.setText("Categoria");

        tv1.setLayoutParams(radioParams);
        ll.addView(tv1);
        linearLayout.addView(ll);
        String[] options= { "Erosão",
        "Poluição",
        "Acidente",
        "Desflorestação",
        "Pesca e caça ilegal",
        "Cheia",
        "Incêndio",
        "Vandalismo",
        "Presença de espécies exóticas e invasoras"};
        question1= Form_functions.createRadioButtons(options, linearLayout, this);

        TextView tv2=new TextView(this);
        tv2.setText("Motivo");
        tv2.setLayoutParams(radioParams);
        linearLayout.addView(tv2);
        String[] options2= { "Preocupado com a situação a nível ambiental",
        "Preocupado com a situação a nível Pessoal",
        "Má Vizinhança",
        "Necessida de informação adicional",
        "Demora do processo de esclarecimento/resolução"};
        question2=Form_functions.createRadioButtons(options2,linearLayout,this);

        TextView tv3=new TextView(this);
        tv3.setText("Descrição");
        tv3.setLayoutParams(radioParams);
        linearLayout.addView(tv3);
        question3=new EditText(this);
        linearLayout.addView(question3);
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

    public void saveSOSRios(View view) {
        progressbar.setVisibility(View.VISIBLE);
        String q1=Form_functions.getRadioButtonOption_string(question1);
        String q2=Form_functions.getRadioButtonOption_string(question2);
        String q3=question3.getText().toString();

        DB_functions.saveSOSRios(this, User.getToken(this),q1,q2,q3);

    }

    public void saveSOSDB(){
        new Thread()
        {
            public void run()
            {
                Sos_rios.this.runOnUiThread(new Runnable()
                {
                    public void run() {
                        progressbar.setVisibility(View.INVISIBLE);
                        Toast toast = Toast.makeText(Sos_rios.this, "Denuncia submetida", Toast.LENGTH_LONG);
                        toast.show();
                        Sos_rios.this.finish();

                    }
                });
            }
        }.start();

    }

    public void errorSOSDB(final String responseMessage){
        new Thread()
        {
            public void run()
            {
                Sos_rios.this.runOnUiThread(new Runnable()
                {
                    public void run() {
                        progressbar.setVisibility(View.INVISIBLE);
                        Toast toast = Toast.makeText(Sos_rios.this, "Erro na submissão: "+responseMessage, Toast.LENGTH_LONG);
                        toast.show();
                    }
                });
            }
        }.start();

    }
}
