package com.hackerbetter.artist.util.common;

import java.io.StringReader;

import javax.xml.parsers.DocumentBuilderFactory;

import org.dom4j.Document;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.InputSource;

/**
 * xml公共类
 * @author Administrator
 *
 */
public class XmlUtil {
	private static final Logger logger=LoggerFactory.getLogger(XmlUtil.class);
	/**
	 * 将字符串转成Document
	 * @param docString
	 * @return
	 */
	public static Document convertStringToDoc(String docString) {
		SAXReader saxReader = new SAXReader();
		Document doc = null;
		try {
			doc = saxReader.read(new StringReader(docString));
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return doc;
	}

	/**
	 * 将字符串转成w3c Document
	 * @param docString
	 * @return
	 */
	public static org.w3c.dom.Document convertStringToW3cDoc(String docString) {
		org.w3c.dom.Document doc = null;
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();  
	        dbf.setNamespaceAware(true);
			doc = dbf.newDocumentBuilder().parse(new InputSource(new StringReader(docString)));
		} catch (Exception e) {
			logger.error("字符串转w3c Document异常",e);
		}
		return doc;
	}
	
}
