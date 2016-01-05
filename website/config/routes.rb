Rails.application.routes.draw do

  get 'search' => 'searchrios#display'
  get 'profile' => 'profile#display'
  get 'home' => 'home#homepage'
  get 'contactos' => 'about#about'
  get 'documentos' => 'documentos_relacionados#documentos'
  get 'distritos' => 'concelho#getDistritos'
  get 'concelhos' => 'concelho#getConcelhosFromDistrito'
  get 'limpeza/new' => 'limpeza#new'
  get 'limpeza/info' => 'limpeza#info'
  get 'limpeza/:id' => 'limpeza#show', :as => :limpeza
  delete 'limpeza/:id' => 'limpeza#destroy'
  get 'respostas' => 'limpeza#getRespostas'

  get 'adminpanel' => 'admin#index' #users index
  get 'adminpanel/form_irrs' => 'admin#form_irrs', :as => :index_form_irrs
  get 'adminpanel/sos_rios' => 'admin#reports', :as => :index_reports
  get 'adminpanel/rotas' => 'admin#rotas', :as => :index_rotas
  get 'adminpanel/trips' => 'admin#trips', :as => :index_trips
  get 'adminpanel/guardarios' => 'admin#guardarios', :as => :index_guardarios
  get "adminpanel/limpezas", :to => 'admin#limpezas', :as => :index_limpezas

  post 'submitProblemasAction' => 'limpeza#submitProblemas'
  post 'changePermissions' => 'users#updatepermissions'

  post 'rota_point_image' => 'rota_point_image#create'
  delete 'rota_point_image/:id' => 'rota_point_image#destroy'

  post 'trip_point_image' => 'trip_image#create'
  delete 'trip_point_image/:id' => 'trip_image#destroy'

  get 'meusguardarios' => 'guardarios#getMine'

  #info pages
  get 'reabilitacao/info' => 'reabilitacaos#info'
  get 'form_irr/info' => 'form_irrs#info'
  get 'report/info' => 'reports#info'
  get 'lab_rios/info' => 'lab_rios#info'
  get 'participacao_publica/info' => 'participacao_publica#info'
  get 'projetos/info' => 'projetos#info'

  #show all
  get 'form_irrs/all' => 'form_irrs#all'
  get 'trips/all' => 'trips#all'

  get 'rio/irrrange' => 'rio#getIRRrange'
  get 'rio/:id' => 'rio#show', as: :rio, :id => /.*/

  resources :form_irr_image
  resources :reabilitacaos
  resources :guardarios, only: [:index, :show, :new, :create, :destroy]
  resources :reports, only: [:index, :show, :new, :create, :destroy]
  resources :form_irrs
  resources :trip_points
  resources :trips
  resources :routes
  resources :rotas

  patch 'validate_form_irrs/:id' => 'form_irrs#validate', :as => :validate_form_irr
  get 'validate_form_irrs' => 'form_irrs#validate_index'

  devise_for :users, :controllers => {registrations: 'registrations'}

  devise_scope :user do
    get '/users/sign_out' => 'devise/sessions#destroy'
  end

  root 'home#homepage'

  # rotas da API, usadas pela app
  namespace :api do
    namespace :v1 do
      resources :problems
      resources :services
      devise_scope :user do
        post "/sign_in", :to => 'sessions#create'
        post "/sign_up", :to => 'registrations#create'
        delete "/sign_out", :to => 'sessions#destroy'
      end
    end
    namespace :v2 do
      post "/form_irrs", :to => 'form_irrs#create'
      get "/form_irrs", :to => 'form_irrs#getMyForms'
      get "/form_irrs/:id", :to => 'form_irrs#get'
      put "/form_irrs/:id", :to => 'form_irrs#update'
      delete "/form_irrs/:id", :to => 'form_irrs#destroy'

      post "/image", :to => 'image#create'

      post "/guardarios", :to => 'guardarios#create'
      get "/guardarios/:id", :to => 'guardarios#get'
      get "/guardarios", :to => 'guardarios#recent'

      post "/reports", :to => 'reports#create'
      get "/reports", :to => 'reports#index'
      get "/reports/:id", :to => 'reports#get'

      get "/limpezas/:opcao", :to => 'limpezas#getRespostas', :opcao => /.*/
      post "/limpezas", :to => 'limpezas#submitProblemas'

      get "/users", :to => 'users#getUser'
      delete "/users", :to => 'users#destroy'
      put "/users", :to => 'users#update'

	    get "/routes", :to => 'routes#index'
      get "/routes/:id", :to => 'routes#show'

      get "/distritos", :to => 'localidades#getDistritos'
      get "/distritos/:distrito", :to => 'localidades#getConcelhos'
    end
  end

  #web views for app
  get 'rios_mapa_webview' => 'application_web_view#showMapa'
end
