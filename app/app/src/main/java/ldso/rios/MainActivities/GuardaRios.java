package ldso.rios.MainActivities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import ldso.rios.Autenticacao.Login;
import ldso.rios.DataBases.DB_functions;
import ldso.rios.DataBases.User;
import ldso.rios.Form.GuardaRios_form;
import ldso.rios.R;
import ldso.rios.Utils_Image;

public class GuardaRios extends AppCompatActivity {

    GridLayout gridLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guarda_rios);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Guarda Rios");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        try {
            if (DB_functions.haveNetworkConnection(getApplicationContext()))
            DB_functions.getGuardaRios(this);
            else
            {
                Toast toast = Toast.makeText(GuardaRios.this, "Sem ligação à Internet", Toast.LENGTH_LONG);
                toast.show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        gridLayout = (GridLayout) findViewById(R.id.girdLayout);
    }

    /**
     * Abre activity para criar um avistamento
     * @param view
     */
    public void form_guardarios(View view){
        if(!User.getInstance().getAuthentication_token().contentEquals("") && !User.getInstance().getAuthentication_token().contentEquals("-1")  ) {
            startActivity(new Intent(this, GuardaRios_form.class));
            this.finish();
        }
        else {
            Toast.makeText(getApplicationContext(), "Atentique-se para poder aceder a este conteúdo.", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Vai buscar as imagens à base de dados dos guarda rios
     * @param *
     * @throws JSONException
     */
    public void images(final String s) throws JSONException {
        new Thread()
        {
            public void run()
            {
                GuardaRios.this.runOnUiThread(new Runnable()
                {
                    public void run() {
                        try {
                            JSONObject g= new JSONObject(s);
                            final JSONArray array= g.getJSONArray("guardarios");
                            for(int i =0;i<array.length();i++)
                            {
                                    final int finalI = i;
                                    Thread thread = new Thread(new Runnable(){
                                        @Override
                                        public void run() {
                                            try {
                                                JSONObject guardario= array.getJSONObject(finalI);
                                                JSONObject array_imagens= (JSONObject) guardario.get("image");
                                                final String url=DB_functions.base_url+array_imagens.get("url");
                                                ImageView imageViewTemp = null;
                                                switch (finalI)
                                                {
                                                    case 0:imageViewTemp= (ImageView) findViewById(R.id.imageView1);break;
                                                    case 1:imageViewTemp= (ImageView) findViewById(R.id.imageView2);break;
                                                    case 2:imageViewTemp= (ImageView) findViewById(R.id.imageView3);break;
                                                    case 3:imageViewTemp= (ImageView) findViewById(R.id.imageView4);break;
                                                    case 4:imageViewTemp= (ImageView) findViewById(R.id.imageView5);break;
                                                    case 5:imageViewTemp= (ImageView) findViewById(R.id.imageView6);break;
                                                    case 6:imageViewTemp= (ImageView) findViewById(R.id.imageView7);break;
                                                    case 7:imageViewTemp= (ImageView) findViewById(R.id.imageView8);break;
                                                    case 8:imageViewTemp= (ImageView) findViewById(R.id.imageView9);break;
                                                    default:break;
                                                }
                                                final ImageView imageView=imageViewTemp;
                                                final Bitmap b= Utils_Image.loadImageURL(url);

                                                new Thread()
                                                {
                                                    public void run()
                                                    {
                                                        GuardaRios.this.runOnUiThread(new Runnable()
                                                        {
                                                            public void run() {
                                                                imageView.setImageBitmap(b);
                                                            }
                                                        });
                                                    }
                                                }.start();
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    });
                                    thread.start();
                            }
                        } catch (Exception e){
                        }
                    }
                });
            }
        }.start();
    }

    //--TOOLBAR
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_homepage, menu);

        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
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
