package ee.alekal.constructionexpenses.chart.controller;

import ee.alekal.constructionexpenses.chart.model.response.ChartResponse;
import ee.alekal.constructionexpenses.chart.service.ChartService;
import ee.alekal.constructionexpenses.common.response.CEResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/chart")
public class ChartController {

    private final ChartService service;

    @GetMapping
    public ResponseEntity<CEResponse<ChartResponse>> getChartData() {
        var serviceResult = service.getChartData();

        var ceResponse = CEResponse.<ChartResponse>builder()
                .data(serviceResult.getObject())
                .httpStatus(HttpStatus.OK.value())
                .build();

        return ResponseEntity.ok(ceResponse);
    }
}
