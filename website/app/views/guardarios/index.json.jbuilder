json.array!(@guardarios) do |guardario|
  json.extract! guardario, :id, :rio, :user_id
  json.url guardario_url(guardario, format: :json)
end
