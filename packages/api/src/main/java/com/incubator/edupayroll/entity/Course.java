package com.incubator.edupayroll.entity;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public enum Course {
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

    private static final Map<Course, String> labelMap = new HashMap<>() {{
        put(Course.Type101, "Gündüz");
        put(Course.Type102, "Gece");
        put(Course.Type103, "%25 Fazla Gündüz");
        put(Course.Type104, "%25 Fazla Gece");
        put(Course.Type106, "Belleticilik");
        put(Course.Type107, "Sınav Görevi");
        put(Course.Type108, "Egzersiz");
        put(Course.Type109, "Hizmetiçi");
        put(Course.Type110, "EDYGG-Gündüz");
        put(Course.Type111, "EDYGG-Gece");
        put(Course.Type112, "EDYGG - %25 Gündüz");
        put(Course.Type113, "EYGG - %25 Gece");
        put(Course.Type114, "Atış Eğitimi");
        put(Course.Type115, "Cezaevleri Eğitim Merkezi - 160*Saat");
        put(Course.Type116, "Takviye Kursu (Gündüz)");
        put(Course.Type117, "Takviye Kursu (Gece/Haftasonu)");
        put(Course.Type118, "Belleticilik %25 Fazla");
        put(Course.Type119, "Nöbet Görevi");
        put(Course.Type1070, "Salon Başkanı");
        put(Course.Type10700, "Gözetmen");
        put(Course.Type107000, "Yedek Gözetmen");
        put(Course.Type1070000, "Yedek Engelli Gözetmen");
        put(Course.Type121, "Nöbet Ücreti % 25 Fazla");
        put(Course.Type122, "İYEP-Gündüz");
        put(Course.Type123, "İYEP-Gece/Haftasonu");
        put(Course.Type212, "Gündüz (2/3-AFAD Eğt.Yard.)");
    }};

    Course(int code) {
        this.code = code;
    }

    public String getLabel() {
        return labelMap.get(this);
    }
}