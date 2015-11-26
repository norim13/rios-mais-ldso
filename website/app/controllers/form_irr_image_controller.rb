class FormIrrImageController < ApplicationController
	def new
		@img = FormIrrImage.new
	end

	def create
		@img = FormIrrImage.new(img_params)

		if @img.save
			render :json => @img
		else
			render :json => @img.errors
		end
	end

	def destroy
		@img = FormIrrImage.find(params[:id])
		@img.destroy

		render :json => @img
	end
	
	private
	def img_params
		params.require(:form_irr_image).permit(:image,:form_irr_id)
	end
end
