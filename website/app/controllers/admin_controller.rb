class AdminController < ApplicationController
  before_filter { |c| c.authenticate_rights current_user.id }

  # Mostra a pagina de admin, com todos os utilizadores
  # Verificando se foi pesquisado algum utilizador, se sim so mostra os correspondentes
  def index
    if params.has_key?(:pesquisa)
        t = User.arel_table
        @users = User.where(
                (t[:nome].matches("%#{params[:pesquisa]}%")).
                  or(t[:email].matches("%#{params[:pesquisa]}%"))
        ).paginate(:page => params[:page], :per_page => 10).order('created_at')
    else
      @users = User.all.paginate(:page => params[:page], :per_page => 10).order('created_at')
    end
    @index_page = 'users'
  end

  # Mostra a pagina de admin, com todos os form irrs
  def form_irrs
    @form_irrs = FormIrr.all.paginate(:page => params[:page], :per_page => 10).order('created_at')
    @index_page = 'form_irrs'
    render 'index'
  end

  # Mostra a pagina de admin, com todos os sos rios (reports)
  def reports
    @reports = Report.all.paginate(:page => params[:page], :per_page => 10).order('created_at')
    @index_page = 'reports'
    render 'index'
  end

  # Mostra a pagina de admin, com todas as rotas
  def rotas
    @rotas = Route.all.paginate(:page => params[:page], :per_page => 10).order('created_at')
    @index_page = 'rotas'
    render 'index'
  end

  # Mostra a pagina de admin, com todas as trips
  def trips
    @trips = Trip.all.paginate(:page => params[:page], :per_page => 10).order('created_at')
    @index_page = 'trips'
    render 'index'
  end

  # Verifica se o utilizador tem permissoes de admin
  def authenticate_rights(user_id)
    user = User.find(user_id)
    redirect_to new_user_session_path unless user.permissoes == 6
  end

end
