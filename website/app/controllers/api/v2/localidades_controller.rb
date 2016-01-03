class Api::V2::LocalidadesController < ApplicationController

  def getDistritos
    render :json => Distrito.all
  end

  def getConcelhos
    render :json => Distrito.find(params[:distrito]).concelhos
  end

end