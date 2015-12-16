class Trip < ActiveRecord::Base
  belongs_to :user
  has_many :trip_points, dependent: :destroy
end
