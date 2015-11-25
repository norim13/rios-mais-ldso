class CreateLimpezas < ActiveRecord::Migration
  def change
    create_table :limpezas do |t|
      t.string :pergunta
      t.string :resposta
      t.timestamps null: false
    end
  end
end
