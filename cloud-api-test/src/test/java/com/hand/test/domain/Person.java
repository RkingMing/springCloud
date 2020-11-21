package com.hand.test.domain;

import java.util.List;
import javax.xml.bind.annotation.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * description:
 *
 * @author xiaoming.li04@hand-china.com
 * @datatime 2020/10/10 15:40
 */
@XmlRootElement(name = "Person")
@Data
@AllArgsConstructor
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
public class Person {
    @XmlAttribute(name = "Id")
    private int id;
    @XmlElement(name = "Name")
    private String name;
    @XmlElement(name="Gender")
    private String gender;
    private String addr;
    @XmlElementWrapper(name = "Sons")
    @XmlElement(name = "Son")
    private List<Son> son;
    private Father father;
}
