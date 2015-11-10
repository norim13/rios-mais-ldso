class ChangeFormIrrDatabasev2 < ActiveRecord::Migration
  def change
    change_table :form_irrs do |t|
      #change outros de checkbox to text area
      t.remove :intervencoes_outras
      t.remove :repteis_outro
      t.remove :aves_outro
      t.remove :mamiferos_outro
      t.remove :peixes_outro
      t.remove :fauna_outro
      t.remove :flora_outro
      t.remove :vegetacaoInvasora_outro

      t.string :intervencoes_outras
      t.string :repteis_outro
      t.string :aves_outro
      t.string :mamiferos_outro
      t.string :peixes_outro
      t.string :fauna_outro
      t.string :flora_outro
      t.string :vegetacaoInvasora_outro

      #add margem
      t.boolean :margem
    end
  end
end
