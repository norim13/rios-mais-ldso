package ldso.rios;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.Toast;

import ldso.rios.DataBases.DB_functions;

public class SelectRioWebview extends AppCompatActivity {

    String nome,codigo;

    WebView webview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_rio_webview);

        webview= (WebView) findViewById(R.id.webView);
        webview.getSettings().setDomStorageEnabled(true);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.addJavascriptInterface(new WebAppInterface(this), "Android");
        webview.loadUrl(DB_functions.base_url+"/rios_mapa_webview");


    }

    public void exit(View view){
        Intent returnIntent = new Intent();
        if (this.nome!=null && this.codigo!=null) {
            returnIntent.putExtra("nomeRio", this.nome);
            returnIntent.putExtra("codigoRio", this.codigo);
            setResult(Activity.RESULT_OK, returnIntent);
            finish();
        }
        else
            Toast.makeText(this.getApplicationContext(),"Selecione um rio primeiro." , Toast.LENGTH_SHORT).show();






    }

    public class WebAppInterface {
        SelectRioWebview mContext;


        /** Instantiate the interface and set the context */
        WebAppInterface(SelectRioWebview c) {
            mContext=c;

        }

        @JavascriptInterface
        public void setRio(String codRio,String nomeRio) {
            this.mContext.codigo=codRio;
            this.mContext.nome=nomeRio;
            Log.e("setRio",codRio);
            Toast.makeText(mContext, codRio+"-"+nomeRio, Toast.LENGTH_SHORT).show();
        }

    }

}
