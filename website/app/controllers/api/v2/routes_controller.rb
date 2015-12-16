class Api::V2::RoutesController < ApplicationController
  # GET /routes
  # GET /routes.json
  def index
    render :json => Route.all
  end

  # GET /routes/1
  # GET /routes/1.json
  def show
    route = Route.find(params[:id])

    if !route.nil?
      route.rota_points.each do |rp|
        imgs.push(rp.rota_point_images)
      end
      render :json => {:success => "true", :route => route, :points => route.rota_points, :images => imgs}
    else
      render :json => {:success => "false"}
    end
  end
end
