package ee.alekal.constructionexpenses.management.controller;

import ee.alekal.constructionexpenses.common.response.CEResponse;
import ee.alekal.constructionexpenses.management.service.ManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/management")
public class ManagementController {

    private final ManagementService service;

    @GetMapping
    public ResponseEntity<Resource> exportData() {
        var resource = service.exportData();

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=ce_data.json")
                .contentLength(resource.contentLength())
                .contentType(MediaType.APPLICATION_JSON)
                .body(resource);
    }

    @PostMapping
    public ResponseEntity<CEResponse<Object>> importData(@RequestParam("file") MultipartFile file) {
        var serviceResult = service.importData(file);

        var ceResponse = CEResponse.builder()
                .data(null)
                .message(serviceResult.getMessage())
                .httpStatus(200)
                .build();

        return ResponseEntity.ok(ceResponse);
    }
}
