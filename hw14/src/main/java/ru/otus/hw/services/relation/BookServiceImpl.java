package ru.otus.hw.services.relation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw.models.relation.Book;
import ru.otus.hw.repositories.relation.BookRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;


    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

}
