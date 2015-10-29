class RegisterController < ApplicationController

  def register
    @utilizador = Utilizador.new
  end

end
