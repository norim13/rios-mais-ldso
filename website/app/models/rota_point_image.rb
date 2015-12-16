class RotaPointImage < ActiveRecord::Base
	mount_uploader :image, ImageUploader
	belongs_to :rota_point
	validates :rota_point, presence: true
end
