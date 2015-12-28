class RoutesController < ApplicationController
  before_action :set_route, only: [:show, :edit, :update, :destroy]
  before_action :custom_auth!, only: [:create,:new, :edit, :update, :destroy]

  def custom_auth!
    if authenticate_user!
      @permissoes = current_user.permissoes
      if current_user.permissoes > 4
        return true
      else
        render 'common/noaccess'
      end
    else
      render 'common/noaccess'
    end
  end

  # GET /routes
  # GET /routes.json
  def index
    @routes = Route.paginate(:page => params[:page], :per_page => 6).order('created_at')
    @images = []
    @routes.each do |route|
      image = nil
      rps = route.rota_points
      rps.each do |rp|
        rpi = rp.rota_point_images.take
        if !rpi.nil?
          image = rpi
          break
        end
      end
      @images.push(image)
    end
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
      render :json => {:success => "true", :points => @route.rota_points}
    else
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
