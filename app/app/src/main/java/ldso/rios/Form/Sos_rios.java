package ldso.rios.Form;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import ldso.rios.Autenticacao.Login;
import ldso.rios.DataBases.DB_functions;
import ldso.rios.DataBases.User;
import ldso.rios.MainActivities.GuardaRios;
import ldso.rios.MainActivities.Profile;
import ldso.rios.Mapa_rios;
import ldso.rios.R;
import ldso.rios.SelectRioWebview;
import ldso.rios.Utils;

public class Sos_rios extends AppCompatActivity {

    LinearLayout linearLayout;
    protected ArrayList<RadioButton> question1;
    protected ArrayList<RadioButton> question2;
    protected EditText question3;
    protected ProgressBar progressbar;


    protected ArrayList<String> arrayListURI;

    Button buttonTakePic;
    Button buttonSelectPic;
    LinearLayout horizontal;

    private  static final int CAM_REQUEST=100;
    private static final int SELECT_PHOTO = 200;
    private static final int SELECT_RIO=300;


    TextView tv1,tv2;



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

        arrayListURI=new ArrayList<String>();


        LayoutInflater inflater = getLayoutInflater();
        View viewInflated = inflater.inflate(R.layout.fragment_location, null);
        linearLayout.addView(viewInflated);


        LinearLayout.LayoutParams radioParams;
        radioParams = new LinearLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        radioParams.setMargins(0, px2, 0, px);

        LinearLayout ll=new LinearLayout(this);
        ll.setLayoutParams(radioParams);
        tv1=new TextView(this);
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

        tv2=new TextView(this);
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


        //Adiciona interface para as fotos

        View viewInflatedImages = inflater.inflate(R.layout.photo_selection, null);

