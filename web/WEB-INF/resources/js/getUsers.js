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
                        var userBankAccountToShow = 'Cannot find bank account';
                            if (user.userBankAccounts != null && user.userBankAccounts[0] != null && user.userBankAccounts[0].money != null) {
                                    userBankAccountToShow = user.userBankAccounts[0].money;
                            }
                            resultStr+=
                                "    <tr>\n" +
                                "        <th scope=\"row\">" + user.user.id + "</th>\n" +
                                "        <td>" + user.user.username + "</td>\n" +
                                "        <td>\n" + user.user.password + "</td>\n" +


                                "        <td>" + userBankAccountToShow + "</td>\n" +
                                "    </tr>\n";
                        });
                    resultStr += "</tbody>\n";
                    $('#table').append(
                        resultStr)
                },
                error: function (e) {
                    console.log("ERROR: ", e);
                }
            });
        }
    });