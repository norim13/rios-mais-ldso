class Distrito < ActiveRecord::Base
  has_many :concelhos, dependent: :destroy
end
