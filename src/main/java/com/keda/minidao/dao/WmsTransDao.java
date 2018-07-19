package com.keda.minidao.dao;

import java.util.List;
import java.util.Map;

import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.minidao.annotation.Arguments;
import org.jeecgframework.minidao.annotation.IdAutoGenerator;
import org.jeecgframework.minidao.annotation.Param;
import org.jeecgframework.minidao.annotation.ResultType;
import org.jeecgframework.minidao.annotation.Sql;
import org.jeecgframework.minidao.pojo.MiniDaoPage;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.keda.minidao.entity.WmsTrans;



/**
 * 描述：交易
 * @author：pengwei
 * @since：2018年06月13日 09时49分11秒 星期三
 * @version:1.0
 */

import org.jeecgframework.minidao.annotation.Arguments;
import org.jeecgframework.minidao.annotation.IdAutoGenerator;
import org.jeecgframework.minidao.annotation.Param;
import org.jeecgframework.minidao.annotation.ResultType;
import org.jeecgframework.minidao.annotation.Sql;
import org.jeecgframework.minidao.pojo.MiniDaoPage;
import org.springframework.stereotype.Repository;

import com.keda.minidao.entity.WmsTrans;



/**
 * 描述：交易
 * @author：pengwei
 * @since：2018年06月13日 09时49分11秒 星期三
 * @version:1.0
 */
@Repository
public interface WmsTransDao {

	/**
	 * 查询指定分钟数内的交易
	 * @param id
	 * @return
	 */
	@Sql("select * from wms_trans where transdate>=DATE_SUB(NOW(),INTERVAL ${min} MINUTE);")
	WmsTrans getTransByTime(@Param("min") Integer min);
	
	/**
	 * 查询返回Java对象
	 * @param id
	 * @return
	 */
	@Sql("select * from wms_trans where id = :id")
	WmsTrans get(@Param("id") String id);
	
	/**
	 * 查询返回Java对象
	 * @deprecated SQL中采用freemarker语法取值,注意需要手工加上单引号（这种写法有SQL注入风险）
	 * @param id
	 * @return
	 */
	@Sql("select * from wms_trans where id = '${id}'")
	WmsTrans getF(@Param("id") String id);
	
	/**
	 * 修改数据
	 * @param loc
	 * @return
	 */
	@Sql("select * from wms_trans where id = '${id}'")
	int update(@Param("trans") WmsTrans trans);
	
	/**
	 * 插入数据
	 * @param trans
	 */
	void insert(@Param("trans") WmsTrans trans);
	
	/**
	 * 插入数据（ID采用自增策略，并返回自增ID）
	 * @param wmsloc
	 */
	@IdAutoGenerator(generator="native")
	int insertNative(@Param("trans") WmsTrans trans);

	/**
	 * 通用分页方法，支持（oracle、mysql、SqlServer、postgresql）
	 * @param WmsTrans
	 * @param page
	 * @param rows
	 * @return
	 */
	@ResultType(WmsTrans.class)
	public MiniDaoPage<WmsTrans> getAll(@Param("trans") WmsTrans trans,@Param("page")  int page,@Param("rows") int rows);
	
	/**
	 * 自定义分页
	 * @param wmsloc
	 * @param startRow  开始序号
	 * @param pageSize  每页显示条数
	 * @return
	 */
	@ResultType(WmsTrans.class)
	@Sql("select * from wms_trans order by id asc limit :startRow,:pageSize")
	public List<WmsTrans> getPageList(@Param("trans") WmsTrans trans,@Param("startRow")  int startRow,@Param("pageSize") int pageSize);
	
	/**
	 * 删除数据
	 * @param wmsloc
	 */
	@Sql("delete from wms_trans where id = :id")
	public void delete(@Param("id") String id);
	
	/**
	 * 返回List<Map>类型，全部数据
	 * @param wmsloc
	 * @return
	 */
	@Arguments({ "trans"})
	@Sql("select * from wms_trans")
	List<Map<String,Object>> getAll(WmsTrans trans);
	
	/**
	 * 返回Map类型，支持多个参数
	 * @param empno
	 * @param name
	 * @return
	 */
	@Sql("select * from wms_trans where empno = :empno and  name = :name")
	Map<String,Object> getMap(@Param("empno") String empno,@Param("name")String name);
	
	/**
	 * 查询分页数量
	 * @return
	 */
	@Sql("select count(*) from wms_trans")
	Integer getCount();
}
