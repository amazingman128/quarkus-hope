package com.smartbird.common.param;

/**
 * 基础请求数据
 */
public class HopeBaseParam {
    /**
     * 默认页码
     */
    public static final Integer DEFAULT_PAGE_INDEX = 1;
    /**
     * 默认每页记录数
     */
    public static final Integer DEFAULT_PAGE_SIZE = 10;

    /**
     * 当前页码
     */
    public Integer pageIndex;
    /**
     * 每页条数
     */
    public Integer pageSize;


    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * 获取当前页码
     *
     * @return
     */
    public Integer getPageIndex() {
        if (null == pageIndex || this.pageIndex < 0) {
            this.setPageIndex(DEFAULT_PAGE_INDEX);
        }
        return pageIndex;
    }

    /**
     * 获取每页记录数
     *
     * @return
     */
    public Integer getPageSize() {
        if (null == pageSize || this.pageSize < 0) {
            this.setPageSize(DEFAULT_PAGE_SIZE);
        }
        return pageSize;
    }

}
