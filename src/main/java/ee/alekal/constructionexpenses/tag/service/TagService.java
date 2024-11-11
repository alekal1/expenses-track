package ee.alekal.constructionexpenses.tag.service;

import ee.alekal.constructionexpenses.common.service.model.ServiceResult;
import ee.alekal.constructionexpenses.tag.model.dto.TagDto;
import ee.alekal.constructionexpenses.tag.model.mapper.TagMapper;
import ee.alekal.constructionexpenses.tag.repository.TagRepository;
import ee.alekal.constructionexpenses.tag.service.validator.TagValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TagService {

    private final TagRepository repository;
    private final TagValidator validator;

    public ServiceResult<TagDto> saveNewTag(TagDto tag) {
        validator.validateSaveNewTag(tag);

        var mappedEntity = TagMapper.INSTANCE.toTagEntity(tag);
        var savedEntity = repository.save(mappedEntity);

        var serviceResult = new ServiceResult<TagDto>();
        serviceResult.setObject(TagMapper.INSTANCE.toTagDto(savedEntity));

        return serviceResult;
    }
}
