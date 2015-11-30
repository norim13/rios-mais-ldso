json.array!(@reabilitacaos) do |reabilitacao|
  json.extract! reabilitacao, :id
  json.url reabilitacao_url(reabilitacao, format: :json)
end
