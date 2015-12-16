class UsersController < ApplicationController

  def new
    @user = User.new
  end

  def show
    @user = User.find(params[:id])
    debugger
  end

  def create
    #render plain: params[:user].inspect
    @user = User.new(user_params)
    @user.permissoes = 1
    if @user.save
      redirect_to @user
    else
      render 'new'
    end
  end

  def updatepermissions

    @users = User.find(params[:user_ids])
    @permissoes = params[:permissoes]

    @users.each_with_index do |u,index|
      u.update_attributes(params[:user].permit(@permissoes[index]))
    end
    flash[:notice] = "Updated users!"

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
