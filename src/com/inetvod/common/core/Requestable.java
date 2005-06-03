package com.inetvod.common.core;

import com.inetvod.common.core.Readable;
import com.inetvod.common.core.Writeable;
import com.inetvod.common.core.StatusCode;

public interface Requestable extends Readable, Writeable
{
	Writeable fulfillRequest() throws Exception;
	StatusCode getStatusCode();
}
