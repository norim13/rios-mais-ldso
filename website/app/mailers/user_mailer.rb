class UserMailer < ApplicationMailer

  default from: 'no-reply@rios.pt'

  def irr_email(user)
    @user = user
    @url  = 'http://example.com/login'
    mail(to: @user.email, subject: 'Formulário IRR submetido')
  end

  def reports_email(report)
    @report = report
    @user_reporter = @report.user

    admins = User.where("permissoes > ?", 3)
    admins_mails = []

    admins.each do |admin|
      admins_mails.push(admin.email)
    end

    mail(bcc: admins_mails, subject: 'SOS rios+ notificação')

  end

  def irr_email(form_irr)
    @form_irr = form_irr
    @user = form_irr.user

    admins = User.where("permissoes > ?", 5)
    admins_mails = []

    admins.each do |admin|
      admins_mails.push(admin.email)
    end

    mail(bcc: admins_mails, subject: 'Formulário IRR notificação')
  end


end
