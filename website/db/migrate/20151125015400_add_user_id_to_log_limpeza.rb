class AddUserIdToLogLimpeza < ActiveRecord::Migration
  def change
    add_reference :log_limpezas, :user, index: true, foreign_key: true
  end
end
