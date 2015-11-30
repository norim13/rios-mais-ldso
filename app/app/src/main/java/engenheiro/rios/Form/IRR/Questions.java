package engenheiro.rios.Form.IRR;

import java.util.ArrayList;

/**
 * Created by filipe on 17/11/2015.
 */
public class Questions {


    private static ArrayList<Integer[]> valuesIRR;


    /**
     * Retorna a informação de uma pergunta
     * @param next_question
     * @return ArrayList<Object> =main_title,sub_title,type,required,options,max,value_irr,maxmin;
     */
    public static ArrayList<Object> getOptions(int next_question){
        ArrayList<Object> response = new ArrayList<Object>() ;
        String main_title="teste";
        String sub_title="teste";
        int type=0;
        Boolean required=false;
        String options[] = new String[0];
        ArrayList<Float[]> maxmin= new ArrayList<Float[]>();
        int max = 0;

        Integer value_irr[] = new Integer[]{};



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
                type=0;
                required= true;
                options= new String[]{"Vertical escavado",
                        "Vertical cortado",
                        "Declive >45%",
                        "Declive <45%",
                        "Suave comport <45%",
                        "Artificial"};
                break;

            case 3:
                main_title= "Hidrogeomorfologia";
                sub_title= "Perfil de margens";
                type=2;
                required= true;
                options= new String[]{"Largura da superfície da água (L) (m):",
                       "Profundidade média (P) (m):",
                        "Velocidade média (V) (m/s):"};
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
                type=0;
                required= true;
                options= new String[]{"Canal sem alterações, estado natural",
                        "Canal ligeiramente perturbado",
                        "Início de uma importante alteração do canal",
                        "Grande alteração do canal",
                        "Canal completamente alterado (canalizado, regularizado)"};
                value_irr= new Integer[]{5,5,5,5,5};
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
                value_irr= new Integer[]{1,2,3,3,4};
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
                value_irr= new Integer[]{5,5,5,5,5,5,5,5};
                break;

            case 9:
                main_title= "Hidrogeomorfologia";
                sub_title= "Qualidade da água";
                type=2;
                required= true;
                options= new String[]{"pH",
                "Condutividade",
                "Temperatura (ºC)",
                "Nível de Oxigénio (mg/L)",
                "O2 %",
                "Nitratos",
                "Nitritos",
                "Transparência"};
                maxmin.add(new Float[]{1f,14f});
                maxmin.add(new Float[]{50f,1500f});
                maxmin.add(new Float[]{0f,40f});
                maxmin.add(new Float[]{0f,15f});
                maxmin.add(new Float[]{0f,200f});
                maxmin.add(new Float[]{0f,80f});
                maxmin.add(new Float[]{0f,4f});
                maxmin.add(new Float[]{1f,4f});

                value_irr= new Integer[]{5,5,5,5,5,5,5,5};
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
                value_irr= new Integer[]{5,4,3,2,2,3};

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
                value_irr= new Integer[]{1,4,1,4,5,5,5};
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
                value_irr= new Integer[]{1,1,4,3,5,4};

                break;

            case 13:
                main_title= "Corredor ecológico";
                sub_title= "O odor (cheiro) da água";
                type=1;
                required= true;
                options= new String[]{"-","Planárias","Planárias",
                        "-","Hirudíneos","Hirudíneos (Sanguessugas)",
                        "-","Dípteros","Oligoquetas (minhocas)","Simulideos","Quironomideos, Sirfídeos, Culidídeos, Tipulídeos (Larva de mosquitos)",
                        "-","Gastrópodes (Mullusca)","Ancilídeo","Limnídeo; Physa","Bivalves",
                        "-","Coleóptero (escaravelho)","Patas Nadadoras (Dystiscidae)","Pata Locomotoras (Hydraena)",
                        "-","Trichóptero (mosca d’água)","Trichóptero (mosca d’água) S/Casulo","Trichóptero (mosca d’água) C/Casul",
                        "-","Odonata","Odonata (Larva de Libelinhas)",
                        "-","Heterópteros","Heterópteros",
                        "-","Plecópteros","Plecópteros (mosca-de-pedra)",
                        "-","Efemerópteros (efémera)","Efemerópteros (efémera)","Cabeça Planar (Ecdyonurus) ",
                        "-","Crustáceos","Crustáceos",
                        "-","Ácaros","Ácaros",
                        "-","Pulga-de-água","Pulga-de-água (Daphnia)",
                        "-","Insetos","Insetos – adultos (adultos na forma aérea)",
                        "-","Mégalopteres","Mégalopteres",
                };
                value_irr= new Integer[]{1,4,5,2,5,2,4,3,3,4,3,2,2,3,1,3,1,3,3,2,null,3};
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
                        "Técnicas de Engenharia Natural"};
                value_irr= new Integer[]{2,2,2,1,1,4,5,3,5,5,4,3,1,3};

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
                value_irr= new Integer[]{1,1,3,2,3,3,3,2,4,5,3,5};

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
                        "Núcleo habitacional <100m",
                        "Edifícios particulares (< 100m)",
                        "Edifícios públicos (estatais) (< 100m)",
                        "ETA/ETAR/elevatórias",
                        "Descarregadores de águas pluviais",
                        "Coletores saneamento",
                        "Defletores artificiais",
                        "Mota lateral"
                };
                value_irr= new Integer[]{1,2,1,1,5,2,3,1,2,2,3,2,1,2,1,1,3,2,3,4,5,4,3,3};

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
                value_irr= new Integer[]{2,3,5,5,2,4,4,3,3,5,3,4,3};

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
                value_irr= new Integer[]{1,1,1,1,1,1};
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
                value_irr= new Integer[]{1,1,3};
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
                value_irr= new Integer[]{1,1,1,1,2,1,1};
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
                value_irr= new Integer[]{1,1,1,3,1,1};

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
                value_irr= new Integer[]{1,1,1,1,1,1};

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
                value_irr= new Integer[]{5,5,5,5,5,5,5,5,5};

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
                value_irr= new Integer[]{1,1,1,1,1,1,1,1,1,1,1};

                break;

            case 25:
                main_title= "Corredor ecológico";
                sub_title= "Estado de conservação do bosque ribeirinho (10m*10m)";
                type=0;
                required= true;
                options= new String[]{
                        "Total (>75%) com bosque - continuidade arbórea com total",
                        "(50-75%) Com bosque ripícola Semi-continua arbórea",
                        "(25-50%) Sem bosque ripícola (Semi-continua arbórea)",
                        "Campos agrícolas (10-25%) Descontinua - na arbórea",
                        "(5 a 10%) Interrompida – com manchas de árvores",
                        "(<5%) Esparsa - Só árvores isoladas ou Urbanizações ou infra-estruturas"
                };
                value_irr= new Integer[]{1,2,3,4,5,5};
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
                value_irr= new Integer[]{1,3,4,3,3,4,3};

                break;

            case 27:
                main_title= "Corredor ecológico";
                sub_title= "Obstrução do leito e margens (vegetação)";
                type=0;
                required= true;
                options= new String[]{
                        "Com pouca ou sem vegetação no leito <5%",
                        "Com alguns ramos e pequenos troncos no leito (5 a 25%)",
                        "Com ramos e troncos no leito e margem (25 a 50%)",
                        "Com obstrução de 50 a 75% com ramos e troncos",
                        "Com obstrução quase total >75% do leito e margens"
                };
                value_irr= new Integer[]{1,2,3,4,5};
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
                value_irr= new Integer[]{1,2,3,4,5};
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
                value_irr= new Integer[]{1,2,3,4,5};
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
                value_irr= new Integer[]{1,2,3,4,5};
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
                value_irr= new Integer[]{1,2,3,4,5};
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
                value_irr= new Integer[]{1,2,3,4,5};
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
                value_irr= new Integer[]{1,2,3,4,5};
                break;


        }

        response.add(main_title);
        response.add(sub_title);
        response.add(type);
        response.add(required);
        response.add(options);
        response.add(max);
        response.add(value_irr);
        response.add(maxmin);

        return response;


    }


    /**
     * retorna um arraylist com todos os valores de cada questão no form IRR
     * @return
     */
    public static ArrayList<Integer[]> getValuesIRR() {
        ArrayList<Integer[]> valuesIRR = new ArrayList<Integer[]>();
        Integer[] iii = new Integer[0];
        valuesIRR.add(iii);
        for (int i=1;i<=33;i++){
            valuesIRR.add((Integer[]) getOptions(i).get(6));
        }
        return valuesIRR;
    }
}






