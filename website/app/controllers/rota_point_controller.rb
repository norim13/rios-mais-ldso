class RotaPointController < ApplicationController

  # criar um ponto numa rota. Utilizado no create da Rota
  def create
    @rota_point = RotaPoint.new(rota_point_params)

    if @rota_point.save
      return @rota_point.id
    else
      return false
    end
  end

  def rota_point_params
    params.require(:point).permit(:nome, :descricao, :lat, :lon, :ordem, :route_id)
  end

end
