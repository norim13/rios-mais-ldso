class CreateRoutes < ActiveRecord::Migration
  def change
    create_table :routes do |t|
      t.string :nome
      t.string :descricao
      t.string :zona
      t.timestamps null: false
    end
  end
end
