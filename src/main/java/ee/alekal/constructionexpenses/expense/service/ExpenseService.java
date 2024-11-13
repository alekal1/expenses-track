package ee.alekal.constructionexpenses.expense.service;

import ee.alekal.constructionexpenses.common.constants.ObjectStatus;
import ee.alekal.constructionexpenses.common.exception.CEBusinessException;
import ee.alekal.constructionexpenses.common.service.model.ServiceResult;
import ee.alekal.constructionexpenses.expense.model.dto.ExpenseDto;
import ee.alekal.constructionexpenses.expense.model.mapper.ExpenseMapper;
import ee.alekal.constructionexpenses.expense.model.request.SearchExpensesRequest;
import ee.alekal.constructionexpenses.expense.model.response.SearchExpensesResponse;
import ee.alekal.constructionexpenses.expense.repository.ExpenseRepository;
import ee.alekal.constructionexpenses.expense.service.validator.ExpenseValidator;
import ee.alekal.constructionexpenses.tag.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class ExpenseService {

    private final ExpenseValidator validator;
    private final ExpenseRepository expenseRepository;
    private final ExpenseRepository repository;
    private final TagRepository tagRepository;

    public ServiceResult<ExpenseDto> saveNewExpense(ExpenseDto expense) {
        validator.validateSaveNewExpense(expense);

        var mappedEntity = ExpenseMapper.INSTANCE.toExpenseEntity(expense);
        var tagEntity = tagRepository.findByName(expense.getTagName()).get();
        mappedEntity.setTag(tagEntity);
        mappedEntity.setStatus(ObjectStatus.CURRENT.getValue());
        tagEntity.addExpense(mappedEntity);

        var savedExpenseEntity = expenseRepository.save(mappedEntity);

        var serviceResult = new ServiceResult<ExpenseDto>();
        serviceResult.setObject(ExpenseMapper.INSTANCE.toExpenseDto(savedExpenseEntity));

        return serviceResult;
    }

    public ServiceResult<SearchExpensesResponse> searchExpenses(Pageable pageable,
                                                                SearchExpensesRequest searchExpensesRequest) {
        var serviceResult = new ServiceResult<SearchExpensesResponse>();

        var expenseEntities = repository.findAllExpenses(
                searchExpensesRequest.getTagName(),
                PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort()));

        Page<ExpenseDto> expenseDtos =
                expenseEntities.map(ExpenseMapper.INSTANCE::toExpenseDto);
        SearchExpensesResponse searchExpensesResponse = getSearchExpenseResponse(expenseDtos);

        serviceResult.setObject(searchExpensesResponse);

        return serviceResult;
    }

    private SearchExpensesResponse getSearchExpenseResponse(Page<ExpenseDto> expenseDtos) {
        SearchExpensesResponse response = new SearchExpensesResponse();

        response.setContent(expenseDtos.getContent());
        response.setTotalPages(expenseDtos.getTotalPages());
        response.setTotalElements(expenseDtos.getTotalElements());
        response.setPageSize(expenseDtos.getSize());
        response.setPageNumber(expenseDtos.getNumber() + 1);

        return response;
    }

    public void deleteExpense(Long id) {
        var expenseEntityOptional = repository.findById(id);
        if (expenseEntityOptional.isEmpty()) {
            throw new CEBusinessException("Expense not found!");
        }

        var entity = expenseEntityOptional.get();
        entity.setStatus(ObjectStatus.DELETED.getValue());

        repository.saveAndFlush(entity);
    }
}
