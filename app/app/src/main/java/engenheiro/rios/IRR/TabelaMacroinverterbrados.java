package engenheiro.rios.IRR;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import engenheiro.rios.GuardaRios;
import engenheiro.rios.Login;
import engenheiro.rios.R;

public class TabelaMacroinverterbrados extends AppCompatActivity {

    protected LinearLayout linearLayout;
    protected ArrayList<CheckBox> question;
    protected HashMap<Integer, Object> answers2;
    protected int question_num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabela_macroinverterbrados);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Novo Formlário");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

       answers2 = (HashMap<Integer, Object>) getIntent().getSerializableExtra("answers2");
        question_num= (Integer) getIntent().getSerializableExtra("question_num");



        linearLayout = (LinearLayout) this.findViewById(R.id.irr_linear);

    Resources r = getResources();
    float px_float = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, r.getDisplayMetrics());
    int px= (int) px_float;
    px_float = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15, r.getDisplayMetrics());
    int px2= (int) px_float;



    LinearLayout.LayoutParams radioParams;
    radioParams = new LinearLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
    radioParams.setMargins(0, px2, 0, px);

    LinearLayout ll=new LinearLayout(this);
    ll.setLayoutParams(radioParams);
    TextView tv0=new TextView(this);
    tv0.setText("Planárias");

    tv0.setLayoutParams(radioParams);
    ll.addView(tv0);
    linearLayout.addView(ll);
    String[] options= { "Planárias"};
    question=Form_functions.createCheckboxes(options,linearLayout,this);

        ll=new LinearLayout(this);
        ll.setLayoutParams(radioParams);
        TextView tv1=new TextView(this);
        tv1.setText("Hirudíneos");
        tv1.setLayoutParams(radioParams);
        ll.addView(tv1);
        linearLayout.addView(ll);
        options= new String[]{"Hirudíneos (Sanguessugas)"};
        ArrayList<CheckBox> cb=Form_functions.createCheckboxes(options, linearLayout, this);
        for(int i=0;i<cb.size();i++)
            question.add(cb.get(i));

        ll=new LinearLayout(this);
        ll.setLayoutParams(radioParams);
        TextView tv2=new TextView(this);
        tv2.setText("Dípteros");
        tv2.setLayoutParams(radioParams);
        ll.addView(tv2);
        linearLayout.addView(ll);
        options= new String[]{"Oligoquetas (minhocas)",
                "Simulideos",
                "Quironomideos, Sirfídeos, Culidídeos, Tipulídeos (Larva de mosquitos)"};
        cb=Form_functions.createCheckboxes(options, linearLayout, this);
        for(int i=0;i<cb.size();i++)
            question.add(cb.get(i));

        ll=new LinearLayout(this);
        ll.setLayoutParams(radioParams);
        TextView tv3=new TextView(this);
        tv3.setText("Gastrópodes (Mullusca)");
        tv3.setLayoutParams(radioParams);
        ll.addView(tv3);
        linearLayout.addView(ll);
        options= new String[]{"Ancilídeo",
                "Limnídeo; Physa",
                "Bivalves"};
        cb=Form_functions.createCheckboxes(options, linearLayout, this);
        for(int i=0;i<cb.size();i++)
            question.add(cb.get(i));

        ll=new LinearLayout(this);
        ll.setLayoutParams(radioParams);
        TextView tv4=new TextView(this);
        tv4.setText("Coleóptero (escaravelho)");
        tv4.setLayoutParams(radioParams);
        ll.addView(tv4);
        linearLayout.addView(ll);
        options= new String[]{"Patas Nadadoras (Dystiscidae)",
                "Pata Locomotoras (Hydraena)" };
        cb=Form_functions.createCheckboxes(options, linearLayout, this);
        for(int i=0;i<cb.size();i++)
            question.add(cb.get(i));

        ll=new LinearLayout(this);
        ll.setLayoutParams(radioParams);
        TextView tv5=new TextView(this);
        tv5.setText("Trichóptero (mosca d’água)");
        tv5.setLayoutParams(radioParams);
        ll.addView(tv5);
        linearLayout.addView(ll);
        options= new String[]{"Trichóptero (mosca d’água) S/Casulo",
                "Trichóptero (mosca d’água) C/Casulo"};
        cb=Form_functions.createCheckboxes(options, linearLayout, this);
        for(int i=0;i<cb.size();i++)
            question.add(cb.get(i));


        ll=new LinearLayout(this);
        ll.setLayoutParams(radioParams);
        TextView tv6=new TextView(this);
        tv6.setText("Odonata");
        tv6.setLayoutParams(radioParams);
        ll.addView(tv6);
        linearLayout.addView(ll);
        options= new String[]{"Odonata (Larva de Libelinhas)"};
        cb=Form_functions.createCheckboxes(options, linearLayout, this);
        for(int i=0;i<cb.size();i++)
            question.add(cb.get(i));

        ll=new LinearLayout(this);
        ll.setLayoutParams(radioParams);
        TextView tv7=new TextView(this);
        tv7.setText("Heterópteros");
        tv7.setLayoutParams(radioParams);
        ll.addView(tv7);
        linearLayout.addView(ll);
        options= new String[]{"Heterópteros"};
        cb=Form_functions.createCheckboxes(options, linearLayout, this);
        for(int i=0;i<cb.size();i++)
            question.add(cb.get(i));

        ll=new LinearLayout(this);
        ll.setLayoutParams(radioParams);
        TextView tv8=new TextView(this);
        tv8.setText("Plecópteros");
        tv8.setLayoutParams(radioParams);
        ll.addView(tv8);
        linearLayout.addView(ll);
        options= new String[]{"Plecópteros (mosca-de-pedra)"};
        cb=Form_functions.createCheckboxes(options, linearLayout, this);
        for(int i=0;i<cb.size();i++)
            question.add(cb.get(i));

        ll=new LinearLayout(this);
        ll.setLayoutParams(radioParams);
        TextView tv9=new TextView(this);
        tv9.setText("Efemerópteros (efémera)");
        tv9.setLayoutParams(radioParams);
        ll.addView(tv9);
        linearLayout.addView(ll);
        options= new String[]{"Baetídeo","Cabeça Planar (Ecdyonurus)"};
        cb=Form_functions.createCheckboxes(options, linearLayout, this);
        for(int i=0;i<cb.size();i++)
            question.add(cb.get(i));

        ll=new LinearLayout(this);
        ll.setLayoutParams(radioParams);
        TextView tv10=new TextView(this);
        tv10.setText("Crustáceos");
        tv10.setLayoutParams(radioParams);
        ll.addView(tv10);
        linearLayout.addView(ll);
        options= new String[]{"Crustáceos"};
        cb=Form_functions.createCheckboxes(options, linearLayout, this);
        for(int i=0;i<cb.size();i++)
            question.add(cb.get(i));

        ll=new LinearLayout(this);
        ll.setLayoutParams(radioParams);
        TextView tv11=new TextView(this);
        tv11.setText("Ácaros");
        tv11.setLayoutParams(radioParams);
        ll.addView(tv11);
        linearLayout.addView(ll);
        options= new String[]{"Ácaros"};
        cb=Form_functions.createCheckboxes(options, linearLayout, this);
        for(int i=0;i<cb.size();i++)
            question.add(cb.get(i));

        ll=new LinearLayout(this);
        ll.setLayoutParams(radioParams);
        TextView tv12=new TextView(this);
        tv12.setText("Pulga-de-água");
        tv12.setLayoutParams(radioParams);
        ll.addView(tv12);
        linearLayout.addView(ll);
        options= new String[]{"Pulga-de-água (Daphnia)"};
        cb=Form_functions.createCheckboxes(options, linearLayout, this);
        for(int i=0;i<cb.size();i++)
            question.add(cb.get(i));

        ll=new LinearLayout(this);
        ll.setLayoutParams(radioParams);
        TextView tv13=new TextView(this);
        tv13.setText("Insetos");
        tv13.setLayoutParams(radioParams);
        ll.addView(tv13);
        linearLayout.addView(ll);
        options= new String[]{"Insetos – adultos (adultos na forma aérea)"};
        cb=Form_functions.createCheckboxes(options, linearLayout, this);
        for(int i=0;i<cb.size();i++)
            question.add(cb.get(i));

        ll=new LinearLayout(this);
        ll.setLayoutParams(radioParams);
        TextView tv14=new TextView(this);
        tv14.setText("Mégalopteres");
        tv14.setLayoutParams(radioParams);
        ll.addView(tv14);
        linearLayout.addView(ll);
        options= new String[]{"Mégalopteres"};
        cb=Form_functions.createCheckboxes(options, linearLayout, this);
        for(int i=0;i<cb.size();i++)
            question.add(cb.get(i));


    }


    public void goto_next(View view) {

        ArrayList<Integer> array_int= Form_functions.getCheckboxes(question);
        if(answers2.get(question_num)!=null){
            answers2.put(question_num,array_int);
        }
        else {
            answers2.remove(question_num);
            answers2.put(question_num,array_int);
        }



        Intent i =new Intent(this, IRR_question.class);
        Questions.getQuestion(14, i, this);
        i.putExtra("answers2", answers2);
        startActivity(i);
        this.overridePendingTransition(0, 0);

    }

    public void goto_previous(View view) {


        this.finish();
        this.overridePendingTransition(0, 0);


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
        if (id == R.id.navigate_guardarios)
            startActivity(new Intent(this, GuardaRios.class));
        if (id == R.id.navigate_account)
            startActivity(new Intent(this, Login.class));
        return super.onOptionsItemSelected(item);
    }

}


/*
1. Planárias",
                        "Hidudíneros (Sanguessugas)",
                        "Simulideos",
                        "Quironomideos, Sirfídeos, Culidídeos, Tipulídeos (Larva de mosquitos)",
                        "Ancilídeo",
                        "4.Limnídeo; Physa",
                        "5. Bivalves",
                        "6.1 Patas Nadadoras (Dystiscidae)",
                        "6.2 Pata Locomotoras (Hydraena)",
                        "7.1 Trichóptero (mosca d’água) S/Casulo",
                        "7.2 Trichóptero (mosca d’água) C/Casulo",
                        "8. Odonata (Larva de Libelinhas)",
                        "9. Heterópteros",
                        "10. Plecópteros (mosca-de-pedra)",
                        "11.1 Baetídeo",
                        "11.2 Cabeça Planar (Ecdyonurus)",
                        "12. Crustáceos",
                        "13. Ácaros",
                        "14. Pulga-de-água (Daphnia)",
                        "15. Insetos – adultos (adultos na forma aérea)",
                        "16. Mégalopteres"};
 */