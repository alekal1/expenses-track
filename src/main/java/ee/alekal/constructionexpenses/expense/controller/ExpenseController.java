package ee.alekal.constructionexpenses.expense.controller;

import ee.alekal.constructionexpenses.common.response.CEResponse;
import ee.alekal.constructionexpenses.expense.model.dto.ExpenseDto;
import ee.alekal.constructionexpenses.expense.model.request.SearchExpensesRequest;
import ee.alekal.constructionexpenses.expense.model.response.SearchExpensesResponse;
import ee.alekal.constructionexpenses.expense.service.ExpenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/expense")
public class ExpenseController {

    private final ExpenseService service;

    @PostMapping
    public ResponseEntity<CEResponse<ExpenseDto>> saveNewExpense(@RequestBody ExpenseDto requestBody) {
        var serviceResult = service.saveNewExpense(requestBody);

        var ceResponse = CEResponse.<ExpenseDto>builder()
                .data(serviceResult.getObject())
                .httpStatus(HttpStatus.OK.value())
                .build();

        return ResponseEntity.ok(ceResponse);
    }


    @GetMapping
    public ResponseEntity<CEResponse<SearchExpensesResponse>> searchExpenses(
            Pageable pageable,
            SearchExpensesRequest expensesRequest) {
        var serviceResult = service.searchExpenses(pageable, expensesRequest);

        var ceResponse = CEResponse.<SearchExpensesResponse>builder()
                .data(serviceResult.getObject())
                .httpStatus(HttpStatus.OK.value())
                .build();

        return ResponseEntity.ok(ceResponse);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<CEResponse<Object>> deleteExpense(@PathVariable Long id) {
        var serviceResult = service.deleteExpense(id);

        var ceResponse = CEResponse.builder()
                .data(null)
                .httpStatus(HttpStatus.OK.value())
                .message(serviceResult.getMessage())
                .build();

        return ResponseEntity.ok(ceResponse);
    }
}
