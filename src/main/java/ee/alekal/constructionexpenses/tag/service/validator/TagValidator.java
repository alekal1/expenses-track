package ee.alekal.constructionexpenses.tag.service.validator;

import ee.alekal.constructionexpenses.common.exception.CEValidationException;
import ee.alekal.constructionexpenses.tag.model.dto.TagDto;
import ee.alekal.constructionexpenses.tag.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TagValidator {

    private final TagRepository tagRepository;

    public void validateSaveNewTag(TagDto tagDto) {
        tagRepository.findByName(tagDto.getName())
                .ifPresent(ignore -> {
                    throw new CEValidationException("Tag already exists!");
                });
    }
}
