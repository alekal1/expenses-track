package ee.alekal.constructionexpenses.expense.service.validator;

import ee.alekal.constructionexpenses.common.exception.CEValidationException;
import ee.alekal.constructionexpenses.expense.model.dto.ExpenseDto;
import ee.alekal.constructionexpenses.expense.repository.ExpenseRepository;
import ee.alekal.constructionexpenses.tag.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExpenseValidator {

    private final ExpenseRepository expenseRepository;
    private final TagRepository tagRepository;

    public void validateSaveNewExpense(ExpenseDto expenseDto) {
        var tagEntity = tagRepository.findByName(expenseDto.getTagName());
        if (tagEntity.isEmpty()) {
            throw new CEValidationException("Tag '" + expenseDto.getTagName() + "' not found!");
        }
    }

    public void validatePathExpense(ExpenseDto expenseDto) {
        var expense = expenseRepository.findById(expenseDto.getId());
        if (expense.isEmpty()) {
            throw new CEValidationException("Expense with id '" + expenseDto.getId() + "' not found!");
        }
    }
}
