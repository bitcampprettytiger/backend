# 1. Project Structure
```
├─board
│  ├─controller
│  ├─dto
│  ├─entity
│  ├─repository
│  └─service
├─cart
│  ├─controller
│  ├─dto
│  ├─entity
│  ├─repository
│  │  └─Impl
│  └─service
│      └─Impl
├─common
│  ├─controller
│  ├─entity
│  ├─exception
│  ├─oauth
│  └─service
├─configuration
├─dto
├─entity
├─favoritePick
│  ├─controller
│  ├─DTO
│  ├─entity
│  ├─repository
│  └─service
│      └─impl
├─gyeongGiApi
│  ├─controller
│  ├─dto
│  ├─entity
│  ├─repository
│  └─service
│      ├─gyeonggiValidationService
│      └─impl
├─jwt
│  ├─entity
│  ├─jwtdto
│  └─repository
├─member
│  ├─controller
│  ├─dto
│  ├─entity
│  ├─model
│  ├─reposiitory
│  └─service
│      └─Impl
├─menu
│  ├─controller
│  ├─dto
│  ├─entity
│  ├─repository
│  └─service
│      └─Impl
├─NcloudAPI
│  ├─controller
│  ├─dto
│  └─service
├─order
│  ├─controller
│  ├─dto
│  ├─entity
│  ├─repository
│  └─service
│      └─impl
├─payments
│  ├─config
│  ├─controller
│  ├─dto
│  ├─entity
│  ├─repository
│  └─service
│      └─Impl
├─response
├─Review
│  ├─common
│  ├─controller
│  ├─dto
│  ├─entity
│  ├─repository
│  └─service
│      └─impl
├─seoulApi
│  ├─controller
│  ├─dto
│  ├─entity
│  ├─repository
│  └─service
│      ├─impl
│      └─userValidaionService
├─socialLogin
│  ├─controller
│  ├─dto
│  ├─repository
│  └─service
├─Util
├─vendor
│  ├─controller
│  ├─dto
│  ├─entity
│  ├─repository
│  │  └─Impl
│  └─service
│      └─Impl
└─webSoket
    ├─config
    ├─controller
    ├─dto
    ├─entity
    ├─handler
    ├─repository
    └─service
```
- cart: cart 모듈에는 cart을 담당하는 객체들이 모여있다.
   - controller : 카트와 관련된 웹 요청을 처리하는 컨트롤러 클래스를 포함합니다. 이 클래스들은 클라이언트 요청을 처리하고 비즈니스 로직을 호출합니다.
   - dto : 컨트롤러와 서비스 사이에서 데이터를 전달하는 데 사용됩니다.
   - entity : 이 클래스들은 데이터베이스 레코드를 자바 객체로 나타냅니다.
   - repository : Cart의 데이터 액세스 계층을 담당하는 모듈
        - Impl : Cart에 repository에서 구현해야될 메소드를 구현하는 담당
   - service : Cart에 비즈니스 로직을 담당하는 모듈
        - Impl : service에서 구현하고 구체화 하는 모듈
- common : 
   - controller : 공통적인 부분 을 담당하는 컨트롤러 입니다.
   - entity: 엔티티에서 공통으로 쓰는 Enum 타잎의 객체를 담당하는 모듈
   - exception: 이셉션을 상속받아서 커스텀으로 이셉션을 줘야할때 필요한 클라스들의 패키지
   - oauth: API를 붙여 인증로직 혹은 로컬 인증로직을 담당하는 모듈
   - service: 공통으로 필요한 서비스 로직을 담당합니다.
# 2. 개발 환경 구축

# 3. 테스트 전략
단위 테스트(Unit Test) < 통합 테스트(Integration Test) < 기능 테스트(Functional Test) 
<img src="./img/image.png">
###  1. 단위 테스트 : 단위 테스트는 Spring에 Junit을 이용하여 구현하고 실행 했습니다.
소스코드의 특정 모듈(프로그램 내 하나의 기능을 부르는 말)이 의도된 대로 정확히 작동하는지 검증하는 절차이며,

함수, 메서드, 개별 코드 같은 작은 단위에 대해 테스트 케이스(Test Case)로 분리하고 테스트 코드를 작성하여 테스트하는 것을 말한다.

외부 API와의 연동이 필수라든가 DB 데이터 접근 등 외부 리소스를 직접 사용해야 하는 테스트라면 단위 테스트가 아니다. 단위 테스트에서 외부와의 연동이 필요하다면 테스트 대역(Test Double)을 사용하면 된다.  
Given/When/Then 패턴
Given : 어떠한 데이터가 주어질 때.

When : 어떠한 기능을 실행하면.

Then : 어떠한 결과를 기대한다.


#### 의존성 주입
<img src="./img/004.JPG">

#### 컨트롤러 계층 단위 테스트 
<img src="./img/002.JPG">

#### 서비스 계층 단위 테스트

<img src="./img/003.JPG" > 

#### 레포지토리 계층 단위 테스트

### 2. 통합 테스트 
단위 테스트로는 RequestMapping, Data Binding, Type Conversion, Validation, 등등을 커버할 수 없습니다. 따라서 코드 커버리지를 높이기 위해서는 통합테스트를 실시해야합니다.

장점
- 모든 빈을 컨테이너에 올리고 테스트 하기 때문에 운영환경과 유사한 환경에서 테스트를 할 수 있습니다.

- 통합테스트 이름 그대로, 전체적인 테스트를 진행할 수 있어, 코드 커버리지가 높아집니다.

단점
- 모든 빈을 컨테이너에 올리고 테스트 하기 때문에 시간이 오래걸립니다.

- 전체적인 테스트를 한번에 진행하기 때문에, 특정 계층 또는 특정 빈에서 발생하는 오류의 디버깅이 어렵습니다.
### PostMan 통합 테스트 
<img src="./img/005.JPG" > 

# 4. 애플리케이션 배포

# 5. API 엔드 포인트 목록 및 사용법
 ### [API 명세서](https://app.swaggerhub.com/apis/BONG94688_1/mukjachiv1/v1)
# 6. 참고 자료
### [상세 정보 ](https://elastic-vanilla-3d4.notion.site/449ab326c7ac4a6d85e711f742534c7a?pvs=4)

## THANK YOU
