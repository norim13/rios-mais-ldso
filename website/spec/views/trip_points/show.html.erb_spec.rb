require 'spec_helper'

describe "trip_points/show" do
  before(:each) do
    @trip_point = assign(:trip_point, stub_model(TripPoint))
  end

  it "renders attributes in <p>" do
    render
    # Run the generator again with the --webrat flag if you want to use webrat matchers
  end
end
