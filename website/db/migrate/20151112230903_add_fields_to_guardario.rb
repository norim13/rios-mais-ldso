class AddFieldsToGuardario < ActiveRecord::Migration
  def change
    add_column :guardarios, :local, :string
    add_column :guardarios, :voar, :string
    add_column :guardarios, :cantar, :string
    add_column :guardarios, :parado, :boolean
    add_column :guardarios, :beber, :boolean
    add_column :guardarios, :cacar, :boolean
    add_column :guardarios, :cuidarcrias, :boolean
    add_column :guardarios, :alimentar, :string
    add_column :guardarios, :outro, :string
  end
end
