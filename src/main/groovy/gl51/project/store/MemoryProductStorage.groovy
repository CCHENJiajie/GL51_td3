package gl51.project.store

class MemoryProductStorage implements  ProductStorage {

	List<Product> productList = []
	int id = 1
	
    @Override
    int save(Product p) {
		p.id = id
        productList.add(p)
		id += 1
    	return p.id
    }

    @Override
    void update(int id, Product p) {
        Product product = this.getByID(id)
        int indexOfProduct = productList.indexOf(product)

         productList.add(indexOfProduct,p)
    }

    @Override
    Product getByID(int id) {
        def product = productList.find { it.id == id }
        if(product == null)
        {
          throw new NotExistingProductException("The wanted product is not exist!")
        }
        return product
    }

    @Override
    void delete(int id) {
        def product = getByID(id)
        productList.remove(product)
    }

    @Override
    List<Product> all() {
        return productList
    }
}
