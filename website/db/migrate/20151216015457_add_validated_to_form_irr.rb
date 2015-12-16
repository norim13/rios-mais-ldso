class AddValidatedToFormIrr < ActiveRecord::Migration
  def change
    add_column :form_irrs, :validated, :boolean
  end
end
