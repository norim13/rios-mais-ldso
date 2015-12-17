package ldso.rios;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;

import ldso.rios.DataBases.DB_functions;

public class RotaPoint_show extends AppCompatActivity {

    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rota_point_show);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle((String) this.getIntent().getSerializableExtra("nome_rota"));
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        String nome_pont= "Ponto "+(int) this.getIntent().getSerializableExtra("ordem_ponto")+" - "+(String) this.getIntent().getSerializableExtra("nome_ponto");

        TextView tv= (TextView) this.findViewById(R.id.nomeRota);
        tv.setText(nome_pont);


        TextView descricao= (TextView) this.findViewById(R.id.descricaoPonto);
        descricao.setText((String) this.getIntent().getSerializableExtra("descricao_ponto"));
        linearLayout= (LinearLayout) this.findViewById(R.id.linearLayout);


        try {
            generateImages((String) this.getIntent().getSerializableExtra("imagens"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void generateImages(String s) throws JSONException {
        Log.e("imagemns",s);
        final JSONArray array= new JSONArray(s);

        for (int i = 0; i < array.length(); i++) {

            if (i+1==(int) this.getIntent().getSerializableExtra("ordem_ponto"))
            {
                final int finalI = i;
                Thread thread = new Thread(new Runnable(){
                    @Override
                    public void run() {
                        try {
                            JSONArray object= (JSONArray) array.get(finalI);
                            Log.e("array",object.toString());
                            JSONObject imageObj= (JSONObject) object.get(0);
                            Log.e("object",imageObj.toString());
                            JSONObject image= (JSONObject) imageObj.get("image");
                            String url= DB_functions.base_url+image.getString("url");

                            try {
                                URL url_value = new URL(url);
                                BitmapFactory.Options options = new BitmapFactory.Options();
                                options.inSampleSize = 8;
                                final Bitmap b = BitmapFactory.decodeStream(url_value.openConnection().getInputStream(), null, options);




                                new Thread()
                                {
                                    public void run()
                                    {
                                        RotaPoint_show.this.runOnUiThread(new Runnable()
                                        {
                                            public void run() {
                                                Log.e("entrou","entrou");

                                                ImageView img= new ImageView(getApplicationContext());
                                                img.setImageBitmap(b);
                                                linearLayout.addView(img);



                                            }
                                        });
                                    }
                                }.start();

                            }
                            catch (Exception e){
                                e.printStackTrace();
                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

                thread.start();
            }





        }

    }



    public static Bitmap LoafImageURL(String url) throws IOException {


        //In case the image is too large
        URL url_value = new URL(url);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 8;
        Bitmap srcBmp = BitmapFactory.decodeStream(url_value.openConnection().getInputStream(), null, options);


        Log.e("depois",url);

        Bitmap dstBmp;

        if (srcBmp.getWidth() >= srcBmp.getHeight()){

            dstBmp = Bitmap.createBitmap(
                    srcBmp,
                    srcBmp.getWidth()/2 - srcBmp.getHeight()/2,
                    0,
                    srcBmp.getHeight(),
                    srcBmp.getHeight()
            );

        }else{

            dstBmp = Bitmap.createBitmap(
                    srcBmp,
                    0,
                    srcBmp.getHeight()/2 - srcBmp.getWidth()/2,
                    srcBmp.getWidth(),
                    srcBmp.getWidth()
            );
        }
        return dstBmp;
    }


}
