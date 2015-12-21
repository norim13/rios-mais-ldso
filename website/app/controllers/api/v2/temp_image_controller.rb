class Api::V2::TempImageController < ApplicationController
  before_filter :authenticate_user_from_token!

  def create
    user_email = params[:user_email].presence
    user       = user_email && User.find_by_email(user_email)

    if params[:image]
      GuardarioImage.create(image: params[:image], guardario_id: params[:guardario_id])

      render :json => '{"success" : "true"}'
    else
      render :json => '{"success" : "false", "error" : "problem saving guardario"}'
    end

  end

  def authenticate_user_from_token!
    user_email = params[:user_email].presence
    user = user_email && User.find_by_email(user_email)

    # Notice how we use Devise.secure_compare to compare the token
    # in the database with the token given in the params, mitigating
    # timing attacks.
    if user && Devise.secure_compare(user.authentication_token, params[:user_token])
      user = User.find_by_email(user_email)
      return true
    else
      render :json => '{"success" : "false", "error" : "authentication problem"}'
    end
  end

end