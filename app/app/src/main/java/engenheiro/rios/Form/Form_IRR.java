package engenheiro.rios.Form;

import java.util.ArrayList;
import java.util.HashMap;

import engenheiro.rios.Form.Perguntas.checkPergunta;
import engenheiro.rios.Form.Perguntas.editPergunta;
import engenheiro.rios.Form.Perguntas.radioPergunta;
import engenheiro.rios.Form.Perguntas.seekPergunta;
import engenheiro.rios.IRR.Questions;

/**
 * Created by filipe on 25/11/2015.
 */
public class Form_IRR extends Form {


    public void generate(){
        this.perguntas= new ArrayList<Pergunta>();
        this.respostas= new HashMap<Integer,Object>();
        //{main_title , sub_title , type , required , options , max,value_irr}
        for(int i=1;i<=33;i++)
        {
            ArrayList<Object> options = Questions.getOptions(i);
            Pergunta nova = null;
            switch ((int)options.get(2))
            {
                case 0:
                    nova= new radioPergunta((String[]) options.get(4),(String) options.get(0),(String)options.get(1),(Boolean) options.get(3),false);
                    break;
                case 1:
                    nova= new checkPergunta((String[]) options.get(4),(String) options.get(0),(String)options.get(1),(Boolean) options.get(3),false);
                    break;
                case 2:
                    nova= new editPergunta((String[]) options.get(4),(String) options.get(0),(String)options.get(1),(Boolean) options.get(3),false);
                    break;
                case 3:
                    nova= new seekPergunta((String[]) options.get(4),(String) options.get(0),(String)options.get(1),(Boolean) options.get(3),false,1,5);
            }
            this.perguntas.add(nova);

        }


    }




}
