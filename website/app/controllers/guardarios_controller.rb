class GuardariosController < ApplicationController
  before_filter :authenticate_user!, only: [:new, :destroy, :getMine]
  before_action :set_guardario, only: [:show, :destroy]

  # GET /guardarios
  # GET /guardarios.json
  # display last
  def index
    @guardarios_img = GuardarioImage.last(9).reverse
  end

  # Vai buscar à base de dados apenas os rios do utilizador loggado
  def getMine
    @guardarios = current_user.guardarios
    render 'myguardarios'
  end

  # GET /guardarios/1
  # GET /guardarios/1.json
  def show
    @imgs = @guardario.guardario_images
  end

  # GET /guardarios/new
  def new
    @guardario = Guardario.new
  end

  # GET /guardarios/1/edit
  def edit
  end

  # POST /guardarios
  # POST /guardarios.json
  def create
    @guardario = Guardario.new(guardario_params)
    @guardario.user_id = current_user.id

    respond_to do |format|
      if @guardario.save
        if params[:images]
          params[:images].each { |image|
            GuardarioImage.create(image: image, guardario_id: @guardario.id)
          }
        end
        format.html { redirect_to @guardario, notice: 'O seu avistamento foi reportado com sucesso.' }
        format.json { render :show, status: :created, location: @guardario }
      else
        format.html { render :new }
        format.json { render json: @guardario.errors, status: :unprocessable_entity }
      end
    end
  end

  # PATCH/PUT /guardarios/1
  # PATCH/PUT /guardarios/1.json
  def update
    respond_to do |format|
      if @guardario.update(guardario_params)
        if params[:images]
          params[:images].each { |image|
            GuardarioImage.create(image: image, guardario_id: @guardario.id)
          }
        end
        format.html { redirect_to @guardario, notice: 'Guardario was successfully updated.' }
        format.json { render :show, status: :ok, location: @guardario }
      else
        format.html { render :edit }
        format.json { render json: @guardario.errors, status: :unprocessable_entity }
      end
    end
  end

  # DELETE /guardarios/1
  # DELETE /guardarios/1.json
  def destroy
    @guardario.destroy
    respond_to do |format|
      format.html { redirect_to guardarios_url, notice: 'Guardario was successfully destroyed.' }
      format.json { head :no_content }
    end
  end

  private

  def set_guardario
    @guardario = Guardario.find(params[:id])
  end

  def guardario_params
    params.require(:guardario).permit(:rio, :nomeRio, :lat, :lon, :local, :voar, :cantar, :parado, :beber, :cacar, :cuidarcrias, :alimentar, :outro, {images: []})
  end
end
