class CreateGuardarios < ActiveRecord::Migration
  def change
    create_table :guardarios do |t|
      t.string :rio
      t.integer :user_id

      t.timestamps null: false
    end
  end
end
