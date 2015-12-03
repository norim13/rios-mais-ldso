package ldso.rios.MainActivities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
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
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import ldso.rios.Autenticacao.Login;
import ldso.rios.DataBases.DB_functions;
import ldso.rios.Form.IRR.FormIRRSwipe;
import ldso.rios.Form.IRR.Form_IRR;
import ldso.rios.Form.IRR.ViewFormIRR;
import ldso.rios.R;

/*
Mostra os formulários de um user
 */
public class Form_IRR_mainActivity extends AppCompatActivity {

    LinearLayout linearLayout;
    CardView cardView;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_irr);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Formulários IRR");
        setSupportActionBar(toolbar);
        linearLayout = (LinearLayout) findViewById(R.id.linear_irr);


        progressBar= (ProgressBar) findViewById(R.id.progressBar);


        //vai buscar o token e email da sessão
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        SharedPreferences settings = getSharedPreferences(Homepage.PREFS_NAME, 0);
        String token=settings.getString("token", "-1");
        String email=settings.getString("email", "-1");

        //vai buscar os forms de um user
        try {
            DB_functions.getForms(token,email,this);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }


        TextView textView=new TextView(this);
        try {
            textView.setText(Form_IRR.loadFromIRR(this.getApplicationContext()).size()+"<-Tamanho");
        } catch (IOException e) {
            e.printStackTrace();
        }
        linearLayout.addView(textView);

    }

    /*
    se clicar no botão "+"
     */
    public void new_form(View view){

        Intent i =new Intent(this, FormIRRSwipe.class);
        ArrayList<Integer[]> arrayList=new ArrayList<Integer[]>();
        Integer[] integers = new Integer[0];
        arrayList.add(integers);

        this.startActivity(i);
    }

    /*
    desenha uma caixa com a informação de um formulário
     */
    public void formsFromUser(final String s) {

        new Thread()
        {
            public void run()
            {
                Form_IRR_mainActivity.this.runOnUiThread(new Runnable()
                {
                    public void run() {
                        try {
                            progressBar.setVisibility(View.INVISIBLE);
                            JSONArray jsonarray = new JSONArray(s);

                            Log.e("teste", "tamanh:" + jsonarray.length());
                            TextView title1= new TextView(getApplicationContext());
                            title1.setText("Submetidos:");
                            title1.setTextSize(20);
                            title1.setTextColor(Color.BLACK);
                            linearLayout.addView(title1);

                            for (int i = 0; i < jsonarray.length(); i++) {
                                final JSONObject form_irr_json = jsonarray.getJSONObject(i);

                                String idRio = form_irr_json.getString("idRio");
                                String irr = form_irr_json.getString("irr");
                                String id = form_irr_json.getString("id");
                                String margem = form_irr_json.getString("margem");


                                LayoutInflater l = getLayoutInflater();
                                View v = l.inflate(R.layout.form_irr_cardview, null);
                                CardView c= (CardView) v.findViewById(R.id.card_view);

                                TextView tv = (TextView) v.findViewById(R.id.name_irr_cardview);
                                tv.setText("Rio: "+idRio);
                                tv= (TextView) v.findViewById(R.id.irr_cardview);
                                tv.setText("IRR: "+irr);
                                tv= (TextView) v.findViewById(R.id.id_cardview);
                                tv.setText("ID: "+id);
                                tv= (TextView) v.findViewById(R.id.margem_cardview);
                                if(new String("0").equals(margem))
                                    tv.setText("Margem: Direita");
                                else tv.setText("Margem: Esquerda");

                                c.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Log.e("form","clicou");
                                        Form_IRR form_irr= new Form_IRR();
                                        try {
                                            form_irr.readResponseJson(form_irr_json);
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                        Intent i;
                                        i = new Intent(v.getContext(), ViewFormIRR.class);
                                        i.putExtra("form_irr",form_irr.getRespostas());
                                        startActivity(i);


                                    }
                                });

                                linearLayout.addView(v);
                            }

                            final ArrayList<HashMap<Integer,Object>> respostas =Form_IRR.loadFromIRR(getApplicationContext());

                            TextView title2= new TextView(getApplicationContext());
                            title2.setText("Guardados:");
                            title2.setTextSize(20);
                            title2.setTextColor(Color.BLACK);
                            linearLayout.addView(title2);

                            for (int i = 0; i < respostas.size(); i++) {

                                String idRio = "idrios";
                                String date= ""+respostas.get(i).get(-1);


                                LayoutInflater l = getLayoutInflater();
                                View v = l.inflate(R.layout.form_irr_cardview, null);
                                CardView c= (CardView) v.findViewById(R.id.card_view);

                                TextView tv = (TextView) v.findViewById(R.id.name_irr_cardview);
                                tv.setText("Rio: "+idRio);
                                tv= (TextView) v.findViewById(R.id.irr_cardview);
                                tv.setText("Date: "+date);


                                final int finalI = i;


                                if(respostas.get(i)==null)
                                    Log.e("teste","a resposta é nula");

                                c.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Log.e("form", "clicou");


                                        Log.e("form","clicou");
                                        Intent i;
                                        i = new Intent(v.getContext(), ViewFormIRR.class);
                                        i.putExtra("saved",(String) respostas.get(finalI).get(-2));
                                        i.putExtra("form_irr",respostas.get(finalI));
                                        startActivity(i);



                                    }
                                });

                                linearLayout.addView(v);
                            }




                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
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
            startActivity(new Intent(this,Login.class));
        return super.onOptionsItemSelected(item);
    }


}
