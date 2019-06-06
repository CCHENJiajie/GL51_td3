package gl51.project.store

import spock.lang.Specification

class MemoryProductStorageTest extends Specification {

    ProductStorage store = new MemoryProductStorage()
    Product p = new Product(id: "1", name: "myproduct")

    def "empty storage returns empty list"() {
         expect:
         store.all() ==  []
    }

    def "adding a product returns the product in the list"() {
        setup:
        store.save(new Product(name: "myproduct"))

        when:
        def all = store.all()

        then:
        all.size() == 1
        all.first().name == 'myproduct'
    }

    def "adding a product will generate a new id"() {
        setup:
        store.save(new Product(name: "myproduct"))

        when:
        def all = store.all()

        then:
        all.first().id != null
    }

    def "deleting a product will remove it from the list"() {
        setup:
        store.save(p)

        when:
        store.delete("1")
        def all= store.all()

        then:
        all.contains(p) == false
    }

    def "modifying a product will change it in the list"() {
        store.save(p)
        Product newProduct = new Product(
                id: 1,
                name: "myproduct updated",
                description: "new product",
                price: 2.50,
                idealTemperature: 10)
        setup:
        store.update("1", newProduct)

        when:
        def proInStore = store.getByID("1")

        then:
        proInStore == newProduct

    }

    def "getting a product by its id will throw a NotExistingProductException if it does not exits"() {
        setup:
        store.save(p)
        when:
        def all = store.all()
        store.getByID("2")
        then:
        all.size() == 1
        thrown NotExistingProductException
    }

    def "getting a product by its id will return it if it does exist"() {
        store.save(p)
        expect:
        store.getByID("1").name=='myproduct'
    }

}
