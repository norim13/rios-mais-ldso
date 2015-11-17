package engenheiro.rios.IRR;

import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;

/**
 * Created by filipe on 17/11/2015.
 */
public class Questions {


    public static void getQuestion(int next_question, Intent intent, Context context)
    {
        String[] options = new String[0];
        intent.putExtra("question_num", next_question);
        String main_title = "teste";
        String sub_title = "teste";

        boolean required = false;
        int type = -1;
        int max =-1;


        if(next_question!=3 && next_question!=9) {
            ArrayList<Object> arrayList=getOptions(next_question);
            main_title= (String) arrayList.get(0);
            sub_title= (String ) arrayList.get(1);
            type= (int) arrayList.get(2);
            required= (boolean) arrayList.get(3);
            options= (String[]) arrayList.get(4);
            max= (int) arrayList.get(5);
            intent.putExtra("main_title", main_title);
            intent.putExtra("sub_title", sub_title);
            intent.putExtra("type",type);
            intent.putExtra("required", required);
            intent.putExtra("options", options);
            intent.putExtra("max", max);
        }
        else if (next_question==3){
            intent.setClass(context,IRR_1_3.class);
            intent.putExtra("question_num", next_question);
            intent.putExtra("main_title","Hidrogeomorfologia");
            intent.putExtra("sub_title", "Perfil de margens");
            intent.putExtra("required", true);
        }
        else if (next_question==9){
            intent.setClass(context, IRR_2_1.class);
            intent.putExtra("question_num", next_question);
            intent.putExtra("required", true);
    }

    }


