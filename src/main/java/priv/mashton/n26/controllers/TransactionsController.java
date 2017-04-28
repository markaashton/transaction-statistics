package priv.mashton.n26.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import priv.mashton.n26.dtos.TransactionRequest;
import priv.mashton.n26.dtos.TransactionRequestValidator;
import priv.mashton.n26.service.TransactionService;

@Controller
public class TransactionsController {

    @Autowired
    TransactionService transactionService;

    @RequestMapping(value = "/transactions", method = RequestMethod.POST, produces="application/json")
    @ResponseBody
    public ResponseEntity createTransaction(@RequestBody TransactionRequest request) {
        try {
            TransactionRequestValidator.validate(request);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().build();
        }

        transactionService.addTransaction(request.getAmount(), request.getTimestamp());
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value = "/transactions", method = RequestMethod.DELETE, produces="application/json")
    public ResponseEntity deleteTransactions() {
        transactionService.clearTransactions();
        return ResponseEntity.ok().build();
    }
}
