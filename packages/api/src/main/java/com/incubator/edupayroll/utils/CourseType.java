package com.incubator.edupayroll.utils;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public enum CourseType {
    Type101(101),
    Type102(102),
    Type103(103),
    Type104(104),
    Type106(106),
    Type107(107),
    Type108(108),
    Type109(109),
    Type110(110),
    Type111(111),
    Type112(112),
    Type113(113),
    Type114(114),
    Type115(115),
    Type116(116),
    Type117(117),
    Type118(118),
    Type119(119),
    Type1070(1070),
    Type10700(10700),
    Type107000(107000),
    Type1070000(1070000),
    Type121(121),
    Type122(122),
    Type123(123),
    Type212(212);

    private final int code;

    private static final Map<CourseType, String> labelMap = new HashMap<>() {{
        put(CourseType.Type101, "Gündüz");
        put(CourseType.Type102, "Gece");
        put(CourseType.Type103, "%25 Fazla Gündüz");
        put(CourseType.Type104, "%25 Fazla Gece");
        put(CourseType.Type106, "Belleticilik");
        put(CourseType.Type107, "Sınav Görevi");
        put(CourseType.Type108, "Egzersiz");
        put(CourseType.Type109, "Hizmetiçi");
        put(CourseType.Type110, "EDYGG-Gündüz");
        put(CourseType.Type111, "EDYGG-Gece");
        put(CourseType.Type112, "EDYGG - %25 Gündüz");
        put(CourseType.Type113, "EYGG - %25 Gece");
        put(CourseType.Type114, "Atış Eğitimi");
        put(CourseType.Type115, "Cezaevleri Eğitim Merkezi - 160*Saat");
        put(CourseType.Type116, "Takviye Kursu (Gündüz)");
        put(CourseType.Type117, "Takviye Kursu (Gece/Haftasonu)");
        put(CourseType.Type118, "Belleticilik %25 Fazla");
        put(CourseType.Type119, "Nöbet Görevi");
        put(CourseType.Type1070, "Salon Başkanı");
        put(CourseType.Type10700, "Gözetmen");
        put(CourseType.Type107000, "Yedek Gözetmen");
        put(CourseType.Type1070000, "Yedek Engelli Gözetmen");
        put(CourseType.Type121, "Nöbet Ücreti % 25 Fazla");
        put(CourseType.Type122, "İYEP-Gündüz");
        put(CourseType.Type123, "İYEP-Gece/Haftasonu");
        put(CourseType.Type212, "Gündüz (2/3-AFAD Eğt.Yard.)");
    }};

    CourseType(int code) {
        this.code = code;
    }

    public String getLabel() {
        return labelMap.get(this);
    }
}