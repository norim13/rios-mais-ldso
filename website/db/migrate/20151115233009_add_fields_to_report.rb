class AddFieldsToReport < ActiveRecord::Migration
  def change
    add_reference :reports, :user, index: true, foreign_key: true
    add_column :reports, :rio, :string
    add_column :reports, :descricao, :string
    add_column :reports, :categoria, :string
    add_column :reports, :motivo , :string
    add_column :reports, :coordenadas, :string
    add_column :reports, :images, :json


  end
end
