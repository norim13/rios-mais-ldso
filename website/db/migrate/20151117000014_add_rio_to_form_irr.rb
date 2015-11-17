class AddRioToFormIrr < ActiveRecord::Migration
  def change
    add_column :form_irrs, :idRio, :string
  end
end