        buttonTakePic= (Button) viewInflatedImages.findViewById(R.id.camera);
        buttonTakePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent,CAM_REQUEST);
            }
        });

        buttonSelectPic= (Button) viewInflatedImages.findViewById(R.id.galery);
        buttonSelectPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, SELECT_PHOTO);
            }
        });

        linearLayout.addView(viewInflatedImages);
        horizontal= (LinearLayout) viewInflatedImages.findViewById(R.id.horizontalLinearLayout);




    }

    //abre o mapa e modifica
    public void abrirMapa(View view) {
        startActivityForResult(new Intent(this, Mapa_rios.class), 1);
    }


    public void abrirWebView(View view) {
        startActivityForResult(new Intent(this, SelectRioWebview.class), SELECT_RIO);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        //se for a resposta do mapa
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                String result = data.getStringExtra("latlan_current");
                if (!result.contentEquals("0")) {
                    ((RadioButton)this.findViewById(R.id.currLocRadioButton)).setText("Atual: " + result);
                }

                result = data.getStringExtra("latlan_picked");
                if (!result.contentEquals("0")) {
                    ((RadioButton)this.findViewById(R.id.selctLocRadioButton)).setText("Escolhida: " + result);
                }
                // Log.e("resultado",result);
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
                Log.e("resultado", "nao recebeu nada");

            }
        }

        //se for a resposta da camara
        else if (requestCode== CAM_REQUEST)
        {
            if (resultCode == Activity.RESULT_OK) {
                Log.e("array", arrayListURI.toString());
                Bitmap thumbnail = (Bitmap) data.getExtras().get("data");

                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

                File destination = new File(Environment.getExternalStorageDirectory(),
                        System.currentTimeMillis() + ".jpg");
                FileOutputStream fo;
                try {
                    destination.createNewFile();
                    fo = new FileOutputStream(destination);
                    fo.write(bytes.toByteArray());
                    fo.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                arrayListURI.add(destination.getAbsolutePath());
                Log.e("arrat", arrayListURI.toString());


                try {

                    LayoutInflater inflater = getLayoutInflater();
                    View viewInflated = inflater.inflate(R.layout.photo_view, null);
                    final LinearLayout novo = (LinearLayout) viewInflated.findViewById(R.id.novo);
                    ImageView cancel = (ImageView) viewInflated.findViewById(R.id.cancel);
                    ImageView i = (ImageView) viewInflated.findViewById(R.id.photoImageView);

                    //poe a imagem tirada
                    Bitmap result = Utils.squareimage(thumbnail);
                    i.setImageBitmap(result);

                    //ao carregar em eliminar, tira do ecra e apaga da lista
                    cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            novo.removeAllViews();
                        }
                    });

                    linearLayout.addView(novo);


                } catch (IOException e) {
                    e.printStackTrace();
                    arrayListURI.remove(destination.getAbsolutePath());
                }

            }


        }

        else if (requestCode==SELECT_RIO)
        {
            if (resultCode == Activity.RESULT_OK) {
                ((EditText)this.findViewById(R.id.editText)).setText(data.getStringExtra("nomeRio")+" id:"+data.getStringExtra("codigoRio"));
            }
        }


        else if (requestCode == SELECT_PHOTO)
        {
            if(resultCode == RESULT_OK){
                Uri selectedImage = data.getData();
                String path= Utils.getRealPathFromURI(selectedImage,this.getApplicationContext());
                File f = new File(Utils.getRealPathFromURI(selectedImage,this.getApplicationContext()));

                if(f.exists())
                {
                    try{

                        arrayListURI.add(path);
                        BitmapFactory.Options options = new BitmapFactory.Options();
                        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                        Bitmap bitmapOtiginal = BitmapFactory.decodeFile(path, options);
                        //cria um htumbnail com max with de 100 pixeis
                        int i1 = bitmapOtiginal.getHeight() * 200 / bitmapOtiginal.getWidth();
                        Bitmap thumbnail = Bitmap.createScaledBitmap(
                                bitmapOtiginal, 200, i1, false);


                        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

                        LayoutInflater inflater = getLayoutInflater();
                        View viewInflated = inflater.inflate(R.layout.photo_view, null);
                        final LinearLayout novo= (LinearLayout) viewInflated.findViewById(R.id.novo);
                        ImageView cancel= (ImageView) viewInflated.findViewById(R.id.cancel);
                        ImageView i= (ImageView) viewInflated.findViewById(R.id.photoImageView);

                        //poe a imagem tirada
                        Bitmap result=Utils.squareimage(thumbnail);
                        i.setImageBitmap(result);

                        //ao carregar em eliminar, tira do ecra e apaga da lista
                        cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                novo.removeAllViews();
                            }
                        });

                        linearLayout.addView(novo);

                    }
                    catch(Exception e){
                        e.printStackTrace();
                    }

                }
                else
                    Log.e("Nao existe","ficheiro nao existe");

            }
        }
    }




    public void saveImageDB(String path) {
        arrayListURI.remove(path);
        if (arrayListURI.size()==0)
        {
            new Thread() {
                public void run() {
                    Sos_rios.this.runOnUiThread(new Runnable() {
                        public void run() {
                            progressbar.setVisibility(View.INVISIBLE);
                            Toast toast = Toast.makeText(Sos_rios.this, "SOS submetido", Toast.LENGTH_LONG);
                            toast.show();
                            Sos_rios.this.finish();

                        }
                    });
                }
            }.start();
        }


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
            if(User.getInstance().getAuthentication_token().contentEquals(""))
                startActivity(new Intent(this, Login.class));
            else {
                startActivity(new Intent(this, Profile.class));
            }
        }
        return super.onOptionsItemSelected(item);
    }

    public void saveSOSRios(View view) {
        String q1=Form_functions.getRadioButtonOption_string(question1);
        String q2=Form_functions.getRadioButtonOption_string(question2);
        String q3=question3.getText().toString();

        Boolean incomplete=false;

        if (q1.contentEquals(""))
        {
            incomplete=true;
            tv1.setError("Campo vaizo");
        }
        if (q2.contentEquals(""))
        {
            incomplete=true;
           tv2.setError("Campo vaizo");
        }
        if (q3.contentEquals(""))
        {
            incomplete=true;
            question3.setError("Campo vaizo");
        }
        if (((EditText)this.findViewById(R.id.editText)).getText().toString().contentEquals(""))
        {
            incomplete=true;
            ((EditText)this.findViewById(R.id.editText)).setError("Campo vaizo");
        }

        Float lat,lang;

        RadioButton r1= ((RadioButton)this.findViewById(R.id.currLocRadioButton));
        RadioButton r2=((RadioButton)this.findViewById(R.id.selctLocRadioButton));

        if (r1.isChecked())
        {
            lat = Float.valueOf(r1.getText().toString().split("Atual: ")[1].split(";")[0]);
            lang = Float.valueOf(r1.getText().toString().split("Atual: ")[1].split(";")[1]);
        }
        else if(r2.isChecked())
        {
            lat = Float.valueOf(r2.getText().toString().split("Escolhida: ")[1].split(";")[0]);
            lang = Float.valueOf(r2.getText().toString().split("Escolhida: ")[1].split(";")[1]);
        }
        else
        {
            lat=lang=0f;
        }





        if (!incomplete) {
            if (DB_functions.haveNetworkConnection(getApplicationContext())) {
                progressbar.setVisibility(View.VISIBLE);
                String nomeRio=((EditText)this.findViewById(R.id.editText)).getText().toString();
                DB_functions.saveSOSRios(this, User.getInstance().getEmail(), User.getInstance().getAuthentication_token(), q1, q2, q3,nomeRio,lat,lang);
            }
            else {
                Toast toast = Toast.makeText(Sos_rios.this, "Sem ligação à Internet", Toast.LENGTH_LONG);
                toast.show();
            }

        }
    }

    public void saveSOSDB(String id) throws IOException, JSONException {
        if (arrayListURI.size()==0) {
            new Thread() {
                public void run() {
                    Sos_rios.this.runOnUiThread(new Runnable() {
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
        for (int i=0;i<arrayListURI.size();i++)
        {
            Log.e("uri",arrayListURI.get(i));
            if (DB_functions.haveNetworkConnection(getApplicationContext()))
            DB_functions.saveImage(this,arrayListURI.get(i), User.getInstance().getEmail(),User.getInstance().getAuthentication_token(),"report",id);
            else {
                Toast toast = Toast.makeText(Sos_rios.this, "Sem ligação à Internet. Imagem não foi enviada.", Toast.LENGTH_LONG);
                toast.show();
            }
        }


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
