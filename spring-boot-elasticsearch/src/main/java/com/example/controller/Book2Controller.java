package com.example.controller;

import com.example.common.Result;
import com.example.entity.Book;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.query.GetQuery;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.management.Query;

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
        //Client client = elasticsearchTemplate.getClient();
        //SearchRequestBuilder srb = client.prepareSearch("book").setTypes("novel");
        //SearchResponse sr = srb.setQuery(QueryBuilders.matchAllQuery()).execute().actionGet(); // 查询所有
        /*SearchRequestBuilder searchRequestBuilder = elasticsearchTemplate.getClient()
                .prepareSearch("book")
                .setTypes("novel")
                .setQuery(QueryBuilders.matchAllQuery());
        SearchResponse sr = searchRequestBuilder.get();
        SearchHits hits = sr.getHits();
        List<Map<String, Object>> list = new ArrayList<>();
        hits.forEach(item -> list.add(item.getSourceAsMap()));*/
       /* SearchQuery searchQuery = QueryBuilders.matchAllQuery();

        elasticsearchOperations.queryForList();*/

        return Result.builder().success().message("查询成功").build();
    }
/*
    @PostMapping("/add")
    public Result add(@RequestBody Book book) {
        try {
            XContentBuilder xContentBuilder = XContentFactory.jsonBuilder()
                    .startObject()
                    .field("author", book.author)
                    .field("bookName", book.bookName)
                    .field("press", book.press)
                    //注意这个时间 一定要加上.getTime() 否则会报错
                    .field("publishedDate", book.publishedDate.getTime())
                    .endObject();
            elasticsearchTemplate.getClient().prepareIndex("book", "novel").setSource(xContentBuilder).get();
            return Result.builder().success().message("保存成功").build();
        } catch (IOException e) {
            throw new NormalException(e.getMessage());
        }
    }

    @DeleteMapping("/delete")
    public Result delete(@RequestParam String id) {
        DeleteResponse deleteResponse = elasticsearchTemplate.getClient().prepareDelete("book", "novel", id).get();
        return Result.builder().success().message("删除成功").data(deleteResponse).build();
    }

    @PutMapping("/update")
    public Result update(@RequestBody Book book) {
        UpdateRequest updateRequest = new UpdateRequest("book", "novel", book.getId());
        try {
            XContentBuilder xContentBuilder = XContentFactory.jsonBuilder()
                    .startObject();
            if (StringUtils.isNoneBlank(book.getAuthor())) {
                xContentBuilder.field("author", book.author);
            }
            if (StringUtils.isNoneBlank(book.getBookName())) {
                xContentBuilder.field("bookName", book.bookName);
            }
            if (StringUtils.isNoneBlank(book.getPress())) {
                xContentBuilder.field("press", book.press);
            }
            if (book.getPublishedDate() != null) {
                //注意这个时间 一定要加上.getTime() 否则会报错
                xContentBuilder.field("publishedDate", book.publishedDate.getTime());
            }
            if (book.getPrice() != null) {
                xContentBuilder.field("price", book.price.doubleValue());
            }
            xContentBuilder.endObject();
            updateRequest.doc(xContentBuilder);
        } catch (IOException e) {
            throw new NormalException(e.getMessage());
        }
        try {
            elasticsearchTemplate.getClient().update(updateRequest).get();
            return Result.builder().success().message("更新成功").build();
        } catch (Exception e) {
            throw new NormalException(e.getMessage());
        }

    }

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
