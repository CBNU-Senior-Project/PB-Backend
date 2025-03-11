![sample_video-VEED](https://github.com/user-attachments/assets/d2b98b39-60d7-462a-b4b2-69f9ff9e211b)


# Phishing Block Server

----

## 🌒 프로젝트 소개
Phishing Block은 사용자의 전화를 실시간으로 감지하고 AI를 활용하여 통화 내용을 분석해 보이스피싱 여부를 분석합니다. 
보이스피싱으로 의심되는 경우 사용자에게 즉시 경고를 제공하며, 등록된 그룹원들에게도 알림을 전송하여 피해를 예방할 수 있도록 돕습니다.
## Tech Stack
***Server***

<img src="https://img.shields.io/badge/java17-%23ED8B00?style=for-the-badge&logo=java17&logoColor=white"> <img src="https://img.shields.io/badge/springboot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white"> <img src="https://img.shields.io/badge/gradle-02303A?style=for-the-badge&logo=gradle&logoColor=white">
<br>

***Database***

<img src="https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white">  <img src="https://img.shields.io/badge/Redis-DC382D?style=for-the-badge&logo=Redis&logoColor=white">
<br>

***AI***

<img src="https://img.shields.io/badge/tensorflow-FF6F00?style=for-the-badge&logo=tensorflow&logoColor=white"> <img src="https://img.shields.io/badge/numpy-013243?style=for-the-badge&logo=numpy&logoColor=white"> <img src="https://img.shields.io/badge/fastapi-009688?style=for-the-badge&logo=fastapi&logoColor=white">
<br>

***Event Broker***

<img src="https://img.shields.io/badge/apache kafka-231F20?style=for-the-badge&logo=apachekafka&logoColor=white">

***Monitoring***

<img src="https://img.shields.io/badge/grafana-F46800?style=for-the-badge&logo=grafana&logoColor=white"> <img src="https://img.shields.io/badge/prometheus-E6522C?style=for-the-badge&logo=prometheus&logoColor=white">


## ⚙Architecture
![architecture](./resources/architecture.png)

## 🔑 서비스 주요 기능
### 1. 보이스피싱 여부 분석
> AI를 활용하여 실시간으로 통화 내용을 분석하고 보이스피싱 가능성을 평가합니다.
### 2. 보이스피싱 감지 시 그룹 알림
> 보이스피싱이 감지될 경우 사용자에게 즉시 경고를 제공하며, 등록된 그룹원들에게도 알림을 전송합니다.
### 3. 피싱 의심 계좌·전화번호·이메일 조회
> 사용자들은 보이스피싱이 의심되는 계좌, 전화번호, 이메일을 조회하여 사기 여부를 확인할 수 있습니다.

## 👏 팀원 소개

<table>
    <tr align="center">
        <td width="33%"><B>염중화(Android)</B></td>
        <td width="33%"><B>윤태경(Android)</B></td>
        <td width="33%"><B>신승용(Server)</B></td>
    </tr>
    <tr align="center">
        <td>
            <p><img src="https://github.com/junghwa1.png" width="70%"/></p>
        </td>
        <td>
            <p><img src="https://github.com/taegung.png" width="70%" alt=""/></p>
        </td>
        <td>
            <p><img src="https://github.com/sso9594.png" width="70%" alt=""/></p>
        </td>
    </tr>
    <tr align="center">
        <td>
            <a href="https://github.com/junghwa1"><I>junghwa1</I></a>
        </td>
        <td>
            <a href="https://github.com/taegung"><I>taegung</I></a>
        </td>
        <td>
            <a href="https://github.com/sso9594"><I>sso9594</I></a>
        </td>
    </tr>
    <tr align="center">
        <td>
            AI 모델 개발, AOS 개발
        </td>
        <td>
            포스트 서버, 커뮤니티 서버 개발
        </td>
        <td>
            Spring Cloud Gateway, 인증 서버, 
            <br>
            알림 서버, AI 판별 서버,
            <br>
            Kafka 구축, 모니터링 인프라 구축, 배포
        </td>
    </tr>
</table>
