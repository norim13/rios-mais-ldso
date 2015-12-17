package ldso.rios;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class RotaPoint_show extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rota_point_show);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle((String) this.getIntent().getSerializableExtra("nome_rota"));
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        String nome_pont= "Ponto "+(int) this.getIntent().getSerializableExtra("ordem_ponto")+" - "+(String) this.getIntent().getSerializableExtra("nome_ponto");

        TextView tv= (TextView) this.findViewById(R.id.nomeRota);
        tv.setText(nome_pont);


        TextView descricao= (TextView) this.findViewById(R.id.descricaoPonto);
        descricao.setText((String) this.getIntent().getSerializableExtra("descricao_ponto"));

    }

}
