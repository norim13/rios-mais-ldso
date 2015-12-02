package ldso.rios.DataBases;

import android.os.AsyncTask;
import android.util.Log;

import java.sql.Connection;
import java.sql.ResultSet;

/**
 * Created by filipe on 28/10/2015.
 */
public class ExecuteDB extends AsyncTask<String,Void,ResultSet> {

    private Connection connection;
    private String query;

    public ExecuteDB(Connection connection, String query) {
        this.connection = connection;
        this.query = query;
    }

    @Override
    protected ResultSet doInBackground(String... params) {
        ResultSet resultSet=null;
        try{
            resultSet=connection.prepareStatement(query).executeQuery(); //retorna o comando

            Log.w("teste", "entrou");
        }catch (Exception e){
            e.printStackTrace();

        }finally {
            try {
                connection.close();
            }catch (Exception e){

            }
        }
        return resultSet;
    }
}
