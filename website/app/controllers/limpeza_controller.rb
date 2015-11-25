class LimpezaController < ApplicationController
  before_filter :authenticate_user!

  def show
    @limpeza = Limpeza.select(:categoria,:categoria_id).group(:categoria,:categoria_id).order(:categoria_id)

    @allOptions = []

    @limpeza.each do |category|
      @optionsOfCategory = Limpeza.where(categoria: category.categoria)
      @size = @optionsOfCategory.count
      @allOptions << @optionsOfCategory
    end

    def getRespostas
      @resposta = Limpeza.find(params[:id])
      render :json => @resposta
    end

  end

end
