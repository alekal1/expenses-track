package ee.alekal.constructionexpenses.management.model;

import ee.alekal.constructionexpenses.expense.model.dto.ExpenseDto;
import ee.alekal.constructionexpenses.tag.model.dto.TagDto;
import lombok.Builder;
import lombok.Data;

import java.util.Collection;

@Data
@Builder
public class JsonContent {

    private Collection<TagDto> tags;
    private Collection<ExpenseDto> expenses;
}
