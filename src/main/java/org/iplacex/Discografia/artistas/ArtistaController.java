
package org.iplacex.Discografia.artistas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin


public class ArtistaController {

    @Autowired
    private IArtistaRepository artistaRepository;

    // POST (Insertar valores)

    @PostMapping(value = "/artista", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Artista> HandleInsertArtistaRequest(@RequestBody Artista artista) {
        Artista nuevoArtista = artistaRepository.save(artista);
        return new ResponseEntity<>(nuevoArtista, HttpStatus.CREATED); 
    }

    // GET (Listar todos los artistas)

    @GetMapping(value = "/artistas", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity <List<Artista>> HandleGetArtistasRequest () {
        List<Artista> artistas = artistaRepository.findAll();
        return new ResponseEntity<>(artistas, HttpStatus.OK);
    }
    

    // GET (Listar un solo artista)

    @GetMapping(value = "/artista/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity <Artista> HandleGetArtistaRequest(@PathVariable("id") String id) {
        Optional<Artista> temp = artistaRepository.findById(id);
        if (temp.isPresent()) {
            return new ResponseEntity<>(temp.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // PUT (Actualizar a los artistas)

    @PutMapping(value = "/artista/{id}", consumes =  MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Artista> HandleUpdateArtistaRequest(@PathVariable("id") String id, @RequestBody Artista artista) {
        if (!artistaRepository.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        artista.id = id;
        Artista actualizado = artistaRepository.save(artista);
        return new ResponseEntity<>(actualizado, HttpStatus.OK);
    }

    // DELETE (eliminar artista)

    @DeleteMapping(value = "/artista/{id}")
    public ResponseEntity<Void> HandleDeleteArtistaRequest(@PathVariable("id") String id) {
        if (!artistaRepository.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        artistaRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
