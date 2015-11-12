class ConcelhoController < ApplicationController

  def getConcelhosFromDistrito
    @distrito = Distrito.find(params[:id])
    @concelhos = @distrito.concelhos
    render :json => @concelhos
  end

end
