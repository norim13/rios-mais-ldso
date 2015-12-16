require 'spec_helper'

describe "TripPoints" do
  describe "GET /trip_points" do
    it "works! (now write some real specs)" do
      # Run the generator again with the --webrat flag if you want to use webrat methods/matchers
      get trip_points_path
      response.status.should be(200)
    end
  end
end
