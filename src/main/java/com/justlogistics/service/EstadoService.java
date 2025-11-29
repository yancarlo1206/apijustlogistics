package com.justlogistics.service;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.justlogistics.entity.Estado;
import com.justlogistics.repository.EstadoRepository;

@Service
public class EstadoService {
	
	 @Autowired
	 private EstadoRepository estadoRepository;
	
		public Estado guardar(Estado request) {

			return estadoRepository.save(request);
		}
	    
    public List<Estado> listar(){
		return estadoRepository.findAll();
	}
	
	public Estado get(Integer id) {
		return estadoRepository.findById(id).orElseThrow(() -> new RuntimeException("Estado no encontrado con ID: " + id));
	}
	
	public Estado actualizar(Integer id,Estado request) {
	   Estado Estado = estadoRepository.findById(id).orElseThrow(() -> new RuntimeException("Estado no encontrado con ID: " + id));    
      
	   Estado.setDescripcion(request.getDescripcion());
	   return estadoRepository.save(Estado);
	}
	
	public boolean eliminar(Integer id) {
        if (estadoRepository.existsById(id)) {
        	estadoRepository.deleteById(id);
            return true;
        }
        return false;
    }

}




