class Report < ActiveRecord::Base
  belongs_to :user
  mount_uploaders :images, ImageUploader

  validates :rio, :categoria, :motivo, :descricao, :presence => true

  validates :descricao, length: {maximum: 500}
end
