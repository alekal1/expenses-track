package ee.alekal.constructionexpenses.chart.model.response;

import lombok.Data;

import java.util.ArrayList;
import java.util.Collection;

@Data
public class ChartResponse {

    private Collection<String> labels = new ArrayList<>();
    private Collection<Dataset> datasets = new ArrayList<>();

    public void addLabel(String label) {
        labels.add(label);
    }

    @Data
    public static class Dataset {
        private String label;
        private Collection<Long> data = new ArrayList<>();

        public void addData(Long sum) {
            data.add(sum);
        }
    }
}
