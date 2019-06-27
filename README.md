# ucbcba-correccion-formato

El sistema tiene como función ayudar a los estudiantes a detectar sus errores de formato en sus trabajos académicos de la UCB. Estos trabajos académicos deben cumplir
el formato de la universidad establecido en el documento “Guía para la presentación de trabajos académicos”.<br>
<br>
Guía para la presentación de trabajos académicos: https://siaa.ucbcba.edu.bo/siaa2/Documentos/Modelos/GUIAUCBPTA2013.pdf
<br><br>
El sistema está desarrollado en Spring-boot y React además se está usando la librería "PDFBox" para la extracción y análisis de datos de un PDF.
<br>
PDFBox: https://pdfbox.apache.org/
<br><br>
El desarrollo del frontend tiene como código base: <br> https://github.com/agentcooper/react-pdf-highlighter
<br><br>
Para correr el programa backend localmente ejecutar el comando:<br> `mvn spring-boot:run` <br>
<br><br>
Para correr el programa fronted localmente ejecutar el comando en la caperta ./frontend:<br> `npm install && npm start` <br>
Acceder a la URL: http://localhost:3000
<br>
Para más información, consultar la documentación del software en el que se especifica la arquitectura y diagramas de clases del sistema. Asimismo, el documento contiene las configuraciones para la puesta en producción del sistema.
Solicitar el documento en la Universidad.
