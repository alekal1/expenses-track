package ee.alekal.constructionexpenses.expense.model.response;

import ee.alekal.constructionexpenses.expense.model.dto.ExpenseDto;
import lombok.Data;

import java.util.Collection;

@Data
public class SearchExpensesResponse {

    private Collection<ExpenseDto> content;
    private Integer totalPages;
    private Long totalElements;
    private Integer pageNumber;
    private Integer pageSize;
}
