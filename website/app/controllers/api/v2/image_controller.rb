class Api::V2::ImageController < ApplicationController
  before_filter :authenticate_user_from_token!

  def create
    user_email = params[:user_email].presence
    user       = user_email && User.find_by_email(user_email)

    controller = params[:controller]
    
    case controller
      when "form_irr"
        if params[:image]
          FormIrrImage.create(image: params[:image], form_irr_id: params[:form_irr_id])

          render :json => '{"success" : "true"}'
        else
          render :json => '{"success" : "false", "error" : "problem saving form irr image"}'
        end
      when "guardario"
        if params[:image]
          GuardarioImage.create(image: params[:image], guardario_id: params[:guardario_id])

          render :json => '{"success" : "true"}'
        else
          render :json => '{"success" : "false", "error" : "problem saving guardario image"}'
        end
      when "report"
        if params[:image]
          ReportImage.create(image: params[:image], report_id: params[:report_id])

          render :json => '{"success" : "true"}'
        else
          render :json => '{"success" : "false", "error" : "problem saving report image"}'
        end
      else
        render :json => '{"success" : "false", "error" : "error in controller"}'
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