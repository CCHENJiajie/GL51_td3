package gl51.project.store

import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Delete
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Patch
import io.micronaut.http.annotation.Post
import store.exceptions.NotExistingProductException


import io.micronaut.http.HttpStatus


@Controller("/store/product")
class ProductController {

    MemoryProductStorage storage = new MemoryProductStorage()

    @Get("/")
    List<Product> index() {
        storage.all()
    }

    @Get("/{id}")
    Product get(String id) {
        try {
            storage.getByID(id)
        }
        catch(NotExistingProductException e) {
            HttpStatus.NOT_FOUND
        }
    }

    @Post("/")
    String create(@Body Product p) {
        storage.save(p)
    }

    @Patch("/{id}")
    HttpStatus update(String id, @Body Product p) {
        try {
            storage.update(id, p)
            HttpStatus.OK
        }
        catch(NotExistingProductException e){
            HttpStatus.NOT_FOUND
        }
    }

    @Delete("/{id}")
    String delete(String id) {
        try {
            storage.delete(id)
            HttpStatus.OK
        }
        catch(NotExistingProductException e){
            HttpStatus.NOT_FOUND
        }
    }
}