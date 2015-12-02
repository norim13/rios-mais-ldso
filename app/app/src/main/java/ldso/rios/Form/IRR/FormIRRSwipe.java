package ldso.rios.Form.IRR;

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

import org.json.JSONException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import ldso.rios.Autenticacao.Login;
import ldso.rios.DataBases.DB_functions;
import ldso.rios.Form.Form_functions;
import ldso.rios.MainActivities.GuardaRios;
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
            this.form.setRespostas((HashMap<Integer, Object>) getIntent().getSerializableExtra("form_irr"));
            novo = false;
        }

        Log.e("teste",this.form.toString());

        File file = new File("form.dat");
        if(file.exists())
            Log.e("form","existe");
        else
            Log.e("form","nao existe");

// Do something else.

        Log.e("form",this.form.toString());
        Log.e("form", "teste" + this.form.getTeste());


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Novo Form");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), this.form);

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);


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
                                Form_functions.getUser(getApplicationContext())[1],form);
                    else
                        DB_functions.update(Form_functions.getUser(getApplicationContext())[0],
                                Form_functions.getUser(getApplicationContext())[1], form);
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
        if (id == R.id.navigate_save)
        {

            try {
                //Form_IRR.loadFromIRR(this.getApplicationContext());
                Form_IRR.saveFormIRR(form, this.getApplicationContext());

            } catch (IOException e) {
                e.printStackTrace();
            }
             //Log.e("teste","tamanho do array"+Form_IRR.all_from_irrs.size());
        }

            return super.onOptionsItemSelected(item);
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        Form_IRR form;
        ProgressBar progessbar;

        public SectionsPagerAdapter(FragmentManager fm, Form_IRR form) {
            super(fm);
            this.form = form;
            progessbar = (ProgressBar) findViewById(R.id.progressBar2);
            Log.e("form", "novo SectionsPageAdapter");
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            Log.e("form", "getItem no Sections");

            return PlaceholderFragment.newInstance(position, form, progessbar);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 33;
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

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber, Form_IRR form, ProgressBar progessbar) {

            PlaceholderFragment fragment = new PlaceholderFragment(form, progessbar);
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);

            return fragment;
        }

        public PlaceholderFragment(Form_IRR form, ProgressBar progessbar) {
            this.form = form;
            this.progessbar = progessbar;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_form_irrswipe, container, false);
            LinearLayout linearLayout = (LinearLayout) rootView.findViewById(R.id.irr_linear);


            int number = getArguments().getInt(ARG_SECTION_NUMBER);
            progessbar.setProgress(number);

            try {
                this.form.getPerguntas().get(number).generate(linearLayout, this.getContext());
            } catch (IOException e) {
                e.printStackTrace();
            }
            this.form.getPerguntas().get(number).setAnswer();

            return rootView;
        }

        @Override
        public void onPause() {
            super.onPause();
            this.form.fillAnswers();
            //Log.e("vai sair", "" + getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }
}
