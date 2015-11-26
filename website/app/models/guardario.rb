class Guardario < ActiveRecord::Base
  belongs_to :user

  has_many :guardario_images, dependent: :destroy
  accepts_nested_attributes_for :guardario_images

  validates :rio, :nomeRio, :local, :presence => true
end
