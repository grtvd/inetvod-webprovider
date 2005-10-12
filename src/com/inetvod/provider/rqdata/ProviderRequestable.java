/**
 * Copyright © 2005 iNetVOD, Inc. All Rights Reserved.
 * Confidential and Proprietary
 */
package com.inetvod.provider.rqdata;

import com.inetvod.common.core.Requestable;
import com.inetvod.common.core.StatusCode;

public interface ProviderRequestable extends Requestable
{
	StatusCode getStatusCode();
}
