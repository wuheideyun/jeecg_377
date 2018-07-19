package com.keda.minidao.entity;

// Generated 2018-6-7 15:13:48 by Hibernate Tools 3.4.0.CR1

import java.util.Date;

/**
 * WmsSodtl generated by hbm2java
 */
public class WmsSodtl implements java.io.Serializable {

	private Integer id;
	private String createName;
	private String createBy;
	private Date createDate;
	private String updateName;
	private String updateBy;
	private Date updateDate;
	private String sysOrgCode;
	private String sysCompanyCode;
	private String bpmStatus;
	private String goodsno;
	private String goodsname;
	private String goodssize;
	private String goodsunit;
	private String remarks;
	private String locno;
	private String zoneno;
	private String soid;
	private String stockqty;
	private String soqty;

	public WmsSodtl() {
	}

	public WmsSodtl(String createName, String createBy, Date createDate,
			String updateName, String updateBy, Date updateDate,
			String sysOrgCode, String sysCompanyCode, String bpmStatus,
			String goodsno, String goodsname, String goodssize,
			String goodsunit, String remarks, String locno, String zoneno,
			String soid, String stockqty, String soqty) {
		this.createName = createName;
		this.createBy = createBy;
		this.createDate = createDate;
		this.updateName = updateName;
		this.updateBy = updateBy;
		this.updateDate = updateDate;
		this.sysOrgCode = sysOrgCode;
		this.sysCompanyCode = sysCompanyCode;
		this.bpmStatus = bpmStatus;
		this.goodsno = goodsno;
		this.goodsname = goodsname;
		this.goodssize = goodssize;
		this.goodsunit = goodsunit;
		this.remarks = remarks;
		this.locno = locno;
		this.zoneno = zoneno;
		this.soid = soid;
		this.stockqty = stockqty;
		this.soqty = soqty;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCreateName() {
		return this.createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	public String getCreateBy() {
		return this.createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getUpdateName() {
		return this.updateName;
	}

	public void setUpdateName(String updateName) {
		this.updateName = updateName;
	}

	public String getUpdateBy() {
		return this.updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public Date getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getSysOrgCode() {
		return this.sysOrgCode;
	}

	public void setSysOrgCode(String sysOrgCode) {
		this.sysOrgCode = sysOrgCode;
	}

	public String getSysCompanyCode() {
		return this.sysCompanyCode;
	}

	public void setSysCompanyCode(String sysCompanyCode) {
		this.sysCompanyCode = sysCompanyCode;
	}

	public String getBpmStatus() {
		return this.bpmStatus;
	}

	public void setBpmStatus(String bpmStatus) {
		this.bpmStatus = bpmStatus;
	}

	public String getGoodsno() {
		return this.goodsno;
	}

	public void setGoodsno(String goodsno) {
		this.goodsno = goodsno;
	}

	public String getGoodsname() {
		return this.goodsname;
	}

	public void setGoodsname(String goodsname) {
		this.goodsname = goodsname;
	}

	public String getGoodssize() {
		return this.goodssize;
	}

	public void setGoodssize(String goodssize) {
		this.goodssize = goodssize;
	}

	public String getGoodsunit() {
		return this.goodsunit;
	}

	public void setGoodsunit(String goodsunit) {
		this.goodsunit = goodsunit;
	}

	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getLocno() {
		return this.locno;
	}

	public void setLocno(String locno) {
		this.locno = locno;
	}

	public String getZoneno() {
		return this.zoneno;
	}

	public void setZoneno(String zoneno) {
		this.zoneno = zoneno;
	}

	public String getSoid() {
		return this.soid;
	}

	public void setSoid(String soid) {
		this.soid = soid;
	}

	public String getStockqty() {
		return this.stockqty;
	}

	public void setStockqty(String stockqty) {
		this.stockqty = stockqty;
	}

	public String getSoqty() {
		return this.soqty;
	}

	public void setSoqty(String soqty) {
		this.soqty = soqty;
	}

}
