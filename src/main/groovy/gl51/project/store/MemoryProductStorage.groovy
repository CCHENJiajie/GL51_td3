package gl51.project.store

class MemoryProductStorage implements  ProductStorage {

    //HashMap<String, String> productHashMap = new HashMap<>();
    //TODO: HashMap  1.<id, Product> ? 2.<Product.key, Product.value>?
    List<Product> products = []

    @Override
    String save(Product p) {
        if(!p.id)
            p.id = UUID.randomUUID().toString()
        this.products.add(p)
        return p.id
    }

    @Override
    void update(String id, Product p) {
        for(int i=0;i<products.size();i++)
        {
            if(products[i].id==id)
            {
                p.id = id
                products[i] = p
            }
        }
    }

    @Override
    Product getByID(String id) {
        for(int i=0;i<products.size();i++) {
            if (products[i].id == id) {
                return products[i]
            }
        }
        throw new NotExistingProductException(" product does not exist !")
    }

    @Override
    void delete(String id) {
        products.remove(this.getByID(id))
        }

    @Override
    List<Product> all() {
        return products
    }
}
