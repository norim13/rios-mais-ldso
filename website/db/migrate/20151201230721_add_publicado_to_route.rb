class AddPublicadoToRoute < ActiveRecord::Migration
  def change
    #true se a rota é visivel para o publico, ou false se ainda não está completa, ficando visivel apenas para a administração.
    add_column :routes, :publicada, :boolean
  end
end