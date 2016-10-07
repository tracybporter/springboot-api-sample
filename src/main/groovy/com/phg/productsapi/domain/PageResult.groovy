package com.phg.productsapi.domain

import groovy.transform.EqualsAndHashCode

@EqualsAndHashCode()
class PageResult {
    Metadata metadata
    List results

    public PageResult(List results = []) {
        this.results = results
        this.metadata = new Metadata(resultSet: new ResultSet(count: results.size()))
    }
}

@EqualsAndHashCode()
class Metadata {
    ResultSet resultSet
}

@EqualsAndHashCode()
class ResultSet {
    int count
    int offset = 0
    int limit = 20
}