# Place all the behaviors and hooks related to the matching controller here.
# All this logic will automatically be available in application.js.
# You can use CoffeeScript in this file: http://coffeescript.org/
`
    $(document).ready(function () {
        $('#btn-add-img-trip-point').click(function(){
            $('#trip-point-img-input').click();
        });

        $("#trip-point-img-input").change(function() {
            var files = $(this)[0].files;
            var file_names = "";
            for (var i = 0; i < files.length; i++) {
                file_names += "<p>"+files[i].name+"</p>";
            }
            $("#img-filenames").html(file_names);
        });
    });
`
