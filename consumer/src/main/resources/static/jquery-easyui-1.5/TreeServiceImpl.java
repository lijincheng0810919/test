/** 
 * <pre>项目名称:cll-easyui 
 * 文件名称:TreeServiceImpl.java 
 * 包名:com.jk.cll.service.impl 
 * 创建日期:2019年7月15日下午4:49:53 
 * Copyright (c) 2019, yuxy123@gmail.com All Rights Reserved.</pre> 
 */  
package com.jk.cll.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jk.cll.dao.TreeDao;
import com.jk.cll.pojo.TreeBean;
import com.jk.cll.service.TreeService;

/** 
 * <pre>项目名称：cll-easyui    
 * 类名称：TreeServiceImpl    
 * 类描述：    
 * 创建人：李成龙   
 * 创建时间：2019年7月15日 下午4:49:53    
 * 修改人：李成龙 
 * 修改时间：2019年7月15日 下午4:49:53    
 * 修改备注：       
 * @version </pre>    
 */
@Service
public class TreeServiceImpl implements TreeService {

	@Autowired
	private TreeDao dao;

	/* (non-Javadoc)    
	 * @see com.jk.cll.service.TreeService#findTree()    
	 */
	@Override
	public List<TreeBean> findTree() {
		int pid = 0;
		List<TreeBean> list = findNodes(pid);
		return list;
	}

	/** <pre>findNodes(这里用一句话描述这个方法的作用)   
	 * 创建人：李成龙    
	 * 创建时间：2019年7月15日 下午5:07:46    
	 * 修改人：李成龙     
	 * 修改时间：2019年7月15日 下午5:07:46    
	 * 修改备注： 
	 * @param pid
	 * @return</pre>    
	 */
	private List<TreeBean> findNodes(int pid) {
		List<TreeBean> list = dao.findTree(pid);
		for (TreeBean treeBean : list) {
			Integer id = treeBean.getId();
			List<TreeBean> node = findNodes(id);
			treeBean.setChildren(node);
		}
		return list;
	}

	/* (non-Javadoc)    
	 * @see com.jk.cll.service.TreeService#findTree2(java.lang.Integer)    
	 */
	@Override
	public List<TreeBean> findTree2(Integer pid) {
		List<TreeBean> list = dao.findTree(pid);
		for (TreeBean treeBean : list) {
			Integer id = treeBean.getId();
			List<TreeBean> node = dao.findTree(id);
			if (node.size()>0) {
				treeBean.setState("closed");
			}
		}
		return list;
	}
	
	
	
}
