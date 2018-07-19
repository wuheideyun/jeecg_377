package com.keda;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.util.LogUtil;
import org.jeecgframework.web.cgform.enhance.CgformEnhanceJavaInter;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

import com.keda.minidao.service.WmsSoService;

import java.util.Map;

/**
 * 出库业务后台逻辑
 * @author pengwei
 * @version 1.0 
 */

@Service("kedaAllocateRelease")
public class KedaAllocateRelease implements CgformEnhanceJavaInter {
	
    static private Log log = LogFactory.getLog(KedaAllocateRelease.class.getName());
	
	@Override
    public void execute(String tableName,Map map) {
		BeanFactory factory = new ClassPathXmlApplicationContext("applicationContext.xml");
		WmsSoService wmsSoService = (WmsSoService) factory.getBean("wmsSoService");
		LogUtil.info("============调用[java增强]成功!========tableName:"+tableName+"===map==="+map);
		try {
			if ((String) map.get("status") == ConstSetBA.FETCHSTATUS_FINISHED ||
					((String) map.get("status")).equals(ConstSetBA.FETCHSTATUS_FINISHED)) {
				throw new BusinessException("已经完成的出库单不能再次分配");
			}
			//启动出库事务
			wmsSoService.soTransactionalInsert(map);
	    }catch (Exception e) {
	    	log.error(e.getMessage(), e);
			e.printStackTrace();
			wmsSoService.updateSoErrormsg((String) map.get("id"), e.getMessage());
			throw new BusinessException(e.getMessage());
		}
	}
	
    public void auto(String tableName,Map map,BeanFactory factory) {
//		BeanFactory factory = new ClassPathXmlApplicationContext("applicationContext.xml");
		WmsSoService wmsSoService = (WmsSoService) factory.getBean("wmsSoService");
		LogUtil.info("============调用[java增强]成功!========tableName:"+tableName+"===map==="+map);
		try {
			if ((String) map.get("status") == ConstSetBA.FETCHSTATUS_FINISHED ||
					((String) map.get("status")).equals(ConstSetBA.FETCHSTATUS_FINISHED)) {
				throw new BusinessException("已经完成的出库单不能再次分配");
			}
			//启动出库事务
			wmsSoService.soTransactionalInsert(map);
	    }catch (Exception e) {
	    	log.error(e.getMessage(), e);
			e.printStackTrace();
			wmsSoService.updateSoErrormsg((String) map.get("id"), e.getMessage());
			throw new BusinessException(e.getMessage());
		}
	}
}