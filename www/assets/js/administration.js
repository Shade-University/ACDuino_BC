var auth;

$(document).ready(function () {
    $(".alert").each(function (i) {
        $(this).hide();
    }); //Hide error messages

    auth = getCookie("Authorization");
    if (auth == null) {
        window.location.replace("index.html");
    } //If no authorization set, return to login page

    fetchClient();
    fetchRfid();
    fetchHistory(); //Fetch data from server in background

    $("#tagAddBtn").click(function () {
        var newTag = {tagId: $("#rifdCode").val(), owner: $("#tagUser").val()}
        console.log(newTag);
        if (!newTag.tagId || !newTag.owner) {
            showError("#tagFailed", "RFID tag nebo vlastník není vyplněn.");
            return;
        } //validate input if not empty

        $.ajax({
            url: SERVER + '/api/v1/tags',
            type: 'post',
            dataType: 'json',
            contentType: "application/json",
            data: JSON.stringify(newTag),
            beforeSend: function (xhr) {
                xhr.setRequestHeader("Authorization", auth);
            },
        }).done(function (response, status, xhr) {
            showError("#tagSuccess", "Podařilo se úspěšně přidat RIFD kartu.");
            fetchRfid();
        }).fail(function (response) {
            if (response.status === 403) {
                document.cookie = "";
                window.location.replace("index.html");
            }
            showError("#tagFailed", "Nepodařilo se přidat RIFD kartu");
        });
    });

    $("#tagTable").on('click', '.tagRemove', function () {
        $.ajax({
            url: SERVER + '/api/v1/tags/' + $(this).attr("data-rfid"),
            type: 'delete',
            beforeSend: function (xhr) {
                xhr.setRequestHeader("Authorization", auth);
            },
        }).done(function (response, status, xhr) {
            console.log("hello3");
            showError("#tagSuccess", "Karta úspěšně odebrána.");
            fetchRfid();
        }).fail(function (response) {
            if (response.status === 403) {
                document.cookie = "";
                window.location.replace("index.html");
            }
            showError("#tagFailed", "Kartu se nepodařilo odebrat.");
        });
    });

    $("#clientConnectBtn").click(function () {
        $.ajax({
            url: 'http://localhost:8080/api/v1/registration/register/'
                + $("#ipAdress").val(),
            type: 'get',
            dataType: 'json',
            beforeSend: function (xhr) {
                xhr.setRequestHeader("Authorization", auth);
            },
        }).done(function (response, status, xhr) {
            var newClient = {name: $("#deviceName").val(), clientIp: $("#ipAdress").val()}
            $.ajax({
                url: 'http://localhost:8080/api/v1/clients',
                type: 'post',
                dataType: 'json',
                contentType: "application/json",
                data: JSON.stringify(newClient),
                beforeSend: function (xhr) {
                    xhr.setRequestHeader("Authorization", auth);
                },
            }).done(function (response, status, xhr) {
                $("#clientConnectSucess").addClass("show");
                $("#clientConnectSucess").show();
                fetchClient();
            }).fail(function (response) {
                if (response.status === 403) {
                    document.cookie = "";
                    window.location.replace("index.html");
                }
                $("#clientConnectFailed").addClass("show");
                $("#clientConnectFailed").show();
            });
        }).fail(function (response) {
            if (response.status === 403) {
                document.cookie = "";
                window.location.replace("index.html");
            }
            $("#clientConnectFailed").addClass("show");
            $("#clientConnectFailed").show();
        });
    });

    function fetchHistory() {
        $.ajax({
            url: 'http://localhost:8080/api/v1/history',
            type: 'get',
            dataType: 'json',
            beforeSend: function (xhr) {
                xhr.setRequestHeader("Authorization", auth);
            },
        }).done(function (response, status, xhr) {
            console.log(response);
            var i = 1;
            $("#historyTable tbody tr").remove(); //Remove everything from table
            response.forEach(function (item) {
                var tr = "<tr>"
                    + "<td>" + i++ + "</td>"
                    + "<td>" + item.request + "</td>"
                    + "<td>" + item.response + "</td>"
                    + "<td>" + item.from + "</td>"
                    + "<td>" + item.to + "</td>"
                    + "<td>" + item.timestamp + "</td>"
                    + "</tr>";
                $("#historyTable tbody").append(tr); //Append new items to table
            })
        }).fail(function (response) {
            if (response.status === 403) {
                document.cookie = "";
                window.location.replace("index.html");
            } //Authorization cookie expired, return to index.html
        });
    }

    function fetchRfid() {
        $.ajax({
            url: 'http://localhost:8080/api/v1/tags',
            type: 'get',
            dataType: 'json',
            beforeSend: function (xhr) {
                xhr.setRequestHeader("Authorization", auth);
            },
        }).done(function (response, status, xhr) {
            console.log(response);
            var i = 1;
            $("#tagTable tbody tr").remove();

            response.forEach(function (item) {
                var firstSeen = item.tagFirstSeen ? item.tagFirstSeen : "Není známo";
                var lastSeen = item.tagLastSeen ? item.tagLastSeen : "Není známo";
                var tr = "<tr>"
                    + "<td>" + i++ + "</td>"
                    + "<td>" + item.tagId + "</td>"
                    + "<td>" + item.owner + "</td>"
                    + "<td>" + firstSeen+ "</td>"
                    + "<td>" + lastSeen + "</td>"
                    + "<td>" + item.tagSeenCount + "</td>"
                    + "<td>" +
                    "<button data-rfid=\"" + item.tagId + "\" type=\"button\" class=\"btn btn-danger tagRemove\" " +
                    ">" + " Odstranit</button> " //data-rfid will hold tagId to further remove call
                    + "</td>"
                    + "</tr>";
                $("#tagTable tbody").append(tr);
            })
        }).fail(function (response) {
            if (response.status === 403) {
                document.cookie = "";
                window.location.replace("index.html");
            }
        });
    }

    function fetchClient() {
        $.ajax({
            url: 'http://localhost:8080/api/v1/clients',
            type: 'get',
            dataType: 'json',
            beforeSend: function (xhr) {
                xhr.setRequestHeader("Authorization", auth);
            },
        }).done(function (response, status, xhr) {
            console.log(response);
            var i = 1;
            $("#tableClient tbody tr").remove();
            response.forEach(function (item) {
                var tr = "<tr>"
                    + "<td>" + i++ + "</td>"
                    + "<td>" + item.clientIp + "</td>"
                    + "<td>" + item.name + "</td>"
                    + "<td>" + item.description + "</td>"
                    + "<div data-client=\"" + item.id + "\"" + "class=\"clientRemove\" type=\"button\" className=\"btn btn-danger\" " +
                    ">" + " Odstranit</div> "
                    + "</td>"
                    + "</tr>";
                $("#tableClient tbody").append(tr);
            })
        }).fail(function (response) {
            if (response.status === 403) {
                document.cookie = "";
                window.location.replace("index.html");
            }
        });
    }

    function getCookie(name) {
        var cookie = document.cookie;
        var prefix = name + "=";
        var begin = cookie.indexOf("; " + prefix);
        if (begin === -1) {
            begin = cookie.indexOf(prefix);
            if (begin !== 0) return null;
        } else {
            begin += 2;
            var end = document.cookie.indexOf(";", begin);
            if (end === -1) {
                end = cookie.length;
            }
        }
        return unescape(cookie.substring(begin + prefix.length, end));
    } //Support method to parse and get cookie

});

function showError(target, text) {
    $(target).children("span").text(text);
    $(target).addClass("show");
    $(target).show(); //Show error
}

$('.alert .close').on('click', function (e) {
    $(this).parent().hide();
}); //Hide alert about error