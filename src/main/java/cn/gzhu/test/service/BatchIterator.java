package cn.gzhu.test.service;

import java.util.List;

public interface BatchIterator<T> {

    List<T> next();

    Boolean hasNextBatch();
}
