package exception;

/**
 * Excepcion personalizada en caso de error a la hora de conversión de json a xml
 *
 * @author Oscar Gimnénez
 */
public class GetRequestException extends RuntimeException {

    private static final String ERROR_MSG =
            "Ha ocurrido un error durante el proceso de conversion de XML a JSON";

    private int id;

    public GetRequestException(String message) {
        super(message);
    }

    public GetRequestException(String message, int id) {
        super(message);
        this.setId(id);
    }

    public GetRequestException() {
        super();
    }

    public GetRequestException(String message, Throwable cause, boolean enableSuppression,
                               boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public GetRequestException(Throwable cause) {
        super(ERROR_MSG, cause);
    }

    public GetRequestException(Exception ex) {
        super(ex.getMessage(), ex);
    }

    public GetRequestException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
