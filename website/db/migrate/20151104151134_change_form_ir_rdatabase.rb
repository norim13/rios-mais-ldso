class ChangeFormIrRdatabase < ActiveRecord::Migration
  def change
    change_table :form_irrs do |t|
      t.remove :erosao
      t.remove :sedimentacao

      t.boolean :erosao_semErosao
      t.boolean :erosao_formacaomais3
      t.boolean :erosao_formacao1a3
      t.boolean :erosao_quedamuros
      t.boolean :erosao_rombos

      t.boolean :sedimentacao_ausente
      t.boolean :sedimentacao_decomposicao
      t.boolean :sedimentacao_mouchoes
      t.boolean :sedimentacao_ilhassemveg
      t.boolean :sedimentacao_ilhascomveg
      t.boolean :sedimentacao_deposicaosemveg
      t.boolean :sedimentacao_deposicaocomveg
      t.boolean :sedimentacao_rochas

      t.remove :poluicao_descargasDomesticas
      t.remove :poluicao_descargasETAR
      t.remove :poluicao_descargasIndustriais
      t.remove :poluicao_descargasQuimicas
      t.remove :poluicao_descargasAguasPluviais
      t.remove :poluicao_presencaCriacaoAnimais
      t.remove :poluicao_lixeiras
      t.remove :poluicao_lixoDomestico
      t.remove :poluicao_entulho
      t.remove :poluicao_monstrosDomesticos
      t.remove :poluicao_sacosDePlastico
      t.remove :poluicao_latasMaterialFerroso
      t.remove :poluicao_queimadas

      t.boolean :poluicao_descargasDomesticas
      t.boolean :poluicao_descargasETAR
      t.boolean :poluicao_descargasIndustriais
      t.boolean :poluicao_descargasQuimicas
      t.boolean :poluicao_descargasAguasPluviais
      t.boolean :poluicao_presencaCriacaoAnimais
      t.boolean :poluicao_lixeiras
      t.boolean :poluicao_lixoDomestico
      t.boolean :poluicao_entulho
      t.boolean :poluicao_monstrosDomesticos
      t.boolean :poluicao_sacosDePlastico
      t.boolean :poluicao_latasMaterialFerroso
      t.boolean :poluicao_queimadas
    end
  end
end