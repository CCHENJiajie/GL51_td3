package gl51.project.store

import io.micronaut.runtime.server.EmbeddedServer
import io.micronaut.http.client.RxHttpClient
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.context.ApplicationContext
import io.micronaut.core.type.Argument
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import spock.lang.AutoCleanup
import spock.lang.Shared
import spock.lang.Specification


class ProductControllerTest extends Specification {

    @Shared @AutoCleanup EmbeddedServer embeddedServer = ApplicationContext.run(EmbeddedServer)
    @Shared @AutoCleanup RxHttpClient client = embeddedServer.applicationContext.createBean(RxHttpClient, embeddedServer.getURL())


    void "test index"() {
        given:
        List<Product> products = client.toBlocking().retrieve(HttpRequest.GET('/store/product'), Argument.listOf(Product).type)

        expect:
        products == []
    }

    void "test create "() {
        setup:
        Product sentProduct = new Product(name: "myProduct1", description: "product1", price: 1, idealTemperature: 1)

        when:
        String id = client.toBlocking().retrieve(HttpRequest.POST('/store/product', sentProduct))
        Product getProduct = client.toBlocking().retrieve(HttpRequest.GET('/store/product/'+id), Argument.of(Product).type)

        then:
        id != ""
        getProduct != null
        getProduct.equals(sentProduct)
    }

    void "test update"() {
        setup:
        String id = client.toBlocking().retrieve(HttpRequest.POST('/store/product', new Product(name: "myProduct1", description: "product1", price: 1, idealTemperature: 1)))

        when:
        Product p = new Product(name: "myProduct2", description: "product2", price: 2, idealTemperature: 2)
        HttpStatus status = client.toBlocking().retrieve(HttpRequest.PATCH('/store/product/'+id, p), Argument.of(HttpStatus).type)
        Product afterProduct = client.toBlocking().retrieve(HttpRequest.GET('/store/product/'+id), Argument.of(Product).type)

        then:
        status.equals(HttpStatus.OK)
        p.equals(afterProduct)
    }

    void "test delete"() {
        setup:
        String id = client.toBlocking().retrieve(HttpRequest.POST('/store/product', new Product(name: "myProduct1", description: "product1", price: 1, idealTemperature: 1)))

        when:
        HttpStatus status = client.toBlocking().retrieve(HttpRequest.DELETE('/store/product/'+id), Argument.of(HttpStatus).type)
        Product p = client.toBlocking().retrieve(HttpRequest.GET('/store/product/'+id), Argument.of(Product).type)

        then:
        status.equals(HttpStatus.OK)
        thrown HttpClientResponseException
        p == null
    }
}
