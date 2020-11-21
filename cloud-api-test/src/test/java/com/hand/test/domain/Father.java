package com.hand.test.domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * description:
 *
 * @author xiaoming.li04@hand-china.com
 * @datatime 2020/10/10 16:11
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class Father {
   @XmlElement(name = "Name")
   private String name;

   @XmlElement(name = "Work")
   private String work;
}
