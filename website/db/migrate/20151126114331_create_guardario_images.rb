class CreateGuardarioImages < ActiveRecord::Migration
  def change
    create_table :guardario_images do |t|
      t.string :image
      t.timestamps null: false
    end

    add_reference :guardario_images, :guardario, index: true, foreign_key: true
  end
end
