package ldso.rios.Form.IRR;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Toast;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import ldso.rios.Autenticacao.Login;
import ldso.rios.DataBases.DB_functions;
import ldso.rios.Form.Form_functions;
import ldso.rios.MainActivities.Form_IRR_mainActivity;
import ldso.rios.MainActivities.GuardaRios;
import ldso.rios.Mapa_rios;
import ldso.rios.R;

/*
Class para mostrar um formulario irr (novo ou editar)
 */
public class FormIRRSwipe extends AppCompatActivity {


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
    Float lat_curr,lon_curr;
    Float lat_sel,lon_sel;
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
            for (int k = 0; k < 32; k++)
                try {
                    Log.e("string-", k + " -" + outros.get(k));
                } catch (Exception e) {

                }
        }

        Log.e("teste", this.form.toString());


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
                Snackbar.make(view, "Validating your action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                form.fillAnswers();

                Log.e("form", "entrar na DB");
                try {
                    if (novo)
                        DB_functions.saveForm(Form_functions.getUser(getApplicationContext())[0],
                                Form_functions.getUser(getApplicationContext())[1], form);

                    else
                        DB_functions.update(Form_functions.getUser(getApplicationContext())[0],
                                Form_functions.getUser(getApplicationContext())[1], id, form);

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

    }

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
                Form_IRR.saveFormIRR(form, this.getApplicationContext());
                Toast toast = Toast.makeText(FormIRRSwipe.this, "Formulário de limpeza guardado", Toast.LENGTH_LONG);
                toast.show();
                Intent intent = new Intent(getApplicationContext(), Form_IRR_mainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

            } catch (IOException e) {
                e.printStackTrace();
            }
            //Log.e("teste","tamanho do array"+Form_IRR.all_from_irrs.size());
        }

        return super.onOptionsItemSelected(item);
    }

    public void abrirMapa(View view) {
        startActivityForResult(new Intent(this, Mapa_rios.class), 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                String result = data.getStringExtra("latlan_current");
                if (!result.contentEquals("0")) {
                    this.form.currLoc.setText("Atual: " + result);
                    this.lat_curr= Float.valueOf(result.split(";")[0]);
                    this.lon_curr= Float.valueOf(result.split(";")[1]);

                }

                result = data.getStringExtra("latlan_picked");
                if (!result.contentEquals("0")) {
                    this.form.selctLoc.setText("Escolhida: " + result);
                    this.lat_sel= Float.valueOf(result.split(";")[0]);
                    this.lon_sel= Float.valueOf(result.split(";")[1]);
                }
                // Log.e("resultado",result);
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
                Log.e("resultado", "nao recebeu nada");

            }
        }
    }//onActivityResult


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        Form_IRR form;
        ProgressBar progessbar;
        private RadioButton currLoc;
        private RadioButton selctLoc;
        FormIRRSwipe app;

        public SectionsPagerAdapter(FragmentManager fm, Form_IRR form, RadioButton currLoc, RadioButton selctLoc, FormIRRSwipe app) {
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
        FormIRRSwipe app;

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber, Form_IRR form, ProgressBar progessbar, SectionsPagerAdapter sectionsPagerAdapter, RadioButton e1, RadioButton e2, FormIRRSwipe app) {

            PlaceholderFragment fragment = new PlaceholderFragment(form, progessbar, e1, e2, app);
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);

            return fragment;
        }

        public PlaceholderFragment(Form_IRR form, ProgressBar progessbar, RadioButton e1, RadioButton e2, FormIRRSwipe app) {
            this.form = form;
            this.progessbar = progessbar;
            this.currLoc = e1;
            this.selctLoc = e2;
            this.app = app;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            int number = getArguments().getInt(ARG_SECTION_NUMBER);
            progessbar.setProgress(number);
            View rootView;
            if (number == 0) {
                rootView = inflater.inflate(R.layout.fragment_form_irrsyipe_inicial, container, false);
                this.app.form.currLoc = (RadioButton) rootView.findViewById(R.id.currLocRadioButton);
                if (this.form.lat_curr!=null)
                this.app.form.currLoc.setText("Atual: "+this.form.lat_curr+";"+this.form.lon_curr);
                else
                    this.app.form.currLoc.setText("Atual: "+0+";"+0);

                this.app.form.selctLoc = (RadioButton) rootView.findViewById(R.id.selctLocRadioButton);
                if (this.form.lat_sel!=null)
                this.app.form.selctLoc.setText("Escolhida: "+this.form.lat_sel+";"+this.form.lon_sel);
                else
                    this.app.form.selctLoc.setText("Escolhida: "+0+";"+0);

                if (this.app.form.current_location!=null){
                    Log.e("bool","nao é null");
                    if (this.app.form.current_location)
                        this.app.form.currLoc.setChecked(true);
                    else
                        this.app.form.selctLoc.setChecked(true);
                }



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
                    Log.e("resultado", result);
                }
                if (resultCode == Activity.RESULT_CANCELED) {
                    //Write your code if there's no result
                    Log.e("resultado", "nao recebeu nada");

                }
            }
        }//onActivityResult


        @Override
        public void onPause() {
            super.onPause();
            this.form.fillAnswers();
        }
    }
}
