$(document).ready(
    function () {
        $('#getCurrencyButton').on("click", function (event) {
            event.preventDefault();
            getCurrency();
        });

        function getCurrency() {
            $.ajax({
                type: "GET",
                url: "getCurrency",
                dataType: 'json',
                data: {"currency" : $('#currencyList').val()},
                success: function (result) {

                    $('#date').empty();
                    $('#currencyName').empty();
                    $('#currencyRate').empty();

                    var date = new Date(result.Date);
                    var currencyName = result.Cur_Name;
                    var currencyRate = result.Cur_OfficialRate;

                    $('#date').append("Date:    " + date.getDate() + " : " + date.getMonth() + " : " + date.getFullYear());
                    $('#currencyName').append("Currency:    " + currencyName);
                    $('#currencyRate').append("Rate to BYN:    " + currencyRate);

                },
                error: function (e) {
                    console.log("ERROR: ", e);
                }

            });
        }
    });