package com.uccwal.naratracer.Test;


import com.uccwal.naratracer.Service.TracerService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
class TracerServiceTest {

    @Test
    void testGetDateTimeData() {
        // 샘플 HTML 문서를 생성합니다.
        String html = "<div>2023-12-21<br>2023-12-22</div>";
        Document document = Jsoup.parse(html);

        // 날짜와 시간을 포함한 div를 선택합니다.
        Elements dateTimeElements = document.select("div");

        // 테스트할 메서드를 호출합니다.
        JSONObject result = TracerService.getDateTimeData(dateTimeElements);

        // 결과를 검증합니다.
        assertEquals("2023-12-21", result.getString("bidEnd"));
        assertEquals("2023-12-22", result.getString("bidStart"));
    }


    // 필요한 경우 다른 기능에 대한 테스트 메서드를 추가할 수 있습니다.
}