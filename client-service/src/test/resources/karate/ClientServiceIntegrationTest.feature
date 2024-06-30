Feature: Client Service Integration Test

  Background:
    * url 'http://localhost:8080/client-service/api/clients'

  Scenario: Obtener todos los clientes
    Given path '/'
    When method get
    Then status 200
    And match response.success == true
    And match response.message == 'Clients retrieved successfully'
    And match response.data == '#[4]'
    And match each response.data contains { personId: '#number', name: '#string', gender: '#string', age: '#number', identification: '#string', address: '#string', phone: '#string', clientId: '#number', password: '#string', status: '#boolean' }

