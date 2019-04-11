package cn.gzhu.test.service;

import java.util.ArrayList;
import java.util.List;

public class BatchListWrapper<T> implements BatchIterator<T> {

    private List<T> target;
    private Integer totalCount;
    //一批查询的数量
    private Integer batchCount;
    //批数
    private Integer ex;
    //计数器
    private Integer counter = 0;
    private List<List<T>> result = new ArrayList<>();


    public BatchListWrapper(List<T> target, Integer batchCount) {
        this.target = target;
        this.batchCount = batchCount;
        this.totalCount = target.size();
        //初始化结果
        //批数
        this.ex = (totalCount % batchCount) == 0 ? totalCount / batchCount : (totalCount / batchCount + 1);
        for (int i = 0; i < ex; i++) {
            int limit = totalCount > batchCount ? (i * batchCount + batchCount) : totalCount;
            //处理最后个数
            limit = i > 0 && limit <= batchCount ? i*batchCount + limit : limit;
            this.result.add(target.subList(i*batchCount, limit));
            totalCount = totalCount - batchCount;
        }

    }

    @Override
    public List<T> next() {
        if (hasNextBatch()) {
            return result.get(counter++);
        }
       return null;
    }

    @Override
    public Boolean hasNextBatch() {
        if (ex > counter) {
            return true;
        }
        return false;
    }
}
