<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Sign In</title>

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
</head>
<body>
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
        <div class="container">
            <form action="/signIn" method="post">
                <div class="form-group">
                    <div class="row justify-content-center pb-2">
                        <div class="col-md-auto">

                            <input type="email" class="form-control" name="email" placeholder="Email">

                        </div>
                    </div>
                    <div class="row justify-content-center pb-2">
                        <div class="col-md-auto">

                            <input type="password" class="form-control" name="password" placeholder="Password">

                        </div>
                    </div>
                    <div class="row justify-content-center pb-2">
                        <div class="col-md-auto">
                            <input type="submit" class="btn btn-warning" value="Sign In">
                        </div>
                    </div>
                </div>
            </form>

        </div>
    </div>
</div>
</body>
</html>
