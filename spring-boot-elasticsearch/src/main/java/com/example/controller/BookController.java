package com.example.controller;

import com.example.common.NormalException;
import com.example.common.Result;
import com.example.dao.BookRepository;
import com.example.entity.Book;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.querydsl.QuerydslUtils;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Optional;

@RestController
@RequestMapping("/api/book")
public class BookController {
    @Autowired
    private BookRepository bookRepository;

    @GetMapping("/all")
    public Result<Iterable<Book>> getAll() {
        Iterable<Book> list = bookRepository.findAll();
        return Result.builder().success().message("查询成功").data(list).build();
    }

    @PostMapping("/add")
    public Result add(@RequestBody Book book) {
        book.setId(null);
        bookRepository.save(book);
        return Result.builder().success().message("保存成功").build();
    }

    @DeleteMapping("/delete")
    public Result delete(@RequestParam String id) {
        Optional<Book> optional = bookRepository.findById(id);
        if (!optional.isPresent()) {
            throw new NormalException("没有此纪录");
        }
        bookRepository.deleteById(id);
        return Result.builder().success().message("删除成功").build();
    }

    @PutMapping("/update")
    public Result update(@RequestBody Book book) {
        Optional<Book> optional = bookRepository.findById(book.getId());
        if (!optional.isPresent()) {
            throw new NormalException("没有此纪录");
        }
        Book book1 = optional.get();
        book1.setBookName(book.getBookName());
        book1.setAuthor(book.getAuthor());
        book1.setPress(book.getPress());
        book1.setPublishedDate(book.getPublishedDate());
        bookRepository.save(book1);
        return Result.builder().success().message("更新成功").build();
    }

    @GetMapping("/get")
    public Result select(@RequestParam String id) {
        Optional<Book> optional = bookRepository.findById(id);
        return Result.builder().success().message("查询成功").data(optional.get()).build();
    }

}
