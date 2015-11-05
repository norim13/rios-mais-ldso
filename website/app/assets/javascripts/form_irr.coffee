# Place all the behaviors and hooks related to the matching controller here.
# All this logic will automatically be available in application.js.
# You can use CoffeeScript in this file: http://coffeescript.org/

$ ->
  $('#myModal').modal({show: false});
  console.log("GGGG");
  $(".form-disabled input").prop("disabled", true);
  $(".form-disabled select").prop("disabled", true);