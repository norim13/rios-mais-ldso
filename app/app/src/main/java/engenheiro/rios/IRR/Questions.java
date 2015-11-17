package engenheiro.rios.IRR;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by filipe on 17/11/2015.
 */
public class Questions {


    public static void getQuestion(int next_question, Intent intent, Context context)
    {
        String[] options;
        intent.putExtra("question_num", next_question);
        switch (next_question){
                case 1:
                    intent.putExtra("main_title","Hidrogeomorfologia");
                    intent.putExtra("sub_title", "Tipo de Vale");
                    intent.putExtra("type",0);
                    intent.putExtra("required", true);
                    options= new String[]{"1","2","3","4","5","6","7"};
                    intent.putExtra("options",options);
                    Log.e("teste","enrou no "+1);
                    break;
                case 2:
                    intent.putExtra("main_title", "Hidrogeomorfologia");
                    intent.putExtra("sub_title", "Perfil de margens");
                    intent.putExtra("type",1);
                    intent.putExtra("required", true);
                    options= new String[]{"Vertical escavado",
                            "Vertical cortado",
                            "Declive >45%",
                            "Declive <45%",
                            "Suave comport <45%",
                            "Artificial"};
                    intent.putExtra("options",options);
                    Log.e("teste", "enrou no " + 2);
                    break;

                case 3:
                    intent.setClass(context,IRR_1_3.class);
                    intent.putExtra("question_num", next_question);
                    intent.putExtra("main_title","Hidrogeomorfologia");
                    intent.putExtra("sub_title", "Perfil de margens");
                    intent.putExtra("required", true);
                    break;

                case 4:
                    intent.putExtra("main_title","Hidrogeomorfologia");
                    intent.putExtra("sub_title", "Substrato das margens (selecionar os que tem mais de 35%)");
                    intent.putExtra("type",1);
                    intent.putExtra("required", true);
                    options= new String[]{"Solo argiloso",
                            "Arenoso",
                            "Pedregoso",
                            "Rochoso",
                            "Artificial pedra",
                            "Artificial Betão(5)"};
                    intent.putExtra("options",options);
                    break;

                case 5:
                    intent.putExtra("main_title", "Hidrogeomorfologia");
                    intent.putExtra("sub_title", "Substrato do leito (selecionar os que tem mais de 35%)");
                    intent.putExtra("type",1);
                    intent.putExtra("required", true);
                    options= new String[]{"Blocos e rocha> 40 cm"
                            ,"Calhaus 20 - 40 cm"
                            ,"Cascalho 2 cm - 20 cm"
                            ,"Areia 0,5 - 2cm"
                            ,"Limo <0,5 mm"
                            ,"Solo"
                            ,"Artificial"
                            ,"Não é visível"};
                    intent.putExtra("options",options);
                    break;

                case 6:
                    intent.putExtra("main_title", "Hidrogeomorfologia");
                    intent.putExtra("sub_title", "Estado geral da linha de água");
                    intent.putExtra("type",1);
                    intent.putExtra("required", true);
                    options= new String[]{"Canal sem alterações, estado natural",
                            "Canal ligeiramente perturbado",
                            "Início de uma importante alteração do canal",
                            "Grande alteração do canal",
                            "Canal completamente alterado (canalizado, regularizado)"};
                    intent.putExtra("options",options);
                    break;
                case 7:
                    intent.putExtra("main_title", "Hidrogeomorfologia");
                    intent.putExtra("sub_title", "Erosão");
                    intent.putExtra("type",1);
                    intent.putExtra("required", true);
                    options= new String[]{"Sem Erosão",
                            "Formação de > 3 regos",
                            "Formação de 1-3 regos",
                            "Queda de muros e árvores",
                            "Rombos com mais de 1 metro com queda de muros ou árvores"};
                    intent.putExtra("options",options);
                    break;

                case 8:
                    intent.putExtra("main_title", "Hidrogeomorfologia");
                    intent.putExtra("sub_title", "Sedimentação");
                    intent.putExtra("type",1);
                    intent.putExtra("required", true);
                    options= new String[]{"Ausente",
                            "Decomposição na margem em curva",
                            "Mouchões (deposição de areia no leito",
                            "Ilhas sem vegetação",
                            "Ilhas com vegetação",
                            "Deposição nas margens (s/vegetação)",
                            "Deposição nas margens (c/vegetação)",
                            "Rochas expostas no leito"};
                    intent.putExtra("options",options);
                    break;

                case 9:
                    intent.setClass(context,IRR_2_1.class);
                    intent.putExtra("question_num", next_question);
                    intent.putExtra("required", true);
                    break;

                case 10:
                    intent.putExtra("main_title","Qualidade da água");
                    intent.putExtra("sub_title", "Indícios na água :))))");
                    intent.putExtra("type",1);
                    intent.putExtra("required", true);
                    options= new String[]{"Óleo (reflexos multicolores)",
                            "Espuma",
                            "Esgotos",
                            "Impurezas e lixos orgânicos",
                            "Sacos de plástico e embalagens",
                            "Latas ou material ferroso",
                            "Outros"};
                    intent.putExtra("options",options);
                    break;

                case 11:
                        intent.putExtra("main_title", "Qualidade da água");
                        intent.putExtra("sub_title", "A cor da água");
                        intent.putExtra("type",0);
                        intent.putExtra("required", true);
                        options= new String[]{"Transparente",
                                "Leitosa",
                                "Castanha",
                                "Verde-escura",
                                "Laranja",
                                "Cinzenta",
                                "Preta",
                                "Outra cor"};
                        intent.putExtra("options",options);
                        break;

                case 12:
                    intent.putExtra("main_title", "Qualidade da água");
                    intent.putExtra("sub_title", "O odor (cheiro) da água");
                    intent.putExtra("type",0);
                    intent.putExtra("required", true);
                    options= new String[]{"Não tem odor",
                            "Cheiro a fresco",
                            "Cheiro a Lama (Vasa)",
                            "Cheiro a esgoto",
                            "Cheiro químico (cloro)",
                            "Cheiro podre (ovos podres)",
                            "Outro odor"};
                    intent.putExtra("options",options);
                    break;

                case 13:
                    intent.putExtra("main_title", "Qualidade da água");
                    intent.putExtra("sub_title", "Tabela de Macroinvertebrados");
                    intent.putExtra("type",0);
                    intent.putExtra("required", true);
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
                    intent.putExtra("options",options);
                    break;

                case 14:
                    intent.putExtra("main_title", "Alterações Antrópicas");
                    intent.putExtra("sub_title", "Intervenções presentes");
                    intent.putExtra("type",1);
                    intent.putExtra("required", true);
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
                    intent.putExtra("options",options);
                    break;

                case 15:
                    intent.putExtra("main_title", "Alterações Antrópicas");
                    intent.putExtra("sub_title", "Ocupação das margens [<10 m]");
                    intent.putExtra("type",1);
                    intent.putExtra("required", true);
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
                    intent.putExtra("options",options);
                    break;

                case 16:
                    intent.putExtra("main_title", "Alterações Antrópicas");
                    intent.putExtra("sub_title", "Património edificado Leito/margem [estado de conservação: 1 - Bom a 5- Mau]");
                    intent.putExtra("type",3);
                    intent.putExtra("required", true);
                    intent.putExtra("max",5);
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
                    intent.putExtra("options",options);
                    break;

                case 17:
                    intent.putExtra("main_title", "Alterações Antrópicas");
                    intent.putExtra("sub_title", "Poluição");
                    intent.putExtra("type",1);
                    intent.putExtra("required", true);
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
                    intent.putExtra("options",options);
                    break;

                case 18:
                    intent.putExtra("main_title", "Corredor ecológico");
                    intent.putExtra("sub_title", "Fauna - Anfíbios autoctones");
                    intent.putExtra("type",1);
                    intent.putExtra("required", true);
                    options= new String[]{
                            "Salamandra-lusitânica (Chioglossa lusitanica)",
                            "Salamandra-de-pintas-amarelas (Salamandra salamandra)",
                            "Tritão-ventre-laranja (Lissotriton boscai)",
                            "Rã-ibérica (Rana ibérica)",
                            "Rã-verde (Rana perezi)",
                            "Sapo-comum (Bufo bufo)"};
                    intent.putExtra("options",options);
                    break;

                case 19:
                    intent.putExtra("main_title", "Corredor ecológico");
                    intent.putExtra("sub_title", "Fauna - Répteis Autoctones");
                    intent.putExtra("type",1);
                    intent.putExtra("required", true);
                    options= new String[]{
                            "Lagarto-de-água (Lacerta schreiberi)",
                            "Cobra-de-água-de-colar (Natrix natrix)",
                            "Cágado (Mauremys leprosa)",
                            "Outro"};
                    intent.putExtra("options",options);
                    break;

                case 20:
                    intent.putExtra("main_title", "Corredor ecológico");
                    intent.putExtra("sub_title", "Fauna - Aves Autoctones");
                    intent.putExtra("type",1);
                    intent.putExtra("required", true);
                    options= new String[]{
                            "Guarda-rios (Alcedo atthis)",
                            "Garça-real (Ardea cinerea)",
                            "Melro-de-água (Cinclus cinclus)",
                            "Galinha-de-água (Gallinula chloropus)",
                            "Pato-real (Anas platyrhynchos)",
                            "Tentilhão-comum (Fringilla coelebs)",
                            "Chapim-real (Parus major)",
                            "Outro"};
                    intent.putExtra("options",options);
                    break;

                case 21:
                    intent.putExtra("main_title", "Corredor ecológico");
                    intent.putExtra("sub_title", "Fauna - Mamíferos Autoctones");
                    intent.putExtra("type",1);
                    intent.putExtra("required", true);
                    options= new String[]{
                            "Lontras (Lutra lutra)",
                            "Morcegos-de-água (Myotis daubentonii)",
                            "Toupeira da água (Galemys pyrenaicus)",
                            "Rato-de-água (Arvicola sapidus)",
                            "Ouriço cacheiro (Erinaceus europaeus)",
                            "Armilho (Mustela Erminea)",
                            "Outro"};
                    intent.putExtra("options",options);
                    break;

                case 22:
                    intent.putExtra("main_title", "Corredor ecológico");
                    intent.putExtra("sub_title", "Fauna - Peixes Autoctones");
                    intent.putExtra("type",1);
                    intent.putExtra("required", true);
                    options= new String[]{
                            "Enguia (Anguilla anguilla)",
                            "Lampreia (Lampetra fluiatilis)",
                            "Salmão (Salmo salar)",
                            "Truta (Salmo trutta fario)",
                            "Boga-portuguesa (Iberochondrostoma lusitanicum)",
                            "Boga-do-norte (Chondrostoma duriense)",
                            "Outro"};
                    intent.putExtra("options",options);
                    break;

                case 23:
                    intent.putExtra("main_title", "Corredor ecológico");
                    intent.putExtra("sub_title", "Fauna Exótica");
                    intent.putExtra("type",1);
                    intent.putExtra("required", true);
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
                    intent.putExtra("options",options);
                    break;

                case 24:
                    intent.putExtra("main_title", "Corredor ecológico");
                    intent.putExtra("sub_title", "Flora");
                    intent.putExtra("type",1);
                    intent.putExtra("required", true);
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
                    intent.putExtra("options",options);
                    break;

                case 25:
                    intent.putExtra("main_title", "Corredor ecológico");
                    intent.putExtra("sub_title", "Estado de conservação do bosque ribeirinho (10m*10m)");
                    intent.putExtra("type",1);
                    intent.putExtra("required", true);
                    options= new String[]{
                            "Total (>75%) com bosque - continuidade arbórea com total",
                            "(50-75%) Com bosque ripícola Semi-continua arbórea",
                            "(25-50%) Sem bosque ripícola (Semi-continua arbórea)",
                            "Campos agrícolas (10-25%) Descontinua - na arbórea",
                            "(5 a 10%) Interrompida – com manchas de árvores",
                            "(<5%) Esparsa - Só árvores isoladas ou Urbanizações ou infra-estruturas"
                    };
                    intent.putExtra("options",options);
                    break;

                case 26:
                    intent.putExtra("main_title", "Corredor ecológico");
                    intent.putExtra("sub_title", "Espécies vegetação invasora");
                    intent.putExtra("type",1);
                    intent.putExtra("required", true);
                    options= new String[]{
                            "Silvas",
                            "Erva-da-fortuna",
                            "Plumas",
                            "Lentilha de água",
                            "Pinheirinha",
                            "Jacinto de água",
                            "Outro"};
                    intent.putExtra("options",options);
                    break;

                case 27:
                    intent.putExtra("main_title", "Corredor ecológico");
                    intent.putExtra("sub_title", "Obstrução do leito e margens (vegetação)");
                    intent.putExtra("type",1);
                    intent.putExtra("required", true);
                    options= new String[]{
                            "Com pouca ou sem vegetação no leito <5%",
                            "Com alguns ramos e pequenos troncos no leito (5 a 25%)",
                            "Com ramos e troncos no leito e margem (25 a 50%)",
                            "Com obstrução de 50 a 75% com ramos e troncos",
                            "Com obstrução quase total >75% do leito e margens"
                    };
                    intent.putExtra("options",options);
                    break;


                case 28:
                    intent.putExtra("main_title", "Participação Publica");
                    intent.putExtra("sub_title", "Disponibilização de informação");
                    intent.putExtra("type",0);
                    intent.putExtra("required", true);
                    options= new String[]{
                            "Local de informação por junta de freguesia mais próxima ou acesso público á internet, Disponibilidade de informação (técnicos e não técnicos) e acesso a informação de projetos de Participação Pública.",
                            "Local de informação por município ou acesso público á internet, Disponibilidade de informação (técnicos e não técnicos) e acesso à informação de projetos de Participação Pública.",
                            "Acesso á internet com indicações da localização da informação e disponibilidade de informação (técnicos e não técnicos).",
                            "Disponibilidade de informação de qualidade deficiente para os objetivos de reabilitação.",
                            "Ausência de locais de informação acessível."
                    };
                    intent.putExtra("options",options);
                    break;

                case 29:
                    intent.putExtra("main_title", "Participação Publica");
                    intent.putExtra("sub_title", "Envolvimento público");
                    intent.putExtra("type",0);
                    intent.putExtra("required", true);
                    options= new String[]{
                            "Envolvimento de Decisores-Chave (propietários, município, ARH), em pelo menos duas sessões por ano de participação pública, grupos tipo \"Projeto Rios\",  Realização de sondagens e Inquéritos a população.",
                            "Envolvimento de Decisores-Chave (município, ARH), em pelo menos 1 sessão de participação pública e/ou associações locais por ano.",
                            "Poucas atividades de participação pública.",
                            "Atividades pontuais e com deficiente envolvimento publico local ou de fraca qualidade.",
                            "Ausência de envolvimento."
                    };
                    intent.putExtra("options",options);
                    break;

                case 30:
                    intent.putExtra("main_title", "Participação Publica");
                    intent.putExtra("sub_title", "Acção");
                    intent.putExtra("type",0);
                    intent.putExtra("required", true);
                    options= new String[]{
                            "São realizadas pelo menos uma das atividades de ações de fiscalização, Monitorização, acompanhamento de participação e envolvimento da população,  e há o seu feedback.",
                            "São realizadas pelo menos uma das atividades de ações de fiscalização e há o seu feedback.",
                            "Integração das decisões de participação nas soluções e inexistência de feedback das decisões finais.",
                            "Sem integração nem feedback das decisões finais.",
                            "Ausência total de atividades."
                    };
                    intent.putExtra("options",options);
                    break;

                case 31:
                    intent.putExtra("main_title", "Organização e Planeamento");
                    intent.putExtra("sub_title", "Legislação");
                    intent.putExtra("type",0);
                    intent.putExtra("required", true);
                    options= new String[]{
                            "Cumpre todos os requisitos legais nacionais, diretivas europeias e convenções internacionais assinadas por Portugal.",
                            "Cumpre todos os requisitos legais nacionais.",
                            "Não cumpre requisitos legais ao nível de pessoas singulares.",
                            "Não cumpre requisitos legais ao nível de pessoas singulares e de pessoas coletivas.",
                            "Não se observa nenhuma aplicação legal."
                    };
                    intent.putExtra("options",options);
                    break;

                case 32:
                    intent.putExtra("main_title", "Organização e Planeamento");
                    intent.putExtra("sub_title", "Estratégia, planos de ordenamento e gestão");
                    intent.putExtra("type",0);
                    intent.putExtra("required", true);
                    options= new String[]{
                            "Existem evidências de implementação de uma estratégia de reabilitação integrada com as figuras de ordenamento locais e regionais.",
                            "Existe uma estratégia de reabilitação integrada ou planos de ordenamento e de gestão de bacia hidrográfica implementados.",
                            "Existem planos de ordenamento e de gestão de bacia hidrográfica desintegrados das figuras de ordenamento local e regional e com diminuta implementação.",
                            "Existem planos de ordenamento e de gestão de bacia hidrográfica sem implementação.",
                            "Não existe nenhum documento estratégica, planeamento de ordenamento e de gestão a nível de recursos hídricos."
                    };
                    intent.putExtra("options",options);
                    break;

                case 33:
                    intent.putExtra("main_title", "Organização e Planeamento");
                    intent.putExtra("sub_title", "Gestão das intervenções de melhoria");
                    intent.putExtra("type",0);
                    intent.putExtra("required", true);
                    options= new String[]{
                            "Definição de objetivos claros de intervenção de melhoria, ações de monitorização com valores de referência, ações de fiscalização, plano de intervenção em caso de acidente ou catástrofe, plano de ações de intervenção de melhoria e ações de manutenção com envolvimento dos proprietários.",
                            "Definição de objetivos claros de intervenção de melhoria, plano de ações de intervenção de melhoria, ações de monitorização com valores de referência e ações de manutenção com envolvimento dos proprietários.",
                            "Definição de objetivos claros de intervenção de melhoria e plano de ações de intervenção de melhoria.",
                            "Definição de objetivos claros de intervenção de melhoria, mas sem qualquer intervenção.",
                            "Não existe nenhuma evidência de gestão das intervenções de melhoria."
                    };
                    intent.putExtra("options", options);
                    break;


        }
        Log.e("teste","antes do i ");
        //i.putExtra("question_num", i);
        Log.e("teste", "depois do i");


    }
    }




