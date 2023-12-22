package com.uccwal.naratracer.Entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "TB_NOTICE") // MongoDB 컬렉션 이름 설정
public class TracerEntity {



    private String category;
    @Id
    @Field("bidNumberLinkText")
    private String bidNumberLinkText;
    private String bidNumberLinkUrl;
    private String urgency;
    private String titleLinkText;
    private String titleLinkUrl;
    private String organization;
    private String bidder;
    private String competitionType;
    private String bidStart;
    private String bidEnd;
    // 다른 필드들 추가

    // 생성자, getter, setter 등 필요한 메소드들


    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getBidNumberLinkText() {
        return bidNumberLinkText;
    }

    public void setBidNumberLinkText(String bidNumberLinkText) {
        this.bidNumberLinkText = bidNumberLinkText;
    }

    public String getBidNumberLinkUrl() {
        return bidNumberLinkUrl;
    }

    public void setBidNumberLinkUrl(String bidNumberLinkUrl) {
        this.bidNumberLinkUrl = bidNumberLinkUrl;
    }

    public String getUrgency() {
        return urgency;
    }

    public void setUrgency(String urgency) {
        this.urgency = urgency;
    }

    public String getTitleLinkText() {
        return titleLinkText;
    }

    public void setTitleLinkText(String titleLinkText) {
        this.titleLinkText = titleLinkText;
    }

    public String getTitleLinkUrl() {
        return titleLinkUrl;
    }

    public void setTitleLinkUrl(String titleLinkUrl) {
        this.titleLinkUrl = titleLinkUrl;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getBidder() {
        return bidder;
    }

    public void setBidder(String bidder) {
        this.bidder = bidder;
    }

    public String getCompetitionType() {
        return competitionType;
    }

    public void setCompetitionType(String competitionType) {
        this.competitionType = competitionType;
    }

    public String getBidStart() {
        return bidStart;
    }

    public void setBidStart(String bidStart) {
        this.bidStart = bidStart;
    }

    public String getBidEnd() {
        return bidEnd;
    }

    public void setBidEnd(String bidEnd) {
        this.bidEnd = bidEnd;
    }

    @Override
    public String toString() {
        return "TracerEntity{" +
                "category='" + category + '\'' +
                ", bidNumberLinkText='" + bidNumberLinkText + '\'' +
                ", bidNumberLinkUrl='" + bidNumberLinkUrl + '\'' +
                ", urgency='" + urgency + '\'' +
                ", titleLinkText='" + titleLinkText + '\'' +
                ", titleLinkUrl='" + titleLinkUrl + '\'' +
                ", organization='" + organization + '\'' +
                ", bidder='" + bidder + '\'' +
                ", competitionType='" + competitionType + '\'' +
                ", bidStart='" + bidStart + '\'' +
                ", bidEnd='" + bidEnd + '\'' +
                '}';
    }
}