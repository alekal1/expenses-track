package ee.alekal.constructionexpenses.tag.model.entity;

import ee.alekal.constructionexpenses.expense.model.entity.ExpenseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collection;

@Getter
@Setter
@Entity
@Table(name = "tag")
public class TagEntity {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany
    private Collection<ExpenseEntity> expenses = new ArrayList<>();

    public void addExpense(ExpenseEntity expense) {
        expenses.add(expense);
    }
}
