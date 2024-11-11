package ee.alekal.constructionexpenses.expense.repository;

import ee.alekal.constructionexpenses.expense.model.entity.ExpenseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ExpenseRepository extends JpaRepository<ExpenseEntity, Long> {

    @Query(value = "SELECT e.* FROM expense e " +
            "LEFT JOIN tag t on t.id = e.tag_id WHERE " +
            "(:tagName is null OR t.id IN (SELECT id FROM tag WHERE tag.name = :tagName))", nativeQuery = true)
    Page<ExpenseEntity> findAllExpenses(
            @Param("tagName") String tagName,
            Pageable pageable
    );
}
