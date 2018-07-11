package com.lance.springboot.audit.service;

import com.lance.springboot.audit.entity.BookEntity;
import com.lance.springboot.audit.dao.BookDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookService {

    @Autowired
    private BookDAO bookDAO;

    public BookEntity addBook(BookEntity bookEntity) {
        BookEntity entity = bookDAO.save(bookEntity);
        return entity;
    }

}
