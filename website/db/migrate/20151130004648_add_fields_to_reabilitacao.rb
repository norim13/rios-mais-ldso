class AddFieldsToReabilitacao < ActiveRecord::Migration
  def change
    add_column :reabilitacaos, :definicao, :boolean
    add_column :reabilitacaos, :diagnostico, :boolean
    add_column :reabilitacaos, :prioritizacao, :boolean
    add_column :reabilitacaos, :objectivos, :boolean
    add_column :reabilitacaos, :solucoes, :boolean
    add_column :reabilitacaos, :elaboracao, :boolean
    add_column :reabilitacaos, :implementacao, :boolean
    add_column :reabilitacaos, :monitorizacao, :boolean
    add_column :reabilitacaos, :avaliacao, :boolean
    add_column :reabilitacaos, :correcao, :boolean
    add_column :reabilitacaos, :ppublica, :boolean
    add_column :reabilitacaos, :parcerias, :boolean
    add_column :reabilitacaos, :legislacao, :boolean
    add_column :reabilitacaos, :lei_agua, :boolean
    add_column :reabilitacaos, :directiva_agua, :boolean
    add_column :reabilitacaos, :directiva_inundacoes, :boolean
    add_column :reabilitacaos, :custo, :boolean
    add_column :reabilitacaos, :cronograma, :json
    add_column :reabilitacaos, :formacao, :boolean
    add_column :reabilitacaos, :emergencia, :boolean
    add_column :reabilitacaos, :reabilitacao, :boolean
    add_column :reabilitacaos, :revisao, :boolean
  end
end
