package ru.otus.hw.dao;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.stereotype.Repository;
import ru.otus.hw.config.TestFileNameProvider;
import ru.otus.hw.dao.dto.QuestionDto;
import ru.otus.hw.domain.Question;
import ru.otus.hw.exceptions.QuestionReadException;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CsvQuestionDao implements QuestionDao {
    public static final int SKIP_LINES_COUNT = 1;

    private final TestFileNameProvider fileNameProvider;

    public CsvQuestionDao(TestFileNameProvider fileNameProvider) {
        this.fileNameProvider = fileNameProvider;
    }

    @Override
    public List<Question> findAll() {
        InputStream inputStream = getFileFromResourceAsStream(fileNameProvider.getTestFileName());

        List<QuestionDto> questionDtoList = getQuestionDtoList(inputStream);

        return convertQuestionFromDto(questionDtoList);
    }

    private InputStream getFileFromResourceAsStream(String fileName) {
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(fileName);

        if (inputStream == null) {
            throw new QuestionReadException("Unable to load file " + fileName);
        } else {
            return inputStream;
        }
    }

    private List<Question> convertQuestionFromDto(List<QuestionDto> questionDtoList) {
        List<Question> questionList = new ArrayList<>();
        for (QuestionDto questionDto : questionDtoList) {
            questionList.add(questionDto.toDomainObject());
        }
        return questionList;
    }

    private List<QuestionDto> getQuestionDtoList(InputStream inputStream) {
        try (Reader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            CsvToBean<QuestionDto> csvToBean = new CsvToBeanBuilder<QuestionDto>(reader)
                    .withType(QuestionDto.class)
                    .withSkipLines(SKIP_LINES_COUNT)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            return csvToBean.stream().toList();
        } catch (Exception e) {
            throw new QuestionReadException(e.getMessage(), e);
        }
    }
}
