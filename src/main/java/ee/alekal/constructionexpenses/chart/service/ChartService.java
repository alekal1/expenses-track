package ee.alekal.constructionexpenses.chart.service;

import ee.alekal.constructionexpenses.chart.model.response.ChartResponse;
import ee.alekal.constructionexpenses.common.service.model.ServiceResult;
import ee.alekal.constructionexpenses.expense.model.entity.ExpenseEntity;
import ee.alekal.constructionexpenses.tag.model.entity.TagEntity;
import ee.alekal.constructionexpenses.tag.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChartService {

    private final TagRepository tagRepository;

    public ServiceResult<ChartResponse> getChartData() {
        var chartResponse = new ChartResponse();

        var allTags = tagRepository.findAll();

        var dataset = new ChartResponse.Dataset();
        dataset.setLabel("Expenses by tag");

        for (TagEntity tagEntity : allTags) {
            chartResponse.addLabel(tagEntity.getName());

            var tagExpenses = tagEntity.getExpenses().stream()
                    .map(ExpenseEntity::getAmount)
                    .mapToLong(Long::longValue)
                    .sum();

            dataset.addData(tagExpenses);
        }

        chartResponse.setDatasets(List.of(dataset));

        var serviceResult = new ServiceResult<ChartResponse>();
        serviceResult.setObject(chartResponse);

        return serviceResult;
    }
}
