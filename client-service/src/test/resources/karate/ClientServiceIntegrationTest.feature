Feature: Client Service Integration Test

  Background:
    * url 'http://localhost:8080/client-service/api/clients'

  Scenario: Obtener todos los clientes
    Given path '/'
    When method get
    Then status 200
    And match response.success == true
    And match response.message == 'Clients retrieved successfully'
    And match response.data == '#[]'

  Scenario: Crear un nuevo cliente
    Given path '/'
    And request { "name": "Juan Perez", "identification": "9876543210" }
    When method post
    Then status 201
    And match response.success == true
    And match response.message == 'Client created successfully'
    And match response.data.name == 'Juan Perez'
    And match response.data.identification == '9876543210'
