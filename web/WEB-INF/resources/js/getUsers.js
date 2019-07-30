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
                    $('#getResult ul').empty();

                    //Проходимся по коллекции, для каждого объекта создаем строку с его данными и добавляем на экран
                    $.each(result,
                        function (i, user) {
                            var resultStr =
                                " Username: "
                                + user.user.username
                                + user.userBankAccounts[0].money
                                + "<p></p>";
                            $('#getResult .list-group').append(
                                resultStr)
                        });
                },
                error: function (e) {
                    $('#getResult ul').empty();
                    console.log("ERROR: ", e);
                }
            });
        }
    });