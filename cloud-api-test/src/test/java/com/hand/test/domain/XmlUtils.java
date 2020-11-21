package com.hand.test.domain;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

/**
 * xml转化工具类
 *
 * @author dawei.wang@hand-china.com 2020-09-23
 */
public class XmlUtils {

    public static String CHARSET_NAME = "UTF-8";
    public static String SEPARATOR = "_";


    /**
     * @param load java对象.Class
     * @return xml文件的String
     * @throws JAXBException
     * @author dawei.wang@hand-china.com 2020-09-23
     * java对象转换为xml文件
     */
    public static String beanToXml(Object obj, Class<?> load) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(load);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.setProperty(Marshaller.JAXB_ENCODING, CHARSET_NAME);
        StringWriter writer = new StringWriter();
        marshaller.marshal(obj, writer);
        return writer.toString();
    }

    /**
     * @param xmlPath xml文件路径
     * @param load    java对象.Class
     * @return java对象
     * @throws JAXBException
     * @throws IOException
     * @author dawei.wang@hand-china.com 2020-09-23
     * xml文件配置转换为对象
     */
    public static Object xmlToBean(String xmlPath, Class<?> load) throws JAXBException, IOException {
        JAXBContext context = JAXBContext.newInstance(load);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        Object object = unmarshaller.unmarshal(new File(xmlPath));
        return object;
    }


    /**
     * 将下划线和后面第一个字母转换成大写
     *
     * @param name
     * @param anotherName
     * @return
     */
    public static String upperTable(String name, String anotherName) {
        name = anotherName;
        //如果最后一个是_ 不做转换
        if (name.indexOf(SEPARATOR) > 0 && name.length() != name.indexOf(SEPARATOR) + 1) {
            int lengthPlace = name.indexOf(SEPARATOR);
            name = name.replaceFirst(SEPARATOR, "");
            String s = name.substring(lengthPlace, lengthPlace + 1);
            s = s.toUpperCase();
            anotherName = name.substring(0, lengthPlace) + s + name.substring(lengthPlace + 1);
        } else {
            return anotherName;
        }
        return upperTable(name, anotherName);
    }


    /**
     * @param t 转化对象
     * @return xml文件的String
     * @throws JAXBException
     * @author dawei.wang@hand-china.com 2020-09-23
     * String xml转化为对象
     */
    public static <T> T convert2Bean(T t, String xml) throws JAXBException {
        StringReader reader = new StringReader(xml);
        JAXBContext jaxbContext = JAXBContext.newInstance(t.getClass());
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        T result = (T) jaxbUnmarshaller.unmarshal(reader);
        return result;
    }
}
