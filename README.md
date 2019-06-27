# ucbcba-correccion-formato

El sistema tiene como función ayudar a los estudiantes a encontrar errores en el formato de sus documentos académicos de la UCBCBA. El formato establecido esta en el documento "Guía para la presentación de trabajos académicos"
de la UCBCBA y el sistema se basa en esta guía para detectar los errores en el formato.<br>
<br>
El formato de los trabajos académicos estan establecidos en: https://siaa.ucbcba.edu.bo/siaa2/Documentos/Modelos/GUIAUCBPTA2013.pdf
<br><br>
El sistema esta desarrollado en Spring-boot y React además se esta usando la librería "PDFBox" para la extracción y análisis de datos de un PDF.
<br>
PDFBox: https://pdfbox.apache.org/
<br><br>
El desarrollo del frontend tiene como código base: <br> https://github.com/agentcooper/react-pdf-highlighter
<br><br>
Para correr el programa backend localmente ejecutar el comando:<br> `mvn spring-boot:run` <br>
<br><br>
Para correr el programa fronted localmente ejecutar el comando en la caperta ./frontend:<br> `npm install && npm start` <br>
<br><br>
Acceder a la URL: http://localhost:3000