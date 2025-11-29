package com.justlogistics.service;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.justlogistics.entity.TipoTransporte;
import com.justlogistics.repository.TipoTransporteRepository;

@Service
public class TipoTransporteService {
	
	 @Autowired
	 private TipoTransporteRepository tipoRepository;
	
		public TipoTransporte guardar(TipoTransporte request) {

			return tipoRepository.save(request);
		}
	    
    public List<TipoTransporte> listar(){
		return tipoRepository.findAll();
	}
	
	public TipoTransporte get(Integer id) {
		return tipoRepository.findById(id).orElseThrow(() -> new RuntimeException("TipoTransporte no encontrado con ID: " + id));
	}
	
	public TipoTransporte actualizar(Integer id,TipoTransporte request) {
	   TipoTransporte TipoTransporte = tipoRepository.findById(id).orElseThrow(() -> new RuntimeException("TipoTransporte no encontrado con ID: " + id));    
      
	   TipoTransporte.setDescripcion(request.getDescripcion());
	   return tipoRepository.save(TipoTransporte);
	}
	
	public boolean eliminar(Integer id) {
        if (tipoRepository.existsById(id)) {
        	tipoRepository.deleteById(id);
            return true;
        }
        return false;
    }

}


