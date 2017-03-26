$(document).ready(function () {

    $("#btnSubmit").click(function (event) {

        //stop submit the form, we will post it manually.
        event.preventDefault();

        fire_ajax_submit();

    });
    
    $("#btnSubmitToDownload").click(function (event) {
        //stop submit the form, we will post it manually.
        event.preventDefault();
        var filePath=$("#fileName").val();
        alert(filePath);
        $.ajax({
            type: "GET",
            url: "/api/file/download",
            data:{path:filePath},
            success: function (data) {
                $("#btnSubmit").prop("disabled", false);

            },
            error: function (e) {

                $("#result").text(e.responseText);
                console.log("ERROR : ", e);
                $("#btnSubmit").prop("disabled", false);

            }
        });

    });

});

function fire_ajax_submit() {

    // Get form
    var form = $('#fileUploadForm')[0];

    var data = new FormData(form);

   

    $("#btnSubmit").prop("disabled", true);
    
    $.ajax({
        type: "POST",
        enctype: 'multipart/form-data',
        url: "/api/upload/uploadFileMultiFiles",
        data: data,
        processData: false, 
        contentType: false,
        cache: false,
        timeout: 600000,
        dataType:"json",
        success: function (data) {

            $("#result").text(JSON.stringify(data));
            console.log("SUCCESS : ", JSON.stringify(data));
            $("#btnSubmit").prop("disabled", false);

        },
        error: function (e) {

            $("#result").text(e.responseText);
            console.log("ERROR : ", e);
            $("#btnSubmit").prop("disabled", false);

        }
    });

}