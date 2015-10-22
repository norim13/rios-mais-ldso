require 'test_helper'

class ProfileControllerTest < ActionController::TestCase
  test "should get display" do
    get :display
    assert_response :success
  end

end
