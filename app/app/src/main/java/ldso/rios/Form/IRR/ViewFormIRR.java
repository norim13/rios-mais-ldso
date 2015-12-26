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
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import ldso.rios.Autenticacao.Login;
import ldso.rios.DataBases.DB_functions;
import ldso.rios.DataBases.User;
import ldso.rios.Form.Form_functions;
import ldso.rios.MainActivities.Form_IRR_mainActivity;
import ldso.rios.MainActivities.GuardaRios;
import ldso.rios.MainActivities.Homepage;
import ldso.rios.R;

/*
View para mostrar um form irr já preenchido
 */
public class ViewFormIRR extends AppCompatActivity {

    LinearLayout linearLayout;
    Form_IRR form;
    String id;


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
        try {
            this.id = ((int) this.getIntent().getSerializableExtra("id")) + "";
        }
        catch (Exception e){

        }

        if(getIntent().getSerializableExtra("form_irr")!= null) {
            Log.e("form", "entrou");
            HashMap<Integer,Object> respostas=(HashMap<Integer, Object>) getIntent().getSerializableExtra("form_irr");
            HashMap<Integer,String> outros= (HashMap<Integer, String>) respostas.get(-3);
            this.form.arrayListURI= (ArrayList<String>) respostas.get(-5);
            this.form.setRespostas(respostas,outros);
            this.form.other_response=outros;

        }



        Form_functions.createTitle("Rio:"+this.form.nomeRio,linearLayout,this.getApplicationContext());
        if (this.form.current_location==null)
        Form_functions.createTitle("Localização:"+0+";"+0,linearLayout,this.getApplicationContext());
        else if (this.form.current_location)
            Form_functions.createTitle("Localização:"+this.form.lat_curr+";"+this.form.lon_curr,linearLayout,this.getApplicationContext());
        else
            Form_functions.createTitle("Localização:"+this.form.lat_sel+";"+this.form.lon_sel,linearLayout,this.getApplicationContext());

        Form_functions.createTitle("Margem:"+ ((this.form.margem == 1) ? "Esquerda" : "Direita"),linearLayout,this.getApplicationContext());

        Log.e("uri",this.form.arrayListURI.size()+"");
        for (String uri :this.form.arrayListURI)
        {


            try {
                ImageView img= new ImageView(getApplicationContext());
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


        for(int i=0;i<=32;i++)
        {
            try {
                this.form.getPerguntas().get(i).generateView(linearLayout, this);
            } catch (IOException e) {
                e.printStackTrace();
            }
            this.form.getPerguntas().get(i).setAnswer();
        }





    }

    public void saveImageDB(String path) {
        Log.e("entrou","entrou na funcao");
        form.arrayListURI.remove(path);
        if (form.arrayListURI.size()==0)
        {
            new Thread() {
                public void run() {
                    ViewFormIRR.this.runOnUiThread(new Runnable() {
                        public void run() {
                            Toast toast = Toast.makeText(ViewFormIRR.this, "IRR submetido", Toast.LENGTH_LONG);
                            toast.show();
                            ViewFormIRR.this.finish();
                        }
                    });
                }
            }.start();
        }

    }


    /*
    se clicar no botão de edit, inicia um FormIRRSwipe para editar o formulário
     */
    public void edit_form(View view){
        //Log.e("vai","vai editar");
        Intent i;
        i = new Intent(this, FormIRRSwipe.class);
        i.putExtra("form_irr", form.getRespostas());
        i.putExtra("form_irr_other",form.other_response);
        i.putExtra("id", id);
        startActivity(i);

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        if(this.getIntent().getSerializableExtra("saved")!=null)
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
            startActivity(new Intent(this, Login.class));
        if (id == R.id.navigate_upload)
        {

            //Form_IRR.loadFromIRR(this.getApplicationContext());
            this.form.fillAnswers();
            Form_IRR.uploadFormIRR(this,this.getApplicationContext(),this.form);
            if (this.form.arrayListURI.size()==0)
            {
                Intent intent = new Intent(getApplicationContext(), Homepage.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }


            //Log.e("teste","tamanho do array"+Form_IRR.all_from_irrs.size());
        }
        if (id == R.id.navigate_remove){
            //Log.e("delete","entrou");
            User u = User.getInstance();
            DB_functions.deleteForm(this,this.getIntent().getSerializableExtra("id").toString(),u.getEmail(),u.getAuthentication_token() );

        }

        return super.onOptionsItemSelected(item);
    }

    public void apagaou() {
        Toast toast = Toast.makeText(ViewFormIRR.this, "Formulário apagado", Toast.LENGTH_LONG);
        toast.show();
        Intent intent = new Intent(getApplicationContext(), Form_IRR_mainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
