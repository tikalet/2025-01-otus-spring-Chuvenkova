package ru.otus.hw.repositories;

import org.springframework.data.annotation.Id;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import ru.otus.hw.models.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @PostFilter("hasPermission(filterObject, 'read')")
    @Query("SELECT bc FROM Comment bc WHERE bc.book.id = :bookId")
    List<Comment> findByBookId(long bookId);

    @PreAuthorize("hasAuthority('COMMENT_EDITOR') || hasPermission(filterObject, 'read')")
    Optional<Comment> findById(Id id);
}
