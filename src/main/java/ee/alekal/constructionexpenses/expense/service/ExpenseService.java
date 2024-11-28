package ee.alekal.constructionexpenses.expense.service;

import ee.alekal.constructionexpenses.common.constants.ObjectStatus;
import ee.alekal.constructionexpenses.common.exception.CEBusinessException;
import ee.alekal.constructionexpenses.common.service.model.ServiceResult;
import ee.alekal.constructionexpenses.expense.model.dto.ExpenseDto;
import ee.alekal.constructionexpenses.expense.model.mapper.ExpenseMapper;
import ee.alekal.constructionexpenses.expense.model.request.SearchExpensesRequest;
import ee.alekal.constructionexpenses.expense.model.response.SearchExpensesResponse;
import ee.alekal.constructionexpenses.expense.repository.ExpenseRepository;
import ee.alekal.constructionexpenses.tag.repository.TagRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final ExpenseRepository repository;
    private final TagRepository tagRepository;

    @Transactional
    public ServiceResult<ExpenseDto> saveNewExpense(ExpenseDto expense) {
        var mappedEntity = ExpenseMapper.INSTANCE.toExpenseEntity(expense);

        var tagEntity = tagRepository.findByName(expense.getTagName())
                .orElseThrow(() -> new CEBusinessException("Tag not found!"));
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

    public ServiceResult<Object> deleteExpense(Long id) {
        var expenseEntity = expenseRepository.findById(id)
                .orElseThrow(() -> new CEBusinessException("Expense not found!"));

        expenseEntity.setStatus(ObjectStatus.DELETED.getValue());

        repository.saveAndFlush(expenseEntity);

        var serviceResult = new ServiceResult<>();
        serviceResult.setMessage("Expense deleted!");

        return serviceResult;
    }
}
