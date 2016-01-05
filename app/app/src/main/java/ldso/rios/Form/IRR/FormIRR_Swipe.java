package ldso.rios.Form.IRR;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Toast;

import org.json.JSONException;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import ldso.rios.Autenticacao.Login;
import ldso.rios.DataBases.DB_functions;
import ldso.rios.DataBases.User;
import ldso.rios.MainActivities.Form_IRR_mainActivity;
import ldso.rios.MainActivities.GuardaRios;
import ldso.rios.Maps.Mapa_rios;
import ldso.rios.R;
import ldso.rios.Maps.SelectRioWebview;
import ldso.rios.Utils_Image;

/*
Class para mostrar um formulario irr (novo ou editar)
 */
public class FormIRR_Swipe extends AppCompatActivity {


    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private Form_IRR form;
    private Boolean novo;                               //se true é um novo formulairo, se é falso, é um edit
    Float lat_curr, lon_curr;
    Float lat_sel, lon_sel;
    LinearLayout horizontal;
    LinearLayout linearLayout;
    private static final int CAM_REQUEST = 100;
    private static final int SELECT_PHOTO = 200;
    private static final int SELECT_RIO = 300;

    int margem;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_irrswipe);

        novo = true;

        //criar um Form IRR e verificar se ha respostas anteriores
        this.form = new Form_IRR();
        this.form.generate();

        //ha respostas, logo é um edit
        if (getIntent().getSerializableExtra("form_irr") != null) {
            HashMap<Integer, Object> respostas = (HashMap<Integer, Object>) getIntent().getSerializableExtra("form_irr");
            HashMap<Integer, String> outros = (HashMap<Integer, String>) getIntent().getSerializableExtra("form_irr_other");
            this.form.setRespostas(respostas, outros);
            this.form.arrayListURI = (ArrayList<String>) this.form.respostas.get(-5);
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Novo Form");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), this.form, this.form.currLoc, this.form.selctLoc, this);

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);


        final String id = (String) this.getIntent().getSerializableExtra("id");
        if (id != null)
            novo = false;


        //se clicar no ok
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                form.fillAnswers();
                try {
                    if (novo) {
                        if (!DB_functions.haveNetworkConnection(getApplicationContext())) {
                            form.fillAnswers();
                            Form_IRR.saveFormIRR(form, getApplicationContext());
                            Toast toast = Toast.makeText(FormIRR_Swipe.this, "Sem ligação à Internet. IRR guardado.", Toast.LENGTH_LONG);
                            toast.show();
                            Intent intent = new Intent(getApplicationContext(), Form_IRR_mainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        } else {
                            String required = form.requiered();
                            if (required.contentEquals("")) {
                                if (form.nomeRioEditText.getText() != null && !form.nomeRioEditText.getText().toString().contentEquals("")) {
                                    DB_functions.saveForm(FormIRR_Swipe.this, User.getInstance().getAuthentication_token(), User.getInstance().getEmail(), form);
                                    if (form.arrayListURI.size() == 0) {
                                        if (!form.respostas.get(-2).toString().contentEquals("")) {
                                            Form_IRR.deleteFormIRRFile(getApplicationContext(), form.respostas.get(-2).toString());
                                        }
                                        Toast toast = Toast.makeText(FormIRR_Swipe.this, "IRR submetido", Toast.LENGTH_LONG);
                                        toast.show();
                                        Intent intent = new Intent(getApplicationContext(), Form_IRR_mainActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);
                                    }
                                } else {
                                    Toast.makeText(getApplicationContext(), "Selecione um rio", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast toast = Toast.makeText(FormIRR_Swipe.this, "Falta preencher " + required, Toast.LENGTH_LONG);
                                toast.show();
                            }
                        }
                    } else {
                        if (DB_functions.haveNetworkConnection(getApplicationContext())) {
                            if (form.nomeRioEditText.getText() != null && !form.nomeRioEditText.getText().toString().contentEquals("")) {
                                DB_functions.updateForm(User.getInstance().getAuthentication_token(), User.getInstance().getEmail(), id, form);
                                Intent intent = new Intent(getApplicationContext(), Form_IRR_mainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                            } else {
                                Toast.makeText(getApplicationContext(), "Selecione um rio", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast toast = Toast.makeText(FormIRR_Swipe.this, "Sem ligação à Internet", Toast.LENGTH_LONG);
                            toast.show();
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

    }


    /**
     * Funcao Chamada sempre que é feito uplaod de uma imagem. Quando forem todas, termina a activity
     *
     * @param path
     */
    public void saveImageDB(String path) {
        form.arrayListURI.remove(path);
        if (form.arrayListURI.size() == 0) {
            new Thread() {
                public void run() {
                    FormIRR_Swipe.this.runOnUiThread(new Runnable() {
                        public void run() {
                            if (!form.respostas.get(-2).toString().contentEquals("")) {
                                Form_IRR.deleteFormIRRFile(getApplicationContext(), form.respostas.get(-2).toString());
                            }
                            Toast toast = Toast.makeText(FormIRR_Swipe.this, "IRR submetido", Toast.LENGTH_LONG);
                            toast.show();
                            Intent intent = new Intent(FormIRR_Swipe.this, Form_IRR_mainActivity.class); // need to set your Intent View here
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            FormIRR_Swipe.this.startActivity(intent);
                            FormIRR_Swipe.this.finish();
                        }
                    });
                }
            }.start();
        }

    }


    /**
     * Abre a activity do mapa para escolher a localizaçao
     *
     * @param view
     */
    public void abrirMapa(View view) {
        startActivityForResult(new Intent(this, Mapa_rios.class), 1);
    }

    /**
     * Abre a activity que permite escolher o rio (webview requer internet)
     *
     * @param view
     */
    public void abrirWebView(View view) {
        startActivityForResult(new Intent(this, SelectRioWebview.class), SELECT_RIO);
    }

    /**
     * Funcao que trata dos Activity on result (Camera,MApa,WebView)
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        //MAPA
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                String result = data.getStringExtra("latlan_current");
                if (!result.contentEquals("0")) {
                    this.form.currLoc.setText("Atual: " + result);
                    this.lat_curr = Float.valueOf(result.split(";")[0]);
                    this.lon_curr = Float.valueOf(result.split(";")[1]);
                }
                result = data.getStringExtra("latlan_picked");
                if (!result.contentEquals("0")) {
                    this.form.selctLoc.setText("Escolhida: " + result);
                    this.lat_sel = Float.valueOf(result.split(";")[0]);
                    this.lon_sel = Float.valueOf(result.split(";")[1]);
                }
            }
            if (resultCode == Activity.RESULT_CANCELED) {
            }
        } else if (requestCode == SELECT_RIO) {
            if (resultCode == Activity.RESULT_OK) {
                this.form.nomeRioEditText.setText(data.getStringExtra("nomeRio") + " id:" + data.getStringExtra("codigoRio"));
            }
        }

        //se for a resposta da camara
        else if (requestCode == CAM_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                thumbnail.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                final File destination = new File(Environment.getExternalStorageDirectory(),
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
                this.form.arrayListURI.add(destination.getAbsolutePath());
                try {

                    LayoutInflater inflater = getLayoutInflater();
                    View viewInflated = inflater.inflate(R.layout.photo_view, null);
                    final LinearLayout novo = (LinearLayout) viewInflated.findViewById(R.id.novo);
                    ImageView cancel = (ImageView) viewInflated.findViewById(R.id.cancel);
                    ImageView i = (ImageView) viewInflated.findViewById(R.id.photoImageView);
                    //poe a imagem tirada
                    Bitmap result = Utils_Image.squareimage(thumbnail);
                    i.setImageBitmap(result);
                    //ao carregar em eliminar, tira do ecra e apaga da lista
                    cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            form.arrayListURI.remove(destination.getAbsolutePath());
                            novo.removeAllViews();
                        }
                    });
                    linearLayout.addView(novo);
                } catch (IOException e) {
                    e.printStackTrace();
                    this.form.arrayListURI.remove(destination.getAbsolutePath());
                }
            }
        } else if (requestCode == SELECT_PHOTO) {
            if (resultCode == RESULT_OK) {
                Uri selectedImage = data.getData();
                String path = Utils_Image.getRealPathFromURI(selectedImage, this.getApplicationContext());
                File f = new File(Utils_Image.getRealPathFromURI(selectedImage, this.getApplicationContext()));

                if (f.exists()) {
                    try {
                        this.form.arrayListURI.add(path);
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
                        final LinearLayout novo = (LinearLayout) viewInflated.findViewById(R.id.novo);
                        ImageView cancel = (ImageView) viewInflated.findViewById(R.id.cancel);
                        ImageView i = (ImageView) viewInflated.findViewById(R.id.photoImageView);
                        //poe a imagem tirada
                        Bitmap result = Utils_Image.squareimage(thumbnail);
                        i.setImageBitmap(result);
                        //ao carregar em eliminar, tira do ecra e apaga da lista
                        cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                novo.removeAllViews();
                            }
                        });
                        linearLayout.addView(novo);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        Form_IRR form;
        ProgressBar progessbar;
        private RadioButton currLoc;
        private RadioButton selctLoc;
        FormIRR_Swipe app;

        public SectionsPagerAdapter(FragmentManager fm, Form_IRR form, RadioButton currLoc, RadioButton selctLoc, FormIRR_Swipe app) {
            super(fm);
            this.form = form;
            progessbar = (ProgressBar) findViewById(R.id.progressBar2);
            this.currLoc = currLoc;
            this.selctLoc = selctLoc;
            this.app = app;
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).

            return PlaceholderFragment.newInstance(position, form, progessbar, this, currLoc, selctLoc, app);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 34;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            ArrayList<Object> options = null;
            switch (position) {
                case 0:
                    return "SECTION 1";
                case 1:
                    return "SECTION 2";
                case 2:
                    return "SECTION 3";
            }
            return null;
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";
        Form_IRR form;
        ProgressBar progessbar;
        SectionsPagerAdapter sectionsPagerAdapter;
        private RadioButton currLoc;
        private RadioButton selctLoc;
        FormIRR_Swipe app;
        LayoutInflater inflaterSaved;

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber, Form_IRR form, ProgressBar progessbar, SectionsPagerAdapter sectionsPagerAdapter, RadioButton e1, RadioButton e2, FormIRR_Swipe app) {

            PlaceholderFragment fragment = new PlaceholderFragment(form, progessbar, e1, e2, app);
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);

            return fragment;
        }

        public PlaceholderFragment(Form_IRR form, ProgressBar progessbar, RadioButton e1, RadioButton e2, FormIRR_Swipe app) {
            this.form = form;
            this.progessbar = progessbar;
            this.currLoc = e1;
            this.selctLoc = e2;
            this.app = app;
        }

        @Override
        public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            this.inflaterSaved = inflater;

            int number = getArguments().getInt(ARG_SECTION_NUMBER);
            progessbar.setProgress(number);
            View rootView;
            if (number == 0) {
                rootView = inflater.inflate(R.layout.fragment_form_irrsyipe_inicial, container, false);

                this.app.form.nomeRioEditText = (EditText) rootView.findViewById(R.id.nomeRioEditText);
                this.app.form.nomeRioEditText.setText(this.app.form.nomeRio);


                this.app.form.currLoc = (RadioButton) rootView.findViewById(R.id.currLocRadioButton);
                if (this.form.lat_curr != null)
                    this.app.form.currLoc.setText("Atual: " + this.form.lat_curr + ";" + this.form.lon_curr);
                else
                    this.app.form.currLoc.setText("Atual: " + 0 + ";" + 0);

                this.app.form.selctLoc = (RadioButton) rootView.findViewById(R.id.selctLocRadioButton);
                if (this.form.lat_sel != null)
                    this.app.form.selctLoc.setText("Escolhida: " + this.form.lat_sel + ";" + this.form.lon_sel);
                else
                    this.app.form.selctLoc.setText("Escolhida: " + 0 + ";" + 0);

                if (this.app.form.current_location != null) {
                    if (this.app.form.current_location)
                        this.app.form.currLoc.setChecked(true);
                    else
                        this.app.form.selctLoc.setChecked(true);
                }


                this.app.form.margEsquerda = (RadioButton) rootView.findViewById(R.id.margemEsquerda);
                this.app.form.margDireita = (RadioButton) rootView.findViewById(R.id.margemDireita);

                if (this.app.form.margem == 1)
                    this.app.form.margEsquerda.setChecked(true);
                else
                    this.app.form.margDireita.setChecked(true);


                //Adiciona interface para as fotos
                View viewInflated = inflater.inflate(R.layout.photo_selection, null);

                Button buttonTakePic = (Button) viewInflated.findViewById(R.id.camera);
                buttonTakePic.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        getActivity().startActivityForResult(cameraIntent, CAM_REQUEST);
                    }
                });

                Button buttonSelectPic = (Button) viewInflated.findViewById(R.id.galery);
                buttonSelectPic.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                        photoPickerIntent.setType("image/*");
                        getActivity().startActivityForResult(photoPickerIntent, SELECT_PHOTO);
                    }
                });

                this.app.linearLayout = (LinearLayout) rootView.findViewById(R.id.linearLayoutInitial);
                this.app.linearLayout.addView(viewInflated);
                this.app.horizontal = (LinearLayout) viewInflated.findViewById(R.id.horizontalLinearLayout);


                if (this.app.form.arrayListURI == null)
                    this.app.form.arrayListURI = new ArrayList<String>();
                //ver se tem fotos ja guardadas
                Thread timer = new Thread() {
                    @Override
                    public void run() {
                        //do something
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                for (final String uri : app.form.arrayListURI) {
                                    try {
                                        BitmapFactory.Options options = new BitmapFactory.Options();
                                        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                                        Bitmap bitmapOtiginal = BitmapFactory.decodeFile(uri, options);
                                        //cria um htumbnail com max with de 100 pixeis
                                        int i1 = bitmapOtiginal.getHeight() * 200 / bitmapOtiginal.getWidth();
                                        Bitmap thumbnail = Bitmap.createScaledBitmap(
                                                bitmapOtiginal, 200, i1, false);
                                        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                                        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
                                        View viewInflatedPhoto = inflater.inflate(R.layout.photo_view, null);
                                        final LinearLayout novo = (LinearLayout) viewInflatedPhoto.findViewById(R.id.novo);
                                        ImageView cancel = (ImageView) viewInflatedPhoto.findViewById(R.id.cancel);
                                        ImageView i = (ImageView) viewInflatedPhoto.findViewById(R.id.photoImageView);
                                        //poe a imagem tirada
                                        Bitmap result = Utils_Image.squareimage(thumbnail);
                                        i.setImageBitmap(result);
                                        //ao carregar em eliminar, tira do ecra e apaga da lista
                                        cancel.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                app.form.arrayListURI.remove(uri);
                                                novo.removeAllViews();
                                            }
                                        });
                                        app.linearLayout.addView(novo);

                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        });
                    }
                };
                timer.start();

            } else {
                rootView = inflater.inflate(R.layout.fragment_form_irrswipe, container, false);
                LinearLayout linearLayout = (LinearLayout) rootView.findViewById(R.id.irr_linear);
                try {
                    this.form.getPerguntas().get(number - 1).generate(linearLayout, this.getContext());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                this.form.getPerguntas().get(number - 1).setAnswer();
            }

            return rootView;
        }


        public void abrirMapa(View view) {
            startActivityForResult(new Intent(null, Mapa_rios.class), 1);
        }

        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {

            if (requestCode == 1) {
                if (resultCode == Activity.RESULT_OK) {
                    String result = data.getStringExtra("latlan_current");
                }
                if (resultCode == Activity.RESULT_CANCELED) {
                }
            }

        }//onActivityResult


        @Override
        public void onPause() {
            super.onPause();
            this.form.fillAnswers();
        }
    }


    //--TOOLBAR
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_form_irr, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.navigate_guardarios)
            startActivity(new Intent(this, GuardaRios.class));
        if (id == R.id.navigate_account)
            startActivity(new Intent(this, Login.class));
        if (id == R.id.navigate_save) {

            try {
                //Form_IRR.loadFromIRR(this.getApplicationContext());
                form.fillAnswers();
                if (!form.respostas.get(-2).toString().contentEquals("")) {
                    Form_IRR.deleteFormIRRFile(this.getApplicationContext(), form.respostas.get(-2).toString());
                }
                Form_IRR.saveFormIRR(form, this.getApplicationContext());
                Toast toast = Toast.makeText(FormIRR_Swipe.this, "Formulário IRR guardado", Toast.LENGTH_LONG);
                toast.show();
                Intent intent = new Intent(getApplicationContext(), Form_IRR_mainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return super.onOptionsItemSelected(item);
    }

    //--TOOLBAR
}
