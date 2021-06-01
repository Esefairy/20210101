package com.culture.query;

import lombok.Data;

@Data
public class CultureQuery extends BaseQuery{

    private String cultureName;

    private Long categoryId;

}
