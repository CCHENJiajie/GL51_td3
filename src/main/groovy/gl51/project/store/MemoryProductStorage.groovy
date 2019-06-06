package gl51.project.store

class MemoryProductStorage implements  ProductStorage {

	List<Product> productList = []
	
    @Override
    String save(Product p) {
		p.id = UUID.randomUUID().toString()
        productList.add(p)
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
        def product = productList.find { it.id == id }
        if(product == null)
        {
          throw new NotExistingProductException("The wanted product is not exist!")
        }
        return product
    }

    @Override
    void delete(String id) {
        def product = getByID(id)
        productList.remove(product)
    }

    @Override
    List<Product> all() {
        return productList
    }
}
