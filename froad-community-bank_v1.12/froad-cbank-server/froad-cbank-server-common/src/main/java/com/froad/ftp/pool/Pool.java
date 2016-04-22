/**
 * Project Name:Froad Cbank Server Common
 * File Name:Pool.java
 * Package Name:com.froad.ftp.pool
 * Date:2015年9月9日上午10:20:42
 * Copyright (c) 2015, F-Road, Inc.
 *
*/
/**
 * Project Name:Froad Cbank Server Common
 * File Name:Pool.java
 * Package Name:com.froad.ftp.pool
 * Date:2015年9月9日 上午10:20:42
 * Copyright (c) 2015, F-Road, Inc.
 *
 */

package com.froad.ftp.pool;

import org.apache.commons.pool.PoolableObjectFactory;
import org.apache.commons.pool.impl.GenericObjectPool;


/**
 * ClassName:Pool
 * Reason:	 TODO ADD REASON.
 * Date:     2015年9月9日 上午10:20:42
 * @author   vania
 * @version  
 * @see 	 
 */
/**
 * ClassName: Pool
 * Function: TODO ADD FUNCTION
 * date: 2015年9月9日 上午10:20:42
 *
 * @author vania
 * @version 
 */
public abstract class Pool<T> {
	private final GenericObjectPool internalPool;

	public Pool(GenericObjectPool.Config poolConfig, PoolableObjectFactory factory) {
		this.internalPool = new GenericObjectPool(factory, poolConfig);
	}

	public T getResource() {
		try {
			return (T) this.internalPool.borrowObject();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public void returnResource(T resource) {
		try {
			this.internalPool.returnObject(resource);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void destroy() {
		try {
			this.internalPool.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int inPoolSize() {
		try {
			return this.internalPool.getNumIdle();
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	public int borrowSize() {
		try {
			return this.internalPool.getNumActive();
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
}
