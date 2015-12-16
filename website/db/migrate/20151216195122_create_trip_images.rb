class CreateTripImages < ActiveRecord::Migration
  def change
    create_table :trip_images do |t|
      t.string :image
      t.timestamps null: false
    end
    add_reference :trip_images, :trip_point, index: true, foreign_key: true
  end
end
