class FormIrr < ActiveRecord::Base
	belongs_to :user
	validates :tipoDeVale,:perfilDeMargens,:larguraDaSuperficieDaAgua,:profundidadeMedia,:seccao,:velocidadeMedia,:caudal,
	          :pH,:condutividade,:temperatura,:nivelDeOxigenio,:percentagemDeOxigenio,:nitratos,:nitritos,:transparencia,
	          :corDaAgua,:odorDaAgua,:patrimonio_moinho,:patrimonio_acude,:patrimonio_microAcude1,:patrimonio_microAcude2,
	          :patrimonio_barragem,:patrimonio_levadas,:patrimonio_pesqueiras,:patrimonio_escadasDePeixe,:patrimonio_poldras,
	          :patrimonio_pontesSemPilar,:patrimonio_pontesComPilar,:patrimonio_passagemAVau,:patrimonio_barcos,:patrimonio_cais,
	          :patrimonio_igreja,:patrimonio_solares,:patrimonio_nucleoHabitacional,:patrimonio_edificiosParticulares,
	          :patrimonio_edificiosPublicos,:patrimonio_ETA,:patrimonio_descarregadoresDeAguasPluviais,:patrimonio_coletoresSaneamento,
	          :patrimonio_defletoresArtificiais,:patrimonio_motaLateral,:conservacaoBosqueRibeirinho,:obstrucaoDoLeitoMargens,
	          :disponibilizacaoDeInformacao,:envolvimentoPublico,:acao,:legislacao,:estrategia,:gestaoDasIntervencoes,presence: true
end
