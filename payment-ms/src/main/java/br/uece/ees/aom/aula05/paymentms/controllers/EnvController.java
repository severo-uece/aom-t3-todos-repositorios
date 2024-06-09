package br.uece.ees.aom.aula05.paymentms.controllers;

import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController()
@RequestMapping("/env")
public class EnvController {

    private Environment env;

    public EnvController(Environment env){
        this.env = env;
    }
    
    @GetMapping("/view")
    public String getMethodName(@RequestParam(name = "name", required = false) String name) {
        if (name == null) {
            name = "eureka.instance.instance-id";
        }
        return name + " = " + env.getProperty(name);
    }
    

}
