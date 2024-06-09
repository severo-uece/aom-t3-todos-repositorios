package br.uece.ees.aom.aula05.cartms.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController()
@RequestMapping("/fake")
public class FakeController {

    @GetMapping("/500")
    public ResponseEntity<String> error500() throws Exception {
        return new ResponseEntity<String>("Erro genérico - Erro Interno do Servidor", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    
    @GetMapping("/400")
    public ResponseEntity<String> error400() throws Exception {
        return new ResponseEntity<String>("Erro genérico - Requisição Incorreta", HttpStatus.BAD_REQUEST);
    }
    
    @GetMapping("/401")
    public ResponseEntity<String> error401() throws Exception {
        return new ResponseEntity<String>("Erro genérico - Não Autorizado", HttpStatus.UNAUTHORIZED);
    }
    
    @GetMapping("/403")
    public ResponseEntity<String> error403() throws Exception {
        return new ResponseEntity<String>("Erro genérico - Acesso Negago", HttpStatus.FORBIDDEN);
    }
    
    @GetMapping("/408")
    public ResponseEntity<String> error408() throws Exception {
        return new ResponseEntity<String>("Erro genérico - Tempo Limite Excedido", HttpStatus.GATEWAY_TIMEOUT);
    }
    
    @GetMapping("/200")
    public ResponseEntity<String> success() throws Exception {
        return new ResponseEntity<String>("Requisição genérica - Sucesso", HttpStatus.OK);
    }
    
}
