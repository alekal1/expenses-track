package ee.alekal.constructionexpenses.management.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import ee.alekal.constructionexpenses.common.exception.CEBusinessException;
import ee.alekal.constructionexpenses.common.service.model.ServiceResult;
import ee.alekal.constructionexpenses.expense.model.mapper.ExpenseMapper;
import ee.alekal.constructionexpenses.expense.repository.ExpenseRepository;
import ee.alekal.constructionexpenses.expense.service.ExpenseService;
import ee.alekal.constructionexpenses.management.model.JsonContent;
import ee.alekal.constructionexpenses.tag.model.mapper.TagMapper;
import ee.alekal.constructionexpenses.tag.repository.TagRepository;
import ee.alekal.constructionexpenses.tag.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ManagementService {

    private final TagRepository tagRepository;
    private final ExpenseRepository expenseRepository;
    private final ObjectMapper objectMapper;
    private final TagService tagService;
    private final ExpenseService expenseService;

    public ByteArrayResource exportData() {
        var allTags = tagRepository.findAll();
        var allExpense = expenseRepository.findAll();

        try {
            var exportResponse = JsonContent.builder()
                    .tags(allTags.stream().map(TagMapper.INSTANCE::toTagDto).toList())
                    .expenses(allExpense.stream().map(ExpenseMapper.INSTANCE::toExpenseDto).toList())
                    .build();
            var jsonData = objectMapper.writeValueAsString(exportResponse);
            return new ByteArrayResource(jsonData.getBytes());
        } catch (Exception e) {
            throw new CEBusinessException(e.getMessage());
        }
    }

    public ServiceResult<Object> importData(MultipartFile jsonFile) {
        try {
            var fileContent = objectMapper.readValue(jsonFile.getBytes(), JsonContent.class);

            fileContent.getTags().forEach(tagService::saveNewTag);
            fileContent.getExpenses().forEach(expenseService::saveNewExpense);

            var serviceResult = new ServiceResult<>();
            serviceResult.setMessage("Data populated.");

            return serviceResult;
        } catch (Exception e) {
            throw new CEBusinessException(e.getMessage());
        }
    }
}
