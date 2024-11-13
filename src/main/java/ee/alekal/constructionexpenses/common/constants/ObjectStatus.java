package ee.alekal.constructionexpenses.common.constants;

import lombok.Getter;

@Getter
public enum ObjectStatus {

    CURRENT("CURRENT"),
    DELETED("DELETED");

    final String value;

    ObjectStatus(String value) {
        this.value = value;
    }
}
