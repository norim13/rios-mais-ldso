class CreateUtilizadors < ActiveRecord::Migration
  def change
    create_table :utilizadors do |t|
      t.string :nome
      t.string :email
      t.string :password
      t.integer :acesso

      t.timestamps null: false
    end
  end
end
