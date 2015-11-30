require 'spec_helper'

describe "reabilitacaos/index" do
  before(:each) do
    assign(:reabilitacaos, [
      stub_model(Reabilitacao),
      stub_model(Reabilitacao)
    ])
  end

  it "renders a list of reabilitacaos" do
    render
    # Run the generator again with the --webrat flag if you want to use webrat matchers
  end
end
