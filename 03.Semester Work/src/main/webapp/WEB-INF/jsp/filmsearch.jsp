<!DOCTYPE html>
<html>
<head>
    <title>Search</title>

    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css"
          integrity="sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
            integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
            crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"
            integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49"
            crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"
            integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy"
            crossorigin="anonymous"></script>
    <script
            src="https://code.jquery.com/jquery-3.5.1.js"
            integrity="sha256-QWo7LDvxbWT2tbbQ97B53yJnYU3WhH/C8ycbRAkjPDc="
            crossorigin="anonymous"></script>
</head>
<body>
<script>

    function renderTable(films, table) {
        let innerHtml = '<thead> <tr>\n' +
            '               <th scope="col">#</th>' +
            '               <th scope="col">Name</th>' +
            '               <th scope="col">Genre</th>' +
            '               <th scope="col">Critics score</th>' +
            '               <th scope="col">Users score</th>' +
            '               <th scope="col">Year</th>' +
            '            </tr> </thead> <tbody>';

        for (let i = 0; i < films.length; i++) {
            innerHtml += '<tr>';
            innerHtml += '  <td>' + films[i]['id'] + '</td>';
            innerHtml += '  <td>' + films[i]['name'] +  '</td>';
            innerHtml += '  <td>' + films[i]['genre'] + '</td>';
            innerHtml += '  <td>' + films[i]['criticScore'] + '</td>';
            innerHtml += '  <td>' + films[i]['usersScore'] + '</td>';
            innerHtml += '  <td>' + films[i]['year'] + '</td>';
            innerHtml += '</tr> </tbody>';
        }

        table.html(innerHtml);
    }

    function showData(name) {

        let data = {
            "name": name
        };

        $.ajax({
            type: "POST",
            url: "/search",
            data: JSON.stringify(data),
            success: function (response) {
                renderTable(response, $('#table'))
            },
            dataType: "json",
            contentType: "application/json"
        });

    }
</script>
<nav class="navbar navbar-expand-lg navbar-light" style="background-color: 	#ffe52c;">
    <a class="navbar-brand" href="/index">
        <img src="https://img.icons8.com/metro/2x/retro-tv.png"
             width="50" height="50" alt="" loading="lazy">
    </a>

    <ul class="navbar-nav mr-auto">
        <li class="nav-item active">
            <a class="nav-link text-dark" href="/index">Home</a>
        </li>
        <li class="nav-item">
            <a class="nav-link text-dark" href="/forum">Forum</a>
        </li>
        <li class="nav-item">
            <a class="nav-link text-dark" href="/films">Films</a>
        </li>
        <li class="nav-item">
            <a class="nav-link text-dark" href="/actors">Actors</a>
        </li>
    </ul>

    <ul class="navbar-nav my-2 my-lg-0">
        <li class="nav-item">
            <a class="nav-link text-dark" href="/signIn">Sign In</a>
        </li>
        <li class="nav-item">
            <a class="nav-link text-dark" href="/signUp">Sign Up</a>
        </li>
    </ul>

</nav>

<div class="vw-auto vh-100 bg-light py-2">
    <div class="pg-inline mx-3 mt-3 my-lg-0 p-3 bg-white border" style="border-radius:20px">

        <form>
            <div class="form-group">
                <h1>Films List</h1>
                <div class="row  pb-2">
                    <div class="col-md-auto">
                        <input class="form-control" placeholder="Search" id="name" name="name" oninput="showData($('#name').val())">
                    </div>
                </div>
            </div>
        </form>

        <table id="table" class="table">

        </table>
    </div>
</div>


</body>
</html>