<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <script
            src="https://code.jquery.com/jquery-3.5.1.js"
            integrity="sha256-QWo7LDvxbWT2tbbQ97B53yJnYU3WhH/C8ycbRAkjPDc="
            crossorigin="anonymous"></script>


</head>
<body>
<script>

    function renderTable(users, table) {
        let innerHtml = '<tr>\n' +
            '               <th>FirstName</th>' +
            '               <th>LastName</th>' +
            '            </tr>';

        for (let i = 0; i < users.length; i++) {
            innerHtml += '<tr>';
            innerHtml += '  <td>' + users[i]['name'] + '</td>';
            innerHtml += '  <td>' + users[i]['surname'] +  '</td>';
            innerHtml += '  <td>' + users[i]['age'] + '</td>';
            innerHtml += '</tr>';
        }

        table.html(innerHtml);
    }

    function showData(name) {

        let data = {
            "name": name
        };

        $.ajax({
            type: "POST",
            url: "/users/search",
            data: JSON.stringify(data),
            success: function (response) {
                renderTable(response, $('#table'))
            },
            dataType: "json",
            contentType: "application/json"
        });

    }
</script>
<input id="name" name="name" oninput="showData($('#name').val())">
<table id="table">

</table>
</body>
</html>