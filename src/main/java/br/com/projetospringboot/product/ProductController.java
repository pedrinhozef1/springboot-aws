package br.com.projetospringboot.product;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/v1/product")
public class ProductController {
    private static final Logger LOG = LoggerFactory.getLogger(ProductController.class);
    private final ProductService productService;

    @PostMapping("/create")
    public ResponseEntity<ProductRepresentation.Details> create(
            @RequestBody @Valid ProductRepresentation.CreateOrUpdate createOrUpdate){

        LOG.info("createOrUpadte {} {} {} {} ", createOrUpdate.getCode(), createOrUpdate.getName(), createOrUpdate.getModel(), createOrUpdate.getPrice());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ProductRepresentation.Details
                        .from(productService.create(createOrUpdate)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductRepresentation.Details> getById(@PathVariable("id") Long id){
        return ResponseEntity
                .ok(ProductRepresentation.Details.from(productService.findById(id)));
    }

    @GetMapping("/")
    public Iterable<Product> getAll(){
        return this.productService.findAll();
    }

    @GetMapping("/code/{code}")
    public ResponseEntity<ProductRepresentation.Details> getByCode(@PathVariable("code") String code){
        return ResponseEntity
                .ok(ProductRepresentation.Details
                        .from(productService.findByCode(code)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductRepresentation.Details> update(@PathVariable("id") Long id,
        @RequestBody @Valid ProductRepresentation.CreateOrUpdate createOrUpdate){

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ProductRepresentation.Details.from(productService.update(id, createOrUpdate)));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id){
        this.productService.delete(id);
    }
}
