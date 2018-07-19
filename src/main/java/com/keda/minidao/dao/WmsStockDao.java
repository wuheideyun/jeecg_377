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

import com.keda.minidao.entity.WmsLoc;
import com.keda.minidao.entity.WmsStock;


/**
 * 描述：库存
 * @author：pengwei
 * @since：2018年06月07日 14时31分08秒 星期四 
 * @version:1.0
 */
@Repository
public interface WmsStockDao {

	
	/**
	 * 根据货位查找顶层库存
	 * @param loc
	 * @return
	 */
	@Sql("select s.* from wms_stock s where s.locno = :loc.locno and s.zoneno = :loc.zoneno and s.topflag = 1")
	WmsStock getStockByLoc(@Param("loc") WmsLoc loc );
	
	/**
	 * 设置某层库存的顶层标志为是
	 * @param stock
	 * @return
	 */
	@Sql("update wms_stock set topflag = 1 where locno = :locno and layer = :layer")
	int updateStockTopflag(@Param("locno") String locno,@Param("layer") int layer);
	
	
	/**
	 * 更新库存的的顶层标志为否，除了newStockid
	 * @param stock
	 * @return
	 */
	@Sql("update wms_stock set topflag = 0 where locno = :locno and id != :newStockid")
	int updateTopflagByLocno(@Param("locno") String locno,@Param("newStockid") int newStockid);
	
	/**
	 * 更新货位的的顶层标志为否
	 * @param stock
	 * @return
	 */
	@Sql("update wms_loc set topflag = 0 where locno = :locno")
	int deleteTopflagByLocno(@Param("locno") String locno);
	
	/**
	 * 查询返回Java对象
	 * @param id
	 * @return
	 */
	@Sql("select * from wms_stock where id = :id")
	WmsStock get(@Param("id") String id);
	
	/**
	 * 查询返回Java对象
	 * @param id
	 * @return
	 */
	@Sql("select * from wms_stock where locno = :locno and goodsno = :goodsno and stockqty =:soqty order by layer desc limit 1")
	WmsStock findStockBySodtl(@Param("goodsno") String goodsno,@Param("soqty") String soqty,@Param("locno")  String locno);
	
	
	/**
	 * 查询返回Java对象
	 * @param id
	 * @return
	 */
	@Sql("select * from wms_stock where goodsno = :goodsno and locno =:locno and layer =:layer ")
	WmsStock findStockByAdj(@Param("goodsno") String goodsno,@Param("locno") String locno,@Param("layer") Integer layer);
	
	/**
	 * 查询返回Java对象
	 * @deprecated SQL中采用freemarker语法取值,注意需要手工加上单引号（这种写法有SQL注入风险）
	 * @param id
	 * @return
	 */
	@Sql("select * from wms_stock where id = '${id}'")
	WmsStock getF(@Param("id") String id);
	
	/**
	 * 查询返回Java对象
	 * @param id
	 * @return
	 */
	@Sql("select s.* from wms_stock s,wms_loc l where s.locno = l.locno and s.goodsno = :goodsno and l.topflag = 0 order by locno limit 1")
	List<WmsStock> getStockByGoodsno(@Param("goodsno") String goodsno);
	
	/**
	 * 修改数据
	 * @param stock
	 * @return
	 */
	int update(@Param("stock") WmsStock wmsstock);
	
	/**
	 * 插入数据
	 * @param stock
	 */
	void insert(@Param("stock") WmsStock wmsstock);
	
	/**
	 * 插入数据（ID采用自增策略，并返回自增ID）
	 * @param wmsstock
	 */
	@IdAutoGenerator(generator="native")
	int insertNative(@Param("wmsstock") WmsStock wmsstock);

	/**
	 * 通用分页方法，支持（oracle、mysql、SqlServer、postgresql）
	 * @param wmsstock
	 * @param page
	 * @param rows
	 * @return
	 */
	@ResultType(WmsStock.class)
	public MiniDaoPage<WmsStock> getAll(@Param("wmsstock") WmsStock wmsstock,@Param("page")  int page,@Param("rows") int rows);
	
	/**
	 * 自定义分页
	 * @param wmsstock
	 * @param startRow  开始序号
	 * @param pageSize  每页显示条数
	 * @return
	 */
	@ResultType(WmsStock.class)
	@Sql("select * from wms_stock order by id asc limit :startRow,:pageSize")
	public List<WmsStock> getPageList(@Param("wmsstock") WmsStock wmsstock,@Param("startRow")  int startRow,@Param("pageSize") int pageSize);
	
	/**
	 * 删除数据
	 * @param wmsstock
	 */
	@Sql("delete from wms_stock where id = :id")
	public void delete(@Param("id") Integer id);
	
	/**
	 * 返回List<Map>类型，全部数据
	 * @param wmsstock
	 * @return
	 */
	@Arguments({ "wmsstock"})
	@Sql("select * from wms_stock")
	List<Map<String,Object>> getAll(WmsStock wmsstock);
	
	/**
	 * 返回Map类型，支持多个参数
	 * @param empno
	 * @param name
	 * @return
	 */
	@Sql("select * from wms_stock where empno = :empno and  name = :name")
	Map<String,Object> getMap(@Param("empno") String empno,@Param("name")String name);
	
	/**
	 * 查询分页数量
	 * @return
	 */
	@Sql("select count(*) from wms_stock")
	Integer getCount();
}
