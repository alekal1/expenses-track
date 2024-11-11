package ee.alekal.constructionexpenses.expense.model.mapper;

import ee.alekal.constructionexpenses.expense.model.dto.ExpenseDto;
import ee.alekal.constructionexpenses.expense.model.entity.ExpenseEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ExpenseMapper {

    ExpenseMapper INSTANCE = Mappers.getMapper(ExpenseMapper.class);

    @Mapping(target = "id", ignore = true)
    ExpenseEntity toExpenseEntity(ExpenseDto expenseDto);

    @Mapping(source = "tag.name", target = "tagName")
    ExpenseDto toExpenseDto(ExpenseEntity expense);
}
