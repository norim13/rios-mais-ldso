class LimpezaController < ApplicationController
  before_filter :authenticate_user!, except: :info

  def show
    @limpeza = Limpeza.select(:categoria,:categoria_id).group(:categoria,:categoria_id).order(:categoria_id)

    @allOptions = []

    @limpeza.each do |category|
      @optionsOfCategory = Limpeza.where(categoria: category.categoria)
      @size = @optionsOfCategory.count
      @allOptions << @optionsOfCategory
    end
  end

  def info
    render 'info'
  end

  def getRespostas
      @resposta = Limpeza.find(params[:id])
      render :json => @resposta
  end

  def submitProblemas
    @loglimpeza = LogLimpeza.new(limpeza_params)
    @loglimpeza.user_id = current_user.id

    respond_to do |format|
      if @loglimpeza.save
        format.html { redirect_to :back }
        format.json { render :show, status: :created, location: @limpeza }
      else
        format.html { redirect_to :back}
        format.json { render json: @limpeza.errors, status: :unprocessable_entity }
      end
    end
  end

  def limpeza_params
    params.permit(:problema1,:problema2,:problema3 , :problema4, :problema5, :problema6, :problema7,
                                       :problema8, :problema9, :problema10, :problema11, :problema12, :problema13,
                                       :cheia_data, :cheia_origem, :cheia_perdas_monetarias, :cheia_destruicao)
  end


end
