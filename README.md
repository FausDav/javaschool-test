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
