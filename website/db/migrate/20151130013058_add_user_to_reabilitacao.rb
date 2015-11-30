class AddUserToReabilitacao < ActiveRecord::Migration
  def change
    add_reference :reabilitacaos, :user, index: true, foreign_key: true
    add_column :reabilitacaos, :rio, :string

  end
end
