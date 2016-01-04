package ldso.rios.Form.IRR;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import ldso.rios.Autenticacao.Login;
import ldso.rios.DataBases.DB_functions;
import ldso.rios.DataBases.User;
import ldso.rios.Form.Form_functions;
import ldso.rios.MainActivities.Form_IRR_mainActivity;
import ldso.rios.MainActivities.GuardaRios;
import ldso.rios.MainActivities.Homepage;
import ldso.rios.MainActivities.Profile;
import ldso.rios.R;

/*
View para mostrar um form irr já preenchido
 */

public class ViewFormIRR extends AppCompatActivity {

    LinearLayout linearLayout;
    LinearLayout linearLayoutPhotos;
    Form_IRR form;
    String id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_form_irr);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Formulário IRR");
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        linearLayout = (LinearLayout) this.findViewById(R.id.linerLayout);

        linearLayout.setFocusable(false);
        linearLayout.setClickable(false);

        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);


        this.form = new Form_IRR();
        this.form.generate();
        try {
            this.id = ((int) this.getIntent().getSerializableExtra("id")) + "";
        } catch (Exception e) {

        }

        if (getIntent().getSerializableExtra("form_irr") != null) {
            HashMap<Integer, Object> respostas = (HashMap<Integer, Object>) getIntent().getSerializableExtra("form_irr");
            HashMap<Integer, String> outros = (HashMap<Integer, String>) respostas.get(-3);
            this.form.arrayListURI = (ArrayList<String>) respostas.get(-5);
            this.form.setRespostas(respostas, outros);
            this.form.other_response = outros;

        }

        Form_functions.createTitle("Rio:" + this.form.nomeRio, linearLayout, this.getApplicationContext());
        Form_functions.createTitle("Margem:" + ((this.form.margem == 1) ? "Esquerda" : "Direita"), linearLayout, this.getApplicationContext());

        for (String uri : this.form.arrayListURI) {
            try {
                ImageView img = new ImageView(getApplicationContext());
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                Bitmap bitmapOtiginal = BitmapFactory.decodeFile(uri, options);
                Display display = getWindowManager().getDefaultDisplay();
                Point size = new Point();
                display.getSize(size);
                int width = size.x;
                int i1 = bitmapOtiginal.getHeight() * width / bitmapOtiginal.getWidth();
                Bitmap thumbnail = Bitmap.createScaledBitmap(
                        bitmapOtiginal, width, i1, false);
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                thumbnail.compress(Bitmap.CompressFormat.JPEG, 100, bytes);

                img.setImageBitmap(thumbnail);
                linearLayout.addView(img);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        linearLayoutPhotos = new LinearLayout(getApplicationContext());
        linearLayout.addView(linearLayoutPhotos);

        String id = (String) getIntent().getSerializableExtra("form_irr_id");
        if (id != null)
            getImagesSite(id);
        for (int i = 0; i <= 32; i++) {
            try {
                this.form.getPerguntas().get(i).generateView(linearLayout, this);
            } catch (IOException e) {
                e.printStackTrace();
            }
            this.form.getPerguntas().get(i).setAnswer();
        }
    }

    /**
     * Funcao Chamada sempre que é feito uplaod de uma imagem. Quando forem todas, termina a activity
     * @param path
     */
    public void saveImageDB(String path) {
        Log.e("entrou", "entrou na funcao");
        form.arrayListURI.remove(path);
        if (form.arrayListURI.size() == 0) {
            new Thread() {
                public void run() {
                    ViewFormIRR.this.runOnUiThread(new Runnable() {
                        public void run() {
                            Toast toast = Toast.makeText(ViewFormIRR.this, "IRR submetido", Toast.LENGTH_LONG);
                            toast.show();
                            Intent intent = new Intent(ViewFormIRR.this, Form_IRR_mainActivity.class); // need to set your Intent View here
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            ViewFormIRR.this.startActivity(intent);
                            ViewFormIRR.this.finish();
                        }
                    });
                }
            }.start();
        }

    }

    /**
     * Se clicar no botão de edit, inicia um FormIRR_Swipe para editar o formulário
     * @param view
     */
    public void edit_form(View view) {
        //Log.e("vai","vai editar");
        Intent i;
        i = new Intent(this, FormIRR_Swipe.class);
        i.putExtra("form_irr", form.getRespostas());
        i.putExtra("form_irr_other", form.other_response);
        i.putExtra("id", id);
        startActivity(i);
    }

    /**
     * Função chamada quando é verificado que um Form foi apagado
     */
    public void apagaou() {
        Intent intent = new Intent(getApplicationContext(), Form_IRR_mainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    /**
     * Vai buscar as imagens de um Form com id
     * @param id id do form irr
     */
    public void getImagesSite(String id) {

        DB_functions.getForm(this, User.getInstance().getAuthentication_token(), User.getInstance().getEmail(), id);

    }

    /**
     * Recebe o Json com o array de imagens e mostra no ecra do dispositivo
     * @param s
     * @throws JSONException
     */
    public void processImages(String s) throws JSONException {

        JSONObject json = new JSONObject(s);

        final JSONArray array = json.getJSONArray("images");
        final ProgressBar progressBar;
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        if (array.length() == 0) {
            new Thread() {
                public void run() {
                    ViewFormIRR.this.runOnUiThread(new Runnable() {
                        public void run() {
                            progressBar.setVisibility(View.INVISIBLE);
                        }
                    });
                }
            }.start();
        }

        for (int i = 0; i < array.length(); i++) {
            final int finalI = i;
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        JSONObject imageObj = (JSONObject) array.get(0);
                        JSONObject image = (JSONObject) imageObj.get("image");
                        String url = DB_functions.base_url + image.getString("url");

                        try {
                            URL url_value = new URL(url);
                            BitmapFactory.Options options = new BitmapFactory.Options();
                            options.inSampleSize = 4;
                            final Bitmap b = BitmapFactory.decodeStream(url_value.openConnection().getInputStream(), null, options);

                            Display display = getWindowManager().getDefaultDisplay();
                            Point size = new Point();
                            display.getSize(size);
                            int width = size.x;
                            int i1 = b.getHeight() * width / b.getWidth();
                            final Bitmap thumbnail = Bitmap.createScaledBitmap(
                                    b, width, i1, false);


                            new Thread() {
                                public void run() {
                                    ViewFormIRR.this.runOnUiThread(new Runnable() {
                                        public void run() {
                                            ImageView img = new ImageView(getApplicationContext());
                                            img.setImageBitmap(thumbnail);
                                            linearLayoutPhotos.addView(img);
                                            int count = linearLayoutPhotos.getChildCount();
                                            View v = null;
                                            for (int i = 0; i < count; i++) {
                                                v = linearLayoutPhotos.getChildAt(i);
                                            }
                                            if (count == array.length())
                                                progressBar.setVisibility(View.INVISIBLE);
                                        }
                                    });
                                }
                            }.start();

                        } catch (Exception e) {
                            e.printStackTrace();
                            new Thread() {
                                public void run() {
                                    ViewFormIRR.this.runOnUiThread(new Runnable() {
                                        public void run() {
                                            progressBar.setVisibility(View.INVISIBLE);
                                        }
                                    });
                                }
                            }.start();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            thread.start();
        }

    }


    //--TOOLBAR
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        if (this.getIntent().getSerializableExtra("saved") != null)
            getMenuInflater().inflate(R.menu.menu_form_upload, menu);
        else
            getMenuInflater().inflate(R.menu.menu_form_irrview, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.navigate_guardarios)
            startActivity(new Intent(this, GuardaRios.class));
        if (id == R.id.navigate_account)
            if(User.getInstance().getAuthentication_token().contentEquals(""))
                startActivity(new Intent(this, Login.class));
            else {
                startActivity(new Intent(this, Profile.class));
            };
        if (id == R.id.navigate_upload) {

            if (DB_functions.haveNetworkConnection(getApplicationContext())) {


                try {
                    String[] temp = form.nomeRio.split(" id:");
                    String nome = temp[0];
                    String codigo = temp[1];
                    //Form_IRR.loadFromIRR(this.getApplicationContext());
                    this.form.fillAnswers();
                    Form_IRR.uploadFormIRR(this, this.getApplicationContext(), this.form);
                    if (this.form.arrayListURI.size() == 0) {
                        Intent intent = new Intent(getApplicationContext(), Homepage.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                } catch (Exception e) {

                }

            } else {
                Toast toast = Toast.makeText(ViewFormIRR.this, "Sem ligação à Internet", Toast.LENGTH_LONG);
                toast.show();
            }

        }
        if (id == R.id.navigate_remove) {
            if (DB_functions.haveNetworkConnection(getApplicationContext())) {
                User u = User.getInstance();
                DB_functions.deleteForm(this, this.getIntent().getSerializableExtra("id").toString(), u.getEmail(), u.getAuthentication_token());
                Toast toast = Toast.makeText(ViewFormIRR.this, "IRR apagado", Toast.LENGTH_LONG);
                toast.show();
                Intent intent = new Intent(getApplicationContext(), Form_IRR_mainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            } else {
                Toast toast = Toast.makeText(ViewFormIRR.this, "Sem ligação à Internet", Toast.LENGTH_LONG);
                toast.show();
            }
        }
        return super.onOptionsItemSelected(item);
    }
    //--TOOLBAR
}
