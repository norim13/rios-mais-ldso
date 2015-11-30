class AddEditUserToFormIrr < ActiveRecord::Migration
  def change
    add_column :form_irrs, :edit_user_id, :integer
  end
end
