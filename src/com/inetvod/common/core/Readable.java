package com.inetvod.common.core;

/**
 * Created by IntelliJ IDEA.
 * User: Bob
 * Date: Jun 8, 2004
 * Time: 11:26:26 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract interface Readable
{
	public abstract void readFrom(DataReader reader) throws Exception;
}
