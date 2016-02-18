# TwitterPeople, Grails proxy de usuarios de Twitter

TwitterPeople es un simple ejemplo de cómo usar pruebas unitarias de servicios
REST y usarlas para autodocumentarlas.

El único recurso disponible en TwitterPeople es un Person que contiene
un subconjunto de atributos de un usuario de Twitter. Al inicio de la aplicación
únicamente existe un usuario de pruebas para demostrar su funcionalidad. Según se
le solicitan nuevos identificadores la aplicación buscará si ya existe el recurso
en la base de datos y si no existe acudirá a Twitter a recuperarlo. Si a su vez, existe en
Twitter creará un Person en base a los atributos que recupera y a partir de entonces
ya estará disponbile para futuras peticiones.
