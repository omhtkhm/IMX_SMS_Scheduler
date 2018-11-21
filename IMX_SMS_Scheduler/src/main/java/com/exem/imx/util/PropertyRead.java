package com.exem.imx.util;
 
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PropertyRead {
	public String conn_ip;
	public String conn_port;
	public String sid;
	public String user;
	public String password;
	
	public String sms_conn_ip;
	public String sms_conn_port;
	public String sms_sid;
	public String sms_user;
	public String sms_password;
	
	public String query1;
	public String sms_insert_query;
	
	public ArrayList userList;
	
	public HashMap<String, String> bindVariablesMap;
	
	Document doc;
			
	private static final PropertyRead INSTANCE = new PropertyRead();
	private PropertyRead(){
		bindVariablesMap = new HashMap<>();
		readConfigFile();
	}
	public static PropertyRead getInstance(){ return INSTANCE;}

	public void readConfigFile() {
		try {

			File fXmlFile = new File("IMXScheduler.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();

			NodeList nList = doc.getElementsByTagName("intermax_repository");

			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;

					conn_ip = getTagValue("conn_ip", eElement);
					conn_port = getTagValue("conn_port", eElement);
					sid = getTagValue("sid", eElement);
					user = getTagValue("user", eElement);
					password = getTagValue("password", eElement);
				}
			}
			
			NodeList sms_nList = doc.getElementsByTagName("sms_db");

			for (int temp = 0; temp < sms_nList.getLength(); temp++) {

				Node nNode = sms_nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;

					sms_conn_ip = getTagValue("conn_ip", eElement);
					sms_conn_port = getTagValue("conn_port", eElement);
					sms_sid = getTagValue("sid", eElement);
					sms_user = getTagValue("user", eElement);
					sms_password = getTagValue("password", eElement);
				}
			}
			
			NodeList metric_nList = doc.getElementsByTagName("sms_metric");

			for (int temp = 0; temp < metric_nList.getLength(); temp++) {

				Node nNode = metric_nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;

					query1 = getTagValue("query1", eElement);
					
					System.out.println("----------------------");
					System.out.println("** sms message query : "+ query1);
				}
			}
			
			NodeList sms_table_nList = doc.getElementsByTagName("sms_table");

			for (int temp = 0; temp < sms_table_nList.getLength(); temp++) {

				Node nNode = sms_table_nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;

					sms_insert_query = getTagValue("sms_insert_query", eElement);
					System.out.println("** sms insert query : "+ sms_insert_query);
					System.out.println("----------------------");
				}
			}
			
			NodeList sms_user_nList = doc.getElementsByTagName("sms_user");
			userList = new ArrayList<HashMap<String,String>>();

			for (int temp = 0; temp < sms_user_nList.getLength(); temp++) {

				Node nNode = sms_user_nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;

					HashMap<String,String> map = new HashMap<String,String>();
					map.put("user_name", getTagValue("user_name", eElement));
					map.put("phone_number", getTagValue("phone_number", eElement));
					userList.add(map);
					
					System.out.println("* name : "+getTagValue("user_name", eElement));
					System.out.println("* phone: "+getTagValue("phone_number", eElement));
					System.out.println("----------------------");
				}
			}
			
			// bind변수 설정값 읽기
			NodeList bind_nList = doc.getElementsByTagName("SMS_SET_BIND_VALUE");

			{
				Node nNode = bind_nList.item(0); 
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
	
					Element eElement = (Element) nNode;
					
                    // SMS_SET_BIND_VALUE xml태그의 자식태그 한번 더 획득
                    NodeList childlist = nNode.getChildNodes();
                    //자식노드 1개이상일경우
                    System.out.println("[Bind Variables]");
                    if(childlist.getLength() > 0) {
                        for(int j=0; j<childlist.getLength(); j++) {
                            // SMS_SET_BIND_VALUE xml 태그내에 존재하는 태그들의 태그명 + 태그에 속하는 내용 출력
                        	if(Node.TEXT_NODE !=childlist.item(j).getNodeType()){
                                System.out.println(" * " + childlist.item(j).getNodeName()+" : "+childlist.item(j).getTextContent());
                                bindVariablesMap.put(childlist.item(j).getNodeName(), childlist.item(j).getTextContent());
                        	}
                        }
                    }
                    System.out.println("----------------------");
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static String getTagValue(String sTag, Element eElement) {
		NodeList nlList = eElement.getElementsByTagName(sTag).item(0).getChildNodes();

		Node nValue = (Node) nlList.item(0);

		return nValue.getNodeValue();
	}
	
//	public void readConfigFile2() {
//		try {
//
//			File fXmlFile = new File("IMXScheduler.xml");
//			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
//			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
//			Document doc = dBuilder.parse(fXmlFile);
//			doc.getDocumentElement().normalize();
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
	
//	public void readValueAtConfigFile(String pElement) {
//		
//		NodeList nList = doc.getElementsByTagName(pElement);
//		
//		for (int temp = 0; temp < nList.getLength(); temp++) { // 같은 엘리먼트가 여러개일 경우, 여러번 반복해서 수집하기 위한 반복
//
//			Node nNode = nList.item(temp);
//			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
//
//				Element eElement = (Element) nNode;
//
//				// HashMap에서 태그 Element를 뽑아서 매핑처리
//				
//		        for( Map.Entry<String, String> elem : cElementMap.entrySet() ){
////		            System.out.println( String.format("키 : %s, 값 : %s", elem.getKey(), elem.getValue()) );
//					
//		            sms_insert_query = getTagValue(elem.getKey(), eElement);
//		        }
//
//			}
//		}
//		
//	}
}
