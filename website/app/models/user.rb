class User < ActiveRecord::Base
  EMAIL_REGEX = /\A[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,4}\z/i
  validates :nome, :presence => true,:length => { :in => 3..20 }
  validates :email, :presence => true, :uniqueness => true, :format => EMAIL_REGEX
  has_secure_password validations: false # This is the key to the solution
  validates :password_confirmation, presence: true
  validates :password, presence: true, :confirmation => true, length: { minimum: 6 } # Or an length you want
end
