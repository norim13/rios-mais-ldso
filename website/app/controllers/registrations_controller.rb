class RegistrationsController < Devise::RegistrationsController
  private

  def sign_up_params
    params[:user][:permissoes] = 1
    params.require(:user).permit(:nome, :email, :password, :password_confirmation, :current_password, :telef, :distrito_id, :concelho_id, :habilitacoes, :profissao, :formacao, :permissoes)
  end

  def account_update_params
    params.require(:user).permit(:nome, :email, :password, :password_confirmation, :current_password, :telef, :distrito_id, :concelho_id, :habilitacoes, :profissao, :formacao)
  end

end
