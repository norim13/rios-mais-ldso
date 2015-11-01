class FormIrrsController < ApplicationController
  def new
  	@form_irr = FormIrr.new
  end

  def index
  	@form_irrs = FormIrr.all
  end

  def edit
  end

  def create
  end

  def show
  	FormIrrs.find(params[:id])
  end

  #private
  #def form_irr_params
    #params.require(:form_irr).permit(:title, :text)
  #end
end