    public static ArrayList<Object> getOptions(int next_question){
        ArrayList<Object> response = new ArrayList<Object>() ;
        String main_title="teste";
        String sub_title="teste";
        int type=0;
        Boolean required=false;
        String options[] = new String[0];
        int max = 0;




        switch (next_question){
            case 1:
                main_title="Hidrogeomorfologia";
                sub_title= "Tipo de Vale";
                type=0;
                required= true;
                options= new String[]{"1","2","3","4","5","6","7"};
                break;
            case 2:
                main_title= "Hidrogeomorfologia";
                sub_title= "Perfil de margens";
                type=1;
                required= true;
                options= new String[]{"Vertical escavado",
                        "Vertical cortado",
                        "Declive >45%",
                        "Declive <45%",
                        "Suave comport <45%",
                        "Artificial"};
                break;

            case 3:
                break;

            case 4:
                main_title="Hidrogeomorfologia";
                sub_title= "Substrato das margens (selecionar os que tem mais de 35%)";
                type=1;
                required= true;
                options= new String[]{"Solo argiloso",
                        "Arenoso",
                        "Pedregoso",
                        "Rochoso",
                        "Artificial pedra",
                        "Artificial Betão(5)"};
                break;

            case 5:
                main_title= "Hidrogeomorfologia";
                sub_title= "Substrato do leito (selecionar os que tem mais de 35%)";
                type=1;
                required= true;
                options= new String[]{"Blocos e rocha> 40 cm"
                        ,"Calhaus 20 - 40 cm"
                        ,"Cascalho 2 cm - 20 cm"
                        ,"Areia 0,5 - 2cm"
                        ,"Limo <0,5 mm"
                        ,"Solo"
                        ,"Artificial"
                        ,"Não é visível"};
                break;

            case 6:
                main_title= "Hidrogeomorfologia";
                sub_title= "Estado geral da linha de água";
                type=1;
                required= true;
                options= new String[]{"Canal sem alterações, estado natural",
                        "Canal ligeiramente perturbado",
                        "Início de uma importante alteração do canal",
                        "Grande alteração do canal",
                        "Canal completamente alterado (canalizado, regularizado)"};
                break;
            case 7:
                main_title= "Hidrogeomorfologia";
                sub_title= "Erosão";
                type=1;
                required= true;
                options= new String[]{"Sem Erosão",
                        "Formação de > 3 regos",
                        "Formação de 1-3 regos",
                        "Queda de muros e árvores",
                        "Rombos com mais de 1 metro com queda de muros ou árvores"};
                break;

            case 8:
                main_title= "Hidrogeomorfologia";
                sub_title= "Sedimentação";
                type=1;
                required= true;
                options= new String[]{"Ausente",
                        "Decomposição na margem em curva",
                        "Mouchões (deposição de areia no leito",
                        "Ilhas sem vegetação",
                        "Ilhas com vegetação",
                        "Deposição nas margens (s/vegetação)",
                        "Deposição nas margens (c/vegetação)",
                        "Rochas expostas no leito"};
                break;

            case 9:
                break;

            case 10:
                main_title="Qualidade da água";
                sub_title= "Indícios na água :))))";
                type=1;
                required= true;
                options= new String[]{"Óleo (reflexos multicolores)",
                        "Espuma",
                        "Esgotos",
                        "Impurezas e lixos orgânicos",
                        "Sacos de plástico e embalagens",
                        "Latas ou material ferroso",
                        "Outros"};
                break;

            case 11:
                main_title= "Qualidade da água";
                sub_title= "A cor da água";
                type=0;
                required= true;
                options= new String[]{"Transparente",
                        "Leitosa",
                        "Castanha",
                        "Verde-escura",
                        "Laranja",
                        "Cinzenta",
                        "Preta",
                        "Outra cor"};

                break;

            case 12:
                main_title= "Qualidade da água";
                sub_title= "O odor (cheiro) da água";
                type=0;
                required= true;
                options= new String[]{"Não tem odor",
                        "Cheiro a fresco",
                        "Cheiro a Lama (Vasa)",
                        "Cheiro a esgoto",
                        "Cheiro químico (cloro)",
                        "Cheiro podre (ovos podres)",
                        "Outro odor"};
                break;

            case 13:
                main_title= "Qualidade da água";
                sub_title= "Tabela de Macroinvertebrados";
                type=0;
                required= true;
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
                break;

            case 14:
                main_title= "Alterações Antrópicas";
                sub_title= "Intervenções presentes";
                type=1;
                required= true;
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
                break;

            case 15:
                main_title= "Alterações Antrópicas";
                sub_title= "Ocupação das margens [<10 m]";
                type=1;
                required= true;
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
                break;

            case 16:
                main_title= "Alterações Antrópicas";
                sub_title= "Património edificado Leito/margem [estado de conservação: 1 - Bom a 5- Mau]";
                type=3;
                required= true;
                max=5;
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
                break;

            case 17:
                main_title= "Alterações Antrópicas";
                sub_title= "Poluição";
                type=1;
                required= true;
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
                break;

            case 18:
                main_title= "Corredor ecológico";
                sub_title= "Fauna - Anfíbios autoctones";
                type=1;
                required= true;
                options= new String[]{
                        "Salamandra-lusitânica (Chioglossa lusitanica)",
                        "Salamandra-de-pintas-amarelas (Salamandra salamandra)",
                        "Tritão-ventre-laranja (Lissotriton boscai)",
                        "Rã-ibérica (Rana ibérica)",
                        "Rã-verde (Rana perezi)",
                        "Sapo-comum (Bufo bufo)"};
                break;

            case 19:
                main_title= "Corredor ecológico";
                sub_title= "Fauna - Répteis Autoctones";
                type=1;
                required= true;
                options= new String[]{
                        "Lagarto-de-água (Lacerta schreiberi)",
                        "Cobra-de-água-de-colar (Natrix natrix)",
                        "Cágado (Mauremys leprosa)",
                        "Outro"};
                break;

            case 20:
                main_title= "Corredor ecológico";
                sub_title= "Fauna - Aves Autoctones";
                type=1;
                required= true;
                options= new String[]{
                        "Guarda-rios (Alcedo atthis)",
                        "Garça-real (Ardea cinerea)",
                        "Melro-de-água (Cinclus cinclus)",
                        "Galinha-de-água (Gallinula chloropus)",
                        "Pato-real (Anas platyrhynchos)",
                        "Tentilhão-comum (Fringilla coelebs)",
                        "Chapim-real (Parus major)",
                        "Outro"};
                break;

            case 21:
                main_title= "Corredor ecológico";
                sub_title= "Fauna - Mamíferos Autoctones";
                type=1;
                required= true;
                options= new String[]{
                        "Lontras (Lutra lutra)",
                        "Morcegos-de-água (Myotis daubentonii)",
                        "Toupeira da água (Galemys pyrenaicus)",
                        "Rato-de-água (Arvicola sapidus)",
                        "Ouriço cacheiro (Erinaceus europaeus)",
                        "Armilho (Mustela Erminea)",
                        "Outro"};
                break;

            case 22:
                main_title= "Corredor ecológico";
                sub_title= "Fauna - Peixes Autoctones";
                type=1;
                required= true;
                options= new String[]{
                        "Enguia (Anguilla anguilla)",
                        "Lampreia (Lampetra fluiatilis)",
                        "Salmão (Salmo salar)",
                        "Truta (Salmo trutta fario)",
                        "Boga-portuguesa (Iberochondrostoma lusitanicum)",
                        "Boga-do-norte (Chondrostoma duriense)",
                        "Outro"};
                break;

            case 23:
                main_title= "Corredor ecológico";
                sub_title= "Fauna Exótica";
                type=1;
                required= true;
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
                break;

            case 24:
                main_title= "Corredor ecológico";
                sub_title= "Flora";
                type=1;
                required= true;
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
                break;

            case 25:
                main_title= "Corredor ecológico";
                sub_title= "Estado de conservação do bosque ribeirinho (10m*10m)";
                type=1;
                required= true;
                options= new String[]{
                        "Total (>75%) com bosque - continuidade arbórea com total",
                        "(50-75%) Com bosque ripícola Semi-continua arbórea",
                        "(25-50%) Sem bosque ripícola (Semi-continua arbórea)",
                        "Campos agrícolas (10-25%) Descontinua - na arbórea",
                        "(5 a 10%) Interrompida – com manchas de árvores",
                        "(<5%) Esparsa - Só árvores isoladas ou Urbanizações ou infra-estruturas"
                };
                break;

            case 26:
                main_title= "Corredor ecológico";
                sub_title= "Espécies vegetação invasora";
                type=1;
                required= true;
                options= new String[]{
                        "Silvas",
                        "Erva-da-fortuna",
                        "Plumas",
                        "Lentilha de água",
                        "Pinheirinha",
                        "Jacinto de água",
                        "Outro"};
                break;

            case 27:
                main_title= "Corredor ecológico";
                sub_title= "Obstrução do leito e margens (vegetação)";
                type=1;
                required= true;
                options= new String[]{
                        "Com pouca ou sem vegetação no leito <5%",
                        "Com alguns ramos e pequenos troncos no leito (5 a 25%)",
                        "Com ramos e troncos no leito e margem (25 a 50%)",
                        "Com obstrução de 50 a 75% com ramos e troncos",
                        "Com obstrução quase total >75% do leito e margens"
                };
                break;


            case 28:
                main_title= "Participação Publica";
                sub_title= "Disponibilização de informação";
                type=0;
                required= true;
                options= new String[]{
                        "Local de informação por junta de freguesia mais próxima ou acesso público á internet, Disponibilidade de informação (técnicos e não técnicos) e acesso a informação de projetos de Participação Pública.",
                        "Local de informação por município ou acesso público á internet, Disponibilidade de informação (técnicos e não técnicos) e acesso à informação de projetos de Participação Pública.",
                        "Acesso á internet com indicações da localização da informação e disponibilidade de informação (técnicos e não técnicos).",
                        "Disponibilidade de informação de qualidade deficiente para os objetivos de reabilitação.",
                        "Ausência de locais de informação acessível."
                };
                break;

            case 29:
                main_title= "Participação Publica";
                sub_title= "Envolvimento público";
                type=0;
                required= true;
                options= new String[]{
                        "Envolvimento de Decisores-Chave (propietários, município, ARH), em pelo menos duas sessões por ano de participação pública, grupos tipo \"Projeto Rios\",  Realização de sondagens e Inquéritos a população.",
                        "Envolvimento de Decisores-Chave (município, ARH), em pelo menos 1 sessão de participação pública e/ou associações locais por ano.",
                        "Poucas atividades de participação pública.",
                        "Atividades pontuais e com deficiente envolvimento publico local ou de fraca qualidade.",
                        "Ausência de envolvimento."
                };
                break;

            case 30:
                main_title= "Participação Publica";
                sub_title= "Acção";
                type=0;
                required= true;
                options= new String[]{
                        "São realizadas pelo menos uma das atividades de ações de fiscalização, Monitorização, acompanhamento de participação e envolvimento da população,  e há o seu feedback.",
                        "São realizadas pelo menos uma das atividades de ações de fiscalização e há o seu feedback.",
                        "Integração das decisões de participação nas soluções e inexistência de feedback das decisões finais.",
                        "Sem integração nem feedback das decisões finais.",
                        "Ausência total de atividades."
                };
                break;

            case 31:
                main_title= "Organização e Planeamento";
                sub_title= "Legislação";
                type=0;
                required= true;
                options= new String[]{
                        "Cumpre todos os requisitos legais nacionais, diretivas europeias e convenções internacionais assinadas por Portugal.",
                        "Cumpre todos os requisitos legais nacionais.",
                        "Não cumpre requisitos legais ao nível de pessoas singulares.",
                        "Não cumpre requisitos legais ao nível de pessoas singulares e de pessoas coletivas.",
                        "Não se observa nenhuma aplicação legal."
                };
                break;

            case 32:
                main_title= "Organização e Planeamento";
                sub_title= "Estratégia, planos de ordenamento e gestão";
                type=0;
                required= true;
                options= new String[]{
                        "Existem evidências de implementação de uma estratégia de reabilitação integrada com as figuras de ordenamento locais e regionais.",
                        "Existe uma estratégia de reabilitação integrada ou planos de ordenamento e de gestão de bacia hidrográfica implementados.",
                        "Existem planos de ordenamento e de gestão de bacia hidrográfica desintegrados das figuras de ordenamento local e regional e com diminuta implementação.",
                        "Existem planos de ordenamento e de gestão de bacia hidrográfica sem implementação.",
                        "Não existe nenhum documento estratégica, planeamento de ordenamento e de gestão a nível de recursos hídricos."
                };
                break;

            case 33:
                main_title= "Organização e Planeamento";
                sub_title= "Gestão das intervenções de melhoria";
                type=0;
                required= true;
                options= new String[]{
                        "Definição de objetivos claros de intervenção de melhoria, ações de monitorização com valores de referência, ações de fiscalização, plano de intervenção em caso de acidente ou catástrofe, plano de ações de intervenção de melhoria e ações de manutenção com envolvimento dos proprietários.",
                        "Definição de objetivos claros de intervenção de melhoria, plano de ações de intervenção de melhoria, ações de monitorização com valores de referência e ações de manutenção com envolvimento dos proprietários.",
                        "Definição de objetivos claros de intervenção de melhoria e plano de ações de intervenção de melhoria.",
                        "Definição de objetivos claros de intervenção de melhoria, mas sem qualquer intervenção.",
                        "Não existe nenhuma evidência de gestão das intervenções de melhoria."
                };
                break;


        }

        response.add(main_title);
        response.add(sub_title);
        response.add(type);
        response.add(required);
        response.add(options);
        response.add(max);

        return response;


    }

    }






