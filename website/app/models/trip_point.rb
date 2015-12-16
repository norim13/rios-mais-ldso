class TripPoint < ActiveRecord::Base
  belongs_to :trip

  has_many :trip_images, dependent: :destroy
  accepts_nested_attributes_for :trip_images
end
