package com.lance.audit.dao;

import com.lance.audit.entity.BookEntity;
import org.springframework.data.repository.CrudRepository;

public interface BookDAO extends CrudRepository<BookEntity, Long> {
}
