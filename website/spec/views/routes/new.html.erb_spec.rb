require 'spec_helper'

describe "routes/new" do
  before(:each) do
    assign(:route, stub_model(Route).as_new_record)
  end

  it "renders new route form" do
    render

    # Run the generator again with the --webrat flag if you want to use webrat matchers
    assert_select "form[action=?][method=?]", routes_path, "post" do
    end
  end
end
