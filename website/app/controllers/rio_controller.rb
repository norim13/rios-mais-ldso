class RioController < ApplicationController

  def show
    @rio_id = params[:id]
    @form_irrs = FormIrr.where(idRio: @rio_id, validated: true)
    @nr_denuncias = Report.where(rio: @rio_id).count

    if @form_irrs.count == 0
      @has_irr = 'no'
    else
      @has_irr = 'yes'
      @avg_hidrogeomorfologia =@form_irrs.max_by(&:irr_hidrogeomorfologia).irr_hidrogeomorfologia
      @avg_qualidadedaagua = @form_irrs.max_by(&:irr_qualidadedaagua).irr_qualidadedaagua
      @avg_alteracoesantropicas = @form_irrs.max_by(&:irr_alteracoesantropicas).irr_alteracoesantropicas
      @avg_corredorecologico = @form_irrs.max_by(&:irr_corredorecologico).irr_corredorecologico
      @avg_participacaopublica = @form_irrs.max_by(&:irr_participacaopublica).irr_participacaopublica
      @avg_organizacaoeplaneamento = @form_irrs.max_by(&:irr_organizacaoeplaneamento).irr_organizacaoeplaneamento
      @irr_total = [@avg_hidrogeomorfologia,@avg_qualidadedaagua,@avg_alteracoesantropicas,
          @avg_corredorecologico, @avg_participacaopublica, @avg_organizacaoeplaneamento].max()

      @imgs = []
      @form_irrs.each do |f|
        f.form_irr_images.each do |img|
          @imgs.push(img)
        end
      end
    end
  end

  # get FormIRR dentro de um raio à volta de lat/lon dados,
  # e dentro de um intervalo de datas especificado
  def getIRRrange
      delta_lat = params[:raio].to_d/1000.0/110.574 #Latitude: 1 deg = 110.574 km
      delta_lon = params[:raio].to_d/1000.0/(111.320*Math.cos(params[:lat].to_d).abs) #Longitude: 1 deg = 111.320*cos(latitude) km
      lat_min = params[:lat].to_d - delta_lat
      lat_max = params[:lat].to_d + delta_lat
      lon_min = params[:lon].to_d - delta_lon
      lon_max = params[:lon].to_d + delta_lon
      p lat_max
      forms = FormIrr.where(:idRio => params[:rio]).where(validated: true).where('created_at >= ? AND created_at <= ?',
                     params[:data_inicio], params[:data_fim]).where("lat > ? AND lat < ? AND lon > ? AND lon < ?",
                     lat_min, lat_max, lon_min, lon_max).order('updated_at DESC')
      media = mediaIRR(forms) #media com todos os IRR
      forms = forms[0..4] #return só os primeiros 5 irrs (most recent)
      render :json => {:success => true, :forms => forms, :media => media, :data_inicio => params[:data_inicio], :data_fim => params[:data_fim]}
          # :delta_lat => delta_lat, :delta_lon => delta_lon,
          #              :lat_min => lat_min, :lat_max => lat_max,
          #              :lon_min => lon_min, :lon_max => lon_max,
          #              :lat => params[:lat].to_d , :lon => params[:lon].to_d,
          #              :cod_rio => params[:rio]}
  end

  def mediaIRR(form_irrs)

    if form_irrs.count == 0
      return nil
    else
      ret = {}
      ret['irr_hidrogeomorfologia'] = form_irrs.max_by(&:irr_hidrogeomorfologia).irr_hidrogeomorfologia
      ret['irr_qualidadedaagua'] = form_irrs.max_by(&:irr_qualidadedaagua).irr_qualidadedaagua
      ret['irr_alteracoesantropicas'] = form_irrs.max_by(&:irr_alteracoesantropicas).irr_alteracoesantropicas
      ret['irr_corredorecologico'] = form_irrs.max_by(&:irr_corredorecologico).irr_corredorecologico
      ret['irr_participacaopublica'] = form_irrs.max_by(&:irr_participacaopublica).irr_participacaopublica
      ret['irr_organizacaoeplaneamento'] = form_irrs.max_by(&:irr_organizacaoeplaneamento).irr_organizacaoeplaneamento
      ret['irr'] = [ret['irr_hidrogeomorfologia'],ret['irr_qualidadedaagua'],ret['irr_alteracoesantropicas'],
                          ret['irr_corredorecologico'], ret['irr_participacaopublica'], ret['irr_organizacaoeplaneamento']].max()
      return ret
    end
  end
end