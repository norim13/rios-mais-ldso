class AddIdToCategoria < ActiveRecord::Migration
  def change
    add_column :limpezas, :categoria_id, :integer
  end
end
