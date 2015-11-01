package engenheiro.rios.DataBases;

import android.util.Log;

import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * Created by filipe on 26/10/2015.
 */
public class User extends _Default {

    private int id;
    private String name;
    private String email;
    private String password;

    public User(){
        super();
        this.id=-1;
        this.name="nome";
        this.email="email";
        this.password="pass";

    }

    public ArrayList<User> getList(){
        DB db= new DB();
        ArrayList<User> lista= new ArrayList<>();
        try{
            ResultSet resultSet = db.select("SELECT * FROM utilizadors");
           // Toast.makeText(r,resultSet.getArray(1).getArray().toString(),Toast.LENGTH_LONG).show();
            if(resultSet != null){
                while (resultSet.next()){
                    User obj = new User();
                    obj.setId(resultSet.getInt("id"));
                    obj.setName(resultSet.getString("nome"));
                    Log.w("teste",resultSet.getString("nome"));
                    obj.setEmail(resultSet.getString("email"));
                    obj.setPassword(resultSet.getString("password"));
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
            command=String.format("INSERT INTO utilizadors (nome,email,password,acesso) VALUES ('%s','%s','%s','%d');",
                   this.getName(),this.getEmail(),this.getPassword(),1);
            Log.w("teste","insert:"+command);
        }
        else {
            command=String.format("UPDTAE utulizadors SET nome = '%s', email= '%s', telefone ='%s' WHERE id = %d;",
                    this.getName(),this.getEmail(),this.getId());
        }
        DB db=new DB();
        db.execute(command);
        Log.w("teste", db._message);
        this._message=db._message;
        this._status=db._status;
    }

    public void erase(){
        String command=String.format("DELETE FROM utulizadors WHERE id = %d;",
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


}
