class CreateUsers < ActiveRecord::Migration
  def change
    create_table :users do |t|
      t.string :nome
      t.string :email
      t.string :password_digest
      t.integer :access

      t.timestamps null: false
    end
  end
end
