package com.nsntc.sell.bean;

import java.io.Serializable;

/**
 * Class Name: Page
 * Package: com.nsntc.sell.bean
 * Description: 分页参数类
 * @author wkm
 * Create DateTime: 2017/12/8 上午3:26
 * Version: 1.0
 */
public class Page implements Serializable {

    /** serialVersionUID*/
	private static final long serialVersionUID = -603217531475214595L;

	/** 当前页 */
	private Integer pageNum = 1;
	/** 每页条数 */
    private Integer pageSize = 20;
	/** 总条数 */
    private Integer totalCount = 0;
	/** 总页数 */
    private Integer totalPage = 0;
	/** 开始条数 */
    private Integer start = 0;
	/** 结束条数 */
    private Integer end = 0;
	/** 首页 */
    private Integer firstPage = 0;
	/** 上一页 */
    private Integer prePage = 0;
	/** 下一页 */
    private Integer nextPage = 0;
	/** 尾页 */
    private Integer lastPage = 0;
   
    /** 当前页 */
	public Integer getPageNum() {
		return pageNum;
	}
	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}
	/** 每页量 */
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	/** 总条数 */
	public Integer getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
		this.setTotalPage();
		this.setStart();
		this.setEnd();
		this.setFirstPage();
		this.setPrePage();
		this.setNextPage();
		this.setLastPage();
	}
	/** 总页数 */
	public Integer getTotalPage() {
		return totalPage;
	}
	public void setTotalPage() {
		this.totalPage = 
				totalCount % pageSize == 0 ? totalCount / pageSize : totalCount / pageSize + 1;
	}
	/** 开始条数 */
	public Integer getStart() {
		return start;
	}
	public void setStart() {
		this.start = (pageNum - 1) * pageSize;
	}
	/** 结束条数 */
	public Integer getEnd() {
		return end;
	}
	public void setEnd() {
		this.end = start + pageSize - 1 > totalCount - 1 ? totalCount - 1 : start + pageSize - 1;
	}
	/** 首页 */
	public Integer getFirstPage() {
		return firstPage;
	}
	public void setFirstPage() {
		this.firstPage = totalCount > 0 ? 1 : 0;
	}
	/** 上一页 */
	public Integer getPrePage() {
		return prePage;
	}
	public void setPrePage() {
		this.prePage =  pageNum - 1 < 1 ? 1 : pageNum - 1;
	}
	/** 下一页 */
	public Integer getNextPage() {
		return nextPage;
	}
	public void setNextPage() {
		this.nextPage = pageNum + 1 > totalPage ? totalPage : pageNum + 1;
	}
	/** 尾页 */
	public Integer getLastPage() {
		return lastPage;
	}
	public void setLastPage() {
		this.lastPage = totalPage;
	}
	
	@Override
	public String toString() {
		return "Page [pageNum=" + pageNum + ", pageSize=" + pageSize
				+ ", totalCount=" + totalCount + ", totalPage=" + totalPage
				+ ", start=" + start + ", end=" + end + ", firstPage="
				+ firstPage + ", prePage=" + prePage + ", nextPage=" + nextPage
				+ ", lastPage=" + lastPage + "]";
	}
}