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

        // get form values
        const formData = {
            name: $("#contactName").val(),
            email: $("#contactEmail").val(),
            message: $("#contactMessage").val()
        };

        // post request
        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: window.location + "submitContactForm",
            data: JSON.stringify(formData),
            dataType: 'json',
            success: onSuccess,
            error: onError
        });
    }

});