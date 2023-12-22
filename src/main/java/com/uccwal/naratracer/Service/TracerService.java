package com.uccwal.naratracer.Service;
import com.uccwal.naratracer.Entity.TracerEntity;
import com.uccwal.naratracer.Repository.TracerRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.jsoup.safety.Whitelist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


@Service
public class TracerService {

    private static final Logger logger = LoggerFactory.getLogger(TracerService.class);
    private final TracerRepository tracerRepository;
    @Autowired
    public TracerService(TracerRepository tracerRepository) {
        this.tracerRepository = tracerRepository;
    }

    public void tracerAndSaveData() {
        try {
            LocalDate today = LocalDate.now();

            // 한 달 전 날짜
            LocalDate oneMonthAgo = today.minusMonths(1);

            // DateTimeFormatter를 사용하여 원하는 형식으로 날짜를 문자열로 변환
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
            String fromDate = oneMonthAgo.format(formatter);
            String toDate = today.format(formatter);
            // 크롤링할 기본 URL
            String baseUrl = "https://www.g2b.go.kr:8101/ep/tbid/tbidList.do?taskClCds=&bidNm=&searchDtType=1&fromBidDt="+ fromDate +"&toBidDt="+ toDate +"&fromOpenBidDt=&toOpenBidDt=&radOrgan=1&instNm=&area=&regYn=Y&bidSearchType=1&searchType=1&recordCountPerPage=100&currentPageNo=";



            // 페이지 1부터 5까지 반복
            for (int pageNo = 1; pageNo <= 5; pageNo++) {
                // 현재 페이지의 URL 생성
                String url = baseUrl + pageNo;

                // Jsoup을 사용하여 페이지 내용을 가져오기
                Document doc = Jsoup.connect(url).get();

                // 크롤링할 데이터 선택
                Element table = doc.select(".table_list_tbidTbl").first();
                Elements rows = table.select("tbody tr");

                // 각 행(업체 정보)에 대해 반복
                for (Element row : rows) {
                    TracerEntity entity = new TracerEntity();

                    // 각 열(데이터)에 대해 반복
                    Elements columns = row.select("td");
                    entity.setCategory(columns.get(0).text());
                    entity.setBidNumberLinkText(getLinkText(columns.get(1).select("a")));
                    entity.setBidNumberLinkUrl(getLinkUrl(columns.get(1).select("a")));
                    entity.setUrgency(columns.get(2).text());
                    entity.setTitleLinkText(getLinkText(columns.get(3).select("a")));
                    entity.setTitleLinkUrl(getLinkUrl(columns.get(3).select("a")));
                    entity.setOrganization(columns.get(4).text());
                    entity.setBidder(columns.get(5).text());
                    entity.setCompetitionType(columns.get(6).text());
                    JSONObject bidDateTimeData = getDateTimeData(columns.get(7).select("div"));
                    entity.setBidStart(bidDateTimeData.getString("bidStart"));
                    entity.setBidEnd(bidDateTimeData.getString("bidEnd"));

                    // MongoDB에 저장
                    tracerRepository.save(entity);
                    //logger.info(String.valueOf(entity));
                }

                // 크롤링한 데이터 출력
                logger.info("Crawled Data: " + url);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void tracerAndPrintData() {
        try {
            // 크롤링할 페이지 URL
            String url = "https://www.g2b.go.kr:8101/ep/tbid/tbidList.do?taskClCds=&bidNm=&searchDtType=1&fromBidDt=2023/11/21&toBidDt=2023/12/21&fromOpenBidDt=&toOpenBidDt=&radOrgan=1&instNm=&area=&regYn=Y&bidSearchType=1&searchType=1&recordCountPerPage=100";

            // Jsoup을 사용하여 페이지 내용을 가져오기
            Document doc = Jsoup.connect(url).get();

            // 크롤링할 데이터 선택
            Element table = doc.select(".table_list_tbidTbl").first();
            Elements rows = table.select("tbody tr");

            // JSON 배열을 만들기 위한 리스트
            List<JSONObject> jsonDataList = new ArrayList<>();

            // 각 행(업체 정보)에 대해 반복
            for (Element row : rows) {
                JSONObject rowData = new JSONObject();

                // 각 열(데이터)에 대해 반복
                Elements columns = row.select("td");
                rowData.put("category", columns.get(0).text());
                rowData.put("bidNumberLink_text", getLinkText(columns.get(1).select("a")));
                rowData.put("bidNumberLink_url", getLinkUrl(columns.get(1).select("a")));
                rowData.put("urgency", columns.get(2).text());
                rowData.put("titleLink_text", getLinkText(columns.get(3).select("a")));
                rowData.put("titleLink_url", getLinkUrl(columns.get(3).select("a")));
                rowData.put("organization", columns.get(4).text());
                rowData.put("bidder", columns.get(5).text());
                rowData.put("competitionType", columns.get(6).text());
                JSONObject bidDateTimeData = getDateTimeData(columns.get(7).select("div"));
                rowData.put("bidStart", bidDateTimeData.getString("bidStart"));
                rowData.put("bidEnd", bidDateTimeData.getString("bidEnd"));
                rowData.put("fingerprintButton", getButtonData(columns.get(8).select("button")));


                jsonDataList.add(rowData);

            }

            // 크롤링한 데이터 출력
            logger.info("Crawled Data: " + url);
            logger.info("Crawled JSON Data: " + new JSONArray(jsonDataList).toString());

            // TODO: 원하는 작업 수행
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



        // 링크 텍스트를 가져오는 함수
        private static String getLinkText(Elements linkElements) {
            if (!linkElements.isEmpty()) {
                Element link = linkElements.first();
                return link.text();
            }
            return "";
        }

        // 링크 URL을 가져오는 함수
        private static String getLinkUrl(Elements linkElements) {
            if (!linkElements.isEmpty()) {
                Element link = linkElements.first();
                return link.attr("href");
            }
            return "";
        }




    // 날짜 및 시간 데이터를 가져오는 함수
        private static JSONObject getDateTimeData(Elements dateTimeElements) {
            JSONObject dateTimeData = new JSONObject();
            if (!dateTimeElements.isEmpty()) {
                String cleanedHtml = Jsoup.clean(dateTimeElements.first().html(), Whitelist.basic());
                String[] dateTimeParts = cleanedHtml.split("<br>");

                if (dateTimeParts.length == 2) {
                    dateTimeData.put("bidStart", dateTimeParts[0].trim());

                    // "bidStart" 값을 정리
                    String bidStartRaw = dateTimeParts[1].trim();
                    String cleanedBidStart = bidStartRaw.replaceAll("<span>", "").replaceAll("</span>", "").replaceAll("\\(", "").replaceAll("\\)", "");
                    dateTimeData.put("bidEnd", cleanedBidStart);
                }
            }
            return dateTimeData;
        }

        // 버튼 데이터를 가져오는 함수
        private static JSONObject getButtonData(Elements buttonElements) {
            JSONObject buttonData = new JSONObject();
            if (!buttonElements.isEmpty()) {
                Element button = buttonElements.first();
                buttonData.put("onClick", button.attr("onclick"));
                buttonData.put("title", button.attr("title"));
            }
            return buttonData;
        }

}
