package com.ppdai.springboot.audit.dao;

import com.ppdai.springboot.audit.entity.BookEntity;
import org.springframework.data.repository.CrudRepository;

public interface BookDAO extends CrudRepository<BookEntity, Long> {
}
