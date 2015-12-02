class RotaPointController < ApplicationController

  def create
    @rota_point = RotaPoint.new(rota_point_params)

    if @rota_point.save
      return true
    else
      return false
    end
  end

  # Never trust parameters from the scary internet, only allow the white list through.
  def rota_point_params
    params.require(:point).permit(:nome, :descricao, :lat, :lon, :ordem, :route_id)
  end

end
