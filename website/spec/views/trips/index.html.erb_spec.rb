require 'spec_helper'

describe "trips/index" do
  before(:each) do
    assign(:trips, [
      stub_model(Trip),
      stub_model(Trip)
    ])
  end

  it "renders a list of trips" do
    render
    # Run the generator again with the --webrat flag if you want to use webrat matchers
  end
end
