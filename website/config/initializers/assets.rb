# Be sure to restart your server when you modify this file.

# Version of your assets, change this if you want to expire all your assets.
Rails.application.config.assets.version = '1.0'

# Add additional assets to the asset load path
# Rails.application.config.assets.paths << Emoji.images_path

# Precompile additional assets.
# application.js, application.css, and all non-JS/CSS in app/assets folder are already added.
# Rails.application.config.assets.precompile += %w( search.scss.js )

Rails.application.config.assets.precompile += %w( navbar.css )
Rails.application.config.assets.precompile += %w( footer.css )

Rails.application.config.assets.precompile += %w( searchrios.css )
Rails.application.config.assets.precompile += %w( users.css )
Rails.application.config.assets.precompile += %w( form_irr.css )
Rails.application.config.assets.precompile += %w( mapa.css )

Rails.application.config.assets.precompile += %w( concelho.js )
Rails.application.config.assets.precompile += %w( footer.js )

Rails.application.config.assets.precompile += %w( external/Chart.min.js )
Rails.application.config.assets.precompile += %w( external/PageMe.js )
Rails.application.config.assets.precompile += %w( external/bootstrap-datepicker.js )
Rails.application.config.assets.precompile += %w( external/jquery.autoellipsis-1.0.10.min.js )

Rails.application.config.assets.precompile += %w( form_irr.js )
Rails.application.config.assets.precompile += %w( form_irr_image.js )
Rails.application.config.assets.precompile += %w( limpeza.js )
Rails.application.config.assets.precompile += %w( rio.js )
Rails.application.config.assets.precompile += %w( routes-view.js )
Rails.application.config.assets.precompile += %w( routes-edit.js )
Rails.application.config.assets.precompile += %w( routes-index.js )
Rails.application.config.assets.precompile += %w( rota_point_image.js )
Rails.application.config.assets.precompile += %w( trips.js )
Rails.application.config.assets.precompile += %w( trip_image.js )
Rails.application.config.assets.precompile += %w( trips-show.js )
Rails.application.config.assets.precompile += %w( guardarios.js )

Rails.application.config.assets.precompile += %w( admin.js )

Rails.application.config.assets.precompile += %w( limpeza.css )
Rails.application.config.assets.precompile += %w( reabilitacaos.css )
Rails.application.config.assets.precompile += %w( chart-irr.css )
Rails.application.config.assets.precompile += %w( rio.css )
Rails.application.config.assets.precompile += %w( routes.css )
Rails.application.config.assets.precompile += %w( trips.css )
Rails.application.config.assets.precompile += %w( profile.css )
Rails.application.config.assets.precompile += %w( admin.css )

Rails.application.config.assets.precompile += %w( external/jquery-ui.min.css )
Rails.application.config.assets.precompile += %w( external/datepicker.css )

