class UsersController < ApplicationController

  def new
  end

  def show
    @user = User.find(params[:id])
  end

  def create
    #render plain: params[:user].inspect
    @user = User.new(user_params)
    @user.save
    redirect_to @user
  end

  private
  def user_params
    params.require(:user).permit(:nome, :email, :password)
  end

end
