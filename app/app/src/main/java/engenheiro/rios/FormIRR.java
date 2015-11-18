package engenheiro.rios;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;

import engenheiro.rios.IRR.IRR_question;
import engenheiro.rios.IRR.Questions;

public class FormIRR extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_irr);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Formulários IRR");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    public void new_form(View view){
        HashMap<Integer,Object> answers2=new HashMap<Integer,Object>();
        Intent i =new Intent(this, IRR_question.class);
        ArrayList<Integer[]> arrayList=new ArrayList<Integer[]>();
        Integer[] integers = new Integer[0];
        arrayList.add(integers);
        Questions.getQuestion(1,i,this,arrayList);
        i.putExtra("answers2", answers2);
        this.startActivity(i);
    }

}
