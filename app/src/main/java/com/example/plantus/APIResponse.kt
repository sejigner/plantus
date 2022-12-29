package com.example.plantus

import com.example.plantus.dataClass.PlantIndex
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.InputStream
import java.io.InputStreamReader
import java.lang.Exception
import java.net.URL

class APIResponse {
    private var plantList = mutableListOf<PlantIndex>()
    // Key : 20221212PWK1VQ3ENERZMKRB9F47G
// 태그 이름 얻어오기
    // 첫번째 검색결과종료 후 줄바꿈
    // 파싱 다 종료 후 StringBuffer 문자열 객체 반환
//// addr 요소의 TEXT 읽어와서 문자열버퍼에 추가
    // 줄바꿈 문자 추가
// 태그 이름 얻어오기// url 위치로 인풋스트림 연결

    // inputstream 으로부터 xml 입력받기
    // 문자열로 된 요청 url을 URL 객체로 생성.
    fun getData(): MutableList<PlantIndex> {
        val buffer = StringBuffer()
        var plant = PlantIndex("","")
        val queryUrl = ("http://api.nongsaro.go.kr/service/garden/gardenList?"
                + "apiKey=20221212PWK1VQ3ENERZMKRB9F47G&numOfRows=100")
        try {
            val url = URL(queryUrl) // 문자열로 된 요청 url을 URL 객체로 생성.
            val `is`: InputStream = url.openStream() // url 위치로 인풋스트림 연결
            val factory: XmlPullParserFactory = XmlPullParserFactory.newInstance()
            val xpp: XmlPullParser = factory.newPullParser()

            // inputstream 으로부터 xml 입력받기
            xpp.setInput(InputStreamReader(`is`, "UTF-8"))
            var tag: String
            xpp.next()
            var eventType: Int = xpp.eventType
            while (eventType != XmlPullParser.END_DOCUMENT) {
                when (eventType) {
                    XmlPullParser.START_DOCUMENT -> {
                        buffer.append("파싱 시작 단계 \n\n")
                    }
                    XmlPullParser.START_TAG -> {
                        tag = xpp.name // 태그 이름 얻어오기
                        if (tag == "item") ; else if (tag == "cntntsNo") {
                            buffer.append("콘텐츠 번호 : ")
                            xpp.next()
                            // cntntsNo 요소의 TEXT 읽어와서 문자열버퍼에 추가
                            buffer.append(xpp.text)
                            buffer.append("\n") // 줄바꿈 문자 추가
                            plant.number = xpp.text
                        } else if (tag == "cntntsSj") {
                            buffer.append("식물명 : ")
                            xpp.next()
                            buffer.append(xpp.text)
                            buffer.append("\n")
                            plant.name = xpp.text
                        }
                    }
                    XmlPullParser.TEXT -> {}
                    XmlPullParser.END_TAG -> {
                        tag = xpp.name // 태그 이름 얻어오기
                        if(plant.name.isNotEmpty()&&plant.number.isNotEmpty()) {
                            plantList.add(plant)
                            plant = PlantIndex("","")
                        }
                        if (tag == "item") buffer.append("\n") // 첫번째 검색결과종료 후 줄바꿈
                    }
                }
                eventType = xpp.next()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        buffer.append("파싱 종료 단계 \n")
        return plantList // 파싱 다 종료 후 StringBuffer 문자열 객체 반환
    }
}