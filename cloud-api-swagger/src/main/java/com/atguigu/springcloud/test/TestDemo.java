package com.atguigu.springcloud.test;

import com.atguigu.springcloud.model.Reader;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * description:
 *
 * @author xiaoming.li04@hand-china.com
 * @datatime 2020/09/14 9:58
 */
public class TestDemo {
    public static void main(String[] args) throws JsonProcessingException {

     /*   ArrayList<String> list1 = new ArrayList<>();
        ArrayList<String> list2 = new ArrayList<>();
        Collections.addAll(list1,"1","2","3");
        Collections.addAll(list2,"4","5","6");
        list1.addAll(list2);
        System.out.println(list1);*/
        Reader reader = new Reader();
        reader.setAge(21);
        reader.setName("jack");
        ObjectMapper objectMapper = new ObjectMapper();
        String s = objectMapper.writeValueAsString(reader);
        System.out.println(s);
        Reader reader1 = objectMapper.readValue(s, Reader.class);
        System.out.println(reader1);
    }

}
