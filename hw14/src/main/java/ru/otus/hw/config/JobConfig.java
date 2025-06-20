package ru.otus.hw.config;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.job.flow.support.SimpleFlow;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.data.MongoPagingItemReader;
import org.springframework.batch.item.data.builder.MongoPagingItemReaderBuilder;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.transaction.PlatformTransactionManager;
import ru.otus.hw.models.mongo.MongoAuthor;
import ru.otus.hw.models.mongo.MongoBook;
import ru.otus.hw.models.mongo.MongoComment;
import ru.otus.hw.models.mongo.MongoGenre;
import ru.otus.hw.models.relation.Author;
import ru.otus.hw.models.relation.Book;
import ru.otus.hw.models.relation.Comment;
import ru.otus.hw.models.relation.Genre;
import ru.otus.hw.processor.AuthorMigrateService;
import ru.otus.hw.processor.BookMigrateService;
import ru.otus.hw.processor.CommentMigrateService;
import ru.otus.hw.processor.GenreMigrateService;

import java.util.HashMap;

@Configuration
public class JobConfig {

    private static final int CONVERT_ITEM_SIZE = 5;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private PlatformTransactionManager platformTransactionManager;

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Bean
    public Job migrateJob(Flow authorGenreAsyncFlow, Step transformBookStep, Step transformCommentStep) {
        return new JobBuilder("migrateJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(authorGenreAsyncFlow)
                .next(transformBookStep)
                .next(transformCommentStep)
                .build()
                .build();
    }

    // Reader
    @StepScope
    @Bean
    public MongoPagingItemReader<MongoAuthor> authorReader(MongoOperations mongoOperations) {
        return new MongoPagingItemReaderBuilder<MongoAuthor>()
                .name("authorReader")
                .template(mongoOperations)
                .jsonQuery("{}")
                .targetType(MongoAuthor.class)
                .pageSize(CONVERT_ITEM_SIZE)
                .sorts(new HashMap<>())
                .build();
    }

    @StepScope
    @Bean
    public MongoPagingItemReader<MongoGenre> genreReader(MongoOperations mongoOperations) {
        return new MongoPagingItemReaderBuilder<MongoGenre>()
                .name("genreReader")
                .template(mongoOperations)
                .jsonQuery("{}")
                .targetType(MongoGenre.class)
                .pageSize(CONVERT_ITEM_SIZE)
                .sorts(new HashMap<>())
                .build();
    }

    @StepScope
    @Bean
    public MongoPagingItemReader<MongoBook> bookReader(MongoOperations mongoOperations) {
        return new MongoPagingItemReaderBuilder<MongoBook>()
                .name("bookReader")
                .template(mongoOperations)
                .jsonQuery("{}")
                .targetType(MongoBook.class)
                .pageSize(CONVERT_ITEM_SIZE)
                .sorts(new HashMap<>())
                .build();
    }

    @StepScope
    @Bean
    public MongoPagingItemReader<MongoComment> commentReader(MongoOperations mongoOperations) {
        return new MongoPagingItemReaderBuilder<MongoComment>()
                .name("commentReader")
                .template(mongoOperations)
                .jsonQuery("{}")
                .targetType(MongoComment.class)
                .pageSize(CONVERT_ITEM_SIZE)
                .sorts(new HashMap<>())
                .build();
    }

    // Processor
    @StepScope
    @Bean
    public ItemProcessor<MongoAuthor, Author> authorProcessor(AuthorMigrateService authorMigrateService) {
        return authorMigrateService::save;
    }

    @StepScope
    @Bean
    public ItemProcessor<MongoGenre, Genre> genreProcessor(GenreMigrateService genreMigrateService) {
        return genreMigrateService::save;
    }

    @StepScope
    @Bean
    public ItemProcessor<MongoBook, Book> bookProcessor(BookMigrateService bookMigrateService) {
        return bookMigrateService::save;
    }

    @StepScope
    @Bean
    public ItemProcessor<MongoComment, Comment> commentProcessor(CommentMigrateService commentMigrateService) {
        return commentMigrateService::save;
    }

    // Writer
    @StepScope
    @Bean
    public JpaItemWriter<Author> authorWriter() {
        return new JpaItemWriterBuilder<Author>()
                .entityManagerFactory(entityManagerFactory)
                .build();
    }

    @StepScope
    @Bean
    public JpaItemWriter<Genre> genreWriter() {
        return new JpaItemWriterBuilder<Genre>()
                .entityManagerFactory(entityManagerFactory)
                .build();
    }

    @StepScope
    @Bean
    public JpaItemWriter<Book> bookWriter() {
        return new JpaItemWriterBuilder<Book>()
                .entityManagerFactory(entityManagerFactory)
                .build();
    }

    @StepScope
    @Bean
    public JpaItemWriter<Comment> coomentWriter() {
        return new JpaItemWriterBuilder<Comment>()
                .entityManagerFactory(entityManagerFactory)
                .build();
    }

    // Parallel Flow author + genre
    @Bean
    public Flow authorGenreAsyncFlow(Flow authorFlow, Flow genreFlow) {
        return new FlowBuilder<SimpleFlow>("authorGenreFlow")
                .split(new SimpleAsyncTaskExecutor("authorGenreFlow_Executor"))
                .add(authorFlow, genreFlow)
                .build();
    }

    @Bean
    public Flow authorFlow(Step transformAuthorStep) {
        return new FlowBuilder<SimpleFlow>("authorFlow")
                .start(transformAuthorStep)
                .build();
    }

    @Bean
    public Flow genreFlow(Step transformGenreStep) {
        return new FlowBuilder<SimpleFlow>("genreFlow")
                .start(transformGenreStep)
                .build();
    }

    // step
    @Bean
    public Step transformAuthorStep(ItemReader<MongoAuthor> authorReader, JpaItemWriter<Author> authorWriter,
                                    ItemProcessor<MongoAuthor, Author> authorProcessor) {
        return new StepBuilder("transformAuthorStep", jobRepository)
                .<MongoAuthor, Author>chunk(CONVERT_ITEM_SIZE, platformTransactionManager)
                .reader(authorReader)
                .processor(authorProcessor)
                .writer(authorWriter)
                .build();
    }

    @Bean
    public Step transformGenreStep(ItemReader<MongoGenre> genreReader, JpaItemWriter<Genre> genreWriter,
                                   ItemProcessor<MongoGenre, Genre> genreProcessor) {
        return new StepBuilder("transformGenreStep", jobRepository)
                .<MongoGenre, Genre>chunk(CONVERT_ITEM_SIZE, platformTransactionManager)
                .reader(genreReader)
                .processor(genreProcessor)
                .writer(genreWriter)
                .build();
    }

    @Bean
    public Step transformBookStep(ItemReader<MongoBook> bookReader, JpaItemWriter<Book> bookWriter,
                                  ItemProcessor<MongoBook, Book> bookProcessor) {
        return new StepBuilder("transformBookStep", jobRepository)
                .<MongoBook, Book>chunk(CONVERT_ITEM_SIZE, platformTransactionManager)
                .reader(bookReader)
                .processor(bookProcessor)
                .writer(bookWriter)
                .build();
    }

    @Bean
    public Step transformCommentStep(ItemReader<MongoComment> reader, JpaItemWriter<Comment> writer,
                                     ItemProcessor<MongoComment, Comment> processor) {
        return new StepBuilder("transformCommentStep", jobRepository)
                .<MongoComment, Comment>chunk(CONVERT_ITEM_SIZE, platformTransactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .taskExecutor(new SimpleAsyncTaskExecutor())
                .build();
    }
}
