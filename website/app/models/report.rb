class Report < ActiveRecord::Base
  belongs_to :user

  has_many :report_images, dependent: :destroy
  accepts_nested_attributes_for :report_images

  validates :rio, :nome_rio, :lat, :lon, :categoria, :motivo, :descricao, :presence => true

  validates :descricao, length: {maximum: 500}
end
