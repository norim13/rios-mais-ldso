ActionMailer::Base.delivery_method = :smtp

ActionMailer::Base.smtp_settings = {
    :address => 'cpanel05.dnscpanel.com',
    :port => '587',
    :authentication => :login,
    :user_name => 'no-reply@rios.pt',
    :password => 'rios+2015',
    :domain => 'rios.pt',
    :enable_starttls_auto => true
}