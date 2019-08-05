$(document).ready(
    function () {
        $('#getBankAccountInfo').click(function (event) {
            event.preventDefault();
            ajaxGet();
        });

        function ajaxGet() {
            $.ajax({
                type: "GET",
                url: "filterBankAccounts",
                data: {"bankAccountId": $("#bankAccountsList").val()},
                success: function (result) {
                    $('#money_value').empty();

                    var money_value = result.data.money;
                    $('#money_value').append("Money on account: " + money_value);
                },
                error: function (e) {
                    console.log("ERROR: ", e);
                }

            });
        }
    });