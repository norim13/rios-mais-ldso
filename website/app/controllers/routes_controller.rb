class RoutesController < ApplicationController
  before_action :set_route, only: [:show, :edit, :update, :destroy]

  # GET /routes
  # GET /routes.json
  def index
    @routes = Route.all
  end

  # GET /routes/1
  # GET /routes/1.json
  def show
    @points = @route.rota_points
  end

  # GET /routes/new
  def new
    @route = Route.new
    @edit = false
  end

  # GET /routes/1/edit
  def edit
    @edit = true
    @points = @route.rota_points
  end

  # POST /routes
  # POST /routes.json
  def create
    @route = Route.new(route_params)
    success = true

    if @route.save
      params[:rota_points].each do |k, p|
        # render :json => p[:ordem]
        # break
        # if RotaPoint.create(p)
        if RotaPoint.create(ordem: p[:ordem], lat: p[:lat], lon: p[:lon], nome: p[:nome], descricao: p[:descricao], route_id: @route.id)
        else
          @route.delete
          success = false
          break
        end
      end
    else
      success = false
    end

    if success
      #format.html { redirect_to @route, notice: 'Route was successfully created.' }
      #format.json { render :show, status: :created, location: @route }
      render :json => '{"success" : "true"}'
    else
      #format.html { render :new }
      #format.json { render json: @route.errors, status: :unprocessable_entity }
      render :json => '{"success" : "false", "error" : "problem"}'
    end

  end

  # PATCH/PUT /routes/1
  # PATCH/PUT /routes/1.json
  def update
    success = true

    if @route.update(route_params)
      #remove all existing points
      RotaPoint.destroy_all("route_id = #{@route.id}")

      #add points from POST
      params[:rota_points].each do |k, p|
        # render :json => p[:ordem]
        # break
        # if RotaPoint.create(p)
        if RotaPoint.create(ordem: p[:ordem], lat: p[:lat], lon: p[:lon], nome: p[:nome], descricao: p[:descricao], route_id: @route.id)
        else
          @route.delete
          success = false
          break
        end
      end
    else #update didn't success
      success = false
    end

    if success
      render :json => {:success => "true", :points => @route.rota_points}
    else
      render :json => '{"success" : "false", "error" : "problem"}'
    end

    # respond_to do |format|
    #   if @route.update(route_params)
    #     format.html { redirect_to @route, notice: 'A Rota foi actualizada com sucesso.' }
    #     format.json { render :show, status: :ok, location: @route }
    #   else
    #     format.html { render :edit }
    #     format.json { render json: @route.errors, status: :unprocessable_entity }
    #   end
    # end
  end

  # DELETE /routes/1
  # DELETE /routes/1.json
  def destroy
    @route.destroy
    respond_to do |format|
      format.html { redirect_to routes_url, notice: 'A Rota foi apagada com sucesso.' }
      format.json { head :no_content }
    end
  end

  private
    # Use callbacks to share common setup or constraints between actions.
    def set_route
      @route = Route.find(params[:id])
    end

    # Never trust parameters from the scary internet, only allow the white list through.
    def route_params
      params.require(:route).permit(:nome, :descricao, :zona, :publicada)
    end
end
