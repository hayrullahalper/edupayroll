package com.incubator.edupayroll.entity.record;

import java.util.HashMap;
import java.util.Map;
import lombok.Getter;

@Getter
public enum RecordType {
  Course101(101),
  Course102(102),
  Course103(103),
  Course104(104),
  Course106(106),
  Course107(107),
  Course108(108),
  Course109(109),
  Course110(110),
  Course111(111),
  Course112(112),
  Course113(113),
  Course114(114),
  Course115(115),
  Course116(116),
  Course117(117),
  Course118(118),
  Course119(119),
  Course1070(1070),
  Course10700(10700),
  Course107000(107000),
  Course1070000(1070000),
  Course121(121),
  Course122(122),
  Course123(123),
  Course212(212);

  private final int code;

  private static final Map<RecordType, String> labelMap =
      new HashMap<>() {
        {
          put(RecordType.Course101, "Gündüz");
          put(RecordType.Course102, "Gece");
          put(RecordType.Course103, "%25 Fazla Gündüz");
          put(RecordType.Course104, "%25 Fazla Gece");
          put(RecordType.Course106, "Belleticilik");
          put(RecordType.Course107, "Sınav Görevi");
          put(RecordType.Course108, "Egzersiz");
          put(RecordType.Course109, "Hizmetiçi");
          put(RecordType.Course110, "EDYGG-Gündüz");
          put(RecordType.Course111, "EDYGG-Gece");
          put(RecordType.Course112, "EDYGG - %25 Gündüz");
          put(RecordType.Course113, "EYGG - %25 Gece");
          put(RecordType.Course114, "Atış Eğitimi");
          put(RecordType.Course115, "Cezaevleri Eğitim Merkezi - 160*Saat");
          put(RecordType.Course116, "Takviye Kursu (Gündüz)");
          put(RecordType.Course117, "Takviye Kursu (Gece/Haftasonu)");
          put(RecordType.Course118, "Belleticilik %25 Fazla");
          put(RecordType.Course119, "Nöbet Görevi");
          put(RecordType.Course1070, "Salon Başkanı");
          put(RecordType.Course10700, "Gözetmen");
          put(RecordType.Course107000, "Yedek Gözetmen");
          put(RecordType.Course1070000, "Yedek Engelli Gözetmen");
          put(RecordType.Course121, "Nöbet Ücreti % 25 Fazla");
          put(RecordType.Course122, "İYEP-Gündüz");
          put(RecordType.Course123, "İYEP-Gece/Haftasonu");
          put(RecordType.Course212, "Gündüz (2/3-AFAD Eğt.Yard.)");
        }
      };

  RecordType(int code) {
    this.code = code;
  }

  public String getLabel() {
    return labelMap.get(this);
  }
}
