package com.hand.test;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.xml.bind.JAXB;
import javax.xml.bind.JAXBException;

import com.hand.test.domain.Father;
import com.hand.test.domain.Person;
import com.hand.test.domain.Son;
import com.hand.test.domain.XmlUtils;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StringUtils;

/**
 * description:
 *
 * @author xiaoming.li04@hand-china.com
 * @datatime 2020/09/26 15:56
 */
@SpringBootTest
public class TestDemo {

    @Test
    public void testMath(){
        List<String> strings = Arrays.asList("1", "2");
        strings.add("3");
    }

    @Test
    public void generateXML() throws JAXBException {
        List<Son> list=new ArrayList<>();
        Son son1 = new Son("eat","男");
        Son son2 = new Son("play","女");
        list.add(son2);
        list.add(son1);
        Father father = new Father("jack","IT");
        Person person = new Person();
        person.setId(1);
        person.setAddr("襄阳");
        person.setGender("男");
        person.setName("rose");
        person.setFather(father);
        person.setSon(list);
//        File file = new File("D:\\person.xml");
//        JAXB.marshal(person, file);
        String s = XmlUtils.beanToXml(person, Person.class);
        System.out.println(s);
    }

    @Test
    public void generateBean() {
        File file = new File("E:\\person.xml");
        Person person = JAXB.unmarshal(file, Person.class);
        System.out.println(person);
    }

    @Test
    public void test1(){
        ArrayList<String> strings = new ArrayList<>();
        strings.add("1");
        add1(strings);
        add2(strings);
        System.out.println(strings);

    }

    public void add1(ArrayList<String> strings){

        strings.add("a");
    }
    public void add2(ArrayList<String> strings){

        strings.add("b");
    }
    @Test
    public void testNull(){
        File file=null;
        System.out.println(StringUtils.isEmpty(file));
        String[] strs={"1","2"};
        Stream<String> stream = Arrays.stream(strs);
        List<Integer> list = stream.map(s -> Integer.valueOf(s)).filter(i->i>1).collect(Collectors.toList());
        System.out.println(list);

    }
}
