class CreateTripPoints < ActiveRecord::Migration
  def change
    create_table :trip_points do |t|
      t.integer :trip_id
      t.string :descricao
      t.float :lat
      t.float :lon
      t.timestamps null: false
    end
    add_foreign_key :trip_points, :trips
  end
end
