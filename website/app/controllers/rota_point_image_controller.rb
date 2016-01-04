class RotaPointImageController < ApplicationController
	def new
		@img = RotaPointImage.new
	end

	# Cria uma imagem na base de dados
	def create
		@img = RotaPointImage.new(img_params)

		if @img.save
			render :json => @img
		else
			render :json => @img.errors
		end
	end

	# Apaga uma imagem da base de dados
	def destroy
		@img = RotaPointImage.find(params[:id])
		@img.destroy

		render :json => @img
	end

	private
	def img_params
		params.require(:rota_point_image).permit(:image,:rota_point_id)
	end
end
