class AddImagesToFormIrrs < ActiveRecord::Migration
  def change
    add_column :form_irrs, :images, :json
  end
end
