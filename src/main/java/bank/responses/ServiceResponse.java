package bank.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Class that contains status of response to AJAX and object with response.
 * @param <T> - object, that contains all information you need in response.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceResponse<T> {
    private String status;
    private T data;
}
