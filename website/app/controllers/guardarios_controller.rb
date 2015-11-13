class GuardariosController < ApplicationController
  before_action :set_guardario, only: [:show, :edit, :update, :destroy]

  # GET /guardarios
  # GET /guardarios.json
  def index
    @guardarios = Guardario.all
  end

  # GET /guardarios/1
  # GET /guardarios/1.json
  def show
     @img_path = "/uploads/guardario/images/#{params[:id]}/"
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
        format.html { redirect_to @guardario, notice: 'Guardario was successfully created.' }
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

    # Use callbacks to share common setup or constraints between actions.
    def set_guardario
      @guardario = Guardario.find(params[:id])
    end

    # Never trust parameters from the scary internet, only allow the white list through.
    def guardario_params
      params.require(:guardario).permit(:rio,:local,:voar,:cantar,:parado,:beber,:cacar,:cuidarcrias,:alimentar,:outro,{images: []})
    end
end
