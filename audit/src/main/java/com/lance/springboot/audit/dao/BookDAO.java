package com.lance.springboot.audit.dao;

import com.lance.springboot.audit.entity.BookEntity;
import org.springframework.data.repository.CrudRepository;

public interface BookDAO extends CrudRepository<BookEntity, Long> {
}
