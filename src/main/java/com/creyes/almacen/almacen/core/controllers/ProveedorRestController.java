package com.creyes.almacen.almacen.core.controllers;

import com.creyes.almacen.almacen.core.models.entity.Proveedor;
import com.creyes.almacen.almacen.core.models.services.IProveedorService;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
public class ProveedorRestController {
    private final IProveedorService proveedorService;
    public ProveedorRestController(IProveedorService proveedorService){
        this.proveedorService=proveedorService;
    }

    @GetMapping("/proveedores")
    public List<Proveedor> index(){
        return this.proveedorService.finAll();

    }

    //paginacion de categorias empoint
    @GetMapping("/proveedores/page/{page}")
    public Page<Proveedor> index (@PathVariable Integer page){

        Pageable pageable = PageRequest.of(page,5);
        return proveedorService.finAll(pageable);
    }

    @GetMapping("/proveedores/{id}")
    public ResponseEntity<?> show(@PathVariable Long id){
        Map<String,Object> response =new HashMap<>();
        Proveedor proveedorEncontrado =this.proveedorService.findById(id);
        if(proveedorEncontrado==null){
            response.put("mensaje","provedor no encontrado");
            return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND);

        }
        return new ResponseEntity<Proveedor>(proveedorEncontrado,HttpStatus.OK);
    }

    @PostMapping("/proveedores")
    public ResponseEntity<?> create(@Valid @RequestBody Proveedor elemento, BindingResult result){

        Proveedor nuevo=null;
        Map<String,Object>response =new HashMap<>();
        if (result.hasErrors()){
            List<String> errors =result.getFieldErrors()
                    .stream()
                    .map(err->"El campo '" + err.getField()+"'"+err.getDefaultMessage())
                    .collect(Collectors.toList());
            response.put("errors",errors);
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.BAD_REQUEST);

        }
        try{
            nuevo=this.proveedorService.save(elemento);


        }catch (DataAccessException e){
            response.put("mensaje","error al realizar el insert en la base de datos");
            response.put("error",e.getMessage().concat(":".concat(e.getMostSpecificCause().getMessage())));
            return new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);

        }

        response.put("mensaje","EL Proveedor ha sido creado con exito !!!");
        response.put("proveedores",nuevo);
        return new ResponseEntity<Map<String,Object>>(response,HttpStatus.CREATED);

        //   return null;
    }
}
