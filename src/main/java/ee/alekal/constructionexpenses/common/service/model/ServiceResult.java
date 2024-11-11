package ee.alekal.constructionexpenses.common.service.model;

import lombok.Getter;
import lombok.Setter;

@Getter
public class ServiceResult<T> {

    @Setter
    private T object;
    @Getter
    @Setter
    private String message;
}
