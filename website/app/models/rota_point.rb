class RotaPoint < ActiveRecord::Base
  belongs_to :route

  has_many :rota_point_images, dependent: :destroy
  accepts_nested_attributes_for :rota_point_images
end
