class Guardario < ActiveRecord::Base
  belongs_to :user

  mount_uploaders :images, ImageUploader
  validates :rio, :nomeRio, :local, :presence => true
end
