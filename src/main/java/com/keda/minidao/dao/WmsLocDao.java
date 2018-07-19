package com.keda.minidao.dao;

import java.util.List;
import java.util.Map;

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
 * 描述：货位
 * @author：pengwei
 * @since：2018年06月011日 16时09分11秒 星期一
 * @version:1.0
 */
@Repository
public interface WmsLocDao {

	/**
	 * 根据货品编码查询货位编码
	 * @param goodsno
	 * @return
	 */
	@Sql(" select s.locno from wms_stock s,wms_loc l where s.goodsno = :goodsno and s.topflag = 1 order by l.loclevel limit 1")
	public String getLocByGoodsno(@Param("goodsno") String goodsno);
	
	/**
	 * 根据货位编码返回区域编码
	 * @param locno
	 * @return
	 */
	@Sql(" select zoneno from wms_loc where locno = :locno")
	public String getZonenoByLocno(@Param("locno") String locno);
	
	
	/**
	 * 返回所有存在库存的货位
	 * @param id
	 * @return
	 */
	@Sql("select distinct l.* from wms_loc l, wms_stock s where l.locno = s.locno and l.zoneno = s.zoneno and l.adjustflag = 1 order by loclevel desc")
	public List<WmsLoc> getAllByStock();
	
	/**
	 * 查询返回Java对象
	 * @param id
	 * @return
	 */
	@Sql("select * from wms_loc where locno = :stock.locno and zoneno = :stock.zoneno")
	WmsLoc getLocByStock(@Param("stock") WmsStock stock);
	
	/**
	 * 根据货位编号查找货位
	 * @param id
	 * @return
	 */
	@Sql("select l.* from wms_loc l where l.locno = :locno order by l.loclevel ")
	WmsLoc getLocByLocno(@Param("locno") String locno);
	
	/**
	 * 查找可自动上架的货位
	 * @param id
	 * @return
	 */
	@Sql("select l.* from wms_loc l where (l.locno = :locno and l.rackflag = 1) order by l.loclevel ")
	WmsLoc getRackableLocByLocno(@Param("locno") String locno);	
	
	
	/**
	 * 查询返回Java对象
	 * @param id
	 * @return
	 */
	@Sql("select * from wms_loc where id = :id")
	WmsLoc get(@Param("id") String id);
	
	/**
	 * 查询返回Java对象
	 * @param id
	 * @return
	 */
	@Sql(" select l.* from wms_loc l where not exists (select 1 from wms_stock s where s.locno = l.locno|| l.rackflag = 0) order by l.loclevel ")
	public List<WmsLoc> getEmptyLoc();
	
	/**
	 * 查询返回一个优先级最高的空货位
	 * @param id
	 * @return
	 */
	@Sql(" select l.* from wms_loc l where not exists (select 1 from wms_stock s where s.locno = l.locno or l.rackflag = 0) and l.loclevel < (select loclevel from wms_loc where id = :locid )order by l.loclevel limit 1")
	WmsLoc getOneEmptyLoc(@Param("locid") Integer locid);
	
	/**
	 * 根据货位最顶层货品编号，查找同品种非满垒可自动上架货位优先级更高的货位
	 * @param id
	 * @return
	 */
	@Sql("select l.* from wms_stock st,wms_loc l where l.locno = st.locno and st.goodsno = (select s.goodsno from wms_stock s where s.locno = :loc.locno and s.topflag = 1)  and st.topflag = 1 and l.rackflag = 1 and l.topflag != 1 and l.id != :loc.id and l.loclevel < (select loclevel from wms_loc where id = :loc.id ) order by l.loclevel limit 1")
	WmsLoc getRackableLocByGoodsno(@Param("loc") WmsLoc loc);
	
	/**
	 * 查询返回Java对象
	 * @deprecated SQL中采用freemarker语法取值,注意需要手工加上单引号（这种写法有SQL注入风险）
	 * @param id
	 * @return
	 */
	@Sql("select * from wms_loc where id = '${id}'")
	WmsLoc getF(@Param("id") String id);
	
	/**
	 * 修改数据
	 * @param loc
	 * @return
	 */
	int update(@Param("loc") WmsLoc wmsloc);
	
	/**
	 * 插入数据
	 * @param loc
	 */
	void insert(@Param("loc") WmsLoc wmsloc);
	
	/**
	 * 插入数据（ID采用自增策略，并返回自增ID）
	 * @param wmsloc
	 */
	@IdAutoGenerator(generator="native")
	int insertNative(@Param("wmsloc") WmsLoc wmsloc);

	/**
	 * 通用分页方法，支持（oracle、mysql、SqlServer、postgresql）
	 * @param WmsLoc
	 * @param page
	 * @param rows
	 * @return
	 */
	@ResultType(WmsLoc.class)
	public MiniDaoPage<WmsLoc> getAll(@Param("wmsloc") WmsLoc wmsloc,@Param("page")  int page,@Param("rows") int rows);
	
	/**
	 * 自定义分页
	 * @param wmsloc
	 * @param startRow  开始序号
	 * @param pageSize  每页显示条数
	 * @return
	 */
	@ResultType(WmsLoc.class)
	@Sql("select * from wms_loc order by id asc limit :startRow,:pageSize")
	public List<WmsLoc> getPageList(@Param("wmsloc") WmsLoc wmsloc,@Param("startRow")  int startRow,@Param("pageSize") int pageSize);
	
	/**
	 * 删除数据
	 * @param wmsloc
	 */
	@Sql("delete from wms_loc where id = :id")
	public void delete(@Param("id") String id);
	
	/**
	 * 返回List<Map>类型，全部数据
	 * @param wmsloc
	 * @return
	 */
	@Arguments({ "wmsloc"})
	@Sql("select * from wms_loc")
	List<Map<String,Object>> getAll(WmsLoc wmsloc);
	
	/**
	 * 返回Map类型，支持多个参数
	 * @param empno
	 * @param name
	 * @return
	 */
	@Sql("select * from wms_loc where empno = :empno and  name = :name")
	Map<String,Object> getMap(@Param("empno") String empno,@Param("name")String name);
	
	/**
	 * 查询分页数量
	 * @return
	 */
	@Sql("select count(*) from wms_loc")
	Integer getCount();
}
