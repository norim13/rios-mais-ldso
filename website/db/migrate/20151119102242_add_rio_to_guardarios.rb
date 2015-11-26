class AddRioToGuardarios < ActiveRecord::Migration
  def change
    add_column :guardarios, :nomeRio, :string
  end
end
