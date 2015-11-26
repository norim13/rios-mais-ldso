class ReportImage < ActiveRecord::Base
	mount_uploader :image, ImageUploader
	belongs_to :report
	validates :report, presence: true
end
