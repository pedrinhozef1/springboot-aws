package br.com.projetospringboot.product;

import com.querydsl.core.types.Predicate;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends CrudRepository<Product, Long>,
        QuerydslPredicateExecutor<Product> {

    List<Product> findByCode(Predicate predicate);

    Optional<Product> findByCode(String code);

}
