/**
 * Copyright © 2005 iNetVOD, Inc. All Rights Reserved.
 * Confidential and Proprietary
 */
package com.inetvod.provider.rqdata;

import com.inetvod.common.core.DataReader;
import com.inetvod.common.core.Readable;
import com.inetvod.common.core.Writeable;
import com.inetvod.common.core.DataWriter;

import java.lang.reflect.Constructor;

public class Authenticate implements Readable, Writeable
{
	/* Constants */
	public static final Constructor CtorDataFiler = DataReader.getCtor(Authenticate.class);

	public static final int AdminUserIDMaxLength = 64;
	public static final int AdminPasswordMaxLength = 32;
	public static final int MemberUserIDMaxLength = 64;
	public static final int MemberPasswordMaxLength = 32;

	/* Fields */
	protected String fAdminUserID;
	protected String fAdminPassword;
	protected String fMemberUserID;
	protected String fMemberPassword;

	public Authenticate(DataReader reader) throws Exception
	{
		readFrom(reader);
	}

	public void readFrom(DataReader reader) throws Exception
	{
		fAdminUserID = reader.readString("AdminUserID", AdminUserIDMaxLength);
		fAdminPassword = reader.readString("AdminPassword", AdminPasswordMaxLength);
		fMemberUserID = reader.readString("MemberUserID", MemberUserIDMaxLength);
		fMemberPassword = reader.readString("MemberPassword", MemberPasswordMaxLength);
	}

	public void writeTo(DataWriter writer) throws Exception
	{
		writer.writeString("AdminUserID", fAdminUserID, AdminUserIDMaxLength);
		writer.writeString("AdminPassword", fAdminPassword, AdminPasswordMaxLength);
		writer.writeString("MemberUserID", fMemberUserID, MemberUserIDMaxLength);
		writer.writeString("MemberPassword", fMemberPassword, MemberPasswordMaxLength);
	}
}
