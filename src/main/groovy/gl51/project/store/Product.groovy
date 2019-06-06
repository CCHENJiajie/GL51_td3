package gl51.project.store
import groovy.transform.EqualsAndHashCode

@EqualsAndHashCode()
class Product {
    String id
    String name
    String description
    double price
    double idealTemperature
}
