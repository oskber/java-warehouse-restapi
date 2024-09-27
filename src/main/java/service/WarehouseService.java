package service;

import entities.Category;
import entities.Product;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Stream;

@ApplicationScoped
public class WarehouseService {
    private final Lock lock = new ReentrantLock();
    private Warehouse warehouse;

    public WarehouseService() {
    }

    @Inject
    public WarehouseService(Warehouse warehouse) {
        this.warehouse = warehouse;
        addDefaultProducts();
    }

    private void addDefaultProducts() {
        addProduct(new Product("1", "Product1", Category.ELECTRONICS, 5, LocalDate.now(), LocalDate.now()));
        addProduct(new Product("2", "Product2", Category.OTHER, 4, LocalDate.now(), LocalDate.now()));
        addProduct(new Product("3", "Product3", Category.CLOTHING, 3, LocalDate.now(), LocalDate.now()));
    }

    public void addProduct(Product product) {
        lock.lock();
        try {
            warehouse.addProduct(product);
        } finally {
            lock.unlock();
        }
    }

    public void modifyProduct(String id, String name, Category category, int rating) {
        lock.lock();
        try {
            warehouse.modifyProduct(id, name, category, rating);
        } finally {
            lock.unlock();
        }
    }

    public List<Product> getAllProducts() {
        lock.lock();
        try {
            return warehouse.getAllProducts();
        } finally {
            lock.unlock();
        }
    }

    public Optional<Product> getProductById(String id) {
        lock.lock();
        try {
            return warehouse.getProductById(id);
        } finally {
            lock.unlock();
        }
    }

    public List<Product> getProductsByCategory(Category category) {
        lock.lock();
        try {
            return warehouse.getProductsByCategory(category);
        } finally {
            lock.unlock();
        }
    }

    public List<Product> getProductsCreatedAfter(LocalDate date) {
        lock.lock();
        try {
            return warehouse.getProductsCreatedAfter(date);
        } finally {
            lock.unlock();
        }
    }

    public List<Product> getProductsByModified() {
        lock.lock();
        try {
            return warehouse.getProductsByModified();
        } finally {
            lock.unlock();
        }
    }

    public List<Category> getCategoriesWithProducts() {
        lock.lock();
        try {
            return warehouse.getCategoriesWithProducts();
        } finally {
            lock.unlock();
        }
    }

    public Map<Category, Long> getNumberOfProductsByCategory() {
        lock.lock();
        try {
            return warehouse.getNumberOfProductsByCategory();
        } finally {
            lock.unlock();
        }
    }

    public Map<Character, Long> getProductsStartingWithLetter() {
        lock.lock();
        try {
            return warehouse.getProductsStartingWithLetter();
        } finally {
            lock.unlock();
        }
    }

    public Stream<Product> getProductsByMaxRatingAndSortedByDate() {
        lock.lock();
        try {
            return warehouse.getProductsByMaxRatingAndSortedByDate();
        } finally {
            lock.unlock();
        }
    }
}
