package com.diselec.utility;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Util {

    public static ArrayList<String> listaCategorias(){
        ArrayList<String> lista = new ArrayList<>();
        lista.add("Displays");
        lista.add("Conectores");
        lista.add("Instrumentos");
        lista.add("Arduino");
        lista.add("Microcontroladores");
        lista.add("Programadores");
        lista.add("Motores");
        lista.add("Sensores");
        lista.add("Sistemas OIT");
        lista.add("Pololu");
        lista.add("Montaje superficial");
        lista.add("Intel");
        lista.add("Impresoras terminas");
        return lista;
    }

    private static final String uploadFolder = ".//src//main//resources//static//img//productos//";

    //GUARDAR IMAGEN OPCION 2
    public static String saveFile(MultipartFile file) throws IOException{
        if (!file.isEmpty()){
            byte[] bytes = file.getBytes();
            String nombreFinal = file.getOriginalFilename();
            nombreFinal = nombreFinal.replace(" ","-");
            nombreFinal = randomAlphaNumeric(8)+nombreFinal;
            Path path = Paths.get(uploadFolder + nombreFinal);
            Files.write(path,bytes);
            return nombreFinal;
        }
        return null;
    }


/*    //GUARDAR IMAGEN
    public static String guardarImagen(MultipartFile multipartFile, HttpServletRequest request){
        String nombreOriginal = multipartFile.getOriginalFilename();
        nombreOriginal = nombreOriginal.replace(" ","-");
        String nombreFinal = randomAlphaNumeric(8)+nombreOriginal;
        String rutaFinal = request.getServletContext().getRealPath("");

        try{
            File fileImage = new File("src/main/resources/static/img/productos/" + nombreFinal);
            System.out.println("El nombre del archivo creado es: " + fileImage.toString());
            System.out.println("el absolute es: " + fileImage.getAbsolutePath());
            multipartFile.transferTo(fileImage);
            return nombreFinal;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }*/


    public static String randomAlphaNumeric(int count){
        String CARACTERES = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder builder = new StringBuilder();
        while (count-- != 0){
            int character = (int) (Math.random() * CARACTERES.length());
            builder.append(CARACTERES.charAt(character));
        }
        return builder.toString();
    }


}
