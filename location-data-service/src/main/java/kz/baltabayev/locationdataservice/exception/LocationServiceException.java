package kz.baltabayev.locationdataservice.exception;

public class LocationServiceException extends RuntimeException {

    public LocationServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
