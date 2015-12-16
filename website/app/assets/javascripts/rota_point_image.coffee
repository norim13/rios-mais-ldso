# Place all the behaviors and hooks related to the matching controller here.
# All this logic will automatically be available in application.js.
# You can use CoffeeScript in this file: http://coffeescript.org/
`
    $(document).ready(function () {
        $('.btn-add-img-rota-point').click(function(){
		        var id = $(this).attr('id').split('-sep-')[1];
            $('#pt-sep-'+id).click();
        });

        $('#rota-point-del-img').click(deleteImg);

        $(".rota-point-img-input").change(function() {
            var id = $(this).attr('id').split('-sep-')[1];
            var files = $(this)[0].files;
            var file_names = "";
            for (var i = 0; i < files.length; i++) {
                file_names += "<p>"+files[i].name+"</p>";
            }
            $("#img-filenames-sep-"+id).html(file_names);
        });
    });

    function deleteImg() {
        var imgs = $('.img-checkbox:checked');

        imgs.each(function() {
            imgId = $(this).attr('data-img-id');
            $.ajax({
                url: '/rota_point_image/' + imgId,
                type: 'DELETE',
                success: function () {
                    $("#div-"+imgId).remove();
                }
            });
        });
    }
`