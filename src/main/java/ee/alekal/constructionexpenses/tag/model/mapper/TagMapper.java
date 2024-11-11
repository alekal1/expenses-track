package ee.alekal.constructionexpenses.tag.model.mapper;

import ee.alekal.constructionexpenses.tag.model.dto.TagDto;
import ee.alekal.constructionexpenses.tag.model.entity.TagEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TagMapper {

    TagMapper INSTANCE = Mappers.getMapper(TagMapper.class);

    TagDto toTagDto(TagEntity tagEntity);

    TagEntity toTagEntity(TagDto tagDto);
}
