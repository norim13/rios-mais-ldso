class UsersController < ApplicationController

  def new
    @user = User.new
  end

  def show
    @user = User.find(params[:id])
  end

  def create
    @user = User.new(user_params)
    @user.permissoes = 1
    if @user.save
      redirect_to @user
    else
      render 'new'
    end
  end

  # Atualiza as permissões dos utilizadores (apenas pelo admin)
  def updatepermissions
    @users = User.find(params[:user_ids])
    @permissoes = params[:permissoes]

    @users.each_with_index do |u,index|
      u.update_attribute(:permissoes,@permissoes[index])
    end

    redirect_to adminpanel_path
  end

  def self.authenticate(email, password)
    user = find_by_email(email)
    if user && user.password_hash == BCrypt::Engine.hash_secret(password, user.password_salt)
      user
    else
      nil
    end
  end

  private
  def user_params
    params.require(:user).permit(:nome, :email, :password, :password_confirmation)
  end

end
