package bank.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * This class using for send response back to JSON from controller
 * @param <T>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ServiceResponse<T> {
    private String status;
    private T data;
}
