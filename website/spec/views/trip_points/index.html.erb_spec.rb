require 'spec_helper'

describe "trip_points/index" do
  before(:each) do
    assign(:trip_points, [
      stub_model(TripPoint),
      stub_model(TripPoint)
    ])
  end

  it "renders a list of trip_points" do
    render
    # Run the generator again with the --webrat flag if you want to use webrat matchers
  end
end
