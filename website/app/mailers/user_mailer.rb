class UserMailer < ApplicationMailer

  default from: 'no-reply@rios.pt'

  def irr_email(user)
    @user = user
    @url  = 'http://example.com/login'
    mail(to: @user.email, subject: 'Formulário IRR submetido')
  end

  def guardarios_email(guardarios)
    @guardario = guardarios

    admins = User.where("permissoes > ?", 5)
    admins_mails = []

    admins.each do |admin|
      admins_mails.push(admin.email)
    end

    mail(bcc: admins_mails, subject: 'Avistamento de um guardarios')

  end

end
