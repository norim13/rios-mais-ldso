require 'spec_helper'

describe "reabilitacaos/edit" do
  before(:each) do
    @reabilitacao = assign(:reabilitacao, stub_model(Reabilitacao))
  end

  it "renders the edit reabilitacao form" do
    render

    # Run the generator again with the --webrat flag if you want to use webrat matchers
    assert_select "form[action=?][method=?]", reabilitacao_path(@reabilitacao), "post" do
    end
  end
end
