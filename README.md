# Sylph


[![Build Status](https://travis-ci.org/monbanquet/Sylph.svg?branch=master)](https://travis-ci.org/monbanquet/Sylph)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/fr.monbanquet/sylph/badge.svg)](https://search.maven.org/artifact/fr.monbanquet/sylph/)
[![Javadocs](http://www.javadoc.io/badge/fr.monbanquet/sylph.svg)](http://www.javadoc.io/doc/fr.monbanquet/sylph)

Sylph is a lightweight HTTP library based on HTTP Client in JDK 11+.  
Sylph add new features, remove repetitive codes and keep same method signature of original client in JDK.

## JDK native features
- Builder pattern
- Immutable objects
- HTTP/1.1 or HTTP/2 
- Synchronous or Asynchronous
- The request and response bodies are exposed as reactive streams


## Sylph features
- New builders 
- Default JSON converter
- Custom response processing
- Custom request and response logs
- An URL Builder
- All Exception are convert to RuntimeException 

## Sylph default configuration override
- 30 second connection timeout (jdk default no connection timeout)
- set default `Content-Type` header to `application/json` (jdk default header is empty)

## Getting start

### A simple request

```java
Todo todoResult = Sylph.newClient()
        .GET("http://jsonplaceholder.typicode.com/todos/1")
        .body(Todo.class);
```

### How to use URI Builder 

```java
URI uri = SylphUri.newUri()
        .scheme("https")
        .host("jsonplaceholder.typicode.com")
        .port(443)
        .path("/posts")
        .queryParams("userId", 1)
        .queryParams("postId", 2)
        .toUri();
``` 

## Architecture

Each Sylph class is a subclass or implements a JDK HTTP Client 

| JDK                  | Sylph                    |
| -------------------- | ------------------------ |
| HttpRequest          | SylphHttpRequest         |
| HttpRequest.Builder  | SylphHttpRequestBuilder  |
| HttpClient           | SylphHttpClient          |
| HttpClient.Builder   | SylphHttpClientBuilder   |
| HttpClient           | SylphHttpClient          |
| HttpResponse<T>      | SylphHttpResponse<T>     |

The Sylph class is a builder and is used to build SylphHttpClient.

SylphHttpClient add some nice features :

- SylphHttpClient POST(String uri)
- SylphHttpClient POST(URI uri)
- SylphHttpClient POST(String uri, T body)
- SylphHttpClient POST(URI uri, T body)
- SylphHttpClient POST(T body)  

Also for PUT, GET and DELETE

There are some useful shortcuts :
- SylphHttpResponse<T> send(Class<T> returnType)
- SylphHttpResponse<Void> send()
- CompletableFuture<SylphHttpResponse<T>> sendAsync(Class<T> returnType)
- CompletableFuture<SylphHttpResponse<Void>> sendAsync() 

And some shortcuts more powerful :
- T body(Class<T> returnType)
- List<T> bodyList(Class<T> returnType)
- CompletableFuture<T> bodyAsync(Class<T> returnType)
- CompletableFuture<List<T>> bodyListAsync(Class<T> returnType)
- CompletableFuture<Void> bodyAsync()

## Configuration

Sylph is easily configurable by using Sylph builder class.

```java
SylphHttpClient http = Sylph.builder()
        .setBaseRequest(SylphHttpRequest.newBuilder())
        .setClient(SylphHttpClient.newBuilder())
        .setParser(new JacksonParser())
        .setRequestLogger(DefaultRequestLogger.create(SylphLogger.INFO))
        .setResponseLogger(new DefaultResponseLogger())
        .setResponseProcessor(new DefaultResponseProcessor())
        .getClient();
```

### Base request

SylphHttpClient contains a SylphHttpRequest with initial request parameters.
For example, you can set a base request with an url, a header and timeout and it will be always use for next requests. 

```java
SylphHttpClient http = Sylph.builder()
        .setBaseRequest(SylphHttpRequest.newBuilder("https://google.fr")
                .headers("X-My-Header", "Sylph")
                .timeout(Duration.ofSeconds(90)))
        .getClient();
```

### Http client 

You can also configure SylphHttpClient. All methods from JDK client are available.

```java
SylphHttpClient http = Sylph.builder()
        .setClient(SylphHttpClient.newBuilder()
                .authenticator(new Authenticator() { })
                .proxy(new ProxySelector() { })
        .getClient();
```

### JSON Parser

By default, Sylph use Jackson as JSON Parser. 
You can use your own by implementing Parser interface.  

```java
SylphHttpClient http = Sylph.builder()
        .setParser(new Parser() {
            @Override
            public <T> String serialize(T input) {
                // To complete
            }
            @Override
            public <T> T deserialize(String input, Class<T> returnType) {
                // To complete
            }
            @Override
            public <T> List<T> deserializeList(String input, Class<T> returnType) {
                // To complete
            }
        })
        .getClient();
```

### Request and response logger

Sylph add a flexible log system. Default implementation is active when log level is debug. 
You can change log logic by using Sylph builder.

```java
SylphHttpClient http = Sylph.builder()
        .setRequestLogger(new RequestLogger() {
            @Override
            public HttpRequest log(HttpRequest request) {
                // To complete
            }
        })
        .setResponseLogger(new ResponseLogger() {
            @Override
            public <T> HttpResponse<T> log(HttpResponse<T> response) {
                // To complete
            }
        })
        .getClient();
```

### Response processor

Sylph add also a response processor for doing actions on response.
By default Sylph check if response status code is equal or greater than 300 and throw an exception.
You can also override this behavior.

```java
SylphHttpClient http = Sylph.builder()
        .setResponseProcessor(new ResponseProcessor() {
            @Override
            public <T> HttpResponse<T> processResponse(HttpResponse<T> response) throws SylphHttpResponseException {
                // To complete
            }
        })
        .getClient();
```