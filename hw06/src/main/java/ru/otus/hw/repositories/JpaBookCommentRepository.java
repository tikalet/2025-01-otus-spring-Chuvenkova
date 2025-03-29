package ru.otus.hw.repositories;

import jakarta.persistence.EntityGraph;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.otus.hw.models.BookComment;

import java.util.List;
import java.util.Optional;

import static org.springframework.data.jpa.repository.EntityGraph.EntityGraphType.FETCH;

@Repository
public class JpaBookCommentRepository implements BookCommentRepository {

    @PersistenceContext
    private final EntityManager entityManager;

    @Autowired
    public JpaBookCommentRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Optional<BookComment> findById(long id) {
        EntityGraph<?> entityGraph = entityManager.getEntityGraph("book-comment-entity-graph");
        TypedQuery<BookComment> query = entityManager.createQuery(
                "SELECT bc FROM BookComment bc WHERE bc.id = :id",
                BookComment.class);
        query.setHint(FETCH.getKey(), entityGraph);
        query.setParameter("id", id);
        return query.getResultList().stream().findFirst();
    }

    @Override
    public List<BookComment> findByBookId(long bookId) {
        TypedQuery<BookComment> query = entityManager.createQuery(
                "SELECT bc FROM BookComment bc WHERE bc.book.id = :book_id",
                BookComment.class);
        query.setParameter("book_id", bookId);
        return query.getResultList();
    }

    @Override
    public BookComment save(BookComment bookComment) {
        if (bookComment.getId() == 0) {
            return insert(bookComment);
        }
        return update(bookComment);
    }

    @Override
    public void deleteById(long id) {
        Optional<BookComment> bookComment = findById(id);
        bookComment.ifPresent(entityManager::remove);
    }

    private BookComment insert(BookComment bookComment) {
        entityManager.persist(bookComment);
        return bookComment;
    }

    private BookComment update(BookComment bookComment) {
        return entityManager.merge(bookComment);
    }
}
