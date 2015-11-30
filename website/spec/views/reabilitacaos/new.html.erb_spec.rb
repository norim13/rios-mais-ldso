require 'spec_helper'

describe "reabilitacaos/new" do
  before(:each) do
    assign(:reabilitacao, stub_model(Reabilitacao).as_new_record)
  end

  it "renders new reabilitacao form" do
    render

    # Run the generator again with the --webrat flag if you want to use webrat matchers
    assert_select "form[action=?][method=?]", reabilitacaos_path, "post" do
    end
  end
end
