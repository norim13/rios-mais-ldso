class RioController < ApplicationController
  def show
    @rio_id = params[:id]
    @form_irrs = FormIrr.where(idRio: @rio_id)
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
end