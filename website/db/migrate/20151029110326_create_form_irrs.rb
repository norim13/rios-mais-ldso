class CreateFormIrrs < ActiveRecord::Migration
  def change
    create_table :form_irrs do |t|
      t.integer :tipoDeVale
      t.integer :perfilDeMargens
      #Volume de agua
      t.float :larguraDaSuperficieDaAgua
      t.float :profundidadeMedia
      t.float :seccao
      t.float :velocidadeMedia
      t.float :caudal
      #substrato das margens
      t.boolean :substratoDasMargens_soloArgiloso
      t.boolean :substratoDasMargens_arenoso
      t.boolean :substratoDasMargens_pedregoso
      t.boolean :substratoDasMargens_rochoso
      t.boolean :substratoDasMargens_artificialPedra
      t.boolean :substratoDasMargens_artificialBetao

      t.boolean :substratoDoLeito_blocoseRocha
      t.boolean :substratoDoLeito_calhaus
      t.boolean :substratoDoLeito_cascalho
      t.boolean :substratoDoLeito_areia
      t.boolean :substratoDoLeito_limo
      t.boolean :substratoDoLeito_solo
      t.boolean :substratoDoLeito_artificial
      t.boolean :substratoDoLeito_naoEVisivel

      #estado geral da linha de agua
      t.integer :estadoGeraldaLinhadeAgua
      t.integer :erosao
      t.integer :sedimentacao

      # Hidrogeomorfologia
      #qualidade da agua
      t.float :pH
      t.float :condutividade
      t.float :temperatura
      t.float :nivelDeOxigenio
      t.float :percentagemDeOxigenio
      t.float :nitratos
      t.float :nitritos
      t.integer :transparencia

      #Indicios na agua
      t.boolean :oleo
      t.boolean :espuma
      t.boolean :esgotos
      t.boolean :impurezas
      t.boolean :sacosDePlastico
      t.boolean :latas
      t.string :indiciosNaAgua_outros

      #a cor da agua
      t.integer :corDaAgua

      #o odor (cheiro) da agua
      t.integer :odorDaAgua

      #tabela de macroinvertebrados
      t.boolean :planarias
      t.boolean :hirudineos
      t.boolean :oligoquetas
      # dipteros
      t.boolean :simulideos
      t.boolean :quironomideos
      # gastropodes
      t.boolean :ancilideo
      t.boolean :limnideo
      t.boolean :bivalves
      # coleoptero
      t.boolean :patasNadadoras
      t.boolean :pataLocomotoras
      # trichoptero
      t.boolean :trichopteroS
      t.boolean :trichopteroC
      t.boolean :odonata
      t.boolean :heteropteros
      t.boolean :plecopteros
      # efemeropteros
      t.boolean :baetideo
      t.boolean :cabecaPlanar
      t.boolean :crustaceos
      t.boolean :acaros
      t.boolean :pulgaDeAgua
      t.boolean :insetos
      t.boolean :megalopteres

      # Qualidade da agua
      # intervencoes presentes
      t.boolean :intervencoes_edificios
      t.boolean :intervencoes_pontes
      t.boolean :intervencoes_limpezasDasMargens
      t.boolean :intervencoes_estabilizacaoDeMargens
      t.boolean :intervencoes_modelacaoDeMargensNatural
      t.boolean :intervencoes_modelacaoDeMargensArtificial
      t.boolean :intervencoes_barragem
      t.boolean :intervencoes_diques
      t.boolean :intervencoes_rioCanalizado
      t.boolean :intervencoes_rioEntubado
      t.boolean :intervencoes_esporoes
      t.boolean :intervencoes_paredoes
      t.boolean :intervencoes_tecnicasDeEngenhariaNatural
      t.boolean :intervencoes_outras

      # ocupacao das margens <10m
      t.boolean :ocupacao_florestaNatural
      t.boolean :ocupacao_florestaPlantadas
      t.boolean :ocupacao_matoAlto
      t.boolean :ocupacao_matoRasteiro
      t.boolean :ocupacao_pastagem
      t.boolean :ocupacao_agricultura
      t.boolean :ocupacao_espacoAbandonado
      t.boolean :ocupacao_jardins
      t.boolean :ocupacao_zonaEdificada
      t.boolean :ocupacao_zonaIndustrial
      t.boolean :ocupacao_ruas
      t.boolean :ocupacao_entulho

      # Patrimonio edificado leito/margem [1 (bom) - 5 (mau)]
      t.integer :patrimonio_moinho
      t.integer :patrimonio_acude
      t.integer :patrimonio_microAcude1
      t.integer :patrimonio_microAcude2
      t.integer :patrimonio_barragem
      t.integer :patrimonio_levadas
      t.integer :patrimonio_pesqueiras
      t.integer :patrimonio_escadasDePeixe
      t.integer :patrimonio_poldras
      t.integer :patrimonio_pontesSemPilar
      t.integer :patrimonio_pontesComPilar
      t.integer :patrimonio_passagemAVau
      # embarcacoes
      t.integer :patrimonio_barcos
      t.integer :patrimonio_cais
      t.integer :patrimonio_igreja
      t.integer :patrimonio_solares
      t.integer :patrimonio_nucleoHabitacional

      t.integer :patrimonio_edificiosParticulares
      t.integer :patrimonio_edificiosPublicos
      t.integer :patrimonio_ETA
      t.integer :patrimonio_descarregadoresDeAguasPluviais
      t.integer :patrimonio_coletoresSaneamento
      t.integer :patrimonio_defletoresArtificiais
      t.integer :patrimonio_motaLateral

      # poluicao
      t.integer :poluicao_descargasDomesticas
      t.integer :poluicao_descargasETAR
      t.integer :poluicao_descargasIndustriais
      t.integer :poluicao_descargasQuimicas
      t.integer :poluicao_descargasAguasPluviais
      t.integer :poluicao_presencaCriacaoAnimais
      t.integer :poluicao_lixeiras
      t.integer :poluicao_lixoDomestico
      t.integer :poluicao_entulho
      t.integer :poluicao_monstrosDomesticos
      t.integer :poluicao_sacosDePlastico
      t.integer :poluicao_latasMaterialFerroso
      t.integer :poluicao_queimadas

      # alteracoes antropicas
      # fauna
      # anfibios autoctones
      t.boolean :salamandraLusitanica
      t.boolean :salamandraPintasAmarelas
      t.boolean :tritaoVentreLaranja
      t.boolean :raIberica
      t.boolean :raVerde
      t.boolean :sapoComum

      # repteis autoctones
      t.boolean :lagartoDeAgua
      t.boolean :cobraAguaDeColar
      t.boolean :cagado
      t.boolean :repteis_outro

      # aves autoctones
      t.boolean :guardaRios
      t.boolean :garcaReal
      t.boolean :melroDeAgua
      t.boolean :galinhaDeAgua
      t.boolean :patoReal
      t.boolean :tentilhaoComum
      t.boolean :chapimReal
      t.boolean :aves_outro

      # mamiferos autoctones
      t.boolean :lontras
      t.boolean :morcegosDeAgua
      t.boolean :toupeiraDaAgua
      t.boolean :ratoDeAgua
      t.boolean :ouricoCacheiro
      t.boolean :armilho
      t.boolean :mamiferos_outro

      # peixes autoctones
      t.boolean :enguia
      t.boolean :lampreia
      t.boolean :salmao
      t.boolean :truta
      t.boolean :bogaPortuguesa
      t.boolean :bogaDoNorte
      t.boolean :peixes_outro

      # fauna exotica
      t.boolean :percaSol
      t.boolean :tartarugaDaFlorida
      t.boolean :caranguejoPeludoChines
      t.boolean :gambusia
      t.boolean :mustelaVison
      t.boolean :lagostimVermelho
      t.boolean :trutaArcoIris
      t.boolean :achiga
      t.boolean :fauna_outro

      # flora
      t.boolean :salgueiral
      t.boolean :amial
      t.boolean :freixal
      t.boolean :choupal
      t.boolean :ulmeiral
      t.boolean :sanguinos
      t.boolean :ladual
      t.boolean :tramazeiras
      t.boolean :carvalhal
      t.boolean :sobreiral
      t.boolean :azinhal
      t.boolean :flora_outro

      # Estado de conservação do bosque ribeirinho (10mx10 m)
      t.integer :conservacaoBosqueRibeirinho

      # especies vegetacao invasora
      t.boolean :silvas
      t.boolean :ervaDaFortuna
      t.boolean :plumas
      t.boolean :lentilhaDaAgua
      t.boolean :pinheirinha
      t.boolean :jacintoDeAgua
      t.boolean :vegetacaoInvasora_outro

      # obstrucao do leito e margens (vegetacao)
      t.integer :obstrucaoDoLeitoMargens

      # corredor ecologico
      t.integer :disponibilizacaoDeInformacao
      t.integer :envolvimentoPublico
      t.integer :acao

      # participacao publica
      t.integer :legislacao
      t.integer :estrategia
      t.integer :gestaoDasIntervencoes

      t.timestamps null: false
    end
  end
end
