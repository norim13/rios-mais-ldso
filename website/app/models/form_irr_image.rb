class FormIrrImage < ActiveRecord::Base
	mount_uploader :image, ImageUploader
	belongs_to :form_irr
	validates :form_irr, presence: true
end
