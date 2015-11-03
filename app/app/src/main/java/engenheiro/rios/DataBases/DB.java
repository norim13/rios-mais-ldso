package engenheiro.rios.DataBases;

import android.content.ContentValues;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

/**
 * Created by filipe on 28/10/2015.
 */
public class DB extends _Default implements Runnable{

    private Connection conn;
    private String host ="***REMOVED***";
    private String db = "***REMOVED***";
    private int port= 5432;
    private String user= "***REMOVED***";
    private String password= "***REMOVED***";
    //private String url= "jdbc:postgresql://***REMOVED***:***REMOVED***@***REMOVED***:5432/***REMOVED***";
   private String url= "jdbc:postgresql://%s:%d/%s";



    public DB(){
        super();
        this.url=String.format(this.url,this.host,this.port,this.db);
        this.url=this.url+"?ssl=true&sslfactory=org.postgresql.ssl.NonValidatingFactory";
        //Log.w("teste",url);

        //Abrir Connection
        this.conection();
        //Fechar Connection
        this.disconect();
    }

    @Override
    public void run() {
        //Connection
        try {
            Class.forName("org.postgresql.Driver");

           // String url2 = "jdbc:postgresql://"+this.host+"?user="+this.user+"&password="+this.password+"&ssl=true";
            //this.conn= DriverManager.getConnection(url2);

            this.conn = DriverManager.getConnection(this.url, this.user, this.password);

        }catch (Exception e){
            e.printStackTrace();
            Log.w("teste",e.getMessage());
            this._message=e.getMessage();
            this._status=false;
        }
    }

    private void conection(){
        Thread thread = new Thread(this);
        thread.start();

        try{
            thread.join();
        }catch (Exception e){
            Log.w("teste",e.getMessage());
            this._message=e.getMessage();
            this._status=false;
        }
    }

    private void disconect(){
        if (this.conn!=null){
            try{
                this.conn.close();
            }catch (Exception e){

            }finally{
                this.conn = null;
            }
        }
    }
    public ResultSet select(String query){
        this.conection();
        ResultSet resultSet=null;
        try {
            resultSet=new ExecuteDB(this.conn,query).execute().get();
        } catch (Exception e) {
            Log.w("teste",e.getMessage());
            this._message=e.getMessage();
            this._status=false;
        }
        return resultSet;
    }

    public ResultSet execute(String query){
        this.conection();
        ResultSet resultSet=null;
        try {


            resultSet=new ExecuteDB(this.conn,query).execute().get();
           // Log.w("teste","result string:"+resultSet.toString());
        } catch (Exception e) {
            e.printStackTrace();
            Log.w("teste","erro no execute"+e.getMessage());
            this._message=e.getMessage();
            this._status=false;
        }


        return resultSet;
    }


    public void insert(String tableName, Object o, ContentValues values) {

    }
}
