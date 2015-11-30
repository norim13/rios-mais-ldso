require "spec_helper"

describe ReabilitacaosController do
  describe "routing" do

    it "routes to #index" do
      get("/reabilitacaos").should route_to("reabilitacaos#index")
    end

    it "routes to #new" do
      get("/reabilitacaos/new").should route_to("reabilitacaos#new")
    end

    it "routes to #show" do
      get("/reabilitacaos/1").should route_to("reabilitacaos#show", :id => "1")
    end

    it "routes to #edit" do
      get("/reabilitacaos/1/edit").should route_to("reabilitacaos#edit", :id => "1")
    end

    it "routes to #create" do
      post("/reabilitacaos").should route_to("reabilitacaos#create")
    end

    it "routes to #update" do
      put("/reabilitacaos/1").should route_to("reabilitacaos#update", :id => "1")
    end

    it "routes to #destroy" do
      delete("/reabilitacaos/1").should route_to("reabilitacaos#destroy", :id => "1")
    end

  end
end
