package ee.alekal.constructionexpenses.tag.controller;

import ee.alekal.constructionexpenses.common.response.CEResponse;
import ee.alekal.constructionexpenses.tag.model.dto.TagDto;
import ee.alekal.constructionexpenses.tag.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/tag")
public class TagController {

    private final TagService service;

    @PostMapping
    public ResponseEntity<CEResponse<TagDto>> saveNewTag(@RequestBody TagDto tagRequestBody) {
        var serviceResult = service.saveNewTag(tagRequestBody);

        var ceResponse = CEResponse.<TagDto>builder()
                .data(serviceResult.getObject())
                .httpStatus(HttpStatus.OK.value())
                .build();

        return ResponseEntity.ok(ceResponse);
    }

    @GetMapping
    public ResponseEntity<CEResponse<List<TagDto>>> getAllTags() {
        var serviceResult = service.getAllTags();

        var ceResponse = CEResponse.<List<TagDto>>builder()
                .data(serviceResult.getObject())
                .httpStatus(HttpStatus.OK.value())
                .build();

        return ResponseEntity.ok(ceResponse);
    }
}
