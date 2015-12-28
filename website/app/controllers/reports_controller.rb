class ReportsController < ApplicationController
  before_filter :authenticate_user!, except: [:info]
  before_action :set_report, only: [:show, :destroy]

  def info
    render 'info'
  end

  # GET /reports
  # GET /reports.json
  def index
    @reports = current_user.reports
  end

  # GET /reports/1
  # GET /reports/1.json
  def show
    @user_reporter = User.find_by_id(@report.user_id)
    @imgs = @report.report_images
  end

  # GET /reports/new
  def new
    @report = Report.new
  end

  # POST /reports
  # POST /reports.json
  def create
    @report = Report.new(report_params)
    @report.user_id = current_user.id

    respond_to do |format|
      if @report.save
        if params[:images]
          params[:images].each { |image|
            ReportImage.create(image: image, report_id: @report.id)
          }
        end
        UserMailer.reports_email(@report).deliver_now
        format.html { redirect_to @report, notice: 'Denúncia enviada.' }
        format.json { render :show, status: :created, location: @report }
      else
        format.html { render :new }
        format.json { render json: @report.errors, status: :unprocessable_entity }
      end
    end
  end

  # DELETE /reports/1
  # DELETE /reports/1.json
  def destroy
    @report.destroy
    respond_to do |format|
      format.html { redirect_to reports_url, notice: 'Denúncia removida.' }
      format.json { head :no_content }
    end
  end

  private
  # Use callbacks to share common setup or constraints between actions.
  def set_report
    @report = Report.find(params[:id])
  end

  # Never trust parameters from the scary internet, only allow the white list through.
  def report_params
    params.require(:report).permit(:rio, :nome_rio, :lat, :lon, :categoria, :motivo, :descricao, :local, {images: []})
  end
end
