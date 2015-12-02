class Route < ActiveRecord::Base
  has_many :rota_points, dependent: :destroy

  #accepts_nested_attributes_for :rota_points
end
