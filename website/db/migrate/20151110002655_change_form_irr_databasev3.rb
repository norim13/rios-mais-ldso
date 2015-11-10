class ChangeFormIrrDatabasev3 < ActiveRecord::Migration
  def change
    change_table :form_irrs do |t|
      #change "margem" from boolean to integer
      t.remove :margem
      t.integer :margem
    end
  end
end
