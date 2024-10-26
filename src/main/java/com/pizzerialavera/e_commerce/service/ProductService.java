package com.pizzerialavera.e_commerce.service;

import com.pizzerialavera.e_commerce.entity.Ingredient;
import com.pizzerialavera.e_commerce.entity.Product;
import com.pizzerialavera.e_commerce.exception.BadRequestException;
import com.pizzerialavera.e_commerce.repository.ProductRepository;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager; // Importa LogManager
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private IngredientService ingredientService;

    // Usa LogManager para obtener el logger
    private static final Logger logger = LogManager.getLogger(ProductService.class);

    // Método para guardar un producto
    public Product saveProduct(Product product) {
        // Verificar si ya existe un producto con el mismo nombre y tamaño
        Optional<Product> existingProduct = productRepository.findByNameAndSize(product.getName(), product.getSize());
        if (existingProduct.isPresent()) {
            logger.warn("Ya existe un producto con el nombre '{}' y tamaño '{}'. No se guardará nuevamente.", product.getName(), product.getSize());
            return existingProduct.get(); // Retorna el producto existente si se desea
        }

        // Verifica si los ingredientes existen y los agrega o los crea
        Set<Ingredient> ingredients = new HashSet<>();
        for (Ingredient ingredient : product.getIngredient()) {
            Optional<Ingredient> existingIngredient = ingredientService.findByString(ingredient.getName());
            if (existingIngredient.isPresent()) {
                ingredients.add(existingIngredient.get());
            } else {
                // Si no existe, se crea un nuevo ingrediente
                Ingredient newIngredient = ingredientService.createIngredient(ingredient);
                ingredients.add(newIngredient);
            }
        }
        product.setIngredients(ingredients);
        return productRepository.save(product);
    }

    // Método para obtener todos los productos
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    // Método para encontrar un producto por su ID
    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    // Método para eliminar un producto por su ID
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    // Método para actualizar un producto
    public void updateProduct(Product product) {
        productRepository.save(product);
    }

    // Método para buscar un producto por un string
    public Optional<Product> findByString(String stringy) {
        return productRepository.findByName(stringy);
    }
}
