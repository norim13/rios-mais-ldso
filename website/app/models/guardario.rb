class Guardario < ActiveRecord::Base
  mount_uploaders :images, ImageUploader
end
