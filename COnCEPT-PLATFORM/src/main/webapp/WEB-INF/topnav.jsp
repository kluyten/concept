<!DOCTYPE html>
<html>
    <head>
        <title>Dashboard &mdash; Concept</title>
        <link rel="icon" href="resources/img/icon.svg" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0">

        <link rel="stylesheet" href="resources/css/bootstrap.min.css" type="text/css" />
        <link rel="stylesheet" href="resources/css/home.css" type="text/css" />	
        <link rel="stylesheet" href="resources/css/navbar.css" type="text/css" />	
    </head>
    <body>
        <!-- navbar -->
        <nav class="nav-main">
            <ul>
                <a href="http://localhost:8080/dashboard"><li class="main"><img src="resources/img/BUTTON_DASHBOARD.png">Dashboard</li></a>
                <!-- <a onclick="setVisibility('chatbar', 'inline');"><li class="communication"></li></a> -->
                <a onclick="TogetherJS(this);
                        return false;"><li class="collaboration"></li></a>
                <!-- <a href="http://192.168.3.5"><li class="pm">Project Management</li></a>
                <a href="http://192.168.3.17:9001"><li class="ba">Brief Analysis</li></a>
                <a href="#"><li class="ms">Moodboard & Storyboard</li></a>
                <a href="#"><li class="mb">Mind Mapping & Brainstorming</li></a>
                <a href="http://192.168.3.6/sketching"><li class="sk">Sketching</li></a>
                <a href="http://192.168.3.5:8080/CSUI/"><li class="vs">Visual Search</li></a>
                <a href="http://192.168.3.6/wordpress"><li class="wp">WordPress</li></a> -->
            </ul>
        </nav>

        <!-- chatbar -->
        <nav class="nav-chat">

            <div class="nav-header">
                <img class="icon" src="resources/img/BUTTON_COMMUNICATION.png">
                <a href="#"><img class="icon settings" src="resources/img/BUTTON_SETTINGS.png"></a>
                <p class="heading">Chat module</p>
                <p class="title">Logged in as <span style="color:#888">User One</span></p>
            </div>

            <div class="nav-content">
                <div class="nav-content-header">
                    Project XYZ &raquo; User List
                </div>
                <ul>

                    <li>
                        <img class="user" src="resources/img/BUTTON_USER.png">
                        <img class="chat" src="resources/img/BUTTON_CHAT.png">
                        <p class="heading">User Two</p>
                        <p class="title">user2@concept-fp7.eu</p>
                    </li>

                    <li>
                        <img class="user" src="resources/img/BUTTON_USER.png">
                        <img class="chat" src="resources/img/BUTTON_CHAT.png">
                        <p class="heading">User Three with long name</p>
                        <p class="title">user3@concept-fp7.eu</p>
                    </li>

                    <li>
                        <img class="user" src="resources/img/BUTTON_USER.png">
                        <img class="chat" src="resources/img/BUTTON_CHAT.png">
                        <p class="heading">User Four</p>
                        <p class="title">user4@concept-fp7.eu</p>
                    </li>

                    <li>
                        <img class="user" src="resources/img/BUTTON_USER.png">
                        <img class="chat" src="resources/img/BUTTON_CHAT.png">
                        <p class="heading">User Five</p>
                        <p class="title">user5@concept-fp7.eu</p>
                    </li>

                    <li>
                        <img class="user" src="resources/img/BUTTON_USER.png">
                        <img class="chat" src="resources/img/BUTTON_CHAT.png">
                        <p class="heading">User Six</p>
                        <p class="title">user6@concept-fp7.eu</p>
                    </li>

                    <li>
                        <img class="user" src="resources/img/BUTTON_USER.png">
                        <img class="chat" src="resources/img/BUTTON_CHAT.png">
                        <p class="heading">User Seven</p>
                        <p class="title">user7@concept-fp7.eu</p>
                    </li>
                </ul>
            </div>

        </nav>

        <!-- placeholder -->
        <nav class="nav-chat-hidden">
            <div class="nav-header">
                <img class="icon" src="resources/img/BUTTON_COMMUNICATION.png">
                <a href="#"><img class="icon settings" src="resources/img/BUTTON_SETTINGS.png"></a>
                <p class="heading">Chat module</p>
                <p class="title">Module hidden</p>
            </div>
        </div>

        <!-- Javascript imports -->
        <script src="resources/js/jquery-1.11.2.min.js" ></script>
        <script src="resources/js/topnav.js"></script>
        <script src="resources/js/togetherjs-min.js"></script>
</body>
</html>