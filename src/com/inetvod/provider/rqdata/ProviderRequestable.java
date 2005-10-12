/**
 * Copyright © 2005 iNetVOD, Inc. All Rights Reserved.
 * Confidential and Proprietary
 */
package com.inetvod.provider.rqdata;

import com.inetvod.common.core.Requestable;

public interface ProviderRequestable extends Requestable
{
	StatusCode getStatusCode();
}
