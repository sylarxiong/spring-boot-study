package com.example.controller;

import com.example.common.Result;
import com.example.entity.Book;
import io.swagger.annotations.ApiOperation;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.query.*;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/api/template")
public class Book2Controller {
    @Resource
    private ElasticsearchOperations elasticsearchOperations;

    @GetMapping("/get")
    public Result select(@RequestParam String id) {
        Book person = elasticsearchOperations.queryForObject(GetQuery.getById(id), Book.class);
        return Result.builder().success().message("查询成功").data(person).build();

    }


    @GetMapping("/all")
    public Result getAll() {
//        CriteriaQuery criteriaQuery = new CriteriaQuery(new Criteria());
//        List<Book> list = elasticsearchOperations.queryForList(criteriaQuery,Book.class);

        SearchQuery searchQuery = new NativeSearchQueryBuilder().build();
        List<Book> list = elasticsearchOperations.queryForList(searchQuery, Book.class);
        return Result.builder().success().message("查询成功").data(list).build();
    }

    @PostMapping("/add")
    public Result add(@RequestBody Book book) {
        IndexQuery indexQuery = new IndexQueryBuilder().withObject(book).withIndexName("book").withType("novel").build();
        elasticsearchOperations.index(indexQuery);
        return Result.builder().success().message("添加成功").build();
    }

    @DeleteMapping("/delete")
    public Result delete(@RequestParam String id) {
        elasticsearchOperations.delete(Book.class, id);
        return Result.builder().success().message("删除成功").build();
    }

    @PutMapping("/update")
    public Result update(@RequestBody Book book) {
        IndexQuery indexQuery = new IndexQueryBuilder().withObject(book).withIndexName("book").withType("novel").build();
        elasticsearchOperations.index(indexQuery);
        return Result.builder().success().message("更新成功").build();


    }

    @ApiOperation("复合查询")
    @GetMapping("/query")
    public Result compoundQuery(@RequestParam(required = false) String bookName,
                                @RequestParam(required = false) String author) {

        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.termQuery("bookName", bookName))
                .withQuery(QueryBuilders.termQuery("author", author))
                .build();


        List<Book> list = elasticsearchOperations.queryForList(searchQuery,Book.class);

        return Result.builder().success().message("查询成功").data(list).build();
    }

}
