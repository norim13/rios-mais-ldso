require "spec_helper"

describe TripPointsController do
  describe "routing" do

    it "routes to #index" do
      get("/trip_points").should route_to("trip_points#index")
    end

    it "routes to #new" do
      get("/trip_points/new").should route_to("trip_points#new")
    end

    it "routes to #show" do
      get("/trip_points/1").should route_to("trip_points#show", :id => "1")
    end

    it "routes to #edit" do
      get("/trip_points/1/edit").should route_to("trip_points#edit", :id => "1")
    end

    it "routes to #create" do
      post("/trip_points").should route_to("trip_points#create")
    end

    it "routes to #update" do
      put("/trip_points/1").should route_to("trip_points#update", :id => "1")
    end

    it "routes to #destroy" do
      delete("/trip_points/1").should route_to("trip_points#destroy", :id => "1")
    end

  end
end
