class AddRioToTrip < ActiveRecord::Migration
  def change
    add_column :trips, :idRio, :string
    add_column :trips, :nomeRio, :string
  end
end
