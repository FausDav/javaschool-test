# URL Shortener - Java Challenge

## Descripción
Se desarrolla una API para el acortado de URLs, así como la recuperación de las mismas a través del alias asignado.
Los alias son válidos mientras el servidor no sea reiniciado, ya que la relación (alias:url) se guarda en una HashTable al momento de la ejecución.

Existen 3 casos para la codificación del alias:
- **URLs que contienen la palabra _google_**
  - Se crea una cadena de caracteres alfabéticos, haciendo distinción entre mayúsculas y minúsculas.
  - Tiene longitud 5.
- **URLs que contienen la palabra _yahoo_**:
  - Se crea una cadena de caracteres alfanuméricos, haciendo distinción entre mayúsculas y minúsculas.
  - Tiene longitud 7.
- **URLs que no coinciden con los casos anteriores**: 
  - Se crea una cadena a partir de la URL dada removiendo los caracteres especiales, números y vocales.

En todos los casos la url se guarda en minúsculas para evitar duplicados por direfencias de mayúsculas y minúsculas.

## Modo de uso

### Método POST
**Endpoint:** (\/)

**Payload**: Json con la estructura _{"url":"\<url\>"}_

**Respuestas**:
- Estatus HTTP: 201; Alias creado correctamente, se devuelve una respuesta de la forma _{"alias": "\<alias\>"}_.
- Estatus HTTP: 400; La URL proporcionada no es válida, se devuelve _{"message": "URL inválida"}_
- Estatus HTTP: 409; LA URL proporcionada ya está registrada, se devuelve _{"message": "La URL ya está registrada"}_

### Método GET
**Endpoint:** (\/\<alias\>)

**Respuestas**:
- Estatus HTTP: 200; Se devuelve la URL que corresponde al alias. Por ejemplo: _https://nearsoft.com_ para el alias _httpsnrsftcm_
- Estatus HTTP: 400; Alias con caracteres inválidos, se devuelve: _Alias no válido_
- Estatus HTTP: 404; No se encuentra el alias, se devuelve: _Alias no registrado_

## Diseño
Se tienen principalmente 3 clases:
- **URL**: Clase usada para recibir el @BodyRequest configurado con un POJO.
- **UrlShortenerService**: Clase que se encarga de los procesos de generar y devolver los alias y su URL ligada.
- **UrlShortenerController**: Clase que se encarga del manejo de las peticiones así como de la validación de las entradas.

Con esta división se tiene claro donde es que hay que realizar modificaciones, por ejemplo, si se quisieran agregar casos de URL se debe modificar la clase _UrlShortenerService_, o si se desean más validaciones a las entradas, la edición se debe realizar en _UrlShortenerController_.

## Ejemplos

1. **Petición**: GET {host}/UrlShortener/abc12

   - **Respuesta**: Alias no registrado

2. **Petición**: POST {host}/UrlShortener/

   - **Cuerpo**: {"url": "www.google.maps.com"}

   - **Respuesta**: {"alias": "gHfeV"}

3. **Petición**: POST {host}/UrlShortener/

   - **Cuerpo**: {"url": "https://www.google.com/maps/place/Castillo+de+Chapultepec/@19.4204447,-99.1841237,17z/data=!4m5!3m4!1s0x85d1fecd47ed8f23:0xa6e0008524818b32!8m2!3d19.4204399!4d-99.181935"}

   - **Respuesta**: {"alias": "AoDhq"}

4. **Petición**: POST {host}/UrlShortener/

   - **Cuerpo**: {"url": "https://yahoo.com.mx/"}

   - **Respuesta**: {"alias": "Fn3hNf1"}

5. **Petición**: POST {host}/UrlShortener/

   - **Cuerpo**: {"url": "https://nearsoft.com"}

   - **Respuesta**: {"alias": "httpsnrsftcm"}

6. **Petición**: POST {host}/UrlShortener/

   - **Cuerpo**: {"url": "https://www.gamedev.tv/test"}

   - **Respuesta**: {"alias": "httpswwwgmdvtvtst"}

7. **Petición**: POST {host}/UrlShortener/

   - **Cuerpo**: {"url": "aaa.aaaa.aa"}

   - **Respuesta**: {"message": "La URL no puede ser procesada, se vuelve vacía con el algoritmo utilizado"}

8. **Petición**: POST {host}/UrlShortener/

   - **Cuerpo**: {"url": "https://tw itter.com/home"}

   - **Respuesta**: {"message": "URL inválida"}

9. **Petición**: GET {host}/UrlShortener/httpsnrsftcm

   - **Respuesta**: https://nearsoft.com
