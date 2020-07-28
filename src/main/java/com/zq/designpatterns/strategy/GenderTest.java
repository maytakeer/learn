package com.zq.designpatterns.strategy;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zhangqing
 * @Package com.zq.designpatterns.strategy
 * @date 2020/7/25 10:02
 */
public class GenderTest {

    private static Map<BaseGender, GenderTypeEnum> genderMap = null;

    static {
        genderMap = new ConcurrentHashMap<>();
        genderMap.put(PersonTypeEnum.MAN, GenderTypeEnum.MAN);
        genderMap.put(PersonTypeEnum.WOMAN, GenderTypeEnum.WOMAN);
        genderMap.put(PersonTypeEnum.OTHER, GenderTypeEnum.OTHER);
        genderMap.put(AnimalTypeEnum.MAN, GenderTypeEnum.MAN);
        genderMap.put(AnimalTypeEnum.WOMAN, GenderTypeEnum.WOMAN);
        genderMap.put(AnimalTypeEnum.OTHER, GenderTypeEnum.OTHER);
    }

    public static void main(String[] args) {
        Integer personType = 1;
        PersonTypeEnum person = PersonTypeEnum.parse(personType);
        System.out.println("person:" + person.getCodeCn());
        GenderTypeEnum genderTypeEnum = genderMap.get(person);
        System.out.println(genderTypeEnum.getCodeCn());
        Integer animalType = 1;
        AnimalTypeEnum animal = AnimalTypeEnum.parse(animalType);
        System.out.println("animal:" + animal.getCodeCn());
        GenderTypeEnum genderTypeEnum1 = genderMap.get(animal);
        System.out.println(genderTypeEnum1.getCodeCn());
    }
}
