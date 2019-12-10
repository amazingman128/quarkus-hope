package com.smartbird.common.response;


import cn.hutool.core.collection.CollectionUtil;
import com.google.common.collect.Lists;
import com.smartbird.common.param.HopeBaseParam;
import io.quarkus.hibernate.orm.panache.PanacheQuery;

import java.io.Serializable;
import java.util.List;

/**
 * 分页数据类
 *
 * @param <T>
 */
public class HopePage<T> implements Serializable {
    /**
     * 记录信息集合
     */
    public List<T> records;

    /**
     * 记录总条数
     */
    public long total;

    /**
     * 每页记录数
     */
    public int pageSize;

    /**
     * 当前页码
     */
    public int pageIndex;

    /**
     * 总页数
     */
    public int pages;

    public static class Builder<T> implements Serializable {
        public static <T> HopePage<T> build(List<T> records, long total, int size, int current, int pages) {
            HopePage<T> iPage = new HopePage<>();
            iPage.records = records;
            iPage.total = total;
            iPage.pageSize = size;
            iPage.pageIndex = current;
            iPage.pages = pages;
            return iPage;
        }

        public static <T> HopePage<T> build(PanacheQuery<T> panacheQuery, HopeBaseParam hopeBaseParam) {
            HopePage<T> iPage = new HopePage<>();
            iPage.records = panacheQuery.page(hopeBaseParam.getPageIndex() - 1, hopeBaseParam.getPageSize()).list();
            iPage.total = panacheQuery.count();
            iPage.pageSize = (int) panacheQuery.count();
            iPage.pageIndex = hopeBaseParam.getPageIndex();
            if (panacheQuery.count() % hopeBaseParam.getPageSize() == 0) {
                iPage.pages = (int) panacheQuery.count() / hopeBaseParam.getPageSize();
            } else {
                iPage.pages = (int) panacheQuery.count() / hopeBaseParam.getPageSize() + 1;
            }
            return iPage;
        }
    }

    public List<T> getRecords() {
        if (CollectionUtil.isEmpty(records)) {
            return Lists.newArrayList();
        }
        return records;
    }

    public HopePage<T> setRecords(List<T> records) {
        this.records = records;
        return this;
    }

    public long getTotal() {
        return total;
    }

    public HopePage<T> setTotal(long total) {
        this.total = total;
        return this;
    }

    public int getPageSize() {
        return pageSize;
    }

    public HopePage<T> setPageSize(int pageSize) {
        this.pageSize = pageSize;
        return this;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public HopePage<T> setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
        return this;
    }

    public int getPages() {
        return pages;
    }

    public HopePage<T> setPages(int pages) {
        this.pages = pages;
        return this;
    }
}