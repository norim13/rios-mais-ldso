class CreateConcelhos < ActiveRecord::Migration
  def change
    create_table :concelhos do |t|
      t.timestamps null: false
    end
  end
end
