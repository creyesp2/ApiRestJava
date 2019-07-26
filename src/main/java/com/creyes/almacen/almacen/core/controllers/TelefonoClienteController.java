package com.creyes.almacen.almacen.core.controllers;

import com.creyes.almacen.almacen.core.models.entity.TelefonoCliente;
import com.creyes.almacen.almacen.core.models.services.ITelefonoClienteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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
@Api(tags="telefonoClientes")
public class TelefonoClienteController {

    private final ITelefonoClienteService iTelefonoClienteService;


    public TelefonoClienteController(ITelefonoClienteService iTelefonoClienteService){
        this.iTelefonoClienteService=iTelefonoClienteService;
    }
    @ApiOperation(value = "Listar telefonoClientes", notes = "Servicio para listar telefonoClientes")
    @ApiResponses(value = {@ApiResponse(code = 201, message = "Lista de telefonoProvedores")})
    @GetMapping("/telefonoClientes")
    public List<TelefonoCliente> index(){
        return this.iTelefonoClienteService.findAll();

    }
    //paginacion de categorias empoint
    @ApiOperation(value = "Paginar listado de telefonoClientes", notes = "Servicio para listar los telefonoClientes paginadas")
    @ApiResponses(value= {@ApiResponse(code=201, message = "telefonoClientes paginadas")})
    @GetMapping("/telefonoClientes/page/{page}")
    public Page<TelefonoCliente> index (@PathVariable Integer page){

        Pageable pageable = PageRequest.of(page,5);
        return iTelefonoClienteService.findAll(pageable);
    }
    @ApiOperation(value = "Buscar telefonoClientes por ID", notes = "Servicio para buscar telefonoClientes por codigo")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "telefonoClientes encontrada"),
            @ApiResponse(code = 404 , message = "telefonoClientes no encontrada")})
    @GetMapping("/telefonoClientes/{id}")
    public ResponseEntity<?> show(@PathVariable Long id){
        Map<String,Object> response =new HashMap<>();
        TelefonoCliente telefonoEncotrado =this.iTelefonoClienteService.findById(id);
        if(telefonoEncotrado==null){
            response.put("mensaje","telefonoClientes no encontrada");
            return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND);

        }
        return new ResponseEntity<TelefonoCliente>(telefonoEncotrado,HttpStatus.OK);
    }
    @ApiOperation(value = "Crear telefonoClientes", notes = "Servicio para crear telefonoClientes")
    @ApiResponses( value = {@ApiResponse(code = 400, message = "Existen errores en el ingreso de datos"),
            @ApiResponse(code = 500, message = "Error en el servidor al crear telefonoClientes"),
            @ApiResponse(code = 201, message = "telefonoClientes creada exitosamente!!!!")})
    @PostMapping("/telefonoClientes")
    public ResponseEntity<?> create(@Valid @RequestBody TelefonoCliente elemento, BindingResult result){

        TelefonoCliente nuevo=null;
        Map<String,Object>response =new HashMap<>();
        if (result.hasErrors()){
            List<String> errors =result.getFieldErrors()
                    .stream()
                    .map(err->"El campo '" + err.getField()+"'"+err.getDefaultMessage())
                    .collect(Collectors.toList());
            response.put("errors",errors);
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.BAD_REQUEST);

        }

        if(nuevo==null)
            try{
                nuevo=this.iTelefonoClienteService.save(elemento);


            }catch (DataAccessException e){
                response.put("mensaje","error al realizar el insert en la base de datos");
                response.put("error",e.getMessage().concat(":".concat(e.getMostSpecificCause().getMessage())));
                return new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);

            }

        response.put("mensaje","telefonoClientes ha sido creado com exito !!!");
        response.put("telefonoClientes",nuevo);
        return new ResponseEntity<Map<String,Object>>(response,HttpStatus.CREATED);

        //   return null;
    }
    public ResponseEntity<?> update(@Valid @RequestBody TelefonoCliente telefonoCliente,BindingResult result, @PathVariable Long id){
        Map<String,Object> response=new HashMap<>();
        TelefonoCliente update=this.iTelefonoClienteService.findById(id);
        TelefonoCliente telefonoUpdate=null;
        if (result.hasErrors()){
            List<String> errors =result.getFieldErrors()
                    .stream()
                    .map(err->"El campo '" + err.getField()+"'"+err.getDefaultMessage())
                    .collect(Collectors.toList());
            response.put("errors",errors);
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.BAD_REQUEST);

        }

        if(update==null){
            response.put("mensaje","Error: no se puede editar telefonoClientes ID"
                    .concat(id.toString())
                    .concat(" ")
                    .concat("no existe en la base de datos"));
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
        }
        try{

            update.setNumero(telefonoCliente.getNumero());
            update.setDescripcion(telefonoCliente.getDescripcion());

            telefonoUpdate= this.iTelefonoClienteService.save(update);

        }catch ( DataAccessException e){
            response.put("mensaje","error al actualizar los  datos");
            response.put("error,e",e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
            return  new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje","telefonoClientes");
        response.put("telefonoClientes",telefonoUpdate);
        return  new ResponseEntity<Map<String,Object>>(response,HttpStatus.CREATED);
    }


    @DeleteMapping("telefonoClientes/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        Map<String,Object> response =new HashMap<>();

        try{
            this.iTelefonoClienteService.delete(id);

        }catch (DataAccessException e){

            response.put("mensaje","error al eliminar telefonoClientes de la base datos");
            response.put("error",e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje","telefonoClientes fue eliminada con exito");
        return new ResponseEntity<Map<String,Object>>(response,HttpStatus.OK);


    }
}
