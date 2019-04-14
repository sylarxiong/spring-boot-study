package com.example.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Document(indexName = "book", type = "novel")
//indexName索引名称 可以理解为数据库名 必须为小写 不然会报org.elasticsearch.indices.InvalidIndexNameException异常
//type类型 可以理解为表名
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Builder
public class Book {
    @Id
    public String id;

    @NotBlank
    public String bookName;

    public String author;

    //@JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    public Date publishedDate;

    public String press;

    public BigDecimal price;
}
