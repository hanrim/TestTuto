# T Drive Android

# 개발 환경

- jdk 8 최신 버전 설치
- Android Studio 2.0.0 이상 최신(현재 베타로 프리뷰 진행중)
- 파일 인코딩 UTF-8 without BOM : 윈도우즈 노트패드에서 편집하시면 안됩니다

---

# Android Studio 설정

- plugin 설치
    - Preferences - Plugin 이동
    - 하단의 Browse repositories.. 선택
    - Fabric for Android Studio 과 CheckStyle-IDEA 플러그인 설치
    - Android Studio 재시작
- Code Style 설정
    - [etc/tdrive-editor-codestyle.xml](etc/tdrive-editor-codestyle.xml) 파일을 Android Studio 설정 폴더밑의 codestyles 폴더에 복사
        - Windows 의 경우 `C:\Users\`{사용자 계정 이름}`AndroidStudio`{사용중인 버전}`\config\codestyles'
        - OS X 의 경우 `~/Library/Preferences/AndroidStudio`{사용중인 버전}`/codestyles`
    - Android Studio 재시작
    - Preferences - Editor - Code 선택
    - Scheme 에서 T Drive 선택
- checkstyle 설정
    - Preferences - Other Settings - Checkstyle 선택
    - Configurations 밑의 + 버튼 선택 후
    - Descriptions 에 T Drive 입력하고 File 옆의 Browse 에서 [etc/tdrive-checkstyle.xml](etc/tdrive-checkstyle.xml) 선택 후 저장

--- 

# 파일 저장전에

- .java, .xml : Find Actions(Ctrl-Shift-A) 에서 Reformat Code 하여 코드 포매팅
- .java
    - Find Actions(Ctrl-Shift-A) 에서 Optimized import 하여 import 구문 정리
    - Find Actions(Ctrl-Shift-A) 에서 Check Current File 하여 코드 스타일 검사

---

# 기타 소스 확인 사항

- .java
    - 속성, 함수 응답, 인자에 @NonNull, @Nullable 의 추가
    - default modifier 의 경우 명시적으로 @DefaultModifier 주석 추가
- .xml 파일
    - 

# fabric

- TODO

---
