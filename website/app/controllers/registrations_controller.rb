class RegistrationsController < Devise::RegistrationsController
  private

  def sign_up_params
    params.require(:user).permit(:nome, :email, :password, :password_confirmation, :telef, :distrito_id, :concelho_id, :habilitacoes, :profissao,:formacao)
  end

  def account_update_params
    params.require(:user).permit(:nome, :email, :password, :password_confirmation, :telef, :distrito_id, :concelho_id, :habilitacoes, :profissao,:formacao)
  end

end
