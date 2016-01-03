class Api::V2::ReportsController < ApplicationController
	before_filter :authenticate_user_from_token!

	# POST /reports
	# POST /reports.json
	def create

		user_email = params[:user_email].presence
		user = user_email && User.find_by_email(user_email)

		report = Report.new(report_params)
		report.user_id = user.id

		if report.save
			render :json => {:success => true, :report_id => report.id }
		else
			render :json => '{"success" : "false", "error" : "problem saving report"}'
		end
	end

	# GET /reports/1
	# GET /reports/1.json
	def get
		report = Report.find(params[:id])
		if !report.nil?
			render :json => {:success => "true", :report => report, :images => report.report_images}
		else
			render :json => {:success => "false"}
		end
	end

	# GET /reports
	# GET /reports.json
	def index
		reports = Report.all
		if !reports.nil?
			render :json => {:success => "true", :reports => reports}
		else
			render :json => {:success => "false"}
		end
	end

	def authenticate_user_from_token!
		user_email = params[:user_email].presence
		user = user_email && User.find_by_email(user_email)

		# Notice how we use Devise.secure_compare to compare the token
		# in the database with the token given in the params, mitigating
		# timing attacks.
		if user && Devise.secure_compare(user.authentication_token, params[:user_token])
			user = User.find_by_email(user_email)
			return true
		else
			render :json => '{"success" : "false", "error" : "authentication problem"}'
		end
	end

	private
	def report_params
		params.require(:report).permit(:rio, :nome_rio, :categoria, :motivo, :descricao, :local, :lat, :lon)
	end
end
