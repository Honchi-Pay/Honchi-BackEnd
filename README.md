# Honchi-BackEnd
HonchiPay는 혼자 배달이나 주문시킬 때 받는 혜택을 위한 과정에서 과소비를 방지하기 위해 만들어진 앱입니다.  
> 배달을 시킬 때 최소주문금액을 맞춰야하기 때문에 과소비와 음식이 남게 되는 상황이 발생할 수 있습니다.
또한 물건을 구매할 시 혜택을 받기 위해 필요 이상으로 물건을 구매할 수도 있습니다.
이처럼 혼치페이는 앞선 문제들을 해결하기 위해 개발되었습니다.

개발 기간: 2020.09.25. ~ 2020.12.06.

## Technology used
### Language
- Java 8

### Library & Framework
- SpringBoot 2.3.3
- security

### Database
- MySQL 8.x
- redis
- RDS
- S3

### Depoly
- Github Action
- docker
- AWS

## Folder Structure
```
📦 Honchi-BackEnd  
├─ .github  
│  └─ workflows  
│     └─CI-CD.yml
├─ .gitignore  
├─ Dockerfile  
├─ README.md  
├─ build.gradle  
├─ gradle  
│  └─ wrapper  
│     ├─ gradle-wrapper.jar  
│     └─ gradle-wrapper.properties  
├─ gradlew  
├─ gradlew.bat  
├─ settings.gradle  
└─ src  
   └─ main  
      ├─ java  
      │  └─ honchi  
      │     └─ api  
      │        ├─ ApiApplication.java
      │        ├─ domain 
      │        │  ├─ auth
      │        │  │  ├─ controller
      │        │  │  ├─ dto
      │        │  │  ├─ exception
      │        │  │  └─ service
      │        │  ├─ buyList
      │        │  │  ├─ controller
      │        │  │  ├─ domain
      │        │  │  │  └─ repository
      │        │  │  ├─ dto
      │        │  │  ├─ exception
      │        │  │  └─ service
      │        │  ├─ chat
      │        │  │  ├─ controller
      │        │  │  ├─ domain
      │        │  │  │  ├─ enums
      │        │  │  │  └─ repository
      │        │  │  ├─ dto
      │        │  │  ├─ exception
      │        │  │  └─ service
      │        │  ├─ image  
      │        │  │  ├─ controller
      │        │  │  └─ service
      │        │  ├─ message  
      │        │  │  ├─ controller
      │        │  │  ├─ domain
      │        │  │  │  ├─ enums
      │        │  │  │  └─ repository
      │        │  │  ├─ dto
      │        │  │  ├─ exception  
      │        │  │  └─ service
      │        │  ├─ post  
      │        │  │  ├─ controller
      │        │  │  ├─ domain
      │        │  │  │  ├─ enums
      │        │  │  │  ├─ kakaoMap
      │        │  │  │  └─ repository
      │        │  │  ├─ dto
      │        │  │  ├─ exception
      │        │  │  └─ service
      │        │  └─ user
      │        │     ├─ controller
      │        │     ├─ domain
      │        │     │  ├─ enums
      │        │     │  └─ repository
      │        │     ├─ dto
      │        │     ├─ exception
      │        │     └─ service
      │        └─ global
      │           ├─ config
      │           │  └─ security
      │           ├─ error
      │           │  └─ exception
      │           └─ s3
      └─ resources

```

## 회고
Honchi-Pay는 제가 진행했던 프로젝트 중 큰 규모의 프로젝트입니다.  
개발할 때는 정신이 없어서 코드가 돌아가는 것에 대해서만 집중적으로 개발을 진행하였습니다.  
개발하는 것에 의의를 두어 진행하다보니 코드의 질이 떨어지는 편입니다.  
프로젝트를 진행하면서 여러가지를 써볼 수 있는 기회가 있어서 좋았지만, 반대로 아쉬운 점도 많았던 프로젝트였습니다.  