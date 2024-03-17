# Rest Docs Support

---

RestDocs Support is a library that makes it easier to write Spring Rest Docs and RestAssured.



## Getting started with Gradle

```groovy

    repositories {
        mavenCentral()
        maven { url 'https://jitpack.io' }
    }

    implementation 'com.github.seunghoona:restdocsAdapter:0.9.7:plain'

```

## Deployment environment

- JAVA 17
- Spring 3.0.x

## License

Please refer to [LICENSE](https://github.com/seunghoona/rest-docs-support/blob/main/LICENSE.txt)


## 1. How to use 'Rest docs Support'
1. [Create Document](#basedocument)
2. [Fields](#basedocumentfields)
3. [How To Request body](#how-to-request-body)
4. [How to Use Query Param](#how-to-use-query-param-)
5. [How to use pathParam](#how-to-use-pathparam-)


### BaseDocument

Below is the class declaration that should be declared and used to generate the document.
```java

void main() {
    BaseDocument.document("put your document name");    
}

```

### BaseDocumentFields

You can create response documents without using jsonPath in a type-safe manner.

| Field    | Rest docs                                               | Java Type         |
|----------|---------------------------------------------------------|-------------------|
| string() | fieldWithPath("fieldName").type(JsonFieldType.STRING)   | String            |
| number() | fieldWithPath("fieldName").type(JsonFieldType.STRING)   | Long, Integer     |
| object() | fieldWithPath("fieldName").type(JsonFieldType.OBJECT)   | Object            |
| bool()   | fieldWithPath("fieldName").type(JsonFieldType.BOOLEAN)  | true, false       |
| empty()  | fieldWithPath("fieldName").type(JsonFieldType.NULL)     | null              |
| type()   | fieldWithPath("fieldName").type(JsonFieldType.ENUM)     | This is enum type |
| list()   | fieldWithPath("fieldName").type(JsonFieldType.ARRAY)    | List, Set         |


### How To Request body

#### Request

```text
localhost:8080
```

```json
{
    "product" : {
        "id": 1, 
        "name": "data", 
        "number": 2000
    }
}
```

#### Response 
```json
{
    "product" : {
        "id": 1, 
        "name": "data", 
        "number": "data"
    }
}
```

```java
    @Test
    void createObject() {
        final var document = document("Put the document name.")
            .requestBody(
                object("product").desc("desc")
                    .with(string("name").desc("this is string name"))
                    .with(number("price").desc("this is number price"))
            )
            .response(
                object("product").desc("desc")
                    .with(number("id").desc("this is number id"))
                    .with(string("name").desc("this is string name"))
                    .with(number("price").desc("this is number price"))
            )
            .end();
    }

```


### How to Use Query Param 
#### Request

```text
 localhost:8080?email=sample@gmail.com&phone=123456789
```

#### Response
```json
{
    "email" : "sample@gmail.com",
    "phone" : "123456789"
}
```

```java

    @Test
    void createQueryParam() {
        final var document = document("Put the document name.")
            .queryParam(
                string("email").desc("this is email"),
                string("phone").desc("this is phone")
            )
            .response(
                string("email").desc("this is email"),
                string("email").desc("this is phone")
            )
            .end();
    }

```

### How to use pathParam 

#### Request

```text
 localhost:8080/1
```

#### Response
```json
{
    "email" : "sample@gmail.com",
    "phone" : "123456789"
}
```

```java
    @Test
    void createPathParam() {
        final var document = document("Put the document name.")
            .pathParam(
                string("id").desc("this is id")
            )
            .response(
                string("email").desc("this is email"),
                string("email").desc("this is phone")
            )
            .end();
    }
```
