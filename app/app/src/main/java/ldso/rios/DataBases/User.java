package ldso.rios.DataBases;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;

import ldso.rios.MainActivities.Homepage;

/**
 * Created by filipe on 26/10/2015.
 */
public class User extends _Default {

    private int id;
    private String name;
    private String email;
    private String authentication_token;
    private String password;

    public User(){
        super();
        this.id=-1;
        this.name="";
        this.email="";
        this.password="";
        this.authentication_token="";

    }

    public ArrayList<User> getList(){
        DB db= new DB();
        ArrayList<User> lista= new ArrayList<>();
        try{
            ResultSet resultSet = db.select("SELECT * FROM users");
           // Toast.makeText(r,resultSet.getArray(1).getArray().toString(),Toast.LENGTH_LONG).show();
            if(resultSet != null){
                while (resultSet.next()){
                    User obj = new User();
                    obj.setId(resultSet.getInt("id"));
                    obj.setName(resultSet.getString("nome"));
                    Log.w("teste", "nome : " + resultSet.getString("nome"));
                    obj.setEmail(resultSet.getString("email"));
                    obj.setPassword(resultSet.getString("password_digest"));
                    lista.add(obj);
                    obj=null;
                }
            }
            else
                Log.w("teste","nao ha nada");

        }catch (Exception e){
            Log.w("teste","erro"+e.getMessage());
            this._message=e.getMessage();
            this._status=false;
        }
        return lista;
    }

    public void save(){
        String command="";
        if (this.getId()==-1){
            /*
            command=String.format("INSERT INTO users (nome,email,password,acesso) VALUES ('%s','%s','%s',%d);",
                   this.getName(),this.getEmail(),this.getPassword(),1);
            Log.w("teste","insert:"+command);
            */

            Calendar c = Calendar.getInstance();
            String curr_time = ""+c.get(Calendar.YEAR)+"-"+c.get(Calendar.MONTH)+"-"+c.get(Calendar.DAY_OF_MONTH)+" "+
                    c.get(Calendar.HOUR_OF_DAY)+":"+c.get(Calendar.MINUTE)+":"+c.get(Calendar.SECOND)+".0";
            command=String.format("INSERT INTO users (nome,email,password_digest,access,created_at,updated_at) VALUES ('%s','%s','%s','%d','%s','%s');",
                    this.getName(),this.getEmail(),this.getPassword(),1,curr_time,curr_time);
            Log.w("teste","insert:"+command);
            Log.w("teste","calendario:"+curr_time);
        }
        else {
            command=String.format("UPDATE users SET nome = '%s', email= '%s', telefone ='%s' WHERE id = %d;",
                    this.getName(),this.getEmail(),this.getId());
        }
        DB db=new DB();
        ResultSet rs=db.execute(command);
        this._message=db._message;
        this._status=db._status;
        Log.w("teste", "mensagem: "+this._status);

    }

    public void erase(){
        String command=String.format("DELETE FROM users WHERE id = %d;",
                   this.getId());
        DB db=new DB();
        db.execute(command);
        this._message=db._message;
        this._status=db._status;

    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public static String getToken(Context c){
        SharedPreferences settings = c.getSharedPreferences(Homepage.PREFS_NAME, 0);
        String token=settings.getString("token", "-1");
        return token;
    }
}
