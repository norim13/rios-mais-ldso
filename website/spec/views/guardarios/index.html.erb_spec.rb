require 'spec_helper'

describe "guardarios/index" do
  before(:each) do
    assign(:guardarios, [
      stub_model(Guardario,
        :rio => "Rio",
        :user_id => ""
      ),
      stub_model(Guardario,
        :rio => "Rio",
        :user_id => ""
      )
    ])
  end

  it "renders a list of guardarios" do
    render
    # Run the generator again with the --webrat flag if you want to use webrat matchers
    assert_select "tr>td", :text => "Rio".to_s, :count => 2
    assert_select "tr>td", :text => "".to_s, :count => 2
  end
end
