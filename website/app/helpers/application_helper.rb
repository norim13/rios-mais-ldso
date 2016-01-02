module ApplicationHelper
  def field_class(resource, field_name)
    p resource.errors
    if resource.errors.messages[field_name]
      if !resource.errors.messages[field_name].empty?
        return "has-error".html_safe
      end
    end
    return "".html_safe
  end
end
