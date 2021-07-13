# aop-part3-chapter07 - 에어비엔비

- 해당 프로젝트의 자세한 내용은 저의 [블로그 글](https://whyprogrammer.tistory.com/600)을 통해 확인할 수도 있습니다.

# 목차

1. 인트로 (완성앱 & 구현 기능 소개)
2. 네이버 지도 API 사용하기
3. 우리집 위치에 마커 찍어보기
4. 지도 위에 BottomSheetDialog 띄우기
5. Retrofit을 사용하여 서버에서 가져온 예약가능 목록 보여주기
6. 지도 위에 예약가능 집 목록 띄우기
7. BottomSheetDialog에 예약가능 집 목록 띄우기
8. 마커와 리스트 연동하기
9. 공유하기 기능 구현하기
11. 마무리

# 결과화면

![1](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FzqI23%2Fbtq8rfXEDIe%2FDFg5AkkdZbPHgZj6xvKtrk%2Fimg.png)

![2](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2Fbk8xT0%2Fbtq8v2a8lYL%2FdaP4SIq4091wDE3zeoGDH1%2Fimg.png)

![3](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FbWkhh6%2Fbtq8qWcos3G%2FMBCM5fUM76lyeSAuCyWPd0%2Fimg.png)

# 이 챕터를 통해 배우는 것

- **Naver Map API** 사용하기
- **ViewPager2** 사용하기
  - 내부 레이아웃이 match parent 이어야 죽지 않음.
- **FrameLayout** 알아보기
- **CoordinatorLayout** 사용하기
    - 프레임 레이아웃에 강력한 기능을 더한 레이아웃
    - 으로 인터렉션이 자유로운 뷰 사용
- **BottomSheetBehavior** 사용하기
- **Retrofit** 사용하기
- **Glide** 사용하기
- 레이아웃 include
- mocky
    - https://designer.mocky.io/
- 공유 기능

### 에어비엔비 주요 기능

Naver Map API 를 이용해서 지도를 띄우고 활용할 수 있음.

Mock API 에서 예약가능 숙소 목록을 받아와서 지도에 표시할 수 있음.

BottomSheetView 를 활용해서 예약 가능 숙소 목록을 인터렉션하게 표시할 수 있음.

ViewPager2 를 활용해서 현재 보고있는 숙소를 표시할 수 있음.

숙소버튼을 눌러 현재 보고 있는 숙소를 앱 외부로 공유할 수 있음.

### api

- 에어비엔비 라기 보다는 임의로 숙소 목록을 구성해서 할것 (moc api 를 쓸 것임)
- 네이버 맵 api 사용
- https://console.ncloud.com/mc/solution/naverService/application?version=v2
- 회원가입 - 카드 등록 - 애플리케이션 추가.

- 프래그먼트로 사용할 수도 있고 맵뷰를 통해 사용할 수도 있음
    - 맵뷰 (뷰의 액티비티 수명주기를 그대로 넘겨주어야 하는 단점이 있음.)

### 삽질

- 네이버 api 추가 되지 않아... 확인 결과 settings.gradle 에 메이븐 경로 추가해야함...

### 레이아웃

- 프레임 레이아웃 : 단일 레이아웃 그릴때 많이 사용. 프레그먼트의 영역을 미리 잡아놓을때 사용할수도있음
    - 프레임 하나를만들어두고 뷰가 중첩이 되게
    - 권장하는건 싱글로만 배치

- 코디네이터 레이아웃
    - 바텀시트다이얼로그
    - 인터렉션이 중요한 뷰를 사용할 때 사용.

### 바텀시트다이얼로그

- 바텀시트를 코디네이트 레이아웃과 하단 비헤이비어값 주어서 사용
- 바텀시트다이얼로그처럼 나왔다 사라지게 하고 싶으면
- 바텀시트다이얼로그기능을 이용해서 뷰를 인플레이트한담에 다이얼로그 쇼 하면 비슷하게 사용가능.

### mocky

- https://run.mocky.io/v3/df8177fd-7299-407d-9c56-e2b9072ea50f
- https://run.mocky.io/v3/6c14ab02-b757-4931-b3ba-2dd9e5765073
```json
{
  "items": [
    {
      "id": 1,
      "title": "강남역!! 최저가!! 레지던스!!",
      "price": "23,000원",
      "lat": 37.498540,
      "lng": 127.028703,
      "imgUrl": "https://i.picsum.photos/id/1077/200/200.jpg?hmac=hiq7UCoz9ZFgr9HcMCpbnKhV-IMyOJqsQtVFyqmqohQ"
    },
    {
      "id": 2,
      "title": "강남역!! 위치 초 근접",
      "price": "45,000원",
      "lat": 37.498915,
      "lng": 127.027587,
      "imgUrl": "https://i.picsum.photos/id/145/200/200.jpg?hmac=lrx3pE1mf9Wpc7PEi4_5VJva3ro0RhS5cYLipT3HymI"
    },
    {
      "id": 3,
      "title": "강남역!! 역대급 가성비",
      "price": "13,000원",
      "lat": 37.497774,
      "lng": 127.026450,
      "imgUrl": "https://i.picsum.photos/id/200/200/200.jpg?hmac=mk1Tu6dXHQvpaA8RfxlDUZjbWG23krNkiB9kyYoEmO8"
    },
    {
      "id": 4,
      "title": "강남역!! 제일 저렴한곳",
      "price": "39,000원",
      "lat": 37.497996,
      "lng": 127.028596,
      "imgUrl": "https://i.picsum.photos/id/619/200/200.jpg?hmac=kS8OI8cYlvghz5FZaiYdIx96pUPQ30oF6bIsAg3AOZ4"
    },
    {
      "id": 5,
      "title": "강남역!! 유명 연예인이 묵었던 곳",
      "price": "45,000원",
      "lat": 37.497808,
      "lng": 127.028542,
      "imgUrl": "https://i.picsum.photos/id/335/200/200.jpg?hmac=CS4kiSEelfhSQQtW7j6SFUV2ZlTmUV1vaX2iZKnbx7c"
    }
  ]
}

```

## 뷰페이저2

- 프레그먼트 전환 시 많이 사용
- 프레그먼트 화면 전환 기능을 이용할 수 있다.
  - 프레그먼트 스테이트 어뎁터
- 뷰 이용 시에는 리사이클러뷰 어뎁터(리스트 어뎁터)