package com.visionaryproviders.agridoctor.utils;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CropData {

	private int id;
    private String cropName;
    private int n;
    private int p;
    private int k;
    private double pH;
    private int soilMoisture;

}
