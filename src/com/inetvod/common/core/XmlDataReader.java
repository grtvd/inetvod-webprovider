/**
 * Copyright © 2004-2005 iNetVOD, Inc. All Rights Reserved.
 * Confidential and Proprietary
 */
package com.inetvod.common.core;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class XmlDataReader extends DataReader
{
	protected Document fDocument;
	protected ArrayList fCurNodeList;

	public XmlDataReader(InputStream stream) throws Exception
	{
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		//dbf.setIgnoringElementContentWhitespace(true);
		//dbf.setNamespaceAware(true);
		DocumentBuilder db = dbf.newDocumentBuilder();
		fDocument = db.parse(stream);
		fCurNodeList = new ArrayList();
		fCurNodeList.add(fDocument);
	}

	public void close()
	{
		//TODO:
//		if(fXmlTextWriter != null)
//		{
//			fXmlTextWriter.WriteEndDocument();
//			fXmlTextWriter.Close();
//		}
	}

	public void flush()
	{
		//TODO:
//		if(fXmlTextWriter != null)
//			fXmlTextWriter.Flush();
	}

	protected Node findChildNode(String fieldName) throws Exception
	{
		if(fCurNodeList.size() == 0)
			throw new Exception("No current node");

		NodeList nodeList = ((Node)(fCurNodeList.get(fCurNodeList.size() - 1))).getChildNodes();
		Node node;

		for(int i = 0; i < nodeList.getLength(); i++)
		{
			node = nodeList.item(i);
			if(node.getNodeName().matches(fieldName))
				return node;
		}

		return null;
	}

//	protected ArrayList FindChildNodes(string fieldName)
//	{
//		if(fCurNodeList.Count == 0)
//			throw new Exception("No current node");
//
//		ArrayList nodes = new ArrayList();
//
//		foreach(XmlNode node in ((XmlNode)(fCurNodeList[fCurNodeList.Count - 1])).ChildNodes)
//		{
//			if(node.LocalName.Equals(fieldName))
//				nodes.Add(node);
//		}
//
//		return nodes;
//	}

	protected String getNodeText(Node node)
	{
		NodeList nodeList = node.getChildNodes();
		Node childNode;

		for(int i = 0; i < nodeList.getLength(); i++)
		{
			childNode = nodeList.item(i);
			if(childNode.getNodeType() == Node.TEXT_NODE)
				return childNode.getNodeValue();
		}

		return null;
	}
	/**
	 * Read a Byte.
	 * @param fieldName
	 * @return may return null
	 */
	public Byte readByte(String fieldName) throws Exception
	{
		String data = readString(fieldName);

		if(data == null)
			return null;

		return Byte.decode(data);
	}

	/**
	 * Read a Short.
	 * @param fieldName
	 * @return may return null
	 */
	public Short readShort(String fieldName) throws Exception
	{
		String data = readString(fieldName);

		if(data == null)
			return null;

		return Short.decode(data);
	}

	/**
	 * Read a Integer.
	 * @param fieldName
	 * @return may return null
	 */
	public Integer readInt(String fieldName) throws Exception
	{
		String data = readString(fieldName);

		if(data == null)
			return null;

		return Integer.decode(data);
	}

	/**
	 * Read a Float.
	 * @param fieldName
	 * @return may return null
	 */
	public Float readFloat(String fieldName) throws Exception
	{
		String data = readString(fieldName);

		if(data == null)
			return null;

		return Float.valueOf(data);
	}

	/**
	 * Read a Double.
	 * @param fieldName
	 * @return may return null
	 */
	public Double readDouble(String fieldName) throws Exception
	{
		String data = readString(fieldName);

		if(data == null)
			return null;

		return Double.valueOf(data);
	}

	/**
	 * Internal read a String.
	 * @param fieldName
	 * @return may return null
	 */
	protected String readString(String fieldName) throws Exception
	{
		Node node = findChildNode(fieldName);
		if(node == null)
			return null;

		String data = getNodeText(node);
		if (data == null)
			return null;

		if(data.length() == 0)
			return null;

		return data;
	}

	/**
	 * Read a String.
	 * @param fieldName
	 * @param maxLength
	 * @return may return null
	 */
	public String readString(String fieldName, int maxLength) throws Exception
	{
		String data = readString(fieldName);
		if(data == null)
			return null;
		int len = data.length();

		if(len > maxLength)
			throw new Exception("invalid len(" + len + "), maxLength(" + maxLength + ")");

		return data;
	}

	/**
	 * Read a Date, no Time component.
	 * @param fieldName
	 * @return may return null
	 */
	public Date readDate(String fieldName) throws Exception
	{
		String data = readString(fieldName);

		if(data == null)
			return null;

		return DateFormat.getDateInstance().parse(data);
	}

	/**
	 * Read a Date with a Time compnent.
	 * @param fieldName
	 * @return may return null
	 */
	public Date readDateTime(String fieldName) throws Exception
	{
		throw new UnsupportedOperationException("need to implement");	//TODO: need to implement
	}

	/**
	 * Read a Boolean.
	 * @param fieldName
	 * @return may return null
	 */
	public Boolean readBoolean(String fieldName) throws Exception
	{
		throw new UnsupportedOperationException("need to implement");	//TODO: need to implement
	}

	/**
	 * Read an Object.
	 * @param fieldName
	 * @param ctorDataFiler
	 * @return may return null
	 */
	public Readable readObject(String fieldName, Constructor ctorDataFiler) throws Exception
	{
		Node node = findChildNode(fieldName);
		if(node == null)
			return null;

		fCurNodeList.add(node);
		Readable streamable = (Readable)ctorDataFiler.newInstance(new Object[] { this });
		fCurNodeList.remove(fCurNodeList.size() - 1);

		return streamable;
	}

	/**
	 * Read a list of complex Objects.
	 * @param fieldName
	 * @param listCtor
	 * @param itemCtorDataFiler
	 * @return will never return null, may return an empty list
	 */
	public List readList(String fieldName, Constructor listCtor, Constructor itemCtorDataFiler) throws Exception
	{
//		IList list = (IList)listCtor.Invoke(new object[] {});
//
//		ArrayList nodes = FindChildNodes(fieldName);
//		if(nodes.Count == 0)
//			return list;
//
//		foreach(XmlNode node in nodes)
//		{
//			fCurNodeList.Add(node);
//			Streamable streamable = (Streamable)itemCtorDataFiler.Invoke(new object[] { this });
//			list.Add(streamable);
//			fCurNodeList.RemoveAt(fCurNodeList.Count - 1);
//		}
//
//		return list;
		throw new UnsupportedOperationException("need to implement");	//TODO: need to implement
	}

	/**
	 * Read a list of Strings (or non-complex items than can be constructed from a sting).
	 * @param fieldName
	 * @param maxLength
	 * @param listCtor
	 * @param itemCtorString
	 * @return will never return null, may return an empty list
	 */
	public List readStringList(String fieldName, int maxLength, Constructor listCtor, Constructor itemCtorString) throws Exception
	{
//		IList list = (IList)listCtor.Invoke(new object[] {});
//
//		ArrayList nodes = FindChildNodes(fieldName);
//		if(nodes.Count == 0)
//			return list;
//
//		foreach(XmlNode node in nodes)
//		{
//			Streamable streamable = (Streamable)itemCtorString.Invoke(new object[] { node.InnerText });
//			list.Add(streamable);
//		}
//
//		return list;
		throw new UnsupportedOperationException("need to implement");	//TODO: need to implement
	}

	/**
	 * Read a DataID object.
	 * @param fieldName
	 * @param maxLength
	 * @param ctorString
	 * @return may return null
	 */
	public DataID readDataID(String fieldName, int maxLength, Constructor ctorString) throws Exception
	{
		String data = readString(fieldName, maxLength);

		if (data == null)
			return null;

		return (DataID)ctorString.newInstance(new Object[] { data });
	}
}
