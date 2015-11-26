# Place all the behaviors and hooks related to the matching controller here.
# All this logic will automatically be available in application.js.
# You can use CoffeeScript in this file: http://coffeescript.org/

`
    $(document).ready(function () {
        $('#btn-add-img-form-irr').click(function(){
            $('#form-irr-img-input').click();
        });

        /*$('#form-irr-img-input').change(function() {
            addImgToFormIrr($('#form-irr-img-input')[0].files[0],
                $('#form-irr-img-input').attr('data-id-form-irr'))
        });*/

        $('#form-irr-del-img').click(deleteImg);
    });

    function addImgToFormIrr(img,productId) {
        var formData = new FormData();
        formData.append('form_irr_image[image]',img);
        formData.append('form_irr_image[form_irr_id]',productId);
        $.ajax({
            url: '/form_irr_image',
            data: formData,
            cache: false,
            contentType: false,
            processData: false,
            type: 'PUT',
            success: function () {
                //location.reload();
            }
        });
    }

    function deleteImg() {
        var imgs = $('.img-checkbox:checked');

		    imgs.each(function() {
            imgId = imgs.attr('data-img-id');
            $.ajax({
                url: '/form_irr_image/' + imgId,
                type: 'DELETE',
                success: function () {
                    //location.reload();
                }
            });
        });
    }

`