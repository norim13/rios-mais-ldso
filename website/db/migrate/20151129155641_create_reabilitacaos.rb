class CreateReabilitacaos < ActiveRecord::Migration
  def change
    create_table :reabilitacaos do |t|

      t.timestamps null: false
    end
  end
end
