class TripImage < ActiveRecord::Base
	mount_uploader :image, ImageUploader
	belongs_to :trip_point
	validates :trip_point, presence: true
end
