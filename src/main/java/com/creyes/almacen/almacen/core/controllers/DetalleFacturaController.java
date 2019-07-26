package com.creyes.almacen.almacen.core.controllers;

import com.creyes.almacen.almacen.core.models.entity.DetalleFactura;
import com.creyes.almacen.almacen.core.models.services.IDetalleFacturaService;
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
@Api(tags="detalleFacturas")
public class DetalleFacturaController {
    private final IDetalleFacturaService detalleFacturaService;


    public DetalleFacturaController(IDetalleFacturaService detalleFacturaService){
        this.detalleFacturaService=detalleFacturaService;
    }
    @ApiOperation(value = "Listar detalleFacturas", notes = "Servicio para listar las detallesFacturas")
    @ApiResponses(value = {@ApiResponse(code = 201, message = "Lista detalleFacturas")})
    @GetMapping("/detalleFacturas")
    public List<DetalleFactura> index(){
        return this.detalleFacturaService.findAll();

    }
    //paginacion de categorias empoint
    @ApiOperation(value = "Paginar listado detalleFacturas", notes = "Servicio para listar detalleFacturas paginadas")
    @ApiResponses(value= {@ApiResponse(code=201, message = "detalleFacturas paginadas")})
    @GetMapping("/detalleFacturas/page/{page}")
    public Page<DetalleFactura> index (@PathVariable Integer page){

        Pageable pageable = PageRequest.of(page,5);
        return detalleFacturaService.findAll(pageable);
    }
    @ApiOperation(value = "Buscar detalleFactura por ID", notes = "Servicio para buscar detalleFactura por codigo")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "detalleFactura encontrado"),
            @ApiResponse(code = 404 , message = "detalleFactura no encontrado")})
    @GetMapping("/detalleFacturas/{id}")
    public ResponseEntity<?> show(@PathVariable Long id){
        Map<String,Object> response =new HashMap<>();
        DetalleFactura detalleFacturaEncontrada =this.detalleFacturaService.findById(id);
        if(detalleFacturaEncontrada==null){
            response.put("mensaje","detalleFactura no encontrada");
            return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND);

        }
        return new ResponseEntity<DetalleFactura>(detalleFacturaEncontrada,HttpStatus.OK);
    }
    @ApiOperation(value = "Crear detalleFactura", notes = "Servicio para crea un detallefactura")
    @ApiResponses( value = {@ApiResponse(code = 400, message = "Existen errores en el ingreso de datos"),
            @ApiResponse(code = 500, message = "Error en el servidor al crear categoria"),
            @ApiResponse(code = 201, message = "detalleFactura creada exitosamente!!!!")})
    @PostMapping("/detalleFacturas")
    public ResponseEntity<?> create(@Valid @RequestBody DetalleFactura elemento, BindingResult result){

        DetalleFactura nuevo=null;
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
                nuevo=this.detalleFacturaService.save(elemento);


            }catch (DataAccessException e){
                response.put("mensaje","error al realizar el insert en la base de datos");
                response.put("error",e.getMessage().concat(":".concat(e.getMostSpecificCause().getMessage())));
                return new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);

            }

        response.put("mensaje","detalleFactura ha sido creada com exito !!!");
        response.put("detalleFacturas",nuevo);
        return new ResponseEntity<Map<String,Object>>(response,HttpStatus.CREATED);

        //   return null;
    }
    public ResponseEntity<?> update(@Valid @RequestBody DetalleFactura detalleFactura,BindingResult result, @PathVariable Long id){
        Map<String,Object> response=new HashMap<>();
        DetalleFactura update=this.detalleFacturaService.findById(id);
        DetalleFactura detalleFacturaUpdate=null;
        if (result.hasErrors()){
            List<String> errors =result.getFieldErrors()
                    .stream()
                    .map(err->"El campo '" + err.getField()+"'"+err.getDefaultMessage())
                    .collect(Collectors.toList());
            response.put("errors",errors);
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.BAD_REQUEST);

        }

        if(update==null){
            response.put("mensaje","Error: no se puede editar detalleFactura ID"
                    .concat(id.toString())
                    .concat(" ")
                    .concat("no existe en la base de datos"));
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
        }
        try{
            update.setFacturaDetalle(detalleFactura.getFacturaDetalle());
            update.setProductoFactura(detalleFactura.getProductoFactura());
            update.setCantidad(detalleFactura.getCantidad());
            update.setPrecio(detalleFactura.getPrecio());
            update.setDescuento(detalleFactura.getDescuento());
            detalleFacturaUpdate= this.detalleFacturaService.save(update);

        }catch ( DataAccessException e){
            response.put("mensaje","error al actualizar los  datos");
            response.put("error,e",e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
            return  new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje","la categoria");
        response.put("categoria",detalleFacturaUpdate);
        return  new ResponseEntity<Map<String,Object>>(response,HttpStatus.CREATED);
    }


    @DeleteMapping("detalleFacturas/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        Map<String,Object> response =new HashMap<>();

        try{
            this.detalleFacturaService.delete(id);

        }catch (DataAccessException e){

            response.put("mensaje","error al eliminar la categoria de la base datos");
            response.put("error",e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje","detalleFactura fue eliminada con exito");
        return new ResponseEntity<Map<String,Object>>(response,HttpStatus.OK);


    }

}
