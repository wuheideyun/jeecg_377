package com.keda;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.util.LogUtil;
import org.jeecgframework.web.cgform.enhance.CgformEnhanceJavaInter;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

import com.keda.minidao.service.WmsStockService;

import java.util.Map;

/**
 * 库存调整
 * @author pengwei
 * @version 1.0 
 */

@Service("kedaStockAdjust")
public class KedaStockAdjust implements CgformEnhanceJavaInter {
    static private Log log = LogFactory.getLog(KedaStockAdjust.class.getName());
	private BeanFactory factory;
	
	@Override
    public void execute(String tableName,Map map) throws BusinessException {
		try {
			LogUtil.info("============调用[java增强]成功!========tableName:"+tableName+"===map==="+map);
			if ((String) map.get("adjstatus") == ConstSetBA.ADJSTATUS_FIHISHED ||
					((String) map.get("adjstatus")).equals(ConstSetBA.ADJSTATUS_FIHISHED)) {
				throw new BusinessException("已经完成的库存调整单不能再次提交");
			}
			factory = new ClassPathXmlApplicationContext("applicationContext.xml");
			WmsStockService wmsStockService = (WmsStockService) factory.getBean("wmsStockService");
			//启动库存调整事务
			wmsStockService.stockAdjustTransactionalInsert(map);
	    }catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new BusinessException(e);
		}
	}
}