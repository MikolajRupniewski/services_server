package pl.rupniewski.service_server.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.logging.Logger;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFundException extends RuntimeException {

    private final Logger LOGGER = Logger.getLogger(ResourceNotFundException.class.getName());
    private String resourceName;
    private String fieldName;
    private Object fieldValue;

    public ResourceNotFundException(String resourceName, String fieldName, Object fieldValue) {
        super(String.format("%s not found with %s : %s", resourceName, fieldName, fieldValue));
        LOGGER.warning(String.format("%s not found with %s : %s", resourceName, fieldName, fieldValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    public String getResourceName() {
        return resourceName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public Object getFieldValue() {
        return fieldValue;
    }
}
