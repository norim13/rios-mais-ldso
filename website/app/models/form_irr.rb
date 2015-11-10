class FormIrr < ActiveRecord::Base
	belongs_to :user
	validates :margem,:tipoDeVale,:perfilDeMargens,:larguraDaSuperficieDaAgua,:profundidadeMedia,:seccao,:velocidadeMedia,:caudal,
	          :pH,:condutividade,:temperatura,:nivelDeOxigenio,:percentagemDeOxigenio,:nitratos,:nitritos,:transparencia,
	          :corDaAgua,:odorDaAgua,:patrimonio_moinho,:patrimonio_acude,:patrimonio_microAcude1,:patrimonio_microAcude2,
	          :patrimonio_barragem,:patrimonio_levadas,:patrimonio_pesqueiras,:patrimonio_escadasDePeixe,:patrimonio_poldras,
	          :patrimonio_pontesSemPilar,:patrimonio_pontesComPilar,:patrimonio_passagemAVau,:patrimonio_barcos,:patrimonio_cais,
	          :patrimonio_igreja,:patrimonio_solares,:patrimonio_nucleoHabitacional,:patrimonio_edificiosParticulares,
	          :patrimonio_edificiosPublicos,:patrimonio_ETA,:patrimonio_descarregadoresDeAguasPluviais,:patrimonio_coletoresSaneamento,
	          :patrimonio_defletoresArtificiais,:patrimonio_motaLateral,:conservacaoBosqueRibeirinho,:obstrucaoDoLeitoMargens,
	          :disponibilizacaoDeInformacao,:envolvimentoPublico,:acao,:legislacao,:estrategia,:gestaoDasIntervencoes,presence: true

	validates :pH, :inclusion => 1..14
	validates :condutividade, :inclusion => 50..1500
	validates :temperatura, :inclusion => 0..40
	validates :nivelDeOxigenio, :inclusion => 0..15
	validates :percentagemDeOxigenio, :inclusion => 0..200
	validates :nitratos, :inclusion => 0..80
	validates :nitritos, :inclusion => 0..4
	validates :transparencia, :inclusion => 1..4
	validates :margem, :inclusion => 1..2 #1- esquerda, 2- direita
end
