package com.creyes.almacen.almacen.core.controllers;
import com.creyes.almacen.almacen.core.models.entity.TipoEnpaque;
import com.creyes.almacen.almacen.core.models.services.TipoEmpaqueServiceImpl;
import com.creyes.almacen.almacen.core.models.services.ITipoEmpaqueService;
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
public class TipoEmpaqueRestController {
    private final ITipoEmpaqueService tipoEmpaqueService;

    public TipoEmpaqueRestController(ITipoEmpaqueService  tipoEmpaqueService){
        this.tipoEmpaqueService =tipoEmpaqueService;
    }

    @GetMapping("/tipoEmpaques")
    public List<TipoEnpaque> index(){
        return this.tipoEmpaqueService.finAll();

    }

    //paginacion de categorias empoint
    @GetMapping("/tipoEmpaques/page/{page}")
    public Page<TipoEnpaque> index (@PathVariable Integer page){

        Pageable pageable = PageRequest.of(page,5);
        return tipoEmpaqueService.finAll(pageable);
    }

    @GetMapping("/tipoEmpaques/{id}")
    public ResponseEntity<?> show(@PathVariable Long id){
        Map<String,Object> response =new HashMap<>();
        TipoEnpaque tipoEmpaqueEncontrado =this.tipoEmpaqueService.findById(id);
        if(tipoEmpaqueEncontrado==null){
            response.put("mensaje","tipo empaque no encontrado");
            return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND);

        }
        return new ResponseEntity<TipoEnpaque>(tipoEmpaqueEncontrado,HttpStatus.OK);
    }

    @PostMapping("/tipoEmpaques")
    public ResponseEntity<?> create(@Valid @RequestBody TipoEnpaque elemento, BindingResult result){

        TipoEnpaque nuevo=null;
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
            nuevo=this.tipoEmpaqueService.save(elemento);


        }catch (DataAccessException e){
            response.put("mensaje","error al realizar el insert en la base de datos");
            response.put("error",e.getMessage().concat(":".concat(e.getMostSpecificCause().getMessage())));
            return new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);

        }

        response.put("mensaje","El tipo Empauqe ha sido creado con exito !!!");
        response.put("tipoEmpaques",nuevo);
        return new ResponseEntity<Map<String,Object>>(response,HttpStatus.CREATED);

        //   return null;
    }
}

