class AddCoordToFormIrr < ActiveRecord::Migration
  def change
    add_column :form_irrs, :lat, :float
    add_column :form_irrs, :lon, :float
    add_column :form_irrs, :nomeRio, :string
  end
end
