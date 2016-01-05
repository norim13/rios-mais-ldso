
class LimpezaController < ApplicationController
  before_filter :authenticate_user!, except: :info


  # Mostrar a página de problemas de limpezas, com opções por cada categoria (vindas da base de dados)
  def new
    @limpeza = Limpeza.select(:categoria,:categoria_id).group(:categoria,:categoria_id).order(:categoria_id)

    @allOptions = []

    @limpeza.each do |category|
      @optionsOfCategory = Limpeza.where(categoria: category.categoria)
      @size = @optionsOfCategory.count
      @allOptions << @optionsOfCategory
    end
  end

  # Mostrar uma página geral de informações sobre limpeza de rios
  def info
    render 'info'
  end

  # GET /limpezas/1
  # GET /limpezas/1.json
  def show
      @limpeza = LogLimpeza.find(params[:id])
      @user_reporter = User.find_by_id(@limpeza.user_id)
      @categorias = Limpeza.select(:categoria).group(:categoria,:categoria_id).order(:categoria_id)
  end

  # DELETE /limpezas/1
  # DELETE /limpezas/1.json
  def destroy
    @limpeza = LogLimpeza.find(params[:id])
    @limpeza.destroy
    respond_to do |format|
      format.html { redirect_to index_limpezas_url, notice: 'Limpeza removida.' }
      format.json { head :no_content }
    end
  end

  # Vai à base de dados buscar a sugestão/resposta para o problema com o id enviado por parametro. Esta função é utilizada
  # no pedido ajax
  def getRespostas
      @resposta = Limpeza.find(params[:id])
      render :json => @resposta
  end

  # Cria na base de dados uma entrada com os problemas submetidos pelo utilizador (para efeitos meramente estatísticos)
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
