require 'spec_helper'

describe "trip_points/new" do
  before(:each) do
    assign(:trip_point, stub_model(TripPoint).as_new_record)
  end

  it "renders new trip_point form" do
    render

    # Run the generator again with the --webrat flag if you want to use webrat matchers
    assert_select "form[action=?][method=?]", trip_points_path, "post" do
    end
  end
end
