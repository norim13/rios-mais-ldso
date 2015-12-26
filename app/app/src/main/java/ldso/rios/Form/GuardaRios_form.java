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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
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
import ldso.rios.Mapa_rios;
import ldso.rios.R;
import ldso.rios.Utils;

public class GuardaRios_form extends AppCompatActivity {

    LinearLayout linearLayout;
    FrameLayout frameLayout;
    protected ArrayList<RadioButton> question1;
    protected ArrayList<RadioButton> question2;
    protected ArrayList<RadioButton> question3;
    protected ArrayList<RadioButton> question4;
    protected ArrayList<CheckBox> question5;
    protected EditText question6;
    protected ProgressBar progressbar;

    protected ArrayList<String> arrayListURI;

    Button buttonTakePic;
    Button buttonSelectPic;
    LinearLayout horizontal;

    private  static final int CAM_REQUEST=100;
    private static final int SELECT_PHOTO = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guarda_rios_form);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Avistamento");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        linearLayout = (LinearLayout) this.findViewById(R.id.irr_linear);
        frameLayout = (FrameLayout) this.findViewById(R.id.frameLayout);
        progressbar = (ProgressBar) this.findViewById(R.id.progressBar);
        arrayListURI=new ArrayList<String>();

        //Adiciona as perguntas
        createsQuestions();



        //Adiciona interface para as fotos

        LayoutInflater inflater = getLayoutInflater();
        View viewInflated = inflater.inflate(R.layout.photo_selection, null);

        buttonTakePic= (Button) viewInflated.findViewById(R.id.camera);
        buttonTakePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent,CAM_REQUEST);
            }
        });

        buttonSelectPic= (Button) viewInflated.findViewById(R.id.galery);
        buttonSelectPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, SELECT_PHOTO);
            }
        });

        linearLayout.addView(viewInflated);
        horizontal= (LinearLayout) viewInflated.findViewById(R.id.horizontalLinearLayout);

    }

    //Adiciona as questoes a activity
    public void createsQuestions(){
        Resources r = getResources();
        float px_float = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, r.getDisplayMetrics());
        int px = (int) px_float;
        px_float = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15, r.getDisplayMetrics());
        int px2 = (int) px_float;


        LinearLayout.LayoutParams radioParams;
        radioParams = new LinearLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        radioParams.setMargins(0, px2, 0, px);

        LinearLayout ll = new LinearLayout(this);
        ll.setLayoutParams(radioParams);
        TextView tv1 = new TextView(this);
        tv1.setText("Local onde foi observado?");

        tv1.setLayoutParams(radioParams);
        ll.addView(tv1);
        linearLayout.addView(ll);
        String[] options = {"Árvores/ramo", "Pedra/rocha", "Ninho"};
        question1 = Form_functions.createRadioButtons(options, linearLayout, this);

        TextView tv2 = new TextView(this);
        tv2.setText("Estava a voar?");
        tv2.setLayoutParams(radioParams);
        linearLayout.addView(tv2);
        String[] options2 = {"Não estava a voar", "Pairar", "Voo picado para mergulhar", "Mergulhar", "De passagem"};
        question2 = Form_functions.createRadioButtons(options2, linearLayout, this);

        TextView tv3 = new TextView(this);
        tv3.setText("Estava a cantar?");
        tv3.setLayoutParams(radioParams);
        linearLayout.addView(tv3);
        String[] options3 = {"Não estava a cantar", "Presença", "Alerta e marcação de teritório"};
        question3 = Form_functions.createRadioButtons(options3, linearLayout, this);

        TextView tv4 = new TextView(this);
        tv4.setText("Estava a alimentar-se?");
        tv4.setLayoutParams(radioParams);
        linearLayout.addView(tv4);
        String[] options4 = {"Não estava a alimentar-se", "Peixe", "Libelinha", "Pequenos insectos"};
        question4 = Form_functions.createRadioButtons(options4, linearLayout, this);

        TextView tv5 = new TextView(this);
        tv5.setText("Comportamentos:");
        linearLayout.addView(tv5);
        tv5.setLayoutParams(radioParams);

        String[] options5 = {"Estava parado?",
                "Estava a beber?",
                "Estava a caçar?",
                "Estava a cuidar das crias?"};
        question5 = Form_functions.createCheckboxes(options5, linearLayout, this);

        question6 = new EditText(this);
        question6.setHint("Outro comportamento?");
        linearLayout.addView(question6);

    }

    //funcao chamada quando o utilizador quer submeter
    public void saveGuardaRios(View view) throws IOException, JSONException {

        for (int i=0;i<arrayListURI.size();i++)
        {
            File f= new File(arrayListURI.get(i));
            if(f.exists())
                Log.e("existe",arrayListURI.get(i));
            else
                Log.e("nao existe",arrayListURI.get(i));

        }
        progressbar.setVisibility(View.VISIBLE);
        Integer escolha = Form_functions.getRadioButtonOption(question1);
        Log.e("escolha",escolha+"");
        String q1="";
        switch (escolha){
            case 1:
                q1="arvores";
                break;
            case 2:
                q1="pedra";
                break;
            case 3:
                q1="solo";
                break;
            case 4:
                q1="mergulhar";
                break;
            case 5:
                q1="ninho";
                break;
            default:
                q1="";
                break;
        }
        Log.e("saiu","saiu do switch");
        String q2 = Form_functions.getRadioButtonOption_string(question2);
        String q3 = Form_functions.getRadioButtonOption_string(question3);
        String q4 = Form_functions.getRadioButtonOption_string(question4);
        ArrayList<Integer> q5 = Form_functions.getCheckboxes(question5);
        String q6 = String.valueOf(question6.getText());

        RadioButton r1= (RadioButton) findViewById(R.id.currLocRadioButton);
        RadioButton r2= (RadioButton) findViewById(R.id.selctLocRadioButton);

        Float lat,lang;

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

        String nomeRio=((EditText)this.findViewById(R.id.nomeRio)).getText().toString();

        Log.e("DB","vai entrar na DB");

        DB_functions.saveGuardaRios(this, User.getInstance().getEmail(), User.getInstance().getAuthentication_token(),q1, q2, q3, q4, q5, q6, lat,lang,nomeRio);
    }

    //funcao chamada quando foi submetido o form
    public void saveGuardaRiosDB(String id) throws IOException, JSONException {
      /*  new Thread() {
            public void run() {
                GuardaRios_form.this.runOnUiThread(new Runnable() {
                    public void run() {
                        progressbar.setVisibility(View.INVISIBLE);
                        Toast toast = Toast.makeText(GuardaRios_form.this, "Avistamento submetido", Toast.LENGTH_LONG);
                        toast.show();
                        GuardaRios_form.this.finish();

                    }
                });
            }
        }.start();
        */

        if (arrayListURI.size()==0)
        {

            new Thread() {
                public void run() {
                    GuardaRios_form.this.runOnUiThread(new Runnable() {
                        public void run() {
                            progressbar.setVisibility(View.INVISIBLE);
                            Toast toast = Toast.makeText(GuardaRios_form.this, "Avistamento submetido", Toast.LENGTH_LONG);
                            toast.show();
                            GuardaRios_form.this.finish();

                        }
                    });
                }
            }.start();


        }

        for (int i=0;i<arrayListURI.size();i++)
        {
            Log.e("uri",arrayListURI.get(i));

            DB_functions.saveImage(this,arrayListURI.get(i), User.getInstance().getEmail(),User.getInstance().getAuthentication_token(),"guardario",id);
        }


    }

    //abre o mapa e modifica
    public void abrirMapa(View view) {
        startActivityForResult(new Intent(this, Mapa_rios.class), 1);
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
            Log.e("array",arrayListURI.toString());
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
            Log.e("arrat",arrayListURI.toString());



            try {

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


            } catch (IOException e) {
                e.printStackTrace();
                arrayListURI.remove(destination.getAbsolutePath());
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


    public void saveImageDB(String path) {
        Log.e("entrou","entrou na funcao");
        arrayListURI.remove(path);
        if (arrayListURI.size()==0)
        {
            new Thread() {
                public void run() {
                    GuardaRios_form.this.runOnUiThread(new Runnable() {
                        public void run() {
                            progressbar.setVisibility(View.INVISIBLE);
                            Toast toast = Toast.makeText(GuardaRios_form.this, "Avistamento submetido", Toast.LENGTH_LONG);
                            toast.show();
                            GuardaRios_form.this.finish();

                        }
                    });
                }
            }.start();
        }


    }
}
