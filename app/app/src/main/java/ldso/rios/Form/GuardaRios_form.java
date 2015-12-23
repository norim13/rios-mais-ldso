package ldso.rios.Form;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
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
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
    GridLayout grid;
    LinearLayout horizontal;

    private  static final int CAM_REQUEST=1313;

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

        LayoutInflater l = getLayoutInflater();
       // LatLng current_location = this.getLocation();
       // current_loc = googleMap.addMarker(new MarkerOptions().position(current_location).title("Localização Actual"));

        /*
        MapFfrag map=new MapFfrag();

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        MapFfrag hello = new MapFfrag();
        fragmentTransaction.add(frameLayout.getId(), hello, "HELLO");
        fragmentTransaction.commit();

*/

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


        buttonTakePic = new Button(getApplicationContext());
        buttonTakePic.setText("Tirar Fotografia");

        buttonTakePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent,CAM_REQUEST);
            }
        });

        linearLayout.addView(buttonTakePic);

        grid= new GridLayout(getApplicationContext());
        grid.setColumnCount(3);

        horizontal = new LinearLayout(getApplicationContext());
        horizontal.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.addView(horizontal);



        //saveGuardaRios();


    }




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

        for (int i=0;i<arrayListURI.size();i++)
        {
            File file= new File(arrayListURI.get(i));
            DB_functions.saveImage(file, User.getInstance().getEmail(),User.getInstance().getAuthentication_token(),"guardario",id);
        }


    }


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
                Bitmap result=Utils.squareimage(thumbnail);
                ImageView i= new ImageView(this.getApplicationContext());
                i.setMaxWidth(200);
                i.setMaxHeight(200);
                i.setImageBitmap(result);
                final LinearLayout novo= new LinearLayout(getApplicationContext());
                novo.setOrientation(LinearLayout.HORIZONTAL);
                ImageView cancel= new ImageView(this.getApplicationContext());
                Bitmap bm = BitmapFactory.decodeResource(this.getResources(), R.drawable.ic_action_cancel);
                cancel.setImageBitmap(bm);

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        novo.removeAllViews();
                    }
                });
                novo.addView(cancel);
                novo.addView(i);
                linearLayout.addView(novo);


            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }//onActivityResult

    public String getImagePath(Uri uri) {
        String selectedImagePath;
        // 1:MEDIA GALLERY --- query from MediaStore.Images.Media.DATA
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        if (cursor != null) {
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            selectedImagePath = cursor.getString(column_index);
        } else {
            selectedImagePath = null;
        }

        if (selectedImagePath == null) {
            // 2:OI FILE Manager --- call method: uri.getPath()
            selectedImagePath = uri.getPath();
        }
        return selectedImagePath;
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
