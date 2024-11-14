package ee.alekal.constructionexpenses.chart.model.response;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.Collection;

@Data
public class ChartResponse {

    private Long totalSum;
    private Collection<Dataset> datasets = new ArrayList<>();

    public void addDataset(Dataset dataset) {
        datasets.add(dataset);
    }

    @Data
    @Builder
    public static class Dataset {
        private String x; // Tag name
        private Long y; // Tag's total
    }
}
