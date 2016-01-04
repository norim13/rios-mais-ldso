class ReabilitacaosController < ApplicationController
  before_action :set_reabilitacao, only: [:show, :edit, :update, :destroy]

  # GET /reabilitacaos
  # GET /reabilitacaos.json
  def index
    @reabilitacaos = Reabilitacao.all
    @percentagens = []

    @reabilitacaos.each do |reabilitacao|
      @percentagens << calculateProgress(reabilitacao.id)
    end

  end

  def info
    render 'info'
  end

  # GET /reabilitacaos/new
  def new
    @reabilitacao = Reabilitacao.new
  end

  # GET /reabilitacaos/1/edit
  def edit
  end

  # Funcao para calcular o progresso de um plano de reabilitação
  def calculateProgress(id)
    @reabilitacao = Reabilitacao.find(id)
    @trues = []
    @falses = []

    #Percorre uma determinada reabilitação e vê (colocando num array) todos os atributos a "TRUE" e todos a "FALSE" noutro
    @reabilitacao.attributes.each do |attr_name, attr_value|
      if attr_value == true
        @trues.push(attr_name)
      elsif attr_value == false
        @falses.push(attr_name)
      else
      end
    end

    # Calcula a percentagem de passos marcados como feitos
    @valor = (@trues.size.to_f/(@trues.size + @falses.size).to_f)*100
    return @valor.round
  end

  # POST /reabilitacaos
  # POST /reabilitacaos.json
  def create
    @reabilitacao = Reabilitacao.new(reabilitacao_params)
    @reabilitacao.user_id = current_user.id

    respond_to do |format|
      if @reabilitacao.save
        format.html { redirect_to action: "index", notice: 'Plano de reabilitação iniciado.' }
        format.json { render :index, status: :created, location: @reabilitacao }
      else
        format.html { render :new }
        format.json { render json: @reabilitacao.errors, status: :unprocessable_entity }
      end
    end
  end

  # PATCH/PUT /reabilitacaos/1
  # PATCH/PUT /reabilitacaos/1.json
  def update
    respond_to do |format|
      if @reabilitacao.update(reabilitacao_params)
        format.html { redirect_to action: "index", notice: 'Plano de reabilitação atualizado.' }
        format.json { render :index, status: :ok, location: @reabilitacao }
      else
        format.html { render :edit }
        format.json { render json: @reabilitacao.errors, status: :unprocessable_entity }
      end
    end
  end

  # DELETE /reabilitacaos/1
  # DELETE /reabilitacaos/1.json
  def destroy
    @reabilitacao.destroy
    respond_to do |format|
      format.html { redirect_to reabilitacaos_url, notice: 'Plano de reabilitação removido.' }
      format.json { head :no_content }
    end
  end

  private
    def set_reabilitacao
      @reabilitacao = Reabilitacao.find(params[:id])
    end

    def reabilitacao_params
      params.require(:reabilitacao).permit(:definicao, :diagnostico, :prioritizacao, :objectivos, :solucoes, :elaboracao, :implementacao, :monitorizacao, :beber, :cacar,
      :avaliacao, :correcao, :ppublica, :parcerias, :legislacao, :lei_agua, :directiva_agua, :directiva_inundacoes, :custo, :cronograma, :formacao, :emergencia, :reabilitacao, :revisao)
    end
end
