package com.creyes.almacen.almacen.core.controllers;

import com.creyes.almacen.almacen.core.models.dao.IFacturaDao;
import com.creyes.almacen.almacen.core.models.entity.Factura;
import com.creyes.almacen.almacen.core.models.services.IFacturaService;
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
@Api(tags="facturas")
public class FacturaController {
    private final IFacturaService facturaService;
    public FacturaController(IFacturaService facturaService){
        this.facturaService=facturaService;
    }
    @ApiOperation(value = "Listar facturas", notes = "Servicio para listar las facturas")
    @ApiResponses(value = {@ApiResponse(code = 201, message = "Lista de facturas")})
    @GetMapping("/facturas")
    public List<Factura> index(){
        return this.facturaService.findAll();

    }
    //paginacion de categorias empoint
    @ApiOperation(value = "Paginar listado de facturas", notes = "Servicio para listar las facturas paginadas")
    @ApiResponses(value= {@ApiResponse(code=201, message = "Categorias paginadas")})
    @GetMapping("/facturas/page/{page}")
    public Page<Factura> index (@PathVariable Integer page){

        Pageable pageable = PageRequest.of(page,5);
        return facturaService.findAll(pageable);
    }
    @ApiOperation(value = "Buscar facturas por ID", notes = "Servicio para buscar factura por codigo")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "factura encontrada"),
            @ApiResponse(code = 404 , message = "factura no encontrada")})
    @GetMapping("/facturas/{id}")
    public ResponseEntity<?> show(@PathVariable Long id){
        Map<String,Object> response =new HashMap<>();
        Factura facturaEncontrada =this.facturaService.findById(id);
        if(facturaEncontrada==null){
            response.put("mensaje","factura no encontrada");
            return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND);

        }
        return new ResponseEntity<Factura>(facturaEncontrada,HttpStatus.OK);
    }
    @ApiOperation(value = "Crear factura", notes = "Servicio para crea una factura")
    @ApiResponses( value = {@ApiResponse(code = 400, message = "Existen errores en el ingreso de datos"),
            @ApiResponse(code = 500, message = "Error en el servidor al crear categoria"),
            @ApiResponse(code = 201, message = "factura creada exitosamente!!!!")})
    @PostMapping("/facturas")
    public ResponseEntity<?> create(@Valid @RequestBody Factura elemento, BindingResult result){

        Factura nuevo=null;
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
                nuevo=this.facturaService.save(elemento);


            }catch (DataAccessException e){
                response.put("mensaje","error al realizar el insert en la base de datos");
                response.put("error",e.getMessage().concat(":".concat(e.getMostSpecificCause().getMessage())));
                return new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);

            }

        response.put("mensaje","La factura ha sido creada com exito !!!");
        response.put("facturas",nuevo);
        return new ResponseEntity<Map<String,Object>>(response,HttpStatus.CREATED);

        //   return null;
    }
    public ResponseEntity<?> update(@Valid @RequestBody Factura factura,BindingResult result, @PathVariable Long id){
        Map<String,Object> response=new HashMap<>();
        Factura update=this.facturaService.findById(id);
        Factura facturaUpdate=null;
        if (result.hasErrors()){
            List<String> errors =result.getFieldErrors()
                    .stream()
                    .map(err->"El campo '" + err.getField()+"'"+err.getDefaultMessage())
                    .collect(Collectors.toList());
            response.put("errors",errors);
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.BAD_REQUEST);

        }

        if(update==null){
            response.put("mensaje","Error: no se puede editar la factura ID"
                    .concat(id.toString())
                    .concat(" ")
                    .concat("no existe en la base de datos"));
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
        }
        try{
            update.setNitFactura(factura.getNitFactura());
            update.setFecha(factura.getFecha());
            update.setTotal(factura.getTotal());
            update.setFacturas(factura.getFacturas());
            facturaUpdate= this.facturaService.save(update);

        }catch ( DataAccessException e){
            response.put("mensaje","error al actualizar los  datos");
            response.put("error,e",e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
            return  new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje","la factura");
        response.put("factura",facturaUpdate);
        return  new ResponseEntity<Map<String,Object>>(response,HttpStatus.CREATED);
    }


    @DeleteMapping("facturas/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        Map<String,Object> response =new HashMap<>();

        try{
            this.facturaService.delete(id);

        }catch (DataAccessException e){

            response.put("mensaje","error al eliminar la categoria de la base datos");
            response.put("error",e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje","la factura fue eliminada con exito");
        return new ResponseEntity<Map<String,Object>>(response,HttpStatus.OK);


    }

}
