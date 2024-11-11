package ee.alekal.constructionexpenses.common.exception;

import ee.alekal.constructionexpenses.common.exception.base.CEBaseException;

public class CEValidationException extends CEBaseException {

    public CEValidationException(String message) {
        super(message);
    }
}
