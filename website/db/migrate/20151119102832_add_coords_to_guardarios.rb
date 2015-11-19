class AddCoordsToGuardarios < ActiveRecord::Migration
  def change
    add_column :guardarios, :lat, :float
    add_column :guardarios, :lon, :float
  end
end
