package ee.alekal.constructionexpenses.tag.repository;

import ee.alekal.constructionexpenses.tag.model.entity.TagEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TagRepository extends JpaRepository<TagEntity, Long> {

    Optional<TagEntity> findByName(String name);
}
