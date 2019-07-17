package bank.model.json;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * This class represents currency rate form JSON
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
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
