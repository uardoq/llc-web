$(document).ready(function () {

    $("#contactUsForm").submit(function (event) {
        // Prevent the form from submitting via the browser.
        event.preventDefault();
        submitContactForm();
    });

    function submitContactForm() {
        const HTTP_UNPROCESSABLE_ENTITY = 422;

        function clearForm() {
            $("#contactName").val("");
            $("#contactEmail").val("");
            $("#contactMessage").val("");
            $("#contactFiles").val("");
        }

        const onSuccess = function () {
            $("#postResultDiv").html(
                "<p style='background-color:#479aff; color:white; padding:20px 20px 20px 20px'>"
                + "We will email you within 24 hours<br>"
                + "</p>");
            clearForm();
        };

        const onError = function (res) {
            // get server response
            let json = res.responseJSON;
            let errors = res.status === HTTP_UNPROCESSABLE_ENTITY ?
                // build html list of all field and error pairs
                Object.keys(json).reduce((htmlString, field) =>
                    htmlString + "Field: " + field + " Error: " + json[field] + "<br>", "")
                :
                // other server error, 5xx
                "We were unable to send your message at this time. Please try again later";

            // wrap list of errors
            let errorHTML =
                "<p style='background-color:#479aff; color:white; padding:20px 20px 20px 20px'>"
                + errors
                + "</p>";
            $("#postResultDiv").html(errorHTML);
        };

        var formData = new FormData();

        formData.append("name", $("#contactName").val().trim());
        formData.append("email", $("#contactEmail").val().trim());
        formData.append("message", $("#contactMessage").val().trim());

        const contactFiles = $("#contactFiles").prop("files");
        for (let i = 0, len = contactFiles.length; i < len; i++) {
            let file = contactFiles[i];
            // only add images
            if (!file.type.match('image.*')) {
                continue;
            }
            // add file under key files[]
            formData.append('files[]', file, file.name);
        }

        for (let p of formData) {
            console.log(p);
        }

        // post request
        $.ajax({
            type: "POST",
            // processData must be set to false so jquery does not try to convert formData into a String (default action).
            processData: false,
            contentType: false,
            url: "/submitContactForm",
            data: formData,
            success: onSuccess,
            error: onError
        });
    }

});