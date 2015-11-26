class ReportImageController < ApplicationController
	def new
		@img = ReportImage.new
	end

	def create
		@img = ReportImage.new(img_params)

		if @img.save
			render :json => @img
		else
			render :json => @img.errors
		end
	end

	def destroy
		@img = ReportImage.find(params[:id])
		@img.destroy

		render :json => @img
	end

	private
	def img_params
		params.require(:report_image).permit(:image,:report_id)
	end
end

