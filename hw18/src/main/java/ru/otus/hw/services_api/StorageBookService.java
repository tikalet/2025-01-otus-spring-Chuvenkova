package ru.otus.hw.services_api;

import ru.otus.hw.dto.StorageInfoDto;

public interface StorageBookService {

    StorageInfoDto findInfo(Long bookId);
}
