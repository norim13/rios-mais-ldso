class Api::V1::RegistrationsController < Devise::RegistrationsController

  respond_to :json

  def create

    user = User.new(user_params)
    user.permissoes = 1
    if user.save
      render :json=> user.as_json(:auth_token=>user.authentication_token, :email=>user.email, :permissoes=>user.permissoes), :status=>201
      return
    else
      warden.custom_failure!
      render :json=> user.errors, :status=>422
    end
  end

  private

  def user_params
    params.require(:user).permit(:nome, :email, :password, :password_confirmation, :current_password, :telef, :distrito_id, :concelho_id, :habilitacoes, :profissao, :formacao)
  end
end