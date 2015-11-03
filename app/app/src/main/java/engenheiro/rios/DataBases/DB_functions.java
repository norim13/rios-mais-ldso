package engenheiro.rios.DataBases;

import java.sql.ResultSet;
import java.util.Calendar;

/**
 * Created by filipe on 02/11/2015.
 */
public class DB_functions {

    public void saveUser(String name,String email, String new_password){
        String command="";
            command=String.format("INSERT INTO users (nome,email,password_digest,access,created_at,updated_at) VALUES ('%s','%s','%s','%d','%s','%s');",
                    name,email,new_password,1,"2015-11-01 22:25:42.171185","2015-11-01 22:25:42.171185");


        Calendar c = Calendar.getInstance();
        String curr_time = ""+c.get(Calendar.YEAR)+"-"+c.get(Calendar.MONTH)+"-"+c.get(Calendar.DAY_OF_YEAR);



        DB db=new DB();
        ResultSet rs=db.execute(command);
        //Log.w("teste", "mensagem: "+this._status);
    }
}
