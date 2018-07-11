package com.lance.springboot.audit.controller;

import com.lance.springboot.audit.entity.BookEntity;
import com.lance.springboot.audit.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public String addBook(@RequestBody BookEntity bookEntity) {
        BookEntity entity = bookService.addBook(bookEntity);
        return entity.getId() + ", " + entity.getName();
    }

}
