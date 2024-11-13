package ee.alekal.constructionexpenses.tag.service.validator;

import ee.alekal.constructionexpenses.common.exception.CEValidationException;
import ee.alekal.constructionexpenses.tag.model.dto.TagDto;
import ee.alekal.constructionexpenses.tag.model.entity.TagEntity;
import ee.alekal.constructionexpenses.tag.repository.TagRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class TagValidatorTest {

    private TagRepository tagRepository;

    private TagValidator tagValidator;

    @BeforeEach
    void init() {
        tagRepository = mock(TagRepository.class);

        tagValidator = new TagValidator(tagRepository);
    }

    @Test
    void givenNonDuplicateEntityInDatabase_whenValidateNewTag_shouldNoThrowError() {
        var tagDto = TagDto.builder()
                        .name("test")
                        .build();

        doReturn(Optional.empty())
                .when(tagRepository)
                .findByName(tagDto.getName());

        assertDoesNotThrow(() -> tagValidator.validateSaveNewTag(tagDto));
    }

    @Test
    void givenDuplicateEntityInDatabase_whenValidateNewTag_shouldThrowError() {
        var tagDto = TagDto.builder()
                .name("test")
                .build();

        var existingEntity = new TagEntity();
        existingEntity.setName("test");

        doReturn(Optional.of(existingEntity))
                .when(tagRepository)
                .findByName(tagDto.getName());

        assertThrows(CEValidationException.class, () -> tagValidator.validateSaveNewTag(tagDto));
    }
}
