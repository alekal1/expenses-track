package ee.alekal.constructionexpenses.chart.service;

import ee.alekal.constructionexpenses.chart.model.response.ChartResponse;
import ee.alekal.constructionexpenses.common.service.model.ServiceResult;
import ee.alekal.constructionexpenses.expense.model.entity.ExpenseEntity;
import ee.alekal.constructionexpenses.tag.model.entity.TagEntity;
import ee.alekal.constructionexpenses.tag.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class ChartService {

    private final TagRepository tagRepository;

    public ServiceResult<ChartResponse> getChartData() {
        var chartResponse = new ChartResponse();

        var allTags = tagRepository.findAll();

        long totalSum = 0;
        for (TagEntity tagEntity : allTags) {
            var tagTotalExpenses = tagEntity.getExpenses().stream()
                    .map(ExpenseEntity::getAmount)
                    .mapToLong(Long::longValue)
                    .sum();

            chartResponse.addDataset(
                    ChartResponse.Dataset.builder()
                            .x(tagEntity.getName())
                            .y(tagTotalExpenses)
                            .build()
            );
            totalSum += tagTotalExpenses;
        }

        chartResponse.setTotalSum(totalSum);

        var serviceResult = new ServiceResult<ChartResponse>();
        serviceResult.setObject(chartResponse);

        return serviceResult;
    }
}
