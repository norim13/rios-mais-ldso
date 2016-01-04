class GuardarioImageController < ApplicationController
	def new
		@img = GuardarioImage.new
	end

	# Cria uma imagem na base de dados
	def create
		@img = GuardarioImage.new(img_params)

		if @img.save
			render :json => @img
		else
			render :json => @img.errors
		end
	end

	# Apaga uma imagem da base de dados
	def destroy
		img = GuardarioImage.find(params[:id])
		img.destroy

		render :json => img
	end

	private
	def img_params
		params.require(:guardario_image).permit(:image,:guardario_id)
	end
end
