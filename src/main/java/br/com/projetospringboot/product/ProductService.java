package br.com.projetospringboot.product;

import br.com.projetospringboot.exceptions.BusinessException;
import br.com.projetospringboot.exceptions.NotFoundException;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductService {
    private ProductRepository productRepository;
    public Product create(ProductRepresentation.CreateOrUpdate createOrUpdate){
        this.verifyByCode(createOrUpdate.getCode());

        Product newProduct = Product.builder()
                .name(createOrUpdate.getName())
                .model(createOrUpdate.getModel())
                .code(createOrUpdate.getCode())
                .price(createOrUpdate.getPrice())
                .build();

        this.productRepository.save(newProduct);

        return newProduct;
    }

    public void verifyByCode(String code){
        Optional<Product> productByCode = productRepository.findByCode(code);

        if (productByCode.isPresent())
            throw new BusinessException("Product with code " + code + " already exists!");
    }

    public Product findById(Long id){
        BooleanExpression filterById = QProduct.product.id.eq(id);

        Product product = this.productRepository.findOne(filterById)
                .orElseThrow(()-> new NotFoundException("Product with ID " + id + " not found!"));

        return product;
    }

    public Iterable<Product> findAll(){
        Iterable<Product> product = this.productRepository.findAll();

        return product;
    }

    public Product findByCode(String code){
        BooleanExpression filterByCode = QProduct.product.code.eq(code);

        Product productByCode = this.productRepository.findOne(filterByCode)
                .orElseThrow(() -> new NotFoundException("Product with CODE " + code + " not found!"));

        return productByCode;
    }

    public Product update(Long id, ProductRepresentation.CreateOrUpdate createOrUpdate){
        Product oldProduct = this.findById(id);

        if (!oldProduct.getCode().equals(createOrUpdate.getCode())){
            this.verifyByCode(createOrUpdate.getCode());
        }

        Product newProduct = oldProduct.toBuilder()
                .name(createOrUpdate.getName())
                .code(createOrUpdate.getCode())
                .model(createOrUpdate.getModel())
                .price(createOrUpdate.getPrice())
                .build();


        return this.productRepository.save(newProduct);
    }

    public void delete(Long id){
        Product product = this.findById(id);

        this.productRepository.deleteById(id);
    }
}
