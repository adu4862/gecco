package com.geccocrawler.gecco.pipeline;

public class MovieDetail {
    String code;
    String detail;
    String title;
    String img1;
     String descb;

    private String movInfoOuter;

    public String getMovInfoOuter() {
        return movInfoOuter;
    }

    public void setMovInfoOuter(String movInfoOuter) {
        this.movInfoOuter = movInfoOuter;
    }
    public String getImg1() {
        return img1;
    }

    public void setImg1(String img1) {
        this.img1 = img1;
    }

    public String getDescb() {
        return descb;
    }

    public void setDescb(String descb) {
        this.descb = descb;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
