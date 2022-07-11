package net.lewes.shoppinglist.repositories;

import net.lewes.shoppinglist.domain.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Long > {
}
