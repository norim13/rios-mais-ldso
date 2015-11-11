class AddDetailsToUser < ActiveRecord::Migration
  def change
    add_column :users, :distrito, :string
    add_column :users, :concelho, :string
    add_column :users, :telef, :int
    add_column :users, :habilitacoes, :string
    add_column :users, :profissao, :string
    add_column :users, :formacao, :string
  end
end
