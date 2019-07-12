package bank.model.json;

import com.google.gson.annotations.SerializedName;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CurrencyRate {

    @SerializedName(value = "Cur_ID")
    private int id;

    @SerializedName(value = "Date")
    private String date;

    @SerializedName(value = "Cur_Abbreviation")
    private String abbreviation;

    @SerializedName(value = "Cur_Scale")
    private double scale;

    @SerializedName(value = "Cur_Name")
    private String name;

    @SerializedName(value = "Cur_OfficialRate")
    private double rate;

}
