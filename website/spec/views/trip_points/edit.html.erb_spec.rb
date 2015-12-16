require 'spec_helper'

describe "trip_points/edit" do
  before(:each) do
    @trip_point = assign(:trip_point, stub_model(TripPoint))
  end

  it "renders the edit trip_point form" do
    render

    # Run the generator again with the --webrat flag if you want to use webrat matchers
    assert_select "form[action=?][method=?]", trip_point_path(@trip_point), "post" do
    end
  end
end
