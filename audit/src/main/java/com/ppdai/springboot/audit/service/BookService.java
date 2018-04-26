package com.ppdai.springboot.audit.service;

import com.ppdai.springboot.audit.dao.BookDAO;
import com.ppdai.springboot.audit.entity.BookEntity;
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
