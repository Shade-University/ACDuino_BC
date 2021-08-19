$(document).ready(function () {
    $(".alert").hide(); //Hide error message

    $(document).bind('keypress', function (e) {
        if (e.keyCode == 13) {
            $('#loginBtn').trigger('click');
        }
    });// enter key

    $("#loginBtn").click(function () {
        var credentials = {username: $("#username").val(), password: $("#password").val()};
        if (!credentials.username || !credentials.password) {
            showError("Uživatelské jméno či heslo nebylo vyplněno");
            return;
        } //validate input if not empty

        $.ajax({
            url: SERVER + '/login',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(credentials) //Send creds to rest api
        }).done(function (response, status, xhr) {
            var authToken = xhr.getResponseHeader("Authorization");
            console.log(xhr.status);
            document.cookie = "Authorization=" + authToken;
            window.location.replace("administration.html"); //Set Authorization header to cookies if login success
        }).fail(function (xhr, status) {
            if (!xhr) {
                showError("Server neodpovídá");
                return;
            }

            if (xhr.status === 403) {
                showError("Vaše přihlašovací údaje nejsou správné")
                $("#username").val("");
                $("#password").val("");
            }
        })
    });

    $('.alert .close').on('click', function (e) {
        $(this).parent().hide();
    }); //Hide alert about error
});

function showError(text) {
    $("#errorText").text(text);
    $(".alert").addClass("show");
    $(".alert").show(); //Show error
}