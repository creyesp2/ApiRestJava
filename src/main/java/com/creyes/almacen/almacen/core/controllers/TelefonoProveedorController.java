package com.creyes.almacen.almacen.core.controllers;


import com.creyes.almacen.almacen.core.models.entity.TelefonoProveedor;
import com.creyes.almacen.almacen.core.models.services.ITelefonoProveedorService;
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
@Api(tags="telefonoProvedores")
public class TelefonoProveedorController {

    private final ITelefonoProveedorService telefonoProveedorService;


    public TelefonoProveedorController(ITelefonoProveedorService telefonoProveedorService){
        this.telefonoProveedorService=telefonoProveedorService;
    }
    @ApiOperation(value = "Listar telefonoProvedores", notes = "Servicio para listar las telefonoProvedores")
    @ApiResponses(value = {@ApiResponse(code = 201, message = "Lista de telefonoProvedores")})
    @GetMapping("/telefonoProvedores")
    public List<TelefonoProveedor> index(){
        return this.telefonoProveedorService.findAll();

    }
    //paginacion de categorias empoint
    @ApiOperation(value = "Paginar listado de telefonoProvedores", notes = "Servicio para listar los telefonoProvedores paginadas")
    @ApiResponses(value= {@ApiResponse(code=201, message = "telefonoProvedores paginadas")})
    @GetMapping("/telefonoProvedores/page/{page}")
    public Page<TelefonoProveedor> index (@PathVariable Integer page){

        Pageable pageable = PageRequest.of(page,5);
        return telefonoProveedorService.findAll(pageable);
    }
    @ApiOperation(value = "Buscar telefonoProvedores por ID", notes = "Servicio para buscar telefonoProvedores por codigo")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "telefonoProvedores encontrada"),
            @ApiResponse(code = 404 , message = "telefonoProvedoresno encontrada")})
    @GetMapping("/telefonoProvedores/{id}")
    public ResponseEntity<?> show(@PathVariable Long id){
        Map<String,Object> response =new HashMap<>();
        TelefonoProveedor telefonoEncotrado =this.telefonoProveedorService.findById(id);
        if(telefonoEncotrado==null){
            response.put("mensaje","telefonoProvedor no encontrada");
            return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND);

        }
        return new ResponseEntity<TelefonoProveedor>(telefonoEncotrado,HttpStatus.OK);
    }
    @ApiOperation(value = "Crear telefonoProvedores", notes = "Servicio para crear telefonoProvedores")
    @ApiResponses( value = {@ApiResponse(code = 400, message = "Existen errores en el ingreso de datos"),
            @ApiResponse(code = 500, message = "Error en el servidor al crear telefonoProvedores"),
            @ApiResponse(code = 201, message = "telefonoProvedores creada exitosamente!!!!")})
    @PostMapping("/telefonoProvedores")
    public ResponseEntity<?> create(@Valid @RequestBody TelefonoProveedor elemento, BindingResult result){

        TelefonoProveedor nuevo=null;
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
                nuevo=this.telefonoProveedorService.save(elemento);


            }catch (DataAccessException e){
                response.put("mensaje","error al realizar el insert en la base de datos");
                response.put("error",e.getMessage().concat(":".concat(e.getMostSpecificCause().getMessage())));
                return new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);

            }

        response.put("mensaje","telefonoProvedores ha sido creado com exito !!!");
        response.put("telefonoProvedores",nuevo);
        return new ResponseEntity<Map<String,Object>>(response,HttpStatus.CREATED);

        //   return null;
    }
    public ResponseEntity<?> update(@Valid @RequestBody TelefonoProveedor telefonoProveedor,BindingResult result, @PathVariable Long id){
        Map<String,Object> response=new HashMap<>();
        TelefonoProveedor update=this.telefonoProveedorService.findById(id);
        TelefonoProveedor telefonoUpdate=null;
        if (result.hasErrors()){
            List<String> errors =result.getFieldErrors()
                    .stream()
                    .map(err->"El campo '" + err.getField()+"'"+err.getDefaultMessage())
                    .collect(Collectors.toList());
            response.put("errors",errors);
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.BAD_REQUEST);

        }

        if(update==null){
            response.put("mensaje","Error: no se puede editar telefonoProvedores ID"
                    .concat(id.toString())
                    .concat(" ")
                    .concat("no existe en la base de datos"));
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
        }
        try{
            update.setNumero(telefonoProveedor.getNumero());
            update.setDescripcion(telefonoProveedor.getDescripcion());
            telefonoUpdate= this.telefonoProveedorService.save(update);

        }catch ( DataAccessException e){
            response.put("mensaje","error al actualizar los  datos");
            response.put("error,e",e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
            return  new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje","telefonoProvedores");
        response.put("telefonoProvedores",telefonoUpdate);
        return  new ResponseEntity<Map<String,Object>>(response,HttpStatus.CREATED);
    }


    @DeleteMapping("telefonoProvedores/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        Map<String,Object> response =new HashMap<>();

        try{
            this.telefonoProveedorService.delete(id);

        }catch (DataAccessException e){

            response.put("mensaje","error al eliminar telefonoProvedores de la base datos");
            response.put("error",e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje","telefonoProvedores fue eliminada con exito");
        return new ResponseEntity<Map<String,Object>>(response,HttpStatus.OK);


    }
}
