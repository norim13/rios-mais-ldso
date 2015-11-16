require 'spec_helper'

describe "guardarios/show" do
  before(:each) do
    @guardario = assign(:guardario, stub_model(Guardario,
      :rio => "Rio",
      :user_id => ""
    ))
  end

  it "renders attributes in <p>" do
    render
    # Run the generator again with the --webrat flag if you want to use webrat matchers
    rendered.should match(/Rio/)
    rendered.should match(//)
  end
end
