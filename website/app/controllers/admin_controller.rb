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
    if params.has_key?(:pesquisa)
      t = FormIrr.arel_table
      @form_irrs = FormIrr.where(
          (t[:nomeRio].matches("%#{params[:pesquisa]}%")).
              or(t[:idRio].matches("%#{params[:pesquisa]}%"))
      ).paginate(:page => params[:page], :per_page => 10).order('created_at')
    else
      @form_irrs = FormIrr.all.paginate(:page => params[:page], :per_page => 10).order('created_at')
    end
    @index_page = 'form_irrs'
    render 'index'
  end

  # Mostra a pagina de admin, com todos os sos rios (reports)
  def reports
    if params.has_key?(:pesquisa)
      t = Report.arel_table
      @reports = Report.where(
          (t[:nome_rio].matches("%#{params[:pesquisa]}%")).
              or(t[:rio].matches("%#{params[:pesquisa]}%")).
                or(t[:categoria].matches("%#{params[:pesquisa]}%")).
                  or(t[:motivo].matches("%#{params[:pesquisa]}%"))
      ).paginate(:page => params[:page], :per_page => 10).order('created_at')
    else
      @reports = Report.all.paginate(:page => params[:page], :per_page => 10).order('created_at')
    end
    @index_page = 'reports'
    render 'index'
  end

  # Mostra a pagina de admin, com todas as rotas
  def rotas
    if params.has_key?(:pesquisa)
      t = Route.arel_table
      @rotas = Route.where(
          (t[:nome].matches("%#{params[:pesquisa]}%")).
              or(t[:descricao].matches("%#{params[:pesquisa]}%")).
              or(t[:zona].matches("%#{params[:pesquisa]}%"))
      ).paginate(:page => params[:page], :per_page => 10).order('created_at')
    else
      @rotas = Route.all.paginate(:page => params[:page], :per_page => 10).order('created_at')
    end
    @index_page = 'rotas'
    render 'index'
  end

  # Mostra a pagina de admin, com todas as trips
  def trips
    if params.has_key?(:pesquisa)
      t = Trip.arel_table
      @trips = Trip.where(
          (t[:nomeRio].matches("%#{params[:pesquisa]}%")).
              or(t[:idRio].matches("%#{params[:pesquisa]}%"))
      ).paginate(:page => params[:page], :per_page => 10).order('created_at')
    else
      @trips = Trip.all.paginate(:page => params[:page], :per_page => 10).order('created_at')
    end
    @index_page = 'trips'
    render 'index'
  end

  # Mostra a pagina de admin, com todos os guardarios
  def guardarios
    if params.has_key?(:pesquisa)
      t = Guardario.arel_table
      @guardarios = Guardario.where(
          (t[:nomeRio].matches("%#{params[:pesquisa]}%")).
              or(t[:rio].matches("%#{params[:pesquisa]}%"))
      ).paginate(:page => params[:page], :per_page => 10).order('created_at')
    else
      @guardarios = Guardario.all.paginate(:page => params[:page], :per_page => 10).order('created_at')
    end
    @index_page = 'guardarios'
    render 'index'
  end

  # Mostra a pagina de admin, com todos os guardarios
  def limpezas
    @limpezas = LogLimpeza.all.paginate(:page => params[:page], :per_page => 10).order('created_at')
    @index_page = 'limpezas'
    render 'index'
  end


  # Verifica se o utilizador tem permissoes de admin
  def authenticate_rights(user_id)
    user = User.find(user_id)
    redirect_to new_user_session_path unless user.permissoes == 6
  end

end
