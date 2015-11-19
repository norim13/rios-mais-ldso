class RioController < ApplicationController
  def show
    @rio_id = params[:id]
    @form_irrs = FormIrr.where(idRio: @rio_id)
    @nr_denuncias = Report.where(rio: @rio_id).count

    if @form_irrs.count == 0
      @has_irr = 'no'
    else
      @has_irr = 'yes'
      @avg_hidrogeomorfologia = @form_irrs.average(:irr_hidrogeomorfologia).round(2)
      @avg_qualidadedaagua = @form_irrs.average(:irr_qualidadedaagua).round(2)
      @avg_alteracoesantropicas = @form_irrs.average(:irr_alteracoesantropicas).round(2)
      @avg_corredorecologico = @form_irrs.average(:irr_corredorecologico).round(2)
      @avg_participacaopublica = @form_irrs.average(:irr_participacaopublica).round(2)
      @avg_organizacaoeplaneamento = @form_irrs.average(:irr_organizacaoeplaneamento).round(2)
      @avg_irr = @form_irrs.average(:irr).round(2)
      @irr_total = [@avg_hidrogeomorfologia,@avg_qualidadedaagua,@avg_alteracoesantropicas,
          @avg_corredorecologico, @avg_participacaopublica, @avg_organizacaoeplaneamento].max().round(2)

      @imgs = []
      @form_irrs.each do |f|
        f.images.each do |img|
          @imgs.push(img)
        end
      end
    end
  end
end