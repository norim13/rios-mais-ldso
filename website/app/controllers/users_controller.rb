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
    @arrayTemp = [];

    params.each_with_index do |key, value,index|
      # target groups using regular expressions
      if (key.to_s[/atribuir-permissao-.*/])
        @arrayTemp << value
      end
    end

    @arrayTemp.each do |obj|
      User.where(:id => @arrayTemp).update(:permissoes,3)
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
