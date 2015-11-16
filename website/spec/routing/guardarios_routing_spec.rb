require "spec_helper"

describe GuardariosController do
  describe "routing" do

    it "routes to #index" do
      get("/guardarios").should route_to("guardarios#index")
    end

    it "routes to #new" do
      get("/guardarios/new").should route_to("guardarios#new")
    end

    it "routes to #show" do
      get("/guardarios/1").should route_to("guardarios#show", :id => "1")
    end

    it "routes to #edit" do
      get("/guardarios/1/edit").should route_to("guardarios#edit", :id => "1")
    end

    it "routes to #create" do
      post("/guardarios").should route_to("guardarios#create")
    end

    it "routes to #update" do
      put("/guardarios/1").should route_to("guardarios#update", :id => "1")
    end

    it "routes to #destroy" do
      delete("/guardarios/1").should route_to("guardarios#destroy", :id => "1")
    end

  end
end
