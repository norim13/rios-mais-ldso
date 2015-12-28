class AlterarParametrosSos < ActiveRecord::Migration
  def change
    remove_column :reports, :coordenadas
    add_column :reports, :lat, :float
    add_column :reports, :lon, :float
    add_column :reports, :nome_rio, :string
  end
end
