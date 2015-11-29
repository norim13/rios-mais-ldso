class GuardarioImageController < ApplicationController
	def new
		@img = GuardarioImage.new
	end

	def create
		@img = GuardarioImage.new(img_params)

		if @img.save
			render :json => @img
		else
			render :json => @img.errors
		end
	end

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
