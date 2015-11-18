class ReportsController < ApplicationController
  before_filter :authenticate_user!
  before_action :set_report, only: [:show, :destroy]

  # GET /reports
  # GET /reports.json
  def index
    @reports = current_user.reports
  end

  # GET /reports/1
  # GET /reports/1.json
  def show
    @user_reporter = User.find_by_id(@report.user_id)
    @img_path = "/uploads/report/images/#{params[:id]}/"
  end

  # GET /reports/new
  def new
    @report = Report.new
    @img_path = "/uploads/report/images/#{params[:id]}/"
  end

  # POST /reports
  # POST /reports.json
  def create
    @report = Report.new(report_params)
    @report.user_id = current_user.id

    respond_to do |format|
      if @report.save
        format.html { redirect_to @report, notice: 'Report was successfully created.' }
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
      format.html { redirect_to reports_url, notice: 'Report was successfully destroyed.' }
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
    params.require(:report).permit(:rio, :categoria, :motivo, :descricao, :local, {images: []})
  end
end
