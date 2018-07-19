package com.keda.minidao.service;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jeecgframework.core.common.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.keda.ConstSetBA;
import com.keda.KedaTransMgr;
import com.keda.minidao.dao.WmsStockAdjustDao;
import com.keda.minidao.dao.WmsLocDao;
import com.keda.minidao.dao.WmsStockDao;
import com.keda.minidao.entity.WmsLoc;
import com.keda.minidao.entity.WmsStock;
import com.keda.minidao.entity.WmsStockAdjust;


@Service
public class WmsStockService {
    static private Log log = LogFactory.getLog(KedaTransMgr.class.getName());
    @Autowired
	private WmsLocDao locDao;
	@Autowired
	private WmsStockDao stockDao;
	@Autowired
	private WmsStockAdjustDao stockAdjustDao;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	
	/**
	 * 库存调整
	 */
	@Transactional
	public void stockAdjustTransactionalInsert(Map map) throws BusinessException{
		String locno = (String)map.get("orglocno");
		String goodsno = (String)map.get("goodsno");
		Integer orglayer = (Integer.valueOf((String)map.get("orglayer")));
		
		WmsStock stock = new WmsStock();
		stock = stockDao.findStockByAdj(goodsno,locno,orglayer);
		
		//设置原货位即将调整的库存下层为顶层
		updateStockTopflag(stock,stockDao);
		
		stock.setLocno((String)map.get("newlocno"));
		stock.setZoneno((String)map.get("newzoneno"));
		stock.setLayer(Integer.valueOf((String)map.get("newlayer")));
		//新库存一定是顶层库存
		stock.setTopflag(ConstSetBA.STOCK_TOP);
		stockDao.update(stock);
		
		WmsLoc newloc = new WmsLoc();
		newloc = locDao.getLocByLocno((String)map.get("newlocno"));
		//判断是否是顶层，如果是则更新货位的顶层标志
		if (stock.getLayer() == ConstSetBA.LAYER_NUMBER) {
			newloc.setTopflag(ConstSetBA.TOPFLAG_TRUE);
		}
		//新货位层数+1
		WmsLocService wmsLocService = new WmsLocService();
		wmsLocService.updateLocLayerByLoc(newloc,ConstSetBA.LAYER_ADD,locDao);
		
		//原货位层数-1
		WmsLoc orgloc = new WmsLoc();
		orgloc = locDao.getLocByLocno((String)map.get("orglocno"));
		wmsLocService.updateLocLayerByLoc(orgloc,ConstSetBA.LAYER_SUB,locDao);
		
		//将目标货位原库存的顶层标志清空
		updateLocTopflag((String)map.get("newlocno"),stock.getId(),stockDao);
		
		//将原货位的顶层标志清空
		updateLocTopflag((String)map.get("orglocno"),stockDao);
		
		//更新库存调整单的状态为完成
		updateStockAdjustStatus((String) map.get("id"));
		} 

	/**
	 * 库存调整
	 */
	@Transactional
	public void stockAdjustTransactionalInsert(WmsStockAdjust stockAdjust) throws BusinessException{
		String orglocno = stockAdjust.getOrglocno();
		String goodsno = stockAdjust.getGoodsno();
		Integer orglayer = (Integer.valueOf(stockAdjust.getOrglayer()));
		
		WmsStock stock = new WmsStock();
		stock = stockDao.findStockByAdj(goodsno,orglocno,orglayer);
		
		//设置原货位即将调整的库存下层为顶层
		updateStockTopflag(stock,stockDao);
		
		stock.setLocno(stockAdjust.getNewlocno());
		stock.setZoneno(stockAdjust.getNewzoneno());
		stock.setLayer(Integer.valueOf(stockAdjust.getNewlayer()));
		//新库存一定是顶层库存
		stock.setTopflag(ConstSetBA.STOCK_TOP);
		stockDao.update(stock);
		
		WmsLoc newloc = new WmsLoc();
		newloc = locDao.getLocByLocno(stockAdjust.getNewlocno());
		//判断是否是顶层，如果是则更新货位的顶层标志
		if (stock.getLayer() == ConstSetBA.LAYER_NUMBER) {
			newloc.setTopflag(ConstSetBA.TOPFLAG_TRUE);
		}
		//新货位层数+1
		WmsLocService wmsLocService = new WmsLocService();
		wmsLocService.updateLocLayerByLoc(newloc,ConstSetBA.LAYER_ADD,locDao);
		
		//原货位层数-1
		WmsLoc orgloc = new WmsLoc();
		orgloc = locDao.getLocByLocno(orglocno);
		wmsLocService.updateLocLayerByLoc(orgloc,ConstSetBA.LAYER_SUB,locDao);
		
		//将目标货位原库存的顶层标志清空
		updateLocTopflag(stockAdjust.getNewlocno(),stock.getId(),stockDao);
		
		//将原货位的顶层标志清空
		updateLocTopflag(orglocno,stockDao);
		
		//更新库存调整单的状态为完成
		updateStockAdjustStatus(stockAdjust.getId().toString());
		} 
	
	//修改调整单状态
	public void updateStockAdjustStatus(String stockadjustid) throws BusinessException {
		try {
		stockAdjustDao.updateStockAdjustStatus(stockadjustid,String.valueOf(ConstSetBA.ADJSTATUS_FIHISHED));
		} catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
	}
	
	
	
	//设置即将出库的库存下层为顶层
	public void updateStockTopflag(WmsStock stock,WmsStockDao stockDao) throws BusinessException {
		try {
			stockDao.updateStockTopflag(stock.getLocno(),stock.getLayer()-1);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new BusinessException(e.getMessage());
		}
	}
	
	//更新货位库存顶层标志为非顶层
	public void updateLocTopflag(String locno,int newStockid,WmsStockDao stockDao) throws BusinessException {
		try {
			stockDao.updateTopflagByLocno(locno,newStockid);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new BusinessException(e.getMessage());
		}
	}
	
	//清空货位的顶层标志
	public void updateLocTopflag(String locno,WmsStockDao stockDao) throws BusinessException {
		try {
			stockDao.deleteTopflagByLocno(locno);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new BusinessException(e.getMessage());
		}
	}
}
