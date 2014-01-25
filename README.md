ckeditor-upload-filter-java
===========================

CKEditor image upload module for Java.

이미지를 "web application" 의 deploy 디렉토리가 아닌 그 외의 곳에 두고 다른 서버가 처리하길 원할때, upload 디렉토리와 이미지 처리 주소를 지정하여 사용 할 수 있습니다.

#USAGE

####1. web.xml 에 다음 내용을 추가합니다.

`WEB-INF/web.xml`
```xml
    <filter>
        <filter-name>CKFilter</filter-name>
        <filter-class>com.labsody.lab.ckeditor.CkFilter</filter-class>
        <init-param>
            <param-name>properties</param-name>
            <param-value>ck.properties</param-value>
        </init-param>
    </filter>
    
    <filter-mapping>
        <filter-name>CKFilter</filter-name>
        <url-pattern>/ckupload</url-pattern>
    </filter-mapping>
```
filter-mapping 은 다른 filter-mapping 들 보다 가장 위에 위치하도록 합니다.

####2. config.js 에 다음 내용을 추가합니다.

* TYPE 1

> ckeditor 설치 디렉토리 밑에 config.js 파일을 열고 다음 내용을 추가합니다.
> 
> `ckeditor/config.js`
> ```javascript
> config.filebrowserUploadUrl = '/ckupload';
> ```
> 당신의 web application 의 'context root' 를 고려해서 추가하시기 바랍니다.

* TYPE 2

> 만약 CKEditor for jQuery 를 사용한다면 'context root' 를 고려하여 다음과 같이 해도 됩니다.
> 
> `yourConfig.jsp`
> ```jsp
> <c:set var="ctx" value="${pageContext.request.contextPath}"/>
> 
> <script type="text/javascript">
> 	$(document).ready(function(){
>     $('.jquery_ckeditor')
>     .ckeditor( function() { /* callback code */ }, { filebrowserUploadUrl:'${ctx}/ckupload' } );
> 	});
> </script>
> ```

####3. ck.properties 파일을 만들고 다음 내용을 추가합니다.

`WEB-INF/classes/ck.properties`
```properties
# CKEditor for java - sample

#optional
ck.image.type.allow=jpg,jpeg,gif,bmp,png
#optional
#ck.image.save.class=Implementation of com.labsody.lab.ckeditor.FileSaveManager 

# if you use "separate image server" or other case. (apache, cdn, nas)
ck.image.dir=/www/images.mydomain.com/upload
ck.image.url=http://images.mydomain.com/upload/

# if you use "web application"'s deployed directory.
#ck.image.dir=/www/www.mydomain.com/upload
#ck.image.url=/upload/
```


