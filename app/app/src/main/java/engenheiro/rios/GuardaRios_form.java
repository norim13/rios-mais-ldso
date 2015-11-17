package engenheiro.rios;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;

import engenheiro.rios.DataBases.DB;
import engenheiro.rios.IRR.Form_functions;

public class GuardaRios_form extends AppCompatActivity {

    LinearLayout linearLayout;
    protected ArrayList<RadioButton> question1;
    protected ArrayList<RadioButton> question2;
    protected ArrayList<RadioButton> question3;
    protected ArrayList<RadioButton> question4;
    protected ArrayList<CheckBox> question5;
    protected EditText question6;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guarda_rios_form);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        linearLayout = (LinearLayout) this.findViewById(R.id.irr_linear);

        Resources r = getResources();
        float px_float = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, r.getDisplayMetrics());
        int px= (int) px_float;
         px_float = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15, r.getDisplayMetrics());
        int px2= (int) px_float;



        LinearLayout.LayoutParams radioParams;
        radioParams = new LinearLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        radioParams.setMargins(0,px2,0,px);

        LinearLayout ll=new LinearLayout(this);
        ll.setLayoutParams(radioParams);
        TextView tv1=new TextView(this);
        tv1.setText("Local onde foi observado?");

        tv1.setLayoutParams(radioParams);
        ll.addView(tv1);
        linearLayout.addView(ll);
        String[] options= { "Árvores/ramo","Pedra/rocha","Ninho"};
        question1=Form_functions.createRadioButtons(options,linearLayout,this);

        TextView tv2=new TextView(this);
        tv2.setText("Estava a voar?");
        tv2.setLayoutParams(radioParams);
        linearLayout.addView(tv2);
        String[] options2= { "Não estava a voar","Pairar","Voo picado para mergulhar","Mergulhar","De passagem"};
        question2=Form_functions.createRadioButtons(options2,linearLayout,this);

        TextView tv3=new TextView(this);
        tv3.setText("Estava a cantar?");
        tv3.setLayoutParams(radioParams);
        linearLayout.addView(tv3);
        String[] options3= { "Não estava a cantar","Presença","Alerta e marcação de teritório"};
        question3=Form_functions.createRadioButtons(options3,linearLayout,this);

        TextView tv4=new TextView(this);
        tv4.setText("Estava a alimentar-se?");
        tv4.setLayoutParams(radioParams);
        linearLayout.addView(tv4);
        String[] options4= { "Não estava a alimentar-se","Peixe","Libelinha","Pequenos insectos"};
        question4=Form_functions.createRadioButtons(options4, linearLayout, this);

        TextView tv5=new TextView(this);
        tv5.setText("Comportamentos:");
        linearLayout.addView(tv5);
        tv5.setLayoutParams(radioParams);

        String[] options5= { "Estava parado?",
                "Estava a beber?",
                "Estava a caçar?",
                "Estava a cuidar das crias?"};
        question5=Form_functions.createCheckboxes(options5, linearLayout, this);

        question6=new EditText(this);
        question6.setHint("Outro comportamento?");
        linearLayout.addView(question6);


        saveGuardaRios();



    }


    public void saveGuardaRios(){
            String command="";
        Calendar c = Calendar.getInstance();
        String curr_time = ""+c.get(Calendar.YEAR)+"-"+c.get(Calendar.MONTH)+"-"+c.get(Calendar.DAY_OF_MONTH)+" "+
                c.get(Calendar.HOUR_OF_DAY)+":"+c.get(Calendar.MINUTE)+":"+c.get(Calendar.SECOND)+".0";
                command=String.format("INSERT INTO guardarios (created_at,updated_at,user_id,rio,descricao,categoria,motivo,coordenadas) VALUES ('%s','%s','%s','%d','%s','%s');",
                        curr_time,curr_time,0,"Douro","desc","categoria","motivo","coordenadas");
                Log.w("teste", "insert:" + command);
                Log.w("teste","calendario:"+curr_time);

            DB db=new DB();
            ResultSet rs=db.execute(command);

            Log.w("teste", "mensagem: "+db.is_status());
            Log.w("teste","status: "+db.get_message());
    }

}
