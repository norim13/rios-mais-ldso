class CreateFormIrrImages < ActiveRecord::Migration
  def change
    create_table :form_irr_images do |t|
      t.string :image
      t.timestamps null: false
    end

    add_reference :form_irr_images, :form_irr, index: true, foreign_key: true
  end
end
