package com.pife.juego.Idiomas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import java.util.HashMap;
import java.util.Map;

public class Idiomas {

    private static Map<String, String> textos = new HashMap<String, String>();

    public static void cargar(String idioma) {
        textos.clear();

        String archivo;

        if (idioma.equals("EN")) {
            archivo = "Idiomas/lang_en.properties";
        } else {
            archivo = "Idiomas/lang_es.properties";
        }

        FileHandle file = Gdx.files.internal(archivo);

        String contenido = file.readString("UTF-8");
        String[] lineas = contenido.split("\n");

        for (int i = 0; i < lineas.length; i++) {
            String linea = lineas[i].trim();

            if (linea.length() == 0) continue;
            if (linea.startsWith("#")) continue;

            int idx = linea.indexOf('=');
            if (idx == -1) continue;

            String clave = linea.substring(0, idx).trim();
            String valor = linea.substring(idx + 1).trim();

            textos.put(clave, valor);
        }
    }

    public static String t(String clave) {
        String valor = textos.get(clave);

        if (valor == null) {
            return clave;
        }

        return valor;
    }
}

