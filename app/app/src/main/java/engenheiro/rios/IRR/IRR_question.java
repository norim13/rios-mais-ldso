package engenheiro.rios.IRR;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import engenheiro.rios.GuardaRios;
import engenheiro.rios.Login;
import engenheiro.rios.R;

public class IRR_question extends AppCompatActivity {
    protected LinearLayout linearLayout;
    protected ArrayList<RadioButton> radio_list;
    protected ArrayList<CheckBox> check_list;
    protected ArrayList<EditText> edit_list;
    protected ArrayList<SeekBar> seek_list;
    protected ArrayList<TextView> seek_list_text;
    protected int min,max;                      //values of min and max on seekbar and edit text. null if there is none
    protected ArrayList<String> image_list;     //null if there is no image
    protected ArrayList<ArrayList<Object>> answers;        //array to pass the answers for one activity to another
    protected HashMap<Integer,Object> answers2;
    protected Integer type;                     //type=0 RadioButton, type=1 CheckBox, type=2 EditText, type=3 SeekBar
    protected Boolean required;
    Integer question_num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_irr_question);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Novo Formulario");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        TextView irr_textview_name_main= (TextView) this.findViewById(R.id.irr_textview_name_main);
        String main_title= (String) getIntent().getSerializableExtra("main_title");
        irr_textview_name_main.setText(main_title);

        TextView irr_textview_name= (TextView) this.findViewById(R.id.irr_textview_name);
        String sub_title= (String) getIntent().getSerializableExtra("sub_title");
        irr_textview_name.setText(sub_title);

        linearLayout = (LinearLayout) this.findViewById(R.id.irr_linear);

        answers= (ArrayList<ArrayList<Object>>) getIntent().getSerializableExtra("answers");
        answers2= (HashMap<Integer, Object>) getIntent().getSerializableExtra("answers2");
        Log.e("teste", "size2:" + answers2.size());
        Log.e("teste", "size:" + answers.size());
        type= (Integer) getIntent().getSerializableExtra("type");
        required=(Boolean) getIntent().getSerializableExtra("required");
        String [] options= (String[]) getIntent().getSerializableExtra("options");
        question_num=0;
        question_num= (Integer) getIntent().getSerializableExtra("question_num");
        int t= Integer.parseInt(type.toString());


        switch (type){
            case 0:                     //RadioButton
                radio_list=Form_functions.createRadioButtons(options, linearLayout, this);

                break;

            case 1:                     //CheckBox
                check_list=Form_functions.createCheckboxes(options,linearLayout,this);

                break;

            case 2:                     //EditText

                edit_list=Form_functions.createEditText(options,linearLayout,this);

                break;

            case 3:                     //SeekBar
                max= (int) getIntent().getSerializableExtra("max");
                ArrayList<ArrayList> arrayList=Form_functions.createSeekbar(options,linearLayout,this, (int) max);
                seek_list=arrayList.get(0);
                seek_list_text=arrayList.get(1);
                break;

            default:
                break;
        }




    }

    public void goto_next(View view){
        Intent i=new Intent(this, IRR_question.class);
        ArrayList<Object> al=new ArrayList<Object>();
        al.add(question_num);
        Object obj = null;
        switch (type){
            case 0:
                obj = Form_functions.getRadioButtonOption(radio_list);
                break;
            case 1:
                obj = Form_functions.getCheckboxes(check_list);
                break;
            case 2:
                obj = Form_functions.getEditTexts(edit_list);
                break;

        }
        answers.add(al);

        answers2.put(question_num, obj);

        int next_question=question_num+1;
        Log.e("teste", "num pergunta:" + question_num + " next:" + next_question);

        i.putExtra("answers2",answers2);

        i.putExtra("answers", answers);

        String[] options;
        switch (next_question){
            case 2:
                i.putExtra("main_title", "Hidrogeomorfologia");
                i.putExtra("sub_title", "Perfil de margens");
                i.putExtra("type",1);
                i.putExtra("required", true);
                options= new String[]{"Vertical escavado",
                        "Vertical cortado",
                        "Declive >45%",
                        "Declive <45%",
                        "Suave comport <45%",
                        "Artificial"};
                i.putExtra("options",options);
                i.putExtra("question_num",next_question);
                startActivity(i);
                break;
            case 3:
                Intent pergunta3=new Intent(this, IRR_1_3.class);
                pergunta3.putExtra("main_title","Hidrogeomorfologia");
                pergunta3.putExtra("answers", answers);
                pergunta3.putExtra("answers2", answers2);
                pergunta3.putExtra("sub_title", "Perfil de margens");
                pergunta3.putExtra("required", true);
                pergunta3.putExtra("question_num",next_question);
                startActivity(pergunta3);
                break;
            //4 is in IRR_1_5

            case 5:
                i.putExtra("main_title", "Hidrogeomorfologia");
                i.putExtra("sub_title", "Substrato do leito (selecionar os que tem mais de 35%)");
                i.putExtra("type",1);
                i.putExtra("required", true);
                options= new String[]{"Blocos e rocha> 40 cm"
                        ,"Calhaus 20 - 40 cm"
                        ,"Cascalho 2 cm - 20 cm"
                        ,"Areia 0,5 - 2cm"
                        ,"Limo <0,5 mm"
                        ,"Solo"
                        ,"Artificial"
                        ,"Não é visível"};
                i.putExtra("options",options);
                i.putExtra("question_num",next_question);
                startActivity(i);
                break;
            case 6:
                i.putExtra("main_title", "Hidrogeomorfologia");
                i.putExtra("sub_title", "Estado geral da linha de água");
                i.putExtra("type",1);
                i.putExtra("required", true);
                options= new String[]{"Canal sem alterações, estado natural",
                        "Canal ligeiramente perturbado",
                        "Início de uma importante alteração do canal",
                        "Grande alteração do canal",
                        "Canal completamente alterado (canalizado, regularizado)"};
                i.putExtra("options",options);
                i.putExtra("question_num",next_question);
                startActivity(i);
                break;
            case 7:
                i.putExtra("main_title", "Hidrogeomorfologia");
                i.putExtra("sub_title", "Erosão");
                i.putExtra("type",1);
                i.putExtra("required", true);
                options= new String[]{"Sem Erosão",
                        "Formação de > 3 regos",
                        "Formação de 1-3 regos",
                        "Queda de muros e árvores",
                        "Rombos com mais de 1 metro com queda de muros ou árvores"};
                i.putExtra("options",options);
                i.putExtra("question_num",next_question);
                startActivity(i);
                break;
            case 8:
                i.putExtra("main_title", "Hidrogeomorfologia");
                i.putExtra("sub_title", "Sedimentação");
                i.putExtra("type",1);
                i.putExtra("required", true);
                options= new String[]{"Ausente",
                        "Decomposição na margem em curva",
                        "Mouchões (deposição de areia no leito",
                        "Ilhas sem vegetação",
                        "Ilhas com vegetação",
                        "Deposição nas margens (s/vegetação)",
                        "Deposição nas margens (c/vegetação)",
                        "Rochas expostas no leito"};
                i.putExtra("options",options);
                i.putExtra("question_num",next_question);
                startActivity(i);
                break;
            case 9:
                Intent pergunta2_1=new Intent(this, IRR_2_1.class);
                pergunta2_1.putExtra("answers",answers);
                pergunta2_1.putExtra("answers2",answers2);
                pergunta2_1.putExtra("required", true);
                pergunta2_1.putExtra("question_num",next_question);
                startActivity(pergunta2_1);
                break;

            case 11:
                i.putExtra("main_title", "Qualidade da água");
                i.putExtra("sub_title", "A cor da água");
                i.putExtra("type",0);
                i.putExtra("required", true);
                options= new String[]{"Transparente",
                        "Leitosa",
                        "Castanha",
                        "Verde-escura",
                        "Laranja",
                        "Cinzenta",
                        "Preta",
                        "Outra cor"};
                i.putExtra("options",options);
                i.putExtra("question_num",next_question);
                startActivity(i);
                break;
            case 12:
                i.putExtra("main_title", "Qualidade da água");
                i.putExtra("sub_title", "O odor (cheiro) da água");
                i.putExtra("answers",answers);
                i.putExtra("type",0);
                i.putExtra("required", true);
                options= new String[]{"Não tem odor",
                        "Cheiro a fresco",
                        "Cheiro a Lama (Vasa)",
                        "Cheiro a esgoto",
                        "Cheiro químico (cloro)",
                        "Cheiro podre (ovos podres)",
                        "Outro odor"};
                i.putExtra("options",options);
                i.putExtra("question_num",next_question);
                startActivity(i);
                break;
            case 13:
                i.putExtra("main_title", "Qualidade da água");
                i.putExtra("sub_title", "Tabela de Macroinvertebrados");
                i.putExtra("answers",answers);
                i.putExtra("type",0);
                i.putExtra("required", true);
                options= new String[]{"1. Planárias",
                        "2. Hidudíneros (Sanguessugas)",
                        "3.1 Simulideos",
                        "3.2 Quironomideos, Sirfídeos, Culidídeos, Tipulídeos (Larva de mosquitos)",
                        "4.1 Ancilídeo",
                        "4.2 Limnídeo; Physa",
                        "5. Bivalves",
                        "6.1 Patas Nadadoras (Dystiscidae)",
                        "6.2 Pata Locomotoras (Hydraena)",
                        "7.1 Trichóptero (mosca d’água) S/Casulo",
                        "7.2 Trichóptero (mosca d’água) C/Casulo",
                        "8. Odonata (Larva de Libelinhas)",
                        "9. Heterópteros",
                        "10. Plecópteros (mosca-de-pedra)",
                        "11.1 Baetídeo",
                        "11.2 Cabeça Planar (Ecdyonurus)",
                        "12. Crustáceos",
                        "13. Ácaros",
                        "14. Pulga-de-água (Daphnia)",
                        "15. Insetos – adultos (adultos na forma aérea)",
                        "16. Mégalopteres"};
                i.putExtra("options",options);
                i.putExtra("question_num",next_question);
                startActivity(i);
                break;

            case 14:
                i.putExtra("main_title", "Alterações Antrópicas");
                i.putExtra("sub_title", "Intervenções presentes");
                i.putExtra("answers",answers);
                i.putExtra("type",1);
                i.putExtra("required", true);
                options= new String[]{"Edificíos",
                        "Pontes",
                        "Limpezas de margens",
                        "Estabilização de margens",
                        "Modelação de margens natural",
                        "Modelação de margens artificial",
                        "Barragem",
                        "Diques",
                        "Rio canalizado",
                        "Rio Entubado",
                        "Esporões",
                        "Pardões",
                        "Paredões",
                        "Técnicas de Engenharia Natural",
                        "Outras"};
                i.putExtra("options",options);
                i.putExtra("question_num",next_question);
                startActivity(i);
                break;

            case 15:
                i.putExtra("main_title", "Alterações Antrópicas");
                i.putExtra("sub_title", "Ocupação das margens [<10 m]");
                i.putExtra("answers",answers);
                i.putExtra("type",1);
                i.putExtra("required", true);
                options= new String[]{
                        "Zona natural com/sem intervenção (Floresta Natural)",
                        "Floresta/arvores plantadas",
                        "Mato alto (1-5 m)",
                        "Mato rasteiro <1m",
                        "Pastagem (pecuária)",
                        "Agricultura",
                        "Espaço abandonado (+ 3 anos)",
                        "Jardins ou espaços de lazer",
                        "Zona edificada (casas/edifícios)",
                        "Zona Industrial",
                        "Vias de comunicação (ruas)",
                        "Entulho e zona degradada"};
                i.putExtra("options",options);
                i.putExtra("question_num",next_question);
                startActivity(i);
                break;
            case 16:
                i.putExtra("main_title", "Alterações Antrópicas");
                i.putExtra("sub_title", "Património edificado Leito/margem [estado de conservação: 1 - Bom a 5- Mau]");
                i.putExtra("answers",answers);
                i.putExtra("type",3);
                i.putExtra("required", true);
                i.putExtra("max",5);
                options= new String[]{
                        "Moinho/azenhas",
                        "Açude >2m",
                        "Micro-Açude (1m)",
                        "Micro-Açude (1-2m)",
                        "Barragem (>10m)",
                        "Levadas",
                        "Pesqueiras",
                        "Escadas de peixe",
                        "Poldras",
                        "Pontes/pontões sem pilar no canal",
                        "Pontes/pontões com pilar no canal",
                        "Passagem a vau",
                        "Barcos",
                        "Cais",
                        "Igreja, capela, santuário <100m",
                        "Solares ou casas agrícolas <100m",
                        "Núcleo habitacional <100m"};
                i.putExtra("options",options);
                i.putExtra("question_num",next_question);
                startActivity(i);
                break;

            case 17:
                i.putExtra("main_title", "Alterações Antrópicas");
                i.putExtra("sub_title", "Poluição");
                i.putExtra("answers",answers);
                i.putExtra("type",1);
                i.putExtra("required", true);
                options= new String[]{
                        "Descargas domésticas",
                        "Descargas ETAR",
                        "Descargas industriais",
                        "Descargas químicas",
                        "Descargas águas pluviais",
                        "Presença de criação animal",
                        "Lixeiras",
                        "Lixo doméstico",
                        "Entulho (restos de obras)",
                        "Monstros domésticos",
                        "Sacos de plástico",
                        "Latas e material ferroso",
                        "Queimadas"};
                i.putExtra("options",options);
                i.putExtra("question_num",next_question);
                startActivity(i);
                break;



            case 18:
                i.putExtra("main_title", "Corredor ecológico");
                i.putExtra("sub_title", "Fauna - Anfíbios autoctones");
                i.putExtra("answers",answers);
                i.putExtra("type",1);
                i.putExtra("required", true);
                options= new String[]{
                        "Salamandra-lusitânica (Chioglossa lusitanica)",
                        "Salamandra-de-pintas-amarelas (Salamandra salamandra)",
                        "Tritão-ventre-laranja (Lissotriton boscai)",
                        "Rã-ibérica (Rana ibérica)",
                        "Rã-verde (Rana perezi)",
                        "Sapo-comum (Bufo bufo)"};
                i.putExtra("options",options);
                i.putExtra("question_num",next_question);
                startActivity(i);
                break;

            case 19:
                i.putExtra("main_title", "Corredor ecológico");
                i.putExtra("sub_title", "Fauna - Répteis Autoctones");
                i.putExtra("answers",answers);
                i.putExtra("type",1);
                i.putExtra("required", true);
                options= new String[]{
                        "Lagarto-de-água (Lacerta schreiberi)",
                        "Cobra-de-água-de-colar (Natrix natrix)",
                        "Cágado (Mauremys leprosa)",
                        "Outro"};
                i.putExtra("options",options);
                i.putExtra("question_num",next_question);
                startActivity(i);
                break;
            case 20:
                i.putExtra("main_title", "Corredor ecológico");
                i.putExtra("sub_title", "Fauna - Aves Autoctones");
                i.putExtra("answers",answers);
                i.putExtra("type",1);
                i.putExtra("required", true);
                options= new String[]{
                        "Guarda-rios (Alcedo atthis)",
                        "Garça-real (Ardea cinerea)",
                        "Melro-de-água (Cinclus cinclus)",
                        "Galinha-de-água (Gallinula chloropus)",
                        "Pato-real (Anas platyrhynchos)",
                        "Tentilhão-comum (Fringilla coelebs)",
                        "Chapim-real (Parus major)",
                        "Outro"};
                i.putExtra("options",options);
                i.putExtra("question_num",next_question);
                startActivity(i);
                break;
            case 21:
                i.putExtra("main_title", "Corredor ecológico");
                i.putExtra("sub_title", "Fauna - Mamíferos Autoctones");
                i.putExtra("answers",answers);
                i.putExtra("type",1);
                i.putExtra("required", true);
                options= new String[]{
                        "Lontras (Lutra lutra)",
                        "Morcegos-de-água (Myotis daubentonii)",
                        "Toupeira da água (Galemys pyrenaicus)",
                        "Rato-de-água (Arvicola sapidus)",
                        "Ouriço cacheiro (Erinaceus europaeus)",
                        "Armilho (Mustela Erminea)",
                        "Outro"};
                i.putExtra("options",options);
                i.putExtra("question_num",next_question);
                startActivity(i);
                break;
            case 22:
                i.putExtra("main_title", "Corredor ecológico");
                i.putExtra("sub_title", "Fauna - Peixes Autoctones");
                i.putExtra("answers",answers);
                i.putExtra("type",1);
                i.putExtra("required", true);
                options= new String[]{
                        "Enguia (Anguilla anguilla)",
                        "Lampreia (Lampetra fluiatilis)",
                        "Salmão (Salmo salar)",
                        "Truta (Salmo trutta fario)",
                        "Boga-portuguesa (Iberochondrostoma lusitanicum)",
                        "Boga-do-norte (Chondrostoma duriense)",
                        "Outro"};
                i.putExtra("options",options);
                i.putExtra("question_num",next_question);
                startActivity(i);
                break;
            case 23:
                i.putExtra("main_title", "Corredor ecológico");
                i.putExtra("sub_title", "Fauna Exótica");
                i.putExtra("answers",answers);
                i.putExtra("type",1);
                i.putExtra("required", true);
                options= new String[]{
                        "Perca-sol (Lepomis gibbosus)",
                        "Tartaruga da Florida (Trachemys scripta)",
                        "Caranguejo-peludo-chinês (Eriocheir sinensis)",
                        "Gambúsia (Gambusia holbrooki)",
                        "Mustela vison",
                        "Lagostim vermelho (Procambarus clarkii)",
                        "Truta-arco-íris (Oncorhynchus mykiss)",
                        "Achigã (Micropterus salmoides)",
                        "Perca-sol (Lepomis gibbosus)",
                        "Outro"};
                i.putExtra("options",options);
                i.putExtra("question_num",next_question);
                startActivity(i);
                break;
            case 24:
                i.putExtra("main_title", "Corredor ecológico");
                i.putExtra("sub_title", "Flora");
                i.putExtra("answers",answers);
                i.putExtra("type",1);
                i.putExtra("required", true);
                options= new String[]{
                        "Salgueiral (salgueiros)",
                        "Amial (amieiral)",
                        "Freixial (freixos)",
                        "Choupal (choupos)",
                        "Ulmeiral (ulmerios)",
                        "Sanguinhos (Sanguinhal)",
                        "Ladual (lódãos-bastardos)",
                        "Tramazeiras",
                        "Carvalhal (Carvalhos)",
                        "Sobreiral",
                        "Azinhal",
                        "Outro"};
                i.putExtra("options",options);
                i.putExtra("question_num",next_question);
                startActivity(i);
                break;
            case 25:
                i.putExtra("main_title", "Corredor ecológico");
                i.putExtra("sub_title", "Estado de conservação do bosque ribeirinho (10m*10m)");
                i.putExtra("answers",answers);
                i.putExtra("type",1);
                i.putExtra("required", true);
                options= new String[]{
                        "Total (>75%) com bosque - continuidade arbórea com total",
                        "(50-75%) Com bosque ripícola Semi-continua arbórea",
                        "(25-50%) Sem bosque ripícola (Semi-continua arbórea)",
                        "Campos agrícolas (10-25%) Descontinua - na arbórea",
                        "(5 a 10%) Interrompida – com manchas de árvores",
                        "(<5%) Esparsa - Só árvores isoladas ou Urbanizações ou infra-estruturas"
                };
                i.putExtra("options",options);
                i.putExtra("question_num",next_question);
                startActivity(i);
                break;
            case 26:
                i.putExtra("main_title", "Corredor ecológico");
                i.putExtra("sub_title", "Espécies vegetação invasora");
                i.putExtra("answers",answers);
                i.putExtra("type",1);
                i.putExtra("required", true);
                options= new String[]{
                        "Silvas",
                        "Erva-da-fortuna",
                        "Plumas",
                        "Lentilha de água",
                        "Pinheirinha",
                        "Jacinto de água",
                        "Outro"};
                i.putExtra("options",options);
                i.putExtra("question_num",next_question);
                startActivity(i);
                break;
            case 27:
                i.putExtra("main_title", "Corredor ecológico");
                i.putExtra("sub_title", "Obstrução do leito e margens (vegetação)");
                i.putExtra("answers",answers);
                i.putExtra("type",1);
                i.putExtra("required", true);
                options= new String[]{
                        "Com pouca ou sem vegetação no leito <5%",
                        "Com alguns ramos e pequenos troncos no leito (5 a 25%)",
                        "Com ramos e troncos no leito e margem (25 a 50%)",
                        "Com obstrução de 50 a 75% com ramos e troncos",
                        "Com obstrução quase total >75% do leito e margens"
                };
                i.putExtra("options",options);
                i.putExtra("question_num",next_question);
                startActivity(i);
                break;


            case 28:
                i.putExtra("main_title", "Participação Publica");
                i.putExtra("sub_title", "Disponibilização de informação");
                i.putExtra("answers",answers);
                i.putExtra("type",0);
                i.putExtra("required", true);
                options= new String[]{
                        "Local de informação por junta de freguesia mais próxima ou acesso público á internet, Disponibilidade de informação (técnicos e não técnicos) e acesso a informação de projetos de Participação Pública.",
                        "Local de informação por município ou acesso público á internet, Disponibilidade de informação (técnicos e não técnicos) e acesso à informação de projetos de Participação Pública.",
                        "Acesso á internet com indicações da localização da informação e disponibilidade de informação (técnicos e não técnicos).",
                        "Disponibilidade de informação de qualidade deficiente para os objetivos de reabilitação.",
                        "Ausência de locais de informação acessível."
                };
                i.putExtra("options",options);
                i.putExtra("question_num",next_question);
                startActivity(i);
                break;

            case 29:
                i.putExtra("main_title", "Participação Publica");
                i.putExtra("sub_title", "Envolvimento público");
                i.putExtra("answers",answers);
                i.putExtra("type",0);
                i.putExtra("required", true);
                options= new String[]{
                        "Envolvimento de Decisores-Chave (propietários, município, ARH), em pelo menos duas sessões por ano de participação pública, grupos tipo \"Projeto Rios\",  Realização de sondagens e Inquéritos a população.",
                        "Envolvimento de Decisores-Chave (município, ARH), em pelo menos 1 sessão de participação pública e/ou associações locais por ano.",
                        "Poucas atividades de participação pública.",
                        "Atividades pontuais e com deficiente envolvimento publico local ou de fraca qualidade.",
                        "Ausência de envolvimento."
                };
                i.putExtra("options",options);
                i.putExtra("question_num",next_question);
                startActivity(i);
                break;

            case 30:
                i.putExtra("main_title", "Participação Publica");
                i.putExtra("sub_title", "Acção");
                i.putExtra("answers",answers);
                i.putExtra("type",0);
                i.putExtra("required", true);
                options= new String[]{
                        "São realizadas pelo menos uma das atividades de ações de fiscalização, Monitorização, acompanhamento de participação e envolvimento da população,  e há o seu feedback.",
                        "São realizadas pelo menos uma das atividades de ações de fiscalização e há o seu feedback.",
                        "Integração das decisões de participação nas soluções e inexistência de feedback das decisões finais.",
                        "Sem integração nem feedback das decisões finais.",
                        "Ausência total de atividades."
                };
                i.putExtra("options",options);
                i.putExtra("question_num",next_question);
                startActivity(i);
                break;

            case 31:
                i.putExtra("main_title", "Organização e Planeamento");
                i.putExtra("sub_title", "Legislação");
                i.putExtra("answers",answers);
                i.putExtra("type",0);
                i.putExtra("required", true);
                options= new String[]{
                        "Cumpre todos os requisitos legais nacionais, diretivas europeias e convenções internacionais assinadas por Portugal.",
                        "Cumpre todos os requisitos legais nacionais.",
                        "Não cumpre requisitos legais ao nível de pessoas singulares.",
                        "Não cumpre requisitos legais ao nível de pessoas singulares e de pessoas coletivas.",
                        "Não se observa nenhuma aplicação legal."
                };
                i.putExtra("options",options);
                i.putExtra("question_num",next_question);
                startActivity(i);
                break;

            case 32:
                i.putExtra("main_title", "Organização e Planeamento");
                i.putExtra("sub_title", "Estratégia, planos de ordenamento e gestão");
                i.putExtra("answers",answers);
                i.putExtra("type",0);
                i.putExtra("required", true);
                options= new String[]{
                        "Existem evidências de implementação de uma estratégia de reabilitação integrada com as figuras de ordenamento locais e regionais.",
                        "Existe uma estratégia de reabilitação integrada ou planos de ordenamento e de gestão de bacia hidrográfica implementados.",
                        "Existem planos de ordenamento e de gestão de bacia hidrográfica desintegrados das figuras de ordenamento local e regional e com diminuta implementação.",
                        "Existem planos de ordenamento e de gestão de bacia hidrográfica sem implementação.",
                        "Não existe nenhum documento estratégica, planeamento de ordenamento e de gestão a nível de recursos hídricos."
                };
                i.putExtra("options",options);
                i.putExtra("question_num",next_question);
                startActivity(i);
                break;
            case 33:
                i.putExtra("main_title", "Organização e Planeamento");
                i.putExtra("sub_title", "Gestão das intervenções de melhoria");
                i.putExtra("answers",answers);
                i.putExtra("type",0);
                i.putExtra("required", true);
                options= new String[]{
                        "Definição de objetivos claros de intervenção de melhoria, ações de monitorização com valores de referência, ações de fiscalização, plano de intervenção em caso de acidente ou catástrofe, plano de ações de intervenção de melhoria e ações de manutenção com envolvimento dos proprietários.",
                        "Definição de objetivos claros de intervenção de melhoria, plano de ações de intervenção de melhoria, ações de monitorização com valores de referência e ações de manutenção com envolvimento dos proprietários.",
                        "Definição de objetivos claros de intervenção de melhoria e plano de ações de intervenção de melhoria.",
                        "Definição de objetivos claros de intervenção de melhoria, mas sem qualquer intervenção.",
                        "Não existe nenhuma evidência de gestão das intervenções de melhoria."
                };
                i.putExtra("options",options);
                i.putExtra("question_num",next_question);
                startActivity(i);
                break;






        }


        this.overridePendingTransition(0, 0);

    }

    public void goto_previous(View view){
        this.finish();
        this.overridePendingTransition(0, 0);
    }




    //menu action bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_homepage, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id==R.id.navigate_guardarios)
            startActivity(new Intent(this,GuardaRios.class));
        if(id==R.id.navigate_account)
            startActivity(new Intent(this,Login.class));
        return super.onOptionsItemSelected(item);
    }

}

