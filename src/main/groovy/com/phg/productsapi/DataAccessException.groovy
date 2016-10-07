package com.phg.productsapi

class DataAccessException extends RuntimeException {
    String message
    int statusCode
    String dataAccessSource
}
