$(document).ready(
    function () {
        $('#getAllUsers').click(function (event) {
            event.preventDefault();
            ajaxGet();
        });

        function ajaxGet() {
            $.ajax({
                type: "GET",
                url: "filterUsers",
                data: {"username": $("#username").val()},
                success: function (result) {

                    //Стереть прошлый результат
                    $('#table').empty();

                    //Проходимся по коллекции, для каждого объекта создаем строку с его данными и добавляем на экран
                    var resultStr =
                        "<thead>\n" +
                        "    <tr>\n" +
                        "        <th scope=\"col\">ID</th>\n" +
                        "        <th scope=\"col\">Username</th>\n" +
                        "        <th scope=\"col\">Password</th>\n" +
                        "        <th scope=\"col\">Money on first bank account</th>\n" +
                        "    </tr>\n" +
                        "    </thead>\n" +
                        "    <tbody>\n";
                    $.each(result,
                        function (i, user) {
                            resultStr+=
                                "    <tr>\n" +
                                "        <th scope=\"row\">" + user.user.id + "</th>\n" +
                                "        <td>" + user.user.username + "</td>\n" +
                                "        <td>\n" + user.user.password + "</td>\n" +
                                "        <td>" + user.userBankAccounts[0].money + "</td>\n" +
                                "    </tr>\n";
                        });
                    resultStr += "</tbody>\n";
                    $('#table').append(
                        resultStr)
                },
                error: function (e) {
                    $('#getResult ul').empty();
                    console.log("ERROR: ", e);
                }
            });
        }
    });