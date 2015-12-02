class ConcelhoController < ApplicationController

  def getDistritos
    @distritos = Distrito.all
    render :json => @distritos
  end

  def getConcelhosFromDistrito
    @distrito = Distrito.find(params[:id])
    @concelhos = @distrito.concelhos
    render :json => @concelhos
  end

end
