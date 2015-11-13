require 'spec_helper'

describe "guardarios/new" do
  before(:each) do
    assign(:guardario, stub_model(Guardario,
      :rio => "MyString",
      :user_id => ""
    ).as_new_record)
  end

  it "renders new guardario form" do
    render

    # Run the generator again with the --webrat flag if you want to use webrat matchers
    assert_select "form[action=?][method=?]", guardarios_path, "post" do
      assert_select "input#guardario_rio[name=?]", "guardario[rio]"
      assert_select "input#guardario_user_id[name=?]", "guardario[user_id]"
    end
  end
end
