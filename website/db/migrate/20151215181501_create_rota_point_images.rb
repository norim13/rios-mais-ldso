class CreateRotaPointImages < ActiveRecord::Migration
  def change
    create_table :rota_point_images do |t|
      t.string :image
      t.timestamps null: false
    end
    add_reference :rota_point_images, :rota_point, index: true, foreign_key: true
  end
end
