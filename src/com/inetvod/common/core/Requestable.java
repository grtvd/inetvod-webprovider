package com.inetvod.common.core;

public interface Requestable extends Readable, Writeable
{
	Writeable fulfillRequest() throws Exception;
	StatusCode getStatusCode();
}
