package ee.alekal.constructionexpenses.common.exception.base;

import lombok.Getter;

@Getter
public class CEBaseException extends IllegalArgumentException {

    private final String message;

    public CEBaseException(String message) {
        super(message);
        this.message = message;
    }
}
