package ldso.rios.MainActivities;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import ldso.rios.Autenticacao.Login;
import ldso.rios.DataBases.DB_functions;
import ldso.rios.DataBases.User;
import ldso.rios.Mapa_Rotas;
import ldso.rios.R;

public class RotasRios_list extends AppCompatActivity {

    ArrayList<Rota> rotas;
    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rotas_rios_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Rotas Rios+");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        rotas= new ArrayList<Rota>();
        linearLayout= (LinearLayout) this.findViewById(R.id.linearLayout);

        try {
            if (DB_functions.haveNetworkConnection(getApplicationContext()))
            DB_functions.getRotasList(this);
            else {
                Toast toast = Toast.makeText(RotasRios_list.this, "Sem ligação à Internet", Toast.LENGTH_LONG);
                toast.show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void routeList(final String resposta) {

        new Thread()
        {
            public void run()
            {
                RotasRios_list.this.runOnUiThread(new Runnable()
                {
                    public void run() {
                        try {
                            JSONArray jsonarray = new JSONArray(resposta);

                            for (int i = 0; i < jsonarray.length(); i++) {
                                final JSONObject rota_json = jsonarray.getJSONObject(i);

                                final Rota r= new Rota(rota_json.getInt("id"),
                                                rota_json.getString("nome"),
                                                rota_json.getString("descricao"),
                                                rota_json.getString("zona"),
                                                rota_json.getString("created_at"),
                                                rota_json.getString("updated_at"),
                                                rota_json.getBoolean("publicada"));

                                LayoutInflater l = getLayoutInflater();
                                View v = l.inflate(R.layout.rotas_cardview, null);
                                CardView c= (CardView) v.findViewById(R.id.card_view);

                                TextView nome=(TextView) v.findViewById(R.id.nome_rota);
                                TextView id=(TextView) v.findViewById(R.id.id_rota);
                                TextView zona=(TextView) v.findViewById(R.id.zona_rota);
                                TextView tempo=(TextView) v.findViewById(R.id.tempo_rota);

                                nome.setText("Nome: "+r.getNome());
                                id.setText("ID: "+r.getId());
                                zona.setText("Zona: "+r.getZona());
                                tempo.setText("Ultima Edição: "+r.getUpdated_at());
                                c.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Log.e("clicou","clicou");
                                        Intent i;
                                        i = new Intent(v.getContext(), Mapa_Rotas.class);
                                        i.putExtra("id",r.getId());
                                        startActivity(i);
                                    }
                                });

                                linearLayout.addView(v);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }.start();
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
        {
            if(User.getInstance().getAuthentication_token().contentEquals("")||
                    User.getInstance().getAuthentication_token().contentEquals("-1"))
                startActivity(new Intent(this, Login.class));
            else {
                startActivity(new Intent(this, Profile.class));
            }
        }
        return super.onOptionsItemSelected(item);
    }

}
