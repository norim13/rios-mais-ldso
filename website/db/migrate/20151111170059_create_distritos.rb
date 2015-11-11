class CreateDistritos < ActiveRecord::Migration
  def change
    create_table :distritos do |t|
      t.string :nome
      t.timestamps null: false
    end
  end
end
