package com.keda;

import java.util.Date;
import java.util.List;

import com.keda.minidao.dao.WmsLocDao;
import com.keda.minidao.dao.WmsStockAdjustDao;
import com.keda.minidao.dao.WmsStockDao;
import com.keda.minidao.dao.WmsTransDao;
import com.keda.minidao.entity.WmsLoc;
import com.keda.minidao.entity.WmsStock;
import com.keda.minidao.entity.WmsStockAdjust;
import com.keda.minidao.entity.WmsTrans;
import com.keda.minidao.service.WmsStockService;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

/**
 * 
 * @ClassName:AutoStockAdjustTask
 * @Description: 库存调整定时任务
 * @date 2018-6-25 上午17:20:00
 * 
 */

@Service("kedaAutoStockAdjustTask")
public class KedaAutoStockAdjustTask implements Job{
	
	BeanFactory factory;
	
	public KedaAutoStockAdjustTask(){
		factory = new ClassPathXmlApplicationContext("applicationContext.xml");
	}

	public void work(){
		long start = System.currentTimeMillis();
		org.jeecgframework.core.util.LogUtil.info("===================定时库存调整任务开始===================");	
		BeanFactory factory = new ClassPathXmlApplicationContext("applicationContext.xml");
		
		//检查5分钟内(可自定义时长)是否存在交易
		WmsTrans trans = new WmsTrans();
		WmsTransDao transDao = (WmsTransDao) factory.getBean("wmsTransDao");
		trans = transDao.getTransByTime(ConstSetBA.INTERVAL_MINUTE);
		if (trans == null) {
			//查询出所有存在库存的可自动调整的货位
			WmsLocDao locDao = (WmsLocDao) factory.getBean("wmsLocDao");
			WmsLoc loc = new WmsLoc();
			List<WmsLoc> stockLocList = locDao.getAllByStock();
			
			for(WmsLoc stockLoc: stockLocList) {
				//查询比此货位优先级高的货位是否存在（1、空货位，2、同品种非满垒货位）
				//1.查找比当前货位优先级高的，优先级最高的空货位
				WmsLoc emptyLoc = locDao.getOneEmptyLoc(stockLoc.getId());
				//2.根据货位最顶层货品编号，查找同品种非满垒货位优先级更高的货位
				WmsLoc sameLoc = locDao.getRackableLocByGoodsno(stockLoc);
				//比较优先级
				//将优先级高的货位作为目标货位
				WmsLoc targetLoc = null;
				if (emptyLoc != null) {
					if (sameLoc != null) {
						if (Integer.valueOf(emptyLoc.getLoclevel()) > Integer.valueOf(sameLoc.getLoclevel()) ) {
							targetLoc = sameLoc;
						}else {
							targetLoc = emptyLoc;
						}
					}else {
						targetLoc = emptyLoc;
					}
				}else if (sameLoc != null) {
					targetLoc = sameLoc;
				}else {
					//如果不存在则继续判断下一个库存货位
					continue;
				}
				//生成调整单
				WmsStock stock = new WmsStock();
				WmsStockDao stockDao = (WmsStockDao) factory.getBean("wmsStockDao");
				//根据货位查找顶层库存
				stock = stockDao.getStockByLoc(stockLoc);
				WmsStockAdjust stockAdjust = new WmsStockAdjust();
				WmsStockAdjustDao stockAdjustDao = (WmsStockAdjustDao) factory.getBean("wmsStockAdjustDao");
				stockAdjust.setAdjstatus(ConstSetBA.ADJSTATUS_INIT);
				stockAdjust.setCreateDate(new Date());
				stockAdjust.setGoodsname(stock.getGoodsname());
				stockAdjust.setGoodsno(stock.getGoodsno());
				stockAdjust.setLotno(stock.getLotno());
				stockAdjust.setNewlayer(targetLoc.getLayer() + 1);
				stockAdjust.setNewlocno(targetLoc.getLocno());
				stockAdjust.setNewzoneno(targetLoc.getZoneno());
				stockAdjust.setOrglayer(stock.getLayer());
				stockAdjust.setOrglocno(stock.getLocno());
				stockAdjust.setOrgzoneno(stock.getZoneno());
				stockAdjust.setStockqty(stock.getStockqty());
				stockAdjust.setCreateName("定时任务自动生成");
				stockAdjust.setId(stockAdjustDao.insertNative(stockAdjust));
				
				//进行库存调整
				WmsStockService wmsStockService = (WmsStockService) factory.getBean("wmsStockService");
				wmsStockService.stockAdjustTransactionalInsert(stockAdjust);
				
				//跳出定时任务
				break;
			}
	     org.jeecgframework.core.util.LogUtil.info("===================定时库存调整任务结束===================");
	     long end = System.currentTimeMillis();
	     long times = end - start;
	     org.jeecgframework.core.util.LogUtil.info("总耗时"+times+"毫秒");
		}
	}
	
	public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
		work();
	}
}
	
