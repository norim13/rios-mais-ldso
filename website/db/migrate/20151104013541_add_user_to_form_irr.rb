class AddUserToFormIrr < ActiveRecord::Migration
  def change
    add_reference :form_irrs, :user, index: true, foreign_key: true

  end
end
