package ldso.rios;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.io.InputStream;

import ldso.rios.Autenticacao.Login;
import ldso.rios.DataBases.User;
import ldso.rios.MainActivities.GuardaRios;
import ldso.rios.MainActivities.Profile;

public class RotaPoint_show extends FillGap2BaseActivity<ObservableScrollView> implements ObservableScrollViewCallbacks {
    @Override
    protected int getLayoutResId() {
        return R.layout.activity_fillgapscrollview;
    }


    @Override
    protected ObservableScrollView createScrollable() {
        ObservableScrollView scrollView = (ObservableScrollView) findViewById(R.id.scroll);
        scrollView.setScrollViewCallbacks(this);
        return scrollView;
    }


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
        ((TextView)this.findViewById(R.id.title)).setText((String) this.getIntent().getSerializableExtra("nome_ponto"));
        ((TextView)this.findViewById(R.id.nomeRota)).setText((String) this.getIntent().getSerializableExtra("nome_rota"));
        ((TextView)this.findViewById(R.id.descricaoPonto)).setText((String) this.getIntent().getSerializableExtra("descricao_ponto"));


        final Float lat=Float.parseFloat(this.getIntent().getSerializableExtra("lat").toString());
        final Float lon=Float.parseFloat(this.getIntent().getSerializableExtra("lon").toString());


        Thread thread = new Thread(new Runnable(){
            @Override
            public void run() {
                try {


                    try {
                        final Bitmap googleMapThumbnail = getGoogleMapThumbnail(lat, lon);


                        new Thread()
                        {
                            public void run()
                            {
                                RotaPoint_show.this.runOnUiThread(new Runnable()
                                {
                                    public void run() {
                                        ((ImageView)findViewById(R.id.image)).setImageBitmap(googleMapThumbnail);



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



    public static Bitmap getGoogleMapThumbnail(double lati, double longi) throws IOException {
        String URL = "http://maps.google.com/maps/api/staticmap?center=" +lati + "," + longi + "&zoom=15&size=600x600&" +
                "&markers=color:red%7C"+lati+","+longi+"&sensor=true";
        Bitmap bmp = null;
        HttpClient httpclient = new DefaultHttpClient();
        HttpGet request = new HttpGet(URL);

        InputStream in = null;
        try {
            in = httpclient.execute(request).getEntity().getContent();
            bmp = BitmapFactory.decodeStream(in);
            in.close();
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return bmp;
    }












    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id==R.id.navigate_guardarios)
            startActivity(new Intent(this,GuardaRios.class));

        if(id==R.id.navigate_account) {

            if(User.getInstance().getAuthentication_token().contentEquals(""))
                startActivity(new Intent(this, Login.class));
            else {
                startActivity(new Intent(this, Profile.class));
            }
        }

        return super.onOptionsItemSelected(item);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_homepage, menu);
        return true;
    }




}