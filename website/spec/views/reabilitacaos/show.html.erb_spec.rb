require 'spec_helper'

describe "reabilitacaos/show" do
  before(:each) do
    @reabilitacao = assign(:reabilitacao, stub_model(Reabilitacao))
  end

  it "renders attributes in <p>" do
    render
    # Run the generator again with the --webrat flag if you want to use webrat matchers
  end
end
