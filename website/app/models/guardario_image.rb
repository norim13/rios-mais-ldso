class GuardarioImage < ActiveRecord::Base
		mount_uploader :image, ImageUploader
		belongs_to :guardario
		validates :guardario, presence: true
end
