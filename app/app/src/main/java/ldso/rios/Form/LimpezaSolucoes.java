package ldso.rios.Form;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import ldso.rios.Autenticacao.Login;
import ldso.rios.DataBases.DB_functions;
import ldso.rios.DataBases.User;
import ldso.rios.MainActivities.GuardaRios;
import ldso.rios.MainActivities.Homepage;
import ldso.rios.MainActivities.Profile;
import ldso.rios.R;

public class LimpezaSolucoes extends AppCompatActivity {

    LinearLayout layoutLimpezaSolucoes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_limpeza_solucoes);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Soluções de Limpeza");
        setSupportActionBar(toolbar);

        layoutLimpezaSolucoes = (LinearLayout) this.findViewById(R.id.limpeza_solucao_linear);

        Log.e("tou a testar: ","Já devia ter chegado aqui se não chumbo");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject((String) this.getIntent().getSerializableExtra("solucoes"));

            Log.e("jsonObject",jsonObject.toString());

            for(int i = 1; i <= 13; i++) {
                String opcao = jsonObject.get("problema" + i).toString();
                Log.e("resposta: ", opcao);

                if(!opcao.equals("") && jsonObject != null) {
                    if (DB_functions.haveNetworkConnection(getApplicationContext()))
                    DB_functions.getSolucoes(this,opcao);
                    else
                    {
                        Toast toast = Toast.makeText(LimpezaSolucoes.this, "Sem ligação à Internet", Toast.LENGTH_LONG);
                        toast.show();
                    }
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.e("objecto: ", jsonObject.toString());

    }

    /**
     * Mostra no ecra as solucoes recebidas por json
     * @param s
     */
    public void solucoesForLimpeza(final String s) {

        new Thread()
        {
            public void run()
            {
                LimpezaSolucoes.this.runOnUiThread(new Runnable()
                {
                    public void run() {
                        try {
                            Log.e("s: ", s);

                            JSONObject jsonobject = new JSONObject(s);
                            String opcao = jsonobject.getString("opcao");
                            String resposta = jsonobject.getString("resposta");
                            String categoria = jsonobject.getString("categoria");

                            Log.e("opcao: ", opcao);
                            Log.e("resposta: ", resposta);
                            Log.e("categoria: ", categoria);

                            LayoutInflater l = getLayoutInflater();
                            View v = l.inflate(R.layout.form_irr_cardview, null);
                            CardView c= (CardView) v.findViewById(R.id.card_view);

                            TextView tv = (TextView) v.findViewById(R.id.name_irr_cardview);
                            tv.setText("Categoria: "+ categoria);
                            tv= (TextView) v.findViewById(R.id.id_rota);
                            tv.setText("");
                            tv= (TextView) v.findViewById(R.id.margem_cardview);
                            tv.setText("Opção: " + opcao);
                            tv= (TextView) v.findViewById(R.id.id_cardview);
                            tv.setText("Sugestão: " + resposta);
                            layoutLimpezaSolucoes.addView(v);

                        }catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
            }
        }.start();
    }

    /**
     * Termina a activity
     * @param view
     */
    public void moveOn(View view) {
        Intent intent = new Intent(getApplicationContext(),Homepage.class);
        startActivity(intent);
    }

    //--TOOLBAR
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
        {
            if(User.getInstance().getAuthentication_token().contentEquals(""))
                startActivity(new Intent(this, Login.class));
            else {
                startActivity(new Intent(this, Profile.class));
            }
        }
        return super.onOptionsItemSelected(item);
    }
    //--TOOLBAR

}
