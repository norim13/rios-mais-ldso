class CreateLogLimpezas < ActiveRecord::Migration
  def change
    create_table :log_limpezas do |t|
      t.string :problema1
      t.string :problema2
      t.string :problema3
      t.string :problema4
      t.string :problema5
      t.string :problema6
      t.string :problema7
      t.string :problema8
      t.string :problema9
      t.string :problema10
      t.string :problema11
      t.string :problema12
      t.string :problema13
      t.date :cheia_data
      t.string :cheia_origem
      t.integer :cheia_perdas_monetarias
      t.string :cheia_destruicao
      t.timestamps null: false
    end
  end
end
