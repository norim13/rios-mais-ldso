class AddIrrInfoToFormIrr < ActiveRecord::Migration
    def change
      add_column :form_irrs, :irr_hidrogeomorfologia, :integer
      add_column :form_irrs, :irr_qualidadedaagua, :integer
      add_column :form_irrs, :irr_alteracoesantropicas, :integer
      add_column :form_irrs, :irr_corredorecologico, :integer
      add_column :form_irrs, :irr_participacaopublica, :integer
      add_column :form_irrs, :irr_organizacaoeplaneamento, :integer
      add_column :form_irrs, :irr, :integer
    end
end
