$(document).ready(
    function () {
        $('#getAllUsers').click(function (event) {
            event.preventDefault();
            ajaxGet();
        });

        function ajaxGet() {
            $.ajax({
                type: "GET",
                url: "filterBankAccounts",
                data: {"username": $("#username").val()},
                success: function (result) {

                    //Стереть прошлый результат
                    $('#getResult ul').empty();

                    //Проходимся по коллекции, для каждого объекта создаем строку с его данными и добавляем на экран
                    $.each(result.data,
                        function (i, bankAccount) {
                            var resultStr = "Id: "
                                + bankAccount.id + "<br>"
                                + " Money:  "
                                + bankAccount.money
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