package ee.alekal.constructionexpenses.common.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CEResponse<OBJ> {
    private String message;
    private Integer httpStatus;
    private OBJ data;
}
