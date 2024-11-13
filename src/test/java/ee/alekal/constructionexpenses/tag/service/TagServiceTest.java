package ee.alekal.constructionexpenses.tag.service;

import ee.alekal.constructionexpenses.tag.model.dto.TagDto;
import ee.alekal.constructionexpenses.tag.model.entity.TagEntity;
import ee.alekal.constructionexpenses.tag.repository.TagRepository;
import ee.alekal.constructionexpenses.tag.service.validator.TagValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class TagServiceTest {

    private TagRepository tagRepository;
    private TagValidator tagValidator;

    private TagService tagService;

    @BeforeEach
    void init() {
        tagRepository = mock(TagRepository.class);
        tagValidator = mock(TagValidator.class);

        tagService = new TagService(
                tagRepository,
                tagValidator
        );
    }

    @Test
    void givenTagDtoRequest_whenSaveNewTag_shouldSaveEntityAndReturnItsDto() {
        var entityToBeSaved = new TagEntity();
        entityToBeSaved.setName("test tag");

        var tagRequest = TagDto.builder()
                .name("test tag")
                .build();

        doReturn(entityToBeSaved).when(tagRepository).save(any(TagEntity.class));

        var serviceResult = tagService.saveNewTag(tagRequest);

        assertNotNull(serviceResult);
        assertNotNull(serviceResult.getObject());

        assertEquals(tagRequest.getName(), serviceResult.getObject().getName());
    }

    @Test
    void givenEmptyTagTable_whenGetAllTags_shouldReturnEmptyList() {

        doReturn(List.of()).when(tagRepository).findAll();

        var serviceResult = tagService.getAllTags();

        assertNotNull(serviceResult);
        assertNotNull(serviceResult.getObject());

        var responseObject = serviceResult.getObject();

        assertTrue(responseObject.isEmpty());
    }

    @Test
    void givenNonEmptyTagTable_whenGetAllTags_shouldReturnNonEmptyList() {
        var entity = new TagEntity();
        entity.setName("testTag");

        doReturn(List.of(entity)).when(tagRepository).findAll();

        var serviceResult = tagService.getAllTags();

        assertNotNull(serviceResult);
        assertNotNull(serviceResult.getObject());

        var responseObject = serviceResult.getObject();

        assertFalse(responseObject.isEmpty());
    }
}
