class CreateRotaPoints < ActiveRecord::Migration
  def change
    create_table :rota_points do |t|
      t.string :nome
      t.string :descricao
      t.float :lat
      t.float :lon
      t.integer :ordem #ordem dos pontos na rota. rota começa no ponto 1.
      t.integer :route_id
    end
    add_foreign_key :rota_points, :routes
  end
end
