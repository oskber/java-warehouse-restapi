# Open Terminal

1. **Pull the Docker Image**:
   ```sh
   docker pull oskber/warehouse:latest
   
2. **Run the Docker Container**:
   ```sh
   docker run -p 8080:8080 -it oskber/warehouse:latest
   
3. **Open the Browser**:
   ```sh
   http://localhost:8080/java-warehouse-restapi-1.0-SNAPSHOT/api/
   ```
   
4. **API Documentation**:
   ```sh
    http://localhost:8080/java-warehouse-restapi-1.0-SNAPSHOT/api/products
    http://localhost:8080/java-warehouse-restapi-1.0-SNAPSHOT/api/products/1
    http://localhost:8080/java-warehouse-restapi-1.0-SNAPSHOT/api/products/categories/OTHER
    http://localhost:8080/java-warehouse-restapi-1.0-SNAPSHOT/api/products?page=1&size=5
   
    ```