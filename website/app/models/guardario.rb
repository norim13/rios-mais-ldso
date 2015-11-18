class Guardario < ActiveRecord::Base
  mount_uploaders :images, ImageUploader
  validates :rio, :local, :presence => true
end
