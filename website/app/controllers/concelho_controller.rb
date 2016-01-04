class ConcelhoController < ApplicationController

  # Vai buscar todos os distritos que se encontram base de dados
  def getDistritos
    @distritos = Distrito.all
    render :json => @distritos
  end

  # Vai buscar os concelhos de um determinado distrito à base de dados
  def getConcelhosFromDistrito
    @distrito = Distrito.find(params[:id])
    @concelhos = @distrito.concelhos
    render :json => @concelhos
  end

end
