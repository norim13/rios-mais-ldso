class TripPointsController < ApplicationController
  before_action :set_trip_point, only: [:show, :edit, :update, :destroy]


  # POST /trip_points
  # POST /trip_points.json
  def create
    @trip_point = TripPoint.new(trip_point_params)
    if @trip_point.save
        render :json => '{"success" : "true"}'
    else
        render :json => '{"success" : "false"}'
    end
  end

  # PATCH/PUT /trip_points/1
  # PATCH/PUT /trip_points/1.json
  def update
    respond_to do |format|
      if @trip_point.update(trip_point_params)
        format.html { redirect_to @trip_point, notice: 'Trip point was successfully updated.' }
        format.json { render :show, status: :ok, location: @trip_point }
      else
        format.html { render :edit }
        format.json { render json: @trip_point.errors, status: :unprocessable_entity }
      end
    end
  end

  # DELETE /trip_points/1
  # DELETE /trip_points/1.json
  def destroy
    @trip_point.destroy
    respond_to do |format|
      format.html { redirect_to trip_points_url, notice: 'Trip point was successfully destroyed.' }
      format.json { head :no_content }
    end
  end

  private
    # Use callbacks to share common setup or constraints between actions.
    def set_trip_point
      @trip_point = TripPoint.find(params[:id])
    end

    # Never trust parameters from the scary internet, only allow the white list through.
    def trip_point_params
      params.require(:trip_point).permit(:descricao, :lat, :lon, :trip_id)
    end
end
