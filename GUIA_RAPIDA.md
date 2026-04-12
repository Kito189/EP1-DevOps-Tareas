--------------------------------------------------
-- GUIA RAPIDA DE EJECUCION (CQRS + DOCKER) --
--------------------------------------------------

1. LEVANTAR EL SISTEMA (POSTGRES + SERVICES)
   docker-compose up -d --build

2. CREAR UN TICKET (ESCRITURA - PUERTO 8081)
   curl.exe -X POST http://localhost:8081/api/tickets `
        -H "Content-Type: application/json" `
        -d '{"descripcion": "Falla en motor 3", "consentimiento": true}'

   * NOTA: Copia el "id" recibido en el JSON de respuesta.

3. CONSULTAR EL TICKET (LECTURA - PUERTO 8082)
   curl.exe http://localhost:8082/api/tickets/PON_AQUI_EL_ID

4. DETENER TODO
   docker-compose down
--------------------------------------------------
