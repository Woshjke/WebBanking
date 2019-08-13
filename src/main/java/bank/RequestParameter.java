package bank;

/**
 * Enum with server response parameters.
 */
public enum RequestParameter {
    TRUE("true");

    String value;

    RequestParameter(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
