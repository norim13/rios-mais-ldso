class Api::V2::UsersController < ApplicationController

	before_filter :authenticate_user_from_token!

	def getUser
		user_email = params[:user_email].presence
		user       = user_email && User.find_by_email(user_email)

		render :json => user
	end

	def update
		user_email = params[:user_email].presence
		user       = user_email && User.find_by_email(user_email)

	    if !user.valid_password?(params[:user][:current_password])
			render :json => '{"success" : "false", "error" : "error updating user, wrong password"}'
		else 
			user.nome = params[:user][:nome]
			user.email = params[:user][:email]

			if(params[:user][:password] != "" and params[:user][:password] == params[:user][:password_confirmation]) 
				user.password = params[:user][:password]
			end

			user.telef = params[:user][:telef]
			user.habilitacoes = params[:user][:habilitacoes]
			user.profissao = params[:user][:profissao]
			user.formacao = params[:user][:formacao]
			
			if user.save
				render :json => user
			else
				render :json => '{"success" : "false", "error" : "error updating user"}'
			end
		end
	end

	def destroy
		user_email = params[:user_email].presence
		user       = user_email && User.find_by_email(user_email)

		if user.destroy
			render :json => '{"success" : "true"}'
		else
			render :json => '{"success" : "false", "error" : "error deleting user"}'
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
	
	private
	def user_params
    	params.require(:user).permit(:nome, :email, :password, :password_confirmation, :current_password, :telef, :distrito_id, :concelho_id, :habilitacoes, :profissao, :formacao)
  	end
end