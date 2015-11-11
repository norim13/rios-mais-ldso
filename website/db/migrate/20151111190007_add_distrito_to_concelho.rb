class AddDistritoToConcelho < ActiveRecord::Migration
  def change
    add_reference :concelhos, :distrito, index: true, foreign_key: true
    add_column :concelhos, :nome, :string
  end
end
