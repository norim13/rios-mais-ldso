class AddimageToGuardrio < ActiveRecord::Migration
  def change
    add_column :guardarios, :images, :json
  end
end
