package ru.otus.hw.services_api;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw.dto.StorageInfoDto;

@RequiredArgsConstructor
@Service
public class StorageBookServiceImpl implements StorageBookService {

    @Override
    public StorageInfoDto findInfo(Long bookId) {
        return new StorageInfoDto(1L, "HRB-432-FND", "AA-01");
    }
}
