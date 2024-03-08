package com.eviden.migration.service;

import com.eviden.migration.model.DrupalProductoCsv;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class DrupalProductoServiceCsv {
    private static int contadorImagenesPath = 0;
    public List<DrupalProductoCsv> importarProductosDrupalDesdeCsv() {
        List<DrupalProductoCsv> productos = new ArrayList<>();

        try {
            log.info("Drupal: Lectura del CSV Productos...");
            //Lectura del fichero csv
            CSVReader csvReader = new CSVReader(new FileReader("src/main/resources/exportar-productos-100.csv"));
            String[] linea;
            //salto la primera linea
            csvReader.readNext();
            while ((linea = csvReader.readNext()) != null) {
                DrupalProductoCsv producto = mapToProductoDrupalCsv(linea);
                productos.add(producto);
            }

        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }
        log.info("Total de productos a insertar {}", productos.size());
        log.info("Total imagenes path a insertar {}", contadorImagenesPath);
        return productos;
    }

    private  DrupalProductoCsv mapToProductoDrupalCsv(String[] linea) {
        log.info("Drupal: obtenido producto {}", linea[0]);
        //separar la ruta de images a un array
        String[] imagenesArray = linea[5].split("\\s*,\\s*");
        //contador del listado de imagenes path
        contadorImagenesPath += imagenesArray.length;
        //separara la cadena de categoria a un array
        String[] categorias = linea[18].split("\\s*,\\s*");
        //devuelve la creacion del nuevo objeto
        return DrupalProductoCsv.builder()
                    .sku(linea[0])
                    .title(linea[1])
                    .path(linea[2])
                    .descripcion(linea[3])
                    .estanteria(linea[4])
                    .imagesPath(List.of(imagenesArray))
                    .cost(linea[6])
                    .precioVentaSinIva(linea[7])
                    .oldPrice(linea[8])
                    .edad(linea[9])
                    .editorial(linea[10])
                    .duracion(linea[11])
                    .dificultad(linea[12])
                    .oferta(linea[13])
                    .nivel(linea[14])
                    .publicado(linea[15])
                    .jugadores(linea[16])
                    .umbral(linea[17])
                    .categorias(List.of(categorias))
                    .tipo(linea[19])
                    .build();
    }
}
