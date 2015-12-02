require 'spec_helper'

describe ParticipacaoPublicaController do

  describe "GET 'info'" do
    it "returns http success" do
      get 'info'
      response.should be_success
    end
  end

end
