package com.hand.test.domain;

import javax.xml.bind.annotation.XmlRootElement;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * description:
 *
 * @author xiaoming.li04@hand-china.com
 * @datatime 2020/10/10 16:08
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "Son")
public class Son {
    private String hobby;
    private String sex;
}
