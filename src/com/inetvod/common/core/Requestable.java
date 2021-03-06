/**
 * Copyright � 2004-2005 iNetVOD, Inc. All Rights Reserved.
 * Confidential and Proprietary
 */
package com.inetvod.common.core;

public interface Requestable extends Readable, Writeable
{
	Writeable fulfillRequest() throws Exception;
}
