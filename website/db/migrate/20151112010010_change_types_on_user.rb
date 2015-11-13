class ChangeTypesOnUser < ActiveRecord::Migration
  def change
    remove_column :users, :distrito
    remove_column :users, :concelho
    add_column :users, :distrito_id, :integer
    add_column :users, :concelho_id, :integer

  end
end
