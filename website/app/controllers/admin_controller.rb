class AdminController < ApplicationController
  before_filter { |c| c.authenticate_rights current_user.id }

  def index
    str = Arel::Nodes::SqlLiteral.new(params[:pesquisa])
    if params.has_key?(:pesquisa)
        t = User.arel_table
        @users = User.where(
                (t[:nome].matches("%#{params[:pesquisa]}%")).
                  or(t[:email].matches("%#{params[:pesquisa]}%"))
        ).paginate(:page => params[:page], :per_page => 10).order('created_at')
    else
      @users = User.all.paginate(:page => params[:page], :per_page => 10).order('created_at')
    end
  end

  def authenticate_rights(user_id)
    user = User.find(user_id)
    redirect_to new_user_session_path unless user.permissoes == 6
  end

end
