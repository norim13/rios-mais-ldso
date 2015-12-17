class TripsController < ApplicationController
  before_action :set_trip, only: [:show, :edit, :update, :destroy]
  before_action :custom_auth!

  def custom_auth!
    if authenticate_user!
      @permissoes = current_user.permissoes
      if current_user.permissoes > 1
        return true
      else
        render 'common/noaccess'
      end
    else
      render 'common/noaccess'
    end
  end


  # GET /trips
  # GET /trips.json
  def index
    @all = false
    @trips = current_user.trips.paginate(:page => params[:page], :per_page => 10).order('updated_at DESC')
  end

  def all
    if current_user.permissoes > 4
      @all = true
      @trips = Trip.all.paginate(:page => params[:page], :per_page => 10).order('updated_at DESC')
      render 'index'
    else
      render 'common/noaccess'
    end
  end

  # GET /trips/1
  # GET /trips/1.json
  def show
    @points = @trip.trip_points
  end

  # GET /trips/new
  def new
    @trip = Trip.new
    @edit = false;
  end

  # GET /trips/1/edit
  def edit
    if( @trip.user_id != current_user.id)
      render 'common/noaccess'
    else
      @edit = true;
      @points = @trip.trip_points
    end
  end

  # POST /trips
  # POST /trips.json
  def create
    @trip = Trip.new(trip_params)
    @trip.user_id = current_user.id
    if @trip.save
      redirect_to edit_trip_path(@trip)
    else

    end
  end

  # PATCH/PUT /trips/1
  # PATCH/PUT /trips/1.json
  def update
    respond_to do |format|
      if @trip.update(trip_params)
        format.html { redirect_to @trip, notice: 'Trip was successfully updated.' }
        format.json { render :show, status: :ok, location: @trip }
      else
        format.html { render :edit }
        format.json { render json: @trip.errors, status: :unprocessable_entity }
      end
    end
  end

  # DELETE /trips/1
  # DELETE /trips/1.json
  def destroy
    @trip.destroy
    respond_to do |format|
      format.html { redirect_to trips_url, notice: 'Trip was successfully destroyed.' }
      format.json { head :no_content }
    end
  end

  private
    # Use callbacks to share common setup or constraints between actions.
    def set_trip
      @trip = Trip.find(params[:id])
    end

    # Never trust parameters from the scary internet, only allow the white list through.
    def trip_params
      params.require(:trip).permit(:idRio, :nomeRio)
    end
end
