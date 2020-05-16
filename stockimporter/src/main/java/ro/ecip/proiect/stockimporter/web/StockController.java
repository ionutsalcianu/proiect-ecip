package ro.ecip.proiect.stockimporter.web;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ro.ecip.proiect.stockimporter.domain.StockService;


@Slf4j
@RestController
@RequestMapping("/api/v1")
@Api(tags = "v1", description = "Endpoint-uri utilizate pentru a gestiona stocurile")
public class StockController {

    @Autowired
    StockService stockService;

    @ApiOperation(value = "Adauga produs nou in stock")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Data was successfully wrote", response = void.class)})
    @PostMapping(path = "/stock-import")
    public void addProductInStock(@RequestParam("productName") String productName,
                                  @RequestParam("productPrice") Long price,
                                  @RequestParam("productQuantity") Long quantity) {
        log.info("Adaugare produs nou in stoc {}, {}, {}", productName, price,quantity);
        stockService.addProductInStock(productName,price, quantity);
    }
}
