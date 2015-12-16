class Api::V2::GuardariosController < ApplicationController

	before_filter :authenticate_user_from_token!

	def create
		user_email = params[:user_email].presence
		user       = user_email && User.find_by_email(user_email)

		@guardario = Guardario.new(guardario_params)
		@guardario.user_id = user.id

		if @guardario.save
			render :json => '{"success" : "true"}'
		else
			render :json => '{"success" : "false", "error" : "problem saving guardario"}'
		end
	end

	def get
		@guardario = Guardario.find(params[:id])
		if !@guardario.nil?
			render :json => {:success => "true", :guardario => @guardario, :images => @guardario.guardario_images}
		else
			render :json => {:success => "false"}
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
	def guardario_params
		params.require(:guardario).permit(:rio, :nomeRio, :lat, :lon, :local, :voar, :cantar, :parado, :beber, :cacar, :cuidarcrias, :alimentar, :outro, {images: []})
	end
end