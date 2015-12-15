class AddPermissoesToUser < ActiveRecord::Migration
  def change
    add_column :users, :permissoes, :integer
  end
end
