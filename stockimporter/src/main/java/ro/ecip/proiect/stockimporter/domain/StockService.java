package ro.ecip.proiect.stockimporter.domain;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ro.ecip.proiect.stockimporter.infrastructure.entity.Product;
import ro.ecip.proiect.stockimporter.infrastructure.repository.ProductRepository;

@Service
@Slf4j
public class StockService {

    @Autowired
    ProductRepository productRepository;

    public void addProductInStock(String name,Long price, Long quantity){
        Product product = productRepository.findProductByName(name);
        if(product != null){
            log.info("produs existent {}, se va updata pretul si cantitatea",name);
            product.setPrice(price);
            product.setQuantity(product.getQuantity() + quantity);
            productRepository.save(product);
        }else{
            log.info("produs inexistent {}, se va se va salva un nou produs",name);
            Product newProduct = new Product();
            newProduct.setQuantity(quantity);
            newProduct.setPrice(price);
            newProduct.setName(name);
            productRepository.save(newProduct);

            RestTemplate rest = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            String server = "http://172.21.80.41:8080/";
            headers.add("Content-Type", "application/json");
            headers.add("Accept", "*/*");
            String json = "{\"productName\": \"" + name + "\", \"price\": \""+price+"\"}";

            HttpEntity<String> requestEntity = new HttpEntity<String>(json, headers);
            log.info("trimitere email catre "+server+ "cu parametrii "+json);
            try{
                ResponseEntity<String> responseEntityEmail = rest.exchange(server + "email", HttpMethod.POST, requestEntity, String.class);
            }catch(Exception e){
                log.warn("Email wasn't send, {}", e.getMessage());
            }
        }
    }

}
