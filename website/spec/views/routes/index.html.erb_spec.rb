require 'spec_helper'

describe "routes/index" do
  before(:each) do
    assign(:routes, [
      stub_model(Route),
      stub_model(Route)
    ])
  end

  it "renders a list of routes" do
    render
    # Run the generator again with the --webrat flag if you want to use webrat matchers
  end
end
