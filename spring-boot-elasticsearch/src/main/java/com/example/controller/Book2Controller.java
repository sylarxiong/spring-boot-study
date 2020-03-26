package com.example.controller;

import com.example.common.NormalException;
import com.example.common.Result;
import com.example.entity.Book;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.*;
import org.elasticsearch.action.delete.DeleteRequestBuilder;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateRequestBuilder;
import org.elasticsearch.client.ElasticsearchClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.threadpool.ThreadPool;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.query.*;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
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
        UpdateRequest updateRequest = new UpdateRequest("book", "novel", book.getId());
        UpdateQuery updateQuery = new UpdateQueryBuilder()
                .withId(book.getId())
                .withIndexName("book")
                .withType("novel")
                .withClass(Book.class)
                .withUpdateRequest(new UpdateRequestBuilder(u))
                .build();

        elasticsearchOperations.update(updateQuery);
        IndexQuery indexQuery = new IndexQueryBuilder().withObject(book).withIndexName("book").withType("novel").build();
        elasticsearchOperations.index(indexQuery);
        return Result.builder().success().message("更新成功").build();


    }
/*
    @ApiOperation("复合查询")
    @GetMapping("/query")
    public Result compoundQuery(@RequestParam(required = false) String bookName,
                                @RequestParam(required = false) String author,
                                @RequestParam(required = false) String press,
                                @RequestParam(required = false, defaultValue = "0") BigDecimal gtPrice,
                                @RequestParam(required = false) BigDecimal ltPrice) {
        //bool查询
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        if (StringUtils.isNoneBlank(bookName)) {
            boolQuery.must(QueryBuilders.matchQuery("bookName", bookName));
        }
        if (StringUtils.isNoneBlank(author)) {
            boolQuery.must(QueryBuilders.matchQuery("author", author));
        }
        if (StringUtils.isNoneBlank(press)) {
            boolQuery.must(QueryBuilders.matchQuery("press", press));
        }
        //范围查询
        RangeQueryBuilder rangeQuery = QueryBuilders.rangeQuery("price").from(gtPrice.doubleValue());
        if (ltPrice != null && ltPrice.compareTo(new BigDecimal("0")) == 1) {
            rangeQuery.to(ltPrice.doubleValue());
        }
        boolQuery.filter(rangeQuery);
        SearchRequestBuilder searchRequestBuilder = elasticsearchTemplate.getClient().prepareSearch("book")
                .setTypes("novel")
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setQuery(boolQuery)
                .setFrom(0)
                .setSize(10);
        SearchResponse searchResponse = searchRequestBuilder.get();
        List<Map<String, Object>> list = new ArrayList<>();
        searchResponse.getHits().forEach(item -> list.add(item.getSourceAsMap()));
        return Result.builder().success().message("查询成功").data(list).build();
    }
*/

}
