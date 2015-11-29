class ChangeLimpeza < ActiveRecord::Migration
  def change
    add_column :limpezas, :categoria, :string
    rename_column :limpezas, :pergunta, :opcao

  end
end
